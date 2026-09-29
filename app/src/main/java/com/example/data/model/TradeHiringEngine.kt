package com.example.data.model

data class TradeJobVacancy(
    val id: String,
    val title: String,
    val tradeCategory: String,
    val dailyPayUsd: Double,
    val expectedDays: Int,
    val positionsNeeded: Int,
    val location: String,
    val experienceRequirement: String,
    val urgencyLevel: String // "Immediate Hire", "Starting This Week", "Ongoing Roster"
)

object TradeHiringData {

    val openVacancies: List<TradeJobVacancy> = listOf(
        TradeJobVacancy(
            id = "hire_gunite",
            title = "30MPa Pneumatic Gunite Shotcrete Nozzle Operator",
            tradeCategory = "Shotcrete & Concrete Application",
            dailyPayUsd = 75.0,
            expectedDays = 14,
            positionsNeeded = 2,
            location = "Harare (Borrowdale) & Norton Sites",
            experienceRequirement = "Minimum 4 years operating pneumatic gunite nozzle. Must understand high-density compaction and zero rebound wastage.",
            urgencyLevel = "Immediate Hire (Breaking Ground)"
        ),
        TradeJobVacancy(
            id = "hire_steel",
            title = "Master Steel Fixer & Structural Rebar Bender",
            tradeCategory = "Steel Reinforcement",
            dailyPayUsd = 55.0,
            expectedDays = 12,
            positionsNeeded = 4,
            location = "Glen Lorne & Borrowdale Brooke",
            experienceRequirement = "Expertise in BS4449 high-yield Y10/Y12 rebar dual grids, cantilevered infinity wall tying, and structural chairs.",
            urgencyLevel = "Immediate Hire"
        ),
        TradeJobVacancy(
            id = "hire_excavator",
            title = "CAT 320 & Backhoe Heavy Excavator Operator",
            tradeCategory = "Heavy Plant & Earthmoving",
            dailyPayUsd = 70.0,
            expectedDays = 8,
            positionsNeeded = 2,
            location = "Workington & Norton Fleet Yard",
            experienceRequirement = "Valid Class 2/Plant operating license. Experience with hydraulic rock hammer for Borrowdale/Glen Lorne granite.",
            urgencyLevel = "Starting This Week"
        ),
        TradeJobVacancy(
            id = "hire_tiler",
            title = "Bullnose Coping Paver & Mosaic Tile Artisan",
            tradeCategory = "Finishes & Coping Masonry",
            dailyPayUsd = 50.0,
            expectedDays = 16,
            positionsNeeded = 3,
            location = "Harare Suburbs & Norton",
            experienceRequirement = "Precision stone coping mitering, bullnose leveling, underwater glass mosaic tiling, and chemical-resistant epoxy grouting.",
            urgencyLevel = "Starting This Week"
        ),
        TradeJobVacancy(
            id = "hire_plumber",
            title = "Pool Hydraulics & Solar DC Pump Technician",
            tradeCategory = "Hydraulics & Electrical",
            dailyPayUsd = 60.0,
            expectedDays = 10,
            positionsNeeded = 2,
            location = "Highlands, Borrowdale & Norton",
            experienceRequirement = "High-pressure PVC pipe solvent welding, multi-cyclone sand filters, variable-speed solar DC pumps, and underwater 12V LED wiring.",
            urgencyLevel = "Ongoing Roster"
        )
    )

    fun getHiringBroadcastText(job: TradeJobVacancy): String {
        return """
            📢 DEMASH POOLS ZIMBABWE IS HIRING!
            🛠️ Position: ${job.title}
            💰 Daily Payment: US$${job.dailyPayUsd.toInt()} per day (Cash / Nostro upon milestone signoff)
            🏦 Payment Policy: Disbursed strictly upon supervisor & Accounts Dept milestone certification. Zero advance disbursement without cleared project funds.
            ⏱️ Duration: ${job.expectedDays} Days on site (${job.positionsNeeded} positions needed)
            📍 Site Location: ${job.location}
            📋 Requirements: ${job.experienceRequirement}
            🚀 Urgency: ${job.urgencyLevel}
            📲 To Apply: WhatsApp Director Liberman Magaya with your work photos/references: +263 78 421 9178
            Office: K17412 Katanga, Norton, Zimbabwe
        """.trimIndent()
    }
}
