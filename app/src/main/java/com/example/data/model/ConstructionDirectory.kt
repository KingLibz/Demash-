package com.example.data.model

data class ConstructionContact(
    val id: String,
    val tradeCategory: String,
    val companyName: String,
    val contactPerson: String,
    val phone: String,
    val location: String,
    val productsSupplied: String,
    val priorityLevel: String // "Primary Partner", "Immediate Backup", "Government/Council"
)

object ConstructionDirectoryData {

    val directoryContacts: List<ConstructionContact> = listOf(
        // Plant & Machinery Hire
        ConstructionContact(
            id = "plant_1",
            tradeCategory = "Heavy Plant & Excavator Hire",
            companyName = "Harare Earthmoving & Lowbed Logistics",
            contactPerson = "Eng. Munyaradzi Sibanda",
            phone = "+263772114520",
            location = "Lytton Road, Workington, Harare",
            productsSupplied = "CAT 320 20-ton Excavators with rock-hammers, JCB 3DX Backhoes, 30-ton Low-bed transporters.",
            priorityLevel = "Primary Partner"
        ),
        ConstructionContact(
            id = "plant_2",
            tradeCategory = "Heavy Plant & Excavator Hire",
            companyName = "Norton Tipper & Plant Services",
            contactPerson = "Tariro Zhou",
            phone = "+263712889045",
            location = "Katanga Industrial Area, Norton",
            productsSupplied = "10m³ & 15m³ heavy tipper trucks, spoil haulage, Bobcat skid-steers for tight cluster yards.",
            priorityLevel = "Primary Partner"
        ),

        // Cement, Sand & Quarry Aggregates
        ConstructionContact(
            id = "mat_1",
            tradeCategory = "Cement & Structural Aggregates",
            companyName = "PPC Zimbabwe (Harare Depot)",
            contactPerson = "Sales Desk - Structural Team",
            phone = "+263242621000",
            location = "Portland Road, Heavy Industrial Sites, Harare",
            productsSupplied = "PPC Surebuild 42.5R high-strength structural Portland cement (palletized loads).",
            priorityLevel = "Primary Partner"
        ),
        ConstructionContact(
            id = "mat_2",
            tradeCategory = "Cement & Structural Aggregates",
            companyName = "Pomona Basalt Quarry",
            contactPerson = "Weighbridge Logistics",
            phone = "+263773400821",
            location = "Pomona, Borrowdale West, Harare",
            productsSupplied = "19mm blue metal stone aggregate, crusher run sub-base, granite ballast.",
            priorityLevel = "Primary Partner"
        ),
        ConstructionContact(
            id = "mat_3",
            tradeCategory = "Cement & Structural Aggregates",
            companyName = "Manyame River Sand Concession",
            contactPerson = "Clement Marange",
            phone = "+263774619330",
            location = "Norton Manyame River Basin",
            productsSupplied = "Washed coarse silica river sand for high-density 30MPa pneumatically applied shotcrete.",
            priorityLevel = "Primary Partner"
        ),

        // Steel Reinforcement
        ConstructionContact(
            id = "steel_1",
            tradeCategory = "Reinforcing Steel & Mesh",
            companyName = "Southerton Steel Merchants",
            contactPerson = "Innocent Mukaro",
            phone = "+263242754890",
            location = "Highfield Road, Southerton, Harare",
            productsSupplied = "Y10 & Y12 deformed high-yield rebar, 1.6mm tie wire, steel spacer stools.",
            priorityLevel = "Primary Partner"
        ),

        // Pumps, Solar Filtration & Hydraulics
        ConstructionContact(
            id = "pump_1",
            tradeCategory = "Pool Plant & Solar Hydraulics",
            companyName = "Waterlinx Zimbabwe (Pvt) Ltd",
            contactPerson = "David Van Zyl",
            phone = "+263242487200",
            location = "Bessemer Road, Graniteside, Harare",
            productsSupplied = "Quality Eco pumps, Speck badu pumps, solar DC pumps, multi-cyclone filters, silica sand.",
            priorityLevel = "Primary Partner"
        ),
        ConstructionContact(
            id = "pump_2",
            tradeCategory = "Pool Plant & Solar Hydraulics",
            companyName = "Harare Pressure Plastics & Pipes",
            contactPerson = "Farai Ndoro",
            phone = "+263772409871",
            location = "Simon Mazorodze Corridor, Harare",
            productsSupplied = "50mm Class 12 & Class 16 high-pressure PVC pipes, sweep bends, solvent cement.",
            priorityLevel = "Primary Partner"
        ),

        // Coping, Finishes & Mosaics
        ConstructionContact(
            id = "finish_1",
            tradeCategory = "Coping, Marbelite & Mosaics",
            companyName = "ZimStone Precast & Natural Slate",
            contactPerson = "Brian Mawere",
            phone = "+263773128904",
            location = "Msasa Commercial Park, Harare",
            productsSupplied = "Honed bullnose sandstone coping (500x250mm), imported crystal glass waterline mosaics, white marbelite.",
            priorityLevel = "Primary Partner"
        ),

        // Municipal Councils & Planning
        ConstructionContact(
            id = "council_1",
            tradeCategory = "Municipal Town Planning",
            companyName = "Norton Town Council - Engineering Dept",
            contactPerson = "Building Inspectorate Office",
            phone = "+263622211",
            location = "208 Galloway Road, Norton",
            productsSupplied = "Pool architectural plans submission, boundary setback verification, town approvals.",
            priorityLevel = "Government/Council"
        ),
        ConstructionContact(
            id = "council_2",
            tradeCategory = "Municipal Town Planning",
            companyName = "City of Harare - Department of Works",
            contactPerson = "Cleveland House Planning Registry",
            phone = "+263242753000",
            location = "Cleveland House, Leopold Takawira St, Harare",
            productsSupplied = "Residential pool permits, drainage approval, water connection clearance.",
            priorityLevel = "Government/Council"
        )
    )

    fun filterByCategory(category: String): List<ConstructionContact> {
        if (category == "All") return directoryContacts
        return directoryContacts.filter { it.tradeCategory.contains(category, ignoreCase = true) }
    }
}
