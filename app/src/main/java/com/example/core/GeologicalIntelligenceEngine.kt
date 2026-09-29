package com.example.core

import com.example.data.model.PoolShape
import com.example.data.model.PoolType
import kotlin.math.roundToInt

data class SoilGeologicalIntel(
    val suburbId: String,
    val suburbName: String,
    val soilFormation: String,
    val compactionRating: String,
    val excavationDifficulty: String,
    val requiredConcreteMpa: Int,
    val recommendedShellThicknessMm: Int,
    val rebarSpec: String,
    val ballastGravelMm: Int,
    val needsHydrostaticRelief: Boolean,
    val rockHammerRequired: Boolean,
    val structuralEngineeringAdvice: String,
    val accountsSafetyGuideline: String
)

data class DealUrgencyPrediction(
    val score: Int, // 0-100
    val ratingCategory: String, // "HOT BUYER", "DIASPORA INVESTOR", "VALUE SEARCHER"
    val predictedCloseDays: Int,
    val closureHook: String,
    val depositClearanceRisk: String // "ZERO RISK", "LOW RISK", "ACCOUNTS HOLD"
)

data class AccountsClearanceReport(
    val originalQuoteUsd: Double,
    val directCostUsd: Double,
    val authorizedVoucherUsd: Double,
    val finalInvoiceUsd: Double,
    val grossProfitUsd: Double,
    val grossProfitMarginPercent: Double,
    val requiredDepositUsd: Double,
    val isApprovedByAccounts: Boolean,
    val approvalStatusText: String,
    val policyStatement: String
)

/**
 * Intelligent Zimbabwe Soil Mechanics, Geological Engineering & Accounts Prudence Engine.
 * Operates with microsecond instant math execution and zero memory bloat.
 */
object GeologicalIntelligenceEngine {

    private val suburbProfiles = mapOf(
        "borrowdale_brooke" to SoilGeologicalIntel(
            suburbId = "borrowdale_brooke",
            suburbName = "Borrowdale Brooke & Borrowdale West",
            soilFormation = "Granite Batholith with shallow ferric topsoil",
            compactionRating = "Very High (Bedrock @ 1.2m)",
            excavationDifficulty = "High · Hydraulic CAT Rock Hammer Required",
            requiredConcreteMpa = 35,
            recommendedShellThicknessMm = 180,
            rebarSpec = "Dual Grid Y12 High-Yield Rebar @ 200mm centers",
            ballastGravelMm = 150,
            needsHydrostaticRelief = false,
            rockHammerRequired = true,
            structuralEngineeringAdvice = "Direct bedrock pinning prevents all structural settlement. 35MPa gunite prevents micro-fracturing on rock contact.",
            accountsSafetyGuideline = "Include CAT rock breaker surcharge in direct cost. 60% deposit required before machinery mobilization."
        ),
        "glen_lorne" to SoilGeologicalIntel(
            suburbId = "glen_lorne",
            suburbName = "Glen Lorne & Chisipite Hills",
            soilFormation = "Steep decomposed granite slope & hill wash",
            compactionRating = "Variable (Prone to slope shear)",
            excavationDifficulty = "Severe · Terraced Stepped Foundation",
            requiredConcreteMpa = 35,
            recommendedShellThicknessMm = 200,
            rebarSpec = "Dual Grid Y12 + Cantilevered Edge Tie Beams",
            ballastGravelMm = 200,
            needsHydrostaticRelief = true,
            rockHammerRequired = true,
            structuralEngineeringAdvice = "Stepped hillside cantilever with hydrostatic relief valve prevents downslope sliding and water table pressure.",
            accountsSafetyGuideline = "Mandatory stepped retaining wall signoff. Verify cleared 60% client deposit before pouring footings."
        ),
        "highlands" to SoilGeologicalIntel(
            suburbId = "highlands",
            suburbName = "Highlands, Newlands & Greendale",
            soilFormation = "Red Ferric Expansive Clay (Black Cotton pockets)",
            compactionRating = "Moderate (High Swell/Shrink index)",
            excavationDifficulty = "Medium · Shoring & Immediate Blinding Required",
            requiredConcreteMpa = 30,
            recommendedShellThicknessMm = 180,
            rebarSpec = "Y10 Bottom Grid + Y12 Top Grid with anti-shear chairs",
            ballastGravelMm = 200,
            needsHydrostaticRelief = true,
            rockHammerRequired = false,
            structuralEngineeringAdvice = "200mm crushed 19mm blue granite ballast cushion isolates the gunite shell from expansive soil moisture cycles.",
            accountsSafetyGuideline = "Lock in 19mm blue granite aggregate delivery before excavation begins to prevent trench collapse during rain."
        ),
        "norton_lakeside" to SoilGeologicalIntel(
            suburbId = "norton_lakeside",
            suburbName = "Norton (Katanga, Galloway & Lakeside)",
            soilFormation = "Alluvial Sandy Loam with elevated water table",
            compactionRating = "Medium (High water permeability)",
            excavationDifficulty = "Medium · Hydrostatic Well-point Dewatering",
            requiredConcreteMpa = 30,
            recommendedShellThicknessMm = 160,
            rebarSpec = "Dual Grid Y10 & Y12 + Sump Relief Well",
            ballastGravelMm = 150,
            needsHydrostaticRelief = true,
            rockHammerRequired = false,
            structuralEngineeringAdvice = "Dual hydrostatic relief valves essential near Lake Chivero/Manyame basin to prevent empty pool floating during heavy rains.",
            accountsSafetyGuideline = "Local Norton base advantages: zero intercity transport surcharge. 60% deposit protects pump and PVC hydraulics."
        ),
        "bulawayo" to SoilGeologicalIntel(
            suburbId = "bulawayo",
            suburbName = "Bulawayo (Burnside, Hillside & Suburbs)",
            soilFormation = "Calcrete, Quartzite & Kalahari Sand mixture",
            compactionRating = "High Compaction · Low Rainfall Basin",
            excavationDifficulty = "Medium-High · Dense Calcrete Layers",
            requiredConcreteMpa = 30,
            recommendedShellThicknessMm = 160,
            rebarSpec = "Y10 & Y12 Deformed Rebar @ 250mm spacing",
            ballastGravelMm = 120,
            needsHydrostaticRelief = false,
            rockHammerRequired = false,
            structuralEngineeringAdvice = "High-efficiency multi-cyclone sand filter with solar pool cover for water conservation in Matabeleland conditions.",
            accountsSafetyGuideline = "Material staging logistics from Norton depot. Mobilization strictly upon cleared 60% Nostro/USD transfer."
        )
    )

    fun getGeologicalIntel(suburbKey: String): SoilGeologicalIntel {
        val key = suburbKey.lowercase().replace(" ", "_")
        return suburbProfiles[key] ?: suburbProfiles["borrowdale_brooke"]!!
    }

    fun getAllProfiles(): List<SoilGeologicalIntel> = suburbProfiles.values.toList()

    /**
     * Intelligent Deal Urgency & Buyer Intent Predictor
     */
    fun evaluateDealUrgency(
        suburbKey: String,
        poolType: PoolType,
        depositStatus: String, // "Cleared 60%", "Ready in Cash", "Diaspora Innbucks", "Pending Loan"
        poolBudgetUsd: Double
    ): DealUrgencyPrediction {
        var baseScore = 70

        if (depositStatus.contains("Cleared") || depositStatus.contains("Cash")) {
            baseScore += 22
        } else if (depositStatus.contains("Diaspora")) {
            baseScore += 16
        } else {
            baseScore -= 15
        }

        if (suburbKey.contains("borrowdale") || suburbKey.contains("glen_lorne")) {
            baseScore += 8
        }

        val finalScore = baseScore.coerceIn(35, 99)
        val category = when {
            finalScore >= 88 -> "HOT BUYER (IMMEDIATE CLOSE)"
            finalScore >= 70 -> "HIGH-VALUE DIASPORA / CASH CLIENT"
            else -> "CONSULTATION STAGE"
        }

        val days = when {
            finalScore >= 88 -> 2
            finalScore >= 75 -> 5
            else -> 14
        }

        val hook = when {
            finalScore >= 88 -> "Homeowner ready for groundbreaking. Offer immediate site pegging & 10-year warranty certificate to close today."
            depositStatus.contains("Diaspora") -> "Family member in UK/SA managing build. Offer WhatsApp live drone feed & milestone payment escrow."
            else -> "Provide detailed 50kg PPC cement & 6m rebar BOQ to demonstrate 32% savings over Harare competitors."
        }

        val risk = when {
            depositStatus.contains("Cleared") -> "ZERO RISK (FUNDS CLEARED)"
            depositStatus.contains("Cash") -> "SAFE (CASH VERIFIED)"
            else -> "ACCOUNTS CLEARANCE REQUIRED"
        }

        return DealUrgencyPrediction(finalScore, category, days, hook, risk)
    }

    /**
     * Strict Accounts Department Profit Margin & Deposit Clearance Gate
     * "We cannot pay people when we don't have anything."
     */
    fun evaluateAccountsClearance(
        quoteUsd: Double,
        directCostUsd: Double,
        requestedVoucherUsd: Double,
        isPromotionLocked: Boolean
    ): AccountsClearanceReport {
        val safeVoucher = if (isPromotionLocked) 0.0 else requestedVoucherUsd

        // Check if applying voucher keeps gross profit margin at or above 34%
        var appliedVoucher = safeVoucher
        var finalInvoice = quoteUsd - appliedVoucher
        var profit = finalInvoice - directCostUsd
        var marginPercent = if (finalInvoice > 0) (profit / finalInvoice) * 100 else 0.0

        if (marginPercent < 34.0 && !isPromotionLocked) {
            // Automatically cap voucher to maintain minimum 34% margin
            // (finalInvoice - directCost) / finalInvoice = 0.34 => finalInvoice = directCost / 0.66
            val minInvoice = directCostUsd / 0.66
            val maxAllowedVoucher = (quoteUsd - minInvoice).coerceAtLeast(0.0)
            appliedVoucher = (maxAllowedVoucher / 10.0).roundToInt() * 10.0
            finalInvoice = quoteUsd - appliedVoucher
            profit = finalInvoice - directCostUsd
            marginPercent = (profit / finalInvoice) * 100
        }

        val requiredDeposit = (finalInvoice * 0.60).roundToInt().toDouble()
        val isApproved = marginPercent >= 34.0 && finalInvoice >= directCostUsd

        val status = when {
            isPromotionLocked -> "PROMOTIONS FROZEN · Direct Costing Standard Enforced"
            appliedVoucher < requestedVoucherUsd -> "VOUCHER ADJUSTED · Margin Protection Active (Floor 34%)"
            else -> "APPROVED BY ACCOUNTS · 60% Deposit Clearance Active"
        }

        val policy = "Demash Accounts Policy: Minimum 60% client mobilization deposit (US$${requiredDeposit.toInt()}) must clear in bank/cash treasury before any material procurement or artisan labor payout is initiated. Zero unbacked cash disbursements."

        return AccountsClearanceReport(
            originalQuoteUsd = quoteUsd,
            directCostUsd = directCostUsd,
            authorizedVoucherUsd = appliedVoucher,
            finalInvoiceUsd = finalInvoice,
            grossProfitUsd = profit,
            grossProfitMarginPercent = marginPercent,
            requiredDepositUsd = requiredDeposit,
            isApprovedByAccounts = isApproved,
            approvalStatusText = status,
            policyStatement = policy
        )
    }
}
