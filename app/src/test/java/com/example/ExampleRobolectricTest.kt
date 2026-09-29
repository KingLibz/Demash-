package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.core.PerformanceEngine
import com.example.data.database.AppDatabase
import com.example.data.model.AiWorkforceData
import com.example.data.model.PoolShape
import com.example.data.model.PoolType
import com.example.data.model.SuburbIntelligenceData
import com.example.data.model.TradeQuotationEngine
import com.example.data.repository.PoolRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Demash Pools", appName)
    }

    @Test
    fun `test pool calculator logic`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val repository = PoolRepository(db.savedEstimateDao(), db.bookingRequestDao())

        // 5m x 3m Gunite Rectangular
        val estimate = repository.calculateEstimate(
            poolType = PoolType.GUNITE,
            shape = PoolShape.RECTANGULAR,
            length = 5.0,
            width = 3.0
        )

        assertEquals(15.0, estimate.surfaceAreaM2, 0.01)
        assertTrue("Estimated price should be around 6475+", estimate.priceUsd > 6000.0)
        assertTrue("Market price should be higher", estimate.marketPriceUsd > estimate.priceUsd)
        assertEquals(32, estimate.savingsPct)
    }

    @Test
    fun `test deep BOQ material breakdown and 34 to 40 percent profit margin`() {
        // Generate BOQ for 6m x 3.5m pool with 36.5% target corporate profit
        val boq = TradeQuotationEngine.generateBOQ(
            length = 6.0,
            width = 3.5,
            poolType = PoolType.GUNITE,
            shape = PoolShape.RECTANGULAR,
            targetMarginPct = 36.5
        )

        // Verify profitability: Gross Profit Margin must be between 34% and 40%
        val actualMargin = (boq.grossProfitUsd / boq.clientQuotationUsd) * 100.0
        assertTrue("Gross profit margin should be >= 34.0%", actualMargin >= 34.0)
        assertTrue("Gross profit margin should be <= 40.0%", actualMargin <= 40.0)

        // Verify competitor savings
        assertTrue("Competitor price should exceed client quote by ~47%", boq.typicalMarketCompetitorPriceUsd > boq.clientQuotationUsd)
        assertEquals(32, boq.clientSavingsPercent)

        // Verify deep itemized materials down to screw and excavator machinery
        val hasExcavator = boq.machineryMobilizations.any { it.equipmentName.contains("Excavator", ignoreCase = true) } ||
                boq.materials.any { it.name.contains("Excavator", ignoreCase = true) }
        val hasScrews = boq.materials.any { it.name.contains("Screws", ignoreCase = true) }
        val hasCement = boq.materials.any { it.name.contains("Cement", ignoreCase = true) }
        val hasRebar = boq.materials.any { it.name.contains("Rebar", ignoreCase = true) }

        assertTrue("BOQ must include heavy excavation machine hours or mobilization", hasExcavator)
        assertTrue("BOQ must include worker teams", boq.laborAllocations.isNotEmpty())
        assertTrue("BOQ must include 316 marine stainless screws", hasScrews)
        assertTrue("BOQ must include PPC 42.5R high-strength cement", hasCement)
        assertTrue("BOQ must include high-tensile Y10 rebar", hasRebar)
    }

    @Test
    fun `test wealthy suburb intelligence profiles`() {
        val suburbs = SuburbIntelligenceData.affluentSuburbs
        assertTrue("Must have multiple affluent Zimbabwe territories", suburbs.size >= 5)

        val borrowdale = SuburbIntelligenceData.getSuburb("borrowdale_brooke")
        assertNotNull("Borrowdale Brooke profile must exist", borrowdale)
        assertTrue("Should mention golf estate or granite", borrowdale!!.soilCharacteristics.contains("Granite", ignoreCase = true))

        val glenLorne = SuburbIntelligenceData.getSuburb("glen_lorne")
        assertNotNull("Glen Lorne profile must exist", glenLorne)
        assertTrue("Should identify hillside or slopes", glenLorne!!.terrainType.contains("hills", ignoreCase = true))
    }

    @Test
    fun `test AI workforce department roster`() {
        val employees = AiWorkforceData.employees
        assertEquals(4, employees.size)

        val engineer = employees.find { it.id == "tinashe_moyo" }
        assertNotNull("Eng. Tinashe Moyo must exist", engineer)
        assertTrue("Sub-second response time", engineer!!.responseTimeSec < 1.0)

        val qs = employees.find { it.id == "farai_mupfumi" }
        assertNotNull("QS Farai Mupfumi must exist", qs)
    }

    @Test
    fun `test performance engine memory metrics`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        PerformanceEngine.updateMetrics(context)
        assertTrue("Engine speed multiplier must be boosted to 20,000x", PerformanceEngine.engineSpeedMultiplier.value >= 20000)
    }
}
