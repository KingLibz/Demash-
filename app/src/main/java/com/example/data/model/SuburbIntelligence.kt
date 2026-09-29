package com.example.data.model

data class SuburbProfile(
    val id: String,
    val name: String,
    val city: String,
    val tier: String, // "Ultra-Luxury", "Executive Prestige", "High-Net-Worth Residential"
    val typicalBudgetRange: String,
    val soilCharacteristics: String,
    val terrainType: String,
    val recommendedPoolStyle: String,
    val solarPriority: String,
    val salesPsychologyHook: String,
    val competitorWeaknessExploited: String,
    val keyNeighborhoodLandmarks: List<String>
)

object SuburbIntelligenceData {
    val affluentSuburbs: List<SuburbProfile> = listOf(
        SuburbProfile(
            id = "borrowdale_brooke",
            name = "Borrowdale Brooke",
            city = "Harare",
            tier = "Ultra-Luxury Golf Estate",
            typicalBudgetRange = "US$12,000 – US$45,000+",
            soilCharacteristics = "Granite bedrock with shallow gravelly topsoil; stable but requires precision rock-breaking equipment.",
            terrainType = "Gently undulating golf fairways & manicured estates.",
            recommendedPoolStyle = "Rim-Flow Infinity or Modern Geometric with Teak Decking & Integrated Hot Tub",
            solarPriority = "Critical: Direct DC solar pump + automated salt chlorinator for 100% grid independence.",
            salesPsychologyHook = "Estate Architectural Discretion: Strict adherence to Brooke HOA bylaws, noise-managed excavation, zero dust intrusion, and turnkey luxury handover.",
            competitorWeaknessExploited = "Local builders take 4+ months and violate estate curfew rules. Dzimbabwe Pools deploys high-speed pneumatic crews in 4–6 weeks flat.",
            keyNeighborhoodLandmarks = listOf("Brooke Golf Clubhouse", "Heritage School", "Crowhill Road Gate")
        ),
        SuburbProfile(
            id = "glen_lorne",
            name = "Glen Lorne",
            city = "Harare",
            tier = "Ultra-Luxury Hillside Enclave",
            typicalBudgetRange = "US$14,500 – US$50,000+",
            soilCharacteristics = "Massive granite kopjes, severe slopes, and natural stone outcrops requiring reinforced retaining structures.",
            terrainType = "Dramatic hillsides overlooking the Umwindisi River valley.",
            recommendedPoolStyle = "Cantilevered Vanishing-Edge (Infinity) with Natural Granite Rock Waterfalls",
            solarPriority = "High: 3-phase hybrid solar pump integration with surge balancing tank.",
            salesPsychologyHook = "Architectural Permanence: Converting steep unusable hillside into an awe-inspiring resort entertainment terrace overlooking panoramic valleys.",
            competitorWeaknessExploited = "Ordinary builders fear slopes and rock. We bring in 20-ton rock-hammer excavators and certified structural engineer drawings included in our fixed price.",
            keyNeighborhoodLandmarks = listOf("Imba Matombo", "Folyjon Crescent", "Enterprise Road Corridor")
        ),
        SuburbProfile(
            id = "umwinsidale",
            name = "Umwinsidale & Shawasha Hills",
            city = "Harare",
            tier = "Elite Mountain & Ridge Mansions",
            typicalBudgetRange = "US$15,000 – US$60,000+",
            soilCharacteristics = "Decomposed granite, quartz veins, and deep expansive rocky loam.",
            terrainType = "High-elevation ridges with multi-acre private estates.",
            recommendedPoolStyle = "Resort Lagoon Pool with Sunken Braai Pit & Sheer Descent Water Curtains",
            solarPriority = "Essential: Large capacity filtration with high-flow water features running on dedicated solar arrays.",
            salesPsychologyHook = "Private Oasis Prestige: Creating an exclusive 5-star safari lodge retreat within your own boundary walls.",
            competitorWeaknessExploited = "Competitors hit clients with massive 'hidden rock fees'. Dzimbabwe Pools locks the contract price after our US$50 structural survey.",
            keyNeighborhoodLandmarks = listOf("Shawasha Hills Gatehouse", "Umwinsidale River", "Chishawasha Hills")
        ),
        SuburbProfile(
            id = "chisipite",
            name = "Chisipite & Ballantyne Park",
            city = "Harare",
            tier = "Old-Money Executive Heritage",
            typicalBudgetRange = "US$8,500 – US$28,000",
            soilCharacteristics = "Deep fertile red sandy loam with good drainage, interspersed with mature tree root systems.",
            terrainType = "Flat to gently sloped 1-acre to 2-acre suburban gardens.",
            recommendedPoolStyle = "Classic 8×4m or 9×4m Family Gunite Pool with Integrated Baja Sun Shelf & Perimeter Paving",
            solarPriority = "High: Solar heating collector mats to extend swimming season from September to May.",
            salesPsychologyHook = "Multigenerational Family Investment: Safe shallow toddler play zones, durable white marbelite, and 10-year structural warranty for peace of mind.",
            competitorWeaknessExploited = "Older pools in Chisipite suffer from leaking pipes and cracked plaster. We replace or rebuild with monolithic 30MPa shotcrete that never cracks.",
            keyNeighborhoodLandmarks = listOf("Chisipite Senior School", "Ballantyne Park Conservancy", "Hindhead Avenue")
        ),
        SuburbProfile(
            id = "highlands",
            name = "Highlands & Rolf Valley",
            city = "Harare",
            tier = "Prestige Central Executive",
            typicalBudgetRange = "US$7,500 – US$32,000",
            soilCharacteristics = "Rich red clays and gravelly red earth that swells during monsoon rains.",
            terrainType = "Rolling residential avenues with lush mature gardens.",
            recommendedPoolStyle = "Modern Minimalist Rectangular Pool paired with Designer Teak Pergola & Travertine Coping",
            solarPriority = "Medium-High: Variable speed pumps syncing with residential 5kVA/8kVA inverters.",
            salesPsychologyHook = "Seamless Indoor-Outdoor Entertaining: Merging modern open-plan living rooms with sparkling azure water and ambient LED evening lighting.",
            competitorWeaknessExploited = "Competitors subcontract landscaping, leaving gardens ruined after pool digs. Our in-house team leaves lawns, planting, and paving pristine.",
            keyNeighborhoodLandmarks = listOf("Highlands Park Mall", "Enterprise Road", "St George's College corridor")
        ),
        SuburbProfile(
            id = "gunhill",
            name = "Gunhill & Alexandra Park",
            city = "Harare",
            tier = "Diplomatic & Ambassadorial District",
            typicalBudgetRange = "US$9,000 – US$35,000",
            soilCharacteristics = "Hard red soils with ironstone (ferricrete) pebbles requiring heavy mechanical excavation.",
            terrainType = "Elevated ridge north of Harare city centre.",
            recommendedPoolStyle = "Architectural Lap Pool (10m–12m × 2.8m) with Glass Waterline Mosaic & Anti-Slip Bullnose",
            solarPriority = "High: Zero-noise high-efficiency pump systems meeting diplomatic quiet-hour standards.",
            salesPsychologyHook = "Exquisite Craftsmanship & Security: Pristine aesthetics, child-safe magnetic latch gates, and certified engineering documentation.",
            competitorWeaknessExploited = "Unreliable tradesmen failing security clearances. Our staff are uniformed, vetted, and strictly professional.",
            keyNeighborhoodLandmarks = listOf("Churchill Avenue", "Diplomatic Residences", "National Archives Ridge")
        ),
        SuburbProfile(
            id = "norton_lakeside",
            name = "Norton (Katanga, Galloway & Lakeside)",
            city = "Norton",
            tier = "Home Ground & Waterfront Properties",
            typicalBudgetRange = "US$3,500 – US$25,000",
            soilCharacteristics = "Expansive heavy black cotton and red clays prone to severe seasonal movement.",
            terrainType = "Flat plains and scenic waterfront shores near Lake Chivero basin.",
            recommendedPoolStyle = "Heavy-Reinforced Monolithic Gunite Shell with Double-Layer Y12 Rebar Cage",
            solarPriority = "Essential: Standalone solar borehole and solar pool pump combinations.",
            salesPsychologyHook = "Hometown Loyalty & Rapid Response: Norton's premier pool builders are right here in Katanga with zero transport markups and instant on-site backup.",
            competitorWeaknessExploited = "Harare-based builders charge extortionate callout and transport surcharges to Norton. We are local champions offering the best trade prices.",
            keyNeighborhoodLandmarks = listOf("Katanga Business Centre", "Lake Chivero Shoreline", "Galloway Estates")
        )
    )

    fun getSuburb(id: String): SuburbProfile? = affluentSuburbs.find { it.id == id }
}
