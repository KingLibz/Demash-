package com.example.data.model

import kotlin.math.ceil
import kotlin.math.roundToInt

data class MaterialItem(
    val category: String,
    val name: String,
    val unit: String,
    val quantity: Double,
    val unitCostUsd: Double,
    val totalCostUsd: Double,
    val technicalNote: String
)

data class WorkerLaborAllocation(
    val category: String,
    val numberOfWorkers: Int,
    val dailyRateUsd: Double,
    val daysExpected: Int,
    val totalPaymentUsd: Double,
    val responsibilities: String
)

data class MachineryMobilizationItem(
    val equipmentName: String,
    val mobilizationFeeUsd: Double,
    val unitRateUsd: Double,
    val unitType: String, // "Hours", "Days", "Trips"
    val unitsRequired: Double,
    val totalMachineryCostUsd: Double,
    val operationalScope: String
)

data class DetailedProjectBOQ(
    val poolDimensions: String,
    val surfaceAreaM2: Double,
    val perimeterM: Double,
    val volumeLiters: Int,
    val materials: List<MaterialItem>,
    val laborAllocations: List<WorkerLaborAllocation>,
    val machineryMobilizations: List<MachineryMobilizationItem>,
    val subtotalMaterialsCostUsd: Double,
    val subtotalLaborCostUsd: Double,
    val subtotalMachineryCostUsd: Double,
    val totalBaseCostUsd: Double,
    val targetProfitMarginPercent: Double, // 34% - 40%
    val grossProfitUsd: Double,
    val clientQuotationUsd: Double,
    val typicalMarketCompetitorPriceUsd: Double,
    val clientSavingsUsd: Double,
    val clientSavingsPercent: Int
)

object TradeQuotationEngine {

    fun generateBOQ(
        length: Double,
        width: Double,
        poolType: PoolType = PoolType.GUNITE,
        shape: PoolShape = PoolShape.RECTANGULAR,
        targetMarginPct: Double = 37.0
    ): DetailedProjectBOQ {
        val area = length * width
        val perimeter = (length + width) * 2.0 * shape.multiplier
        val avgDepth = when (poolType) {
            PoolType.PLUNGE -> 1.35
            PoolType.INFINITY -> 1.55
            PoolType.SCHOOL -> 1.65
            else -> 1.45
        }
        val volumeLiters = (area * avgDepth * 1000).roundToInt()
        val shellSurfaceArea = area + (perimeter * avgDepth)

        // ================= 1. MATERIALS BREAKDOWN =================
        val materials = mutableListOf<MaterialItem>()

        // Earthmoving Fill & Drainage
        materials.add(
            MaterialItem(
                category = "Earthwork & Sub-base",
                name = "Crushed Granite Gravel Ballast & Drainage Blanket (150mm)",
                unit = "Tons",
                quantity = ceil(area * 0.28),
                unitCostUsd = 22.0,
                totalCostUsd = ceil(area * 0.28) * 22.0,
                technicalNote = "Heavy compacted stone bedding preventing soil heave in wet seasons."
            )
        )

        // Steel Reinforcement & Fasteners
        val rebarTons = (shellSurfaceArea * 9.5) / 1000.0
        val rebarBars = ceil(rebarTons * 1000 / 7.1)
        val tieWireRolls = ceil(rebarBars / 30.0)
        val screwsPcs = ceil(perimeter * 8.0)

        materials.add(
            MaterialItem(
                category = "Steel & Fasteners",
                name = "Y10 High-Tensile Deformed Rebar (6m lengths)",
                unit = "Bars",
                quantity = rebarBars,
                unitCostUsd = 8.5,
                totalCostUsd = rebarBars * 8.5,
                technicalNote = "200mm x 200mm dual-layer reinforcement grid conforming to BS4449."
            )
        )
        materials.add(
            MaterialItem(
                category = "Steel & Fasteners",
                name = "1.6mm Annealed Steel Tie Wire (5kg rolls)",
                unit = "Rolls",
                quantity = tieWireRolls,
                unitCostUsd = 12.0,
                totalCostUsd = tieWireRolls * 12.0,
                technicalNote = "Heavy gauge wire for securing cross-sections and rebar chairs."
            )
        )
        materials.add(
            MaterialItem(
                category = "Steel & Fasteners",
                name = "Grade 316 Marine Stainless Steel Formwork Screws (4.8x50mm)",
                unit = "Pieces",
                quantity = screwsPcs,
                unitCostUsd = 0.25,
                totalCostUsd = screwsPcs * 0.25,
                technicalNote = "Corrosion-proof marine fasteners for coping edge and shutter alignment."
            )
        )

        // Concrete & Shotcrete Materials
        val concreteM3 = shellSurfaceArea * 0.18
        val cementBags = ceil(concreteM3 * 7.5)
        val riverSandM3 = ceil(concreteM3 * 0.65)
        val blueMetalAggregateM3 = ceil(concreteM3 * 0.75)

        materials.add(
            MaterialItem(
                category = "Concrete Shell",
                name = "PPC 42.5R High-Strength Portland Cement (50kg)",
                unit = "Bags",
                quantity = cementBags,
                unitCostUsd = 10.5,
                totalCostUsd = cementBags * 10.5,
                technicalNote = "Pre-tested 30MPa rapid-hardening mix with crystalline waterproofing admixture."
            )
        )
        materials.add(
            MaterialItem(
                category = "Concrete Shell",
                name = "Washed Coarse River Sand (Silica rich)",
                unit = "Cubic Meters",
                quantity = riverSandM3,
                unitCostUsd = 24.0,
                totalCostUsd = riverSandM3 * 24.0,
                technicalNote = "Zero loam silt content for optimum shotcrete adhesion and density."
            )
        )
        materials.add(
            MaterialItem(
                category = "Concrete Shell",
                name = "19mm Blue Metal Basalt Stone Aggregate",
                unit = "Cubic Meters",
                quantity = blueMetalAggregateM3,
                unitCostUsd = 28.0,
                totalCostUsd = blueMetalAggregateM3 * 28.0,
                technicalNote = "Quarried crushed stone for compressive structural ballast."
            )
        )

        // Hydraulics & Piping
        val pvcLengths = ceil(perimeter * 0.9)
        val pvcElbows = 14.0
        val weirSkimmerPcs = maxOf(1.0, ceil(area / 35.0))

        materials.add(
            MaterialItem(
                category = "Hydraulics",
                name = "50mm Class 12 High-Pressure PVC Pipe (6m)",
                unit = "Lengths",
                quantity = pvcLengths,
                unitCostUsd = 14.0,
                totalCostUsd = pvcLengths * 14.0,
                technicalNote = "Solvent-welded rigid pressure lines with expansion sweep bends."
            )
        )
        materials.add(
            MaterialItem(
                category = "Hydraulics",
                name = "50mm Schedule 40 Sweep Bends & Unions",
                unit = "Fittings",
                quantity = pvcElbows,
                unitCostUsd = 4.5,
                totalCostUsd = pvcElbows * 4.5,
                technicalNote = "Zero friction head loss curves reducing pump load by 18%."
            )
        )
        materials.add(
            MaterialItem(
                category = "Hydraulics",
                name = "Titan Leaves™ High-Capacity Vortex Weir Skimmer & Sump",
                unit = "Sets",
                quantity = weirSkimmerPcs,
                unitCostUsd = 145.0,
                totalCostUsd = weirSkimmerPcs * 145.0,
                technicalNote = "Upgraded to Titan Leaves™ today: High-capacity dual-mesh leaf vortex engineered for dense Zimbabwean Jacaranda & Msasa foliage."
            )
        )
        materials.add(
            MaterialItem(
                category = "Hydraulics",
                name = "Titan Leaves™ In-Line Pre-Pump Debris Interceptor",
                unit = "Unit",
                quantity = 1.0,
                unitCostUsd = 85.0,
                totalCostUsd = 85.0,
                technicalNote = "Heavy-debris leaf canister preventing pump impeller burnout during spring leaf-fall."
            )
        )

        // Plant Equipment & Finishes
        val pumpCost = when {
            volumeLiters < 25000 -> 280.0
            volumeLiters < 55000 -> 360.0
            else -> 520.0
        }
        val filterCost = when {
            volumeLiters < 25000 -> 240.0
            volumeLiters < 55000 -> 320.0
            else -> 480.0
        }
        val silicaSandBags = if (volumeLiters < 55000) 3.0 else 4.0

        materials.add(
            MaterialItem(
                category = "Plant Equipment",
                name = "Quality Eco-Flow High-Torque Pool Pump",
                unit = "Unit",
                quantity = 1.0,
                unitCostUsd = pumpCost,
                totalCostUsd = pumpCost,
                technicalNote = "Thermal overload protected motor; compatible with 3-5kVA solar inverters."
            )
        )
        materials.add(
            MaterialItem(
                category = "Plant Equipment",
                name = "Multiport Top-Mount Sand Filter + Graded Silica Sand",
                unit = "System",
                quantity = 1.0,
                unitCostUsd = filterCost + (silicaSandBags * 18.0),
                totalCostUsd = filterCost + (silicaSandBags * 18.0),
                technicalNote = "Corrosion-proof thermoplastic tank with backwash sight glass."
            )
        )

        // Finishes
        val copingStones = ceil(perimeter / 0.5)
        val mosaicSqm = ceil(perimeter * 0.25)
        val marbeliteBags = ceil(shellSurfaceArea / 2.2)

        materials.add(
            MaterialItem(
                category = "Finishes",
                name = "Non-Slip Honed Bullnose Coping Stones (500x250mm)",
                unit = "Pieces",
                quantity = copingStones,
                unitCostUsd = 6.5,
                totalCostUsd = copingStones * 6.5,
                technicalNote = "Heat-resistant honed coping with drip groove."
            )
        )
        materials.add(
            MaterialItem(
                category = "Finishes",
                name = "Imported Crystal Glass Waterline Mosaic Tiles (300x300mm)",
                unit = "Square Meters",
                quantity = mosaicSqm,
                unitCostUsd = 32.0,
                totalCostUsd = mosaicSqm * 32.0,
                technicalNote = "Zero porosity waterline band with waterproof epoxy polymer grout."
            )
        )
        materials.add(
            MaterialItem(
                category = "Finishes",
                name = "Super-Fine White Marble Plaster (Marbelite Premix 40kg)",
                unit = "Bags",
                quantity = marbeliteBags,
                unitCostUsd = 22.0,
                totalCostUsd = marbeliteBags * 22.0,
                technicalNote = "Hand-troweled silky smooth finish with silicone water-barrier sealer."
            )
        )

        // ================= 2. WORKER LABOR ALLOCATION =================
        val laborAllocations = listOf(
            WorkerLaborAllocation(
                category = "Master Gunite Nozzleman (Pneumatic 30MPa)",
                numberOfWorkers = 1,
                dailyRateUsd = 45.0,
                daysExpected = if (area <= 18) 3 else 5,
                totalPaymentUsd = 1 * 45.0 * (if (area <= 18) 3 else 5),
                responsibilities = "High-velocity pneumatic shotcrete compaction, rebound reduction & monolithic curve carving."
            ),
            WorkerLaborAllocation(
                category = "Certified Structural Steel Fixers",
                numberOfWorkers = 2,
                dailyRateUsd = 35.0,
                daysExpected = if (area <= 18) 3 else 4,
                totalPaymentUsd = 2 * 35.0 * (if (area <= 18) 3 else 4),
                responsibilities = "Bending, spacing and securing dual Y10 rebar cage with engineer-certified anchor chairs."
            ),
            WorkerLaborAllocation(
                category = "Master Hydraulic & Solar Pump Plumber",
                numberOfWorkers = 1,
                dailyRateUsd = 40.0,
                daysExpected = 3,
                totalPaymentUsd = 1 * 40.0 * 3,
                responsibilities = "Pressure testing Class 12 PVC lines at 3.5 bar, manifold valves & inverter pump electricals."
            ),
            WorkerLaborAllocation(
                category = "Master Marbelite Plasterers & Tilers",
                numberOfWorkers = 2,
                dailyRateUsd = 35.0,
                daysExpected = if (area <= 18) 4 else 6,
                totalPaymentUsd = 2 * 35.0 * (if (area <= 18) 4 else 6),
                responsibilities = "Laser leveling waterline mosaics, bullnose coping joints & silky-smooth marbelite hand troweling."
            ),
            WorkerLaborAllocation(
                category = "Heavy Machine Excavator Operator",
                numberOfWorkers = 1,
                dailyRateUsd = 45.0,
                daysExpected = 2,
                totalPaymentUsd = 1 * 45.0 * 2,
                responsibilities = "Precision depth excavation, slope grading, rock breaking and truck loading."
            ),
            WorkerLaborAllocation(
                category = "Site General Hands & Formwork Crew",
                numberOfWorkers = 4,
                dailyRateUsd = 20.0,
                daysExpected = if (area <= 18) 12 else 18,
                totalPaymentUsd = 4 * 20.0 * (if (area <= 18) 12 else 18),
                responsibilities = "Soil wheeling, shutter timber erection, aggregate batching, site tidy and clean up."
            )
        )

        // ================= 3. MACHINERY & MOBILIZATION =================
        val soilVolumeM3 = (area * (avgDepth + 0.35) * 1.25).roundToInt()
        val excavatorHours = maxOf(6.0, ceil(soilVolumeM3 / 14.0))
        val tipperTrips = ceil(soilVolumeM3 / 10.0)

        val machineryMobilizations = listOf(
            MachineryMobilizationItem(
                equipmentName = "CAT 320 / JCB 20-Ton Excavator with Rock-Hammer",
                mobilizationFeeUsd = 180.0, // Low-bed transport to site
                unitRateUsd = 65.0,
                unitType = "Hours",
                unitsRequired = excavatorHours,
                totalMachineryCostUsd = 180.0 + (excavatorHours * 65.0),
                operationalScope = "Precision dig, laser grading, granite rock-breaking and soil loading."
            ),
            MachineryMobilizationItem(
                equipmentName = "10m³ Heavy Tipper Haulage Trucks",
                mobilizationFeeUsd = 50.0,
                unitRateUsd = 45.0,
                unitType = "Trips",
                unitsRequired = tipperTrips,
                totalMachineryCostUsd = 50.0 + (tipperTrips * 45.0),
                operationalScope = "Safe removal of clay and rock spoil to certified municipal dumpsite."
            ),
            MachineryMobilizationItem(
                equipmentName = "Atlas Copco High-CFM Pneumatic Shotcrete Compressor",
                mobilizationFeeUsd = 95.0,
                unitRateUsd = 120.0,
                unitType = "Days",
                unitsRequired = if (area <= 18) 2.0 else 3.0,
                totalMachineryCostUsd = 95.0 + (if (area <= 18) 2.0 else 3.0) * 120.0,
                operationalScope = "Continuous 8-bar pneumatic air delivery for 30MPa structural gunite spraying."
            ),
            MachineryMobilizationItem(
                equipmentName = "Wacker Neuson Plate Compactor & Poker Vibrator",
                mobilizationFeeUsd = 35.0,
                unitRateUsd = 30.0,
                unitType = "Days",
                unitsRequired = 3.0,
                totalMachineryCostUsd = 35.0 + (3.0 * 30.0),
                operationalScope = "Sub-base soil compaction and concrete void elimination."
            )
        )

        // Totals
        val subtotalMaterials = materials.sumOf { it.totalCostUsd }
        val subtotalLabor = laborAllocations.sumOf { it.totalPaymentUsd }
        val subtotalMachinery = machineryMobilizations.sumOf { it.totalMachineryCostUsd }

        val totalBaseCost = subtotalMaterials + subtotalLabor + subtotalMachinery

        // Corporate Profit Margin (e.g. 36.5% profit)
        val marginFraction = targetMarginPct / 100.0
        val clientQuote = totalBaseCost / (1.0 - marginFraction)
        val grossProfit = clientQuote - totalBaseCost

        // Market comparison: typical builders charge ~47% more (price / 0.68)
        val competitorPrice = clientQuote * 1.47
        val clientSavings = competitorPrice - clientQuote

        return DetailedProjectBOQ(
            poolDimensions = "${String.format(java.util.Locale.US, "%.1f", length)}m × ${String.format(java.util.Locale.US, "%.1f", width)}m",
            surfaceAreaM2 = (area * 10).roundToInt() / 10.0,
            perimeterM = (perimeter * 10).roundToInt() / 10.0,
            volumeLiters = volumeLiters,
            materials = materials,
            laborAllocations = laborAllocations,
            machineryMobilizations = machineryMobilizations,
            subtotalMaterialsCostUsd = (subtotalMaterials * 10).roundToInt() / 10.0,
            subtotalLaborCostUsd = (subtotalLabor * 10).roundToInt() / 10.0,
            subtotalMachineryCostUsd = (subtotalMachinery * 10).roundToInt() / 10.0,
            totalBaseCostUsd = (totalBaseCost * 10).roundToInt() / 10.0,
            targetProfitMarginPercent = targetMarginPct,
            grossProfitUsd = (grossProfit * 10).roundToInt() / 10.0,
            clientQuotationUsd = (clientQuote * 10).roundToInt() / 10.0,
            typicalMarketCompetitorPriceUsd = (competitorPrice * 10).roundToInt() / 10.0,
            clientSavingsUsd = (clientSavings * 10).roundToInt() / 10.0,
            clientSavingsPercent = 32
        )
    }
}
