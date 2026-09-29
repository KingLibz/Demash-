package com.example.data.repository

import com.example.data.database.BookingRequestDao
import com.example.data.database.SavedEstimateDao
import com.example.data.model.BookingRequest
import com.example.data.model.EstimateResult
import com.example.data.model.FAQItem
import com.example.data.model.PoolPreset
import com.example.data.model.PoolSchoolGuide
import com.example.data.model.PoolService
import com.example.data.model.PoolShape
import com.example.data.model.PoolType
import com.example.data.model.PricingPackage
import com.example.data.model.SavedEstimate
import com.example.data.model.ShowcaseProject
import kotlinx.coroutines.flow.Flow
import kotlin.math.roundToInt

class PoolRepository(
    private val savedEstimateDao: SavedEstimateDao,
    private val bookingRequestDao: BookingRequestDao
) {
    val allSavedEstimates: Flow<List<SavedEstimate>> = savedEstimateDao.getAllEstimates()
    val allBookings: Flow<List<BookingRequest>> = bookingRequestDao.getAllBookings()

    suspend fun saveEstimate(estimate: SavedEstimate): Long =
        savedEstimateDao.insertEstimate(estimate)

    suspend fun deleteEstimate(estimate: SavedEstimate) =
        savedEstimateDao.deleteEstimate(estimate)

    suspend fun deleteEstimateById(id: Long) =
        savedEstimateDao.deleteById(id)

    suspend fun saveBooking(booking: BookingRequest): Long =
        bookingRequestDao.insertBooking(booking)

    suspend fun updateBookingStatus(id: Long, status: String) =
        bookingRequestDao.updateStatus(id, status)

    suspend fun deleteBooking(booking: BookingRequest) =
        bookingRequestDao.deleteBooking(booking)

    suspend fun clearAllBookings() =
        bookingRequestDao.clearAllBookings()

    fun calculateEstimate(
        poolType: PoolType,
        shape: PoolShape,
        length: Double,
        width: Double
    ): EstimateResult {
        val area = length * width
        val base = poolType.basePrice
        val rate = poolType.ratePerSqm

        val calculated = (base + (area * rate)) * shape.multiplier

        // Minimum floor pricing for specific types as per Zimbabwe trade specs
        val finalPrice = when (poolType) {
            PoolType.PLUNGE -> maxOf(3500.0, calculated)
            PoolType.SPLASH -> maxOf(1800.0, calculated)
            PoolType.GUNITE -> maxOf(4800.0, calculated)
            PoolType.INFINITY -> maxOf(11500.0, calculated)
            PoolType.SCHOOL -> maxOf(16000.0, calculated)
            PoolType.COMMERCIAL -> maxOf(12000.0, calculated)
            else -> calculated
        }

        // Market price is typically ~32% higher for identical spec in Zimbabwe
        val marketPrice = finalPrice / 0.68
        val savings = marketPrice - finalPrice
        val savingsPct = 32

        // Diagnostics
        val avgDepth = when (poolType) {
            PoolType.PLUNGE -> 1.4
            PoolType.SPLASH -> 1.1
            PoolType.SCHOOL -> 1.6
            PoolType.COMMERCIAL -> 1.5
            else -> 1.45
        }
        val volumeLiters = (area * avgDepth * 1000).roundToInt()
        val depthLabel = when (poolType) {
            PoolType.PLUNGE -> "1.2m – 1.5m flat plunge depth"
            PoolType.SPLASH -> "1.0m – 1.2m shallow family depth"
            PoolType.SCHOOL -> "1.2m shallow end to 2.0m deep end"
            else -> "1.1m shallow end to 1.8m deep end"
        }
        val pumpRating = when {
            volumeLiters < 25000 -> "0.75 HP Eco Pump (2-bag sand filter)"
            volumeLiters < 50000 -> "1.0 HP Quality Pump (3-bag sand filter)"
            volumeLiters < 90000 -> "1.5 HP Heavy Duty Pump (4-bag sand filter)"
            else -> "2.0 – 3.0 HP Commercial Dual Pump System"
        }

        return EstimateResult(
            length = length,
            width = width,
            poolType = poolType,
            shape = shape,
            surfaceAreaM2 = (area * 10).roundToInt() / 10.0,
            estimatedVolumeLiters = volumeLiters,
            estimatedDepthM = depthLabel,
            recommendedPumpHp = pumpRating,
            priceUsd = finalPrice,
            marketPriceUsd = marketPrice,
            savingsUsd = savings,
            savingsPct = savingsPct
        )
    }

    val presets: List<PoolPreset> = listOf(
        PoolPreset(
            id = "plunge3",
            name = "Plunge 3 × 2m",
            length = 3.0,
            width = 2.0,
            poolType = PoolType.PLUNGE,
            shape = PoolShape.RECTANGULAR,
            subtitle = "Townhouses, Norton & Harare cluster homes"
        ),
        PoolPreset(
            id = "small4",
            name = "Small Family 4 × 2.5m",
            length = 4.0,
            width = 2.5,
            poolType = PoolType.GUNITE,
            shape = PoolShape.RECTANGULAR,
            subtitle = "Compact yard, fast heating"
        ),
        PoolPreset(
            id = "family5",
            name = "Family Favourite 5 × 3m",
            length = 5.0,
            width = 3.0,
            poolType = PoolType.GUNITE,
            shape = PoolShape.RECTANGULAR,
            subtitle = "Most requested build across Zimbabwe",
            isPopular = true
        ),
        PoolPreset(
            id = "large6",
            name = "Large Family 6 × 3.5m",
            length = 6.0,
            width = 3.5,
            poolType = PoolType.GUNITE,
            shape = PoolShape.FREEFORM,
            subtitle = "Generous leisure zone with steps & bench"
        ),
        PoolPreset(
            id = "xl7",
            name = "XL Executive 7 × 4m",
            length = 7.0,
            width = 4.0,
            poolType = PoolType.GUNITE,
            shape = PoolShape.RECTANGULAR,
            subtitle = "Spacious swimming & poolside entertaining"
        ),
        PoolPreset(
            id = "grand8",
            name = "Grand Resort 8 × 4m",
            length = 8.0,
            width = 4.0,
            poolType = PoolType.GUNITE,
            shape = PoolShape.FREEFORM,
            subtitle = "Resort luxury with sheer descent waterfall"
        ),
        PoolPreset(
            id = "lap9",
            name = "Fitness Lap 9 × 2.5m",
            length = 9.0,
            width = 2.5,
            poolType = PoolType.GUNITE,
            shape = PoolShape.LAP,
            subtitle = "Dedicated swim training & narrow boundaries"
        ),
        PoolPreset(
            id = "school15",
            name = "School Starter 15 × 6m",
            length = 15.0,
            width = 6.0,
            poolType = PoolType.SCHOOL,
            shape = PoolShape.RECTANGULAR,
            subtitle = "Junior school sports & swim academy"
        ),
        PoolPreset(
            id = "school25",
            name = "Semi-Olympic 25 × 8m",
            length = 25.0,
            width = 8.0,
            poolType = PoolType.SCHOOL,
            shape = PoolShape.RECTANGULAR,
            subtitle = "Full school competition with 4 lanes"
        ),
        PoolPreset(
            id = "commercial12",
            name = "Lodge & Safari 12 × 5m",
            length = 12.0,
            width = 5.0,
            poolType = PoolType.COMMERCIAL,
            shape = PoolShape.FREEFORM,
            subtitle = "Tourism lodges, Victoria Falls & Kariba style"
        )
    )

    val pricingPackages: List<PricingPackage> = listOf(
        PricingPackage(
            id = "pkg_plunge",
            name = "Plunge Pool",
            dimensions = "Up to 3.2m × 2m · 2–3 week build",
            buildTime = "2–3 weeks",
            priceUsd = 3500,
            marketPriceUsd = 4600,
            savePercent = 32,
            highlights = listOf(
                "Compact gunite or heavy-duty vinyl build",
                "Quality 0.75HP pump & sand filter included",
                "Perfect for townhouse yards & cluster homes",
                "LED underwater pool light included",
                "6 months free aftercare maintenance"
            ),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1571863533956-01c88e79957e?auto=format&fit=crop&w=800&q=70"
        ),
        PricingPackage(
            id = "pkg_family",
            name = "Family Favourite",
            dimensions = "5m × 3m gunite · 3–6 week build",
            buildTime = "3–6 weeks",
            priceUsd = 6500,
            marketPriceUsd = 8600,
            savePercent = 32,
            highlights = listOf(
                "Upgraded to Titan Leaves™ dual-basket vortex debris skimmer",
                "Full structural gunite shell — any shape",
                "Tiled waterline + white or sky blue marbelite",
                "Complete 1.0HP pump, sand filter & high-spec PVC piping",
                "Non-slip bullnose coping & safety steps",
                "6 months free aftercare with chemicals included"
            ),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1576013551627-0cc20b96c2a7?auto=format&fit=crop&w=800&q=70"
        ),
        PricingPackage(
            id = "pkg_titan_leaves",
            name = "Titan Leaves™ Estate Edition",
            dimensions = "7.5m × 4m · Titan Leaves™ Spec",
            buildTime = "3–4 weeks",
            priceUsd = 8900,
            marketPriceUsd = 12400,
            savePercent = 32,
            highlights = listOf(
                "🌟 Upgraded to Titan Leaves™ Today: High-Capacity Leaf Canister",
                "Engineered for heavy Jacaranda & Msasa seasonal foliage fall",
                "Zero pump cavitation with in-line pre-pump vortex trap",
                "Reinforced 32MPa monolithic shell with dual Y12 grid",
                "Bullnose charcoal coping & dual underwater LED illumination",
                "Accounts-cleared 60% mobilization milestone protection"
            ),
            isFeatured = true,
            imageUrl = "https://images.unsplash.com/photo-1576013551627-0cc20b96c2a7?auto=format&fit=crop&w=800&q=70"
        ),
        PricingPackage(
            id = "pkg_grand",
            name = "Grand Resort",
            dimensions = "8m × 4m freeform · priority build",
            buildTime = "4–6 weeks",
            priceUsd = 11500,
            marketPriceUsd = 15200,
            savePercent = 32,
            highlights = listOf(
                "Full Titan Leaves™ commercial-grade leaf filtration suite",
                "Architectural freeform design with custom 3D concept",
                "Solar heating pre-plumbed & ready",
                "Natural stone coping + designer mosaic accents",
                "Decking, braai area & pergola bundle options",
                "Smart phone automation ready",
                "6 months comprehensive white-glove aftercare"
            ),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?auto=format&fit=crop&w=800&q=70"
        )
    )

    val services: List<PoolService> = listOf(
        PoolService(
            id = "srv_construction",
            title = "Pool Construction",
            startingPrice = "From US$3,500",
            description = "Gunite/shotcrete, fiberglass & vinyl-lined pools — design, excavation, shell, tiling, and filtration turnkey.",
            keyFeatures = listOf(
                "10-year structural warranty on gunite shells",
                "Fixed-price contract with zero hidden variation fees",
                "Weekly WhatsApp photo & video build updates",
                "Turnkey commissioning with starter chemicals"
            ),
            iconName = "pool"
        ),
        PoolService(
            id = "srv_landscaping",
            title = "Landscaping & Gardens",
            startingPrice = "From US$1,200",
            description = "Lawns, planting, automated irrigation systems, paving, and retaining walls that frame your pool like a resort.",
            keyFeatures = listOf(
                "10–15% discount when bundled with a pool build",
                "Drought-resistant indigenous Zimbabwean plant selection",
                "Automated smart drip and spray irrigation",
                "Custom retaining walls for sloped Harare/Norton plots"
            ),
            iconName = "landscape"
        ),
        PoolService(
            id = "srv_decks",
            title = "Decks, Pergolas & Paving",
            startingPrice = "Custom Quote",
            description = "Entertainment zones around the water — hardwood timber decks, shade pergolas, braai entertainment areas, and designer coping.",
            keyFeatures = listOf(
                "Teak, Saligna and composite decking options",
                "Custom steel and timber pergolas with louvers",
                "Non-slip honed sandstone, travertine & wet-cast paving",
                "Built-in outdoor braai, seating & lighting"
            ),
            iconName = "deck"
        ),
        PoolService(
            id = "srv_water_features",
            title = "Water Features & Koi Ponds",
            startingPrice = "From US$850",
            description = "Fountains, stacked stone waterfalls, sheer descents, and biological koi ponds that turn an ordinary yard into a private paradise.",
            keyFeatures = listOf(
                "Sheer descent waterfall blades embedded in walls",
                "Natural granite rock waterfalls & bubbling streams",
                "Multi-stage biological filtration for clear koi ponds",
                "Underwater LED glow integration"
            ),
            iconName = "water"
        ),
        PoolService(
            id = "srv_renovations",
            title = "Renovations & Repairs",
            startingPrice = "From US$450",
            description = "Leak detection, re-marbelite resurfacing, mosaic waterline retiling, pump replacements, and rapid green-water rescue.",
            keyFeatures = listOf(
                "Re-plastering with high-density white or blue marbelite",
                "Precision acoustic & pressure leak detection",
                "Pump & sand filter servicing or upgrades",
                "Green pool rescue: crystal clear in 24–48 hours"
            ),
            iconName = "build"
        ),
        PoolService(
            id = "srv_maintenance",
            title = "Maintenance Plans",
            startingPrice = "From US$85/month",
            description = "Regular monthly care — vacuuming, brushing, pH & chlorine testing, filter backwash, and pump checks. 6 months free with any new build.",
            keyFeatures = listOf(
                "Bi-weekly or weekly scheduled technician visits",
                "All pool sanitising chemicals supplied and balanced",
                "Written water health and equipment logbook",
                "Emergency priority callouts included"
            ),
            iconName = "cleaning_services"
        )
    )

    val showcaseProjects: List<ShowcaseProject> = listOf(
        ShowcaseProject(
            id = "proj_1",
            title = "Modern Freeform Oasis",
            location = "Norton, Mashonaland West",
            category = "Gunite",
            dimensions = "7m × 4m",
            buildTime = "4 weeks",
            approxPrice = "US$8,200",
            description = "Custom gunite freeform swimming pool with natural stone coping, solar heating integration, and lush garden borders.",
            imageUrl = "https://images.unsplash.com/photo-1561501878-aabd62634533?auto=format&fit=crop&w=1000&q=70",
            tags = listOf("Gunite", "Freeform", "Solar Ready", "Norton")
        ),
        ShowcaseProject(
            id = "proj_2",
            title = "Executive Rim-Flow Infinity Pool",
            location = "Borrowdale Brooke, Harare",
            category = "Infinity",
            dimensions = "9m × 4m",
            buildTime = "6 weeks",
            approxPrice = "US$16,800",
            description = "Architectural vanishing edge pool perched over scenic hillside views, complete with catch basin, travertine paving, and timber deck.",
            imageUrl = "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?auto=format&fit=crop&w=1000&q=70",
            tags = listOf("Infinity Edge", "Travertine", "Harare", "Luxury")
        ),
        ShowcaseProject(
            id = "proj_3",
            title = "Courtyard Plunge & Braai Terrace",
            location = "Highlands, Harare",
            category = "Plunge",
            dimensions = "3.5m × 2.2m",
            buildTime = "2.5 weeks",
            approxPrice = "US$3,850",
            description = "Compact plunge pool with integrated seating bench and hydro-massage jets, designed for a modern cluster home.",
            imageUrl = "https://images.unsplash.com/photo-1571863533956-01c88e79957e?auto=format&fit=crop&w=1000&q=70",
            tags = listOf("Plunge", "Compact", "Highlands", "Jets")
        ),
        ShowcaseProject(
            id = "proj_4",
            title = "Resort Deck & Landscaped Pool",
            location = "Glen Lorne, Harare",
            category = "Landscaping",
            dimensions = "8m × 4m with 45m² Deck",
            buildTime = "5 weeks",
            approxPrice = "US$13,400",
            description = "Turnkey pool and garden transformation: hardwood teak decking, ambient evening ground lights, and tropical irrigated palms.",
            imageUrl = "https://images.unsplash.com/photo-1585421514738-01798e348b17?auto=format&fit=crop&w=1000&q=70",
            tags = listOf("Landscaping", "Timber Deck", "Glen Lorne")
        ),
        ShowcaseProject(
            id = "proj_5",
            title = "Family Classic with Sun Shelf",
            location = "Avondale, Harare",
            category = "Gunite",
            dimensions = "6m × 3.5m",
            buildTime = "3.5 weeks",
            approxPrice = "US$7,100",
            description = "Classic rectangular gunite pool featuring a 2m wide toddler sun shelf (baja shelf) and child safety fencing.",
            imageUrl = "https://images.unsplash.com/photo-1576013551627-0cc20b96c2a7?auto=format&fit=crop&w=1000&q=70",
            tags = listOf("Gunite", "Sun Shelf", "Family", "Avondale")
        ),
        ShowcaseProject(
            id = "proj_6",
            title = "Safari Lodge Lagoon Pool",
            location = "Norton Lakeside Resort, Norton",
            category = "Commercial",
            dimensions = "14m × 6m",
            buildTime = "7 weeks",
            approxPrice = "US$21,500",
            description = "Commercial grade lagoon pool for lodge visitors with natural rock waterfall, zero-depth beach entry, and high-volume commercial filtration.",
            imageUrl = "https://images.unsplash.com/photo-1466692476868-aef1dfb1e735?auto=format&fit=crop&w=1000&q=70",
            tags = listOf("Commercial", "Lodge", "Waterfall", "Beach Entry")
        )
    )

    val poolSchoolGuides: List<PoolSchoolGuide> = listOf(
        PoolSchoolGuide(
            id = "guide_1",
            title = "Gunite vs Fiberglass: Which is Best for Zimbabwean Soil?",
            category = "Engineering",
            readTime = "4 min read",
            summary = "Understanding red clay, gravel, and sandy loam ground conditions in Norton and Harare, and why structural shell choice matters.",
            content = listOf(
                "Zimbabwe's soils vary dramatically between areas. Regions like Norton, Westgate, and parts of Borrowdale have heavy red soils and expansive clays that contract during the dry winter and expand heavily in the rainy season.",
                "Why Gunite wins for expansive soils: Gunite (pneumatically projected shotcrete over an engineered rebar cage) creates a continuous, rigid monolithic shell with superior flexural strength. At Dzimbabwe Pools, our gunite shells carry a 10-year structural warranty specifically engineered to withstand ground movement.",
                "When Fiberglass makes sense: In stable, sandy loam or rocky grounds (like parts of Chisipite or Mount Pleasant), high-grade fiberglass offers faster 7–10 day installations and low algae adherence.",
                "Our recommendation: Book a professional site survey (US$50). Our structural engineer tests soil density and water tables on-site before finalizing the pool design."
            )
        ),
        PoolSchoolGuide(
            id = "guide_2",
            title = "The True Cost of Building a Swimming Pool in Zimbabwe",
            category = "Pricing & Budget",
            readTime = "5 min read",
            summary = "A transparent look at excavation, shells, equipment, council plans, and why Dzimbabwe Pools saves clients typically 32%.",
            content = listOf(
                "Typical pool builders in Harare and Bulawayo mark up equipment and subcontract multiple teams, pushing an ordinary 5×3m pool up to US$8,500 – US$10,000.",
                "How Dzimbabwe Pools keeps quality high at ~32% below market: We employ an in-house crew of excavators, steel fixers, gunite technicians, and landscapers. One contract, one crew, and zero middlemen.",
                "Key cost components you should always look for in a quote: Shell & rebar structure, bullnose non-slip coping, 1.0HP pool pump, sand filter with graded silica sand, surface skimmer, weir, return jets, LED light, and electrical box.",
                "Fixed-price guarantee: Once our site survey is done, our quote is 100% locked. We never hit you with unexpected 'rock fees' or 'variation costs'."
            )
        ),
        PoolSchoolGuide(
            id = "guide_3",
            title = "Overcoming Load Shedding: Solar Pump & Inverter Pool Systems",
            category = "Energy & Green Tech",
            readTime = "3 min read",
            summary = "How to keep your pool water circulating and crystal clean without relying on ZESA grid power.",
            content = listOf(
                "Power cuts in Zimbabwe can cause pool water to turn green within 72 hours in summer if filtration stops.",
                "Direct Solar DC Pumps: We install DC solar pool pumps connected directly to 2 to 4 solar panels with no expensive batteries needed. As long as the sun shines, the pool circulates and sanitises automatically.",
                "Inverter Integration: If you already have a 3kVA or 5kVA home solar setup, we supply ultra-efficient variable-speed (VFD) pumps that draw as little as 250W during low-speed filtration, running effortlessly off your backup system.",
                "Ask our engineer during your site visit about solar pump options for complete energy independence."
            )
        ),
        PoolSchoolGuide(
            id = "guide_4",
            title = "Council Approvals & Bylaws: Norton, Harare & Bulawayo",
            category = "Regulations",
            readTime = "3 min read",
            summary = "Local authority requirements, property boundary setbacks, and child safety fence standards.",
            content = listOf(
                "Do you need town council plans? Yes, most municipalities (Norton Town Council, City of Harare, Bulawayo) require an approved pool plan showing boundary setbacks (typically 1.5m to 2.0m from neighboring perimeter walls).",
                "We handle the paperwork: Dzimbabwe Pools drafts and submits the municipal architectural plan on your behalf, typically clearing approvals in 3 to 10 working days.",
                "Child safety compliance: We strongly advocate for self-closing security fences with magnetic latches or reinforced pool safety nets to protect children and pets."
            )
        ),
        PoolSchoolGuide(
            id = "guide_5",
            title = "Rainy Season Care: Rescuing Green Water in 48 Hours",
            category = "Maintenance",
            readTime = "4 min read",
            summary = "Managing heavy downpours, pH balance, and chlorine depletion during November through March.",
            content = listOf(
                "Summer rain in Zimbabwe washes airborne dust, pollen, and nitrogen into the water, rapidly destroying free available chlorine.",
                "Step 1: Check and balance pH to 7.2 – 7.6. Chlorine is 80% ineffective if pH rises above 7.8.",
                "Step 2: Shock dose with HTH granular chlorine (usually 2 to 3 cups per 50,000L) dissolved in a bucket of water around the deep end.",
                "Step 3: Run the pump continuously for 24 hours, backwash the sand filter every 6 hours, and add a clarifier or flocculant to drop suspended silt.",
                "Did you know? Every new Dzimbabwe Pools build includes 6 months of free aftercare, including all testing and balancing chemicals!"
            )
        )
    )

    val faqItems: List<FAQItem> = listOf(
        FAQItem(
            question = "How much does a swimming pool cost in Zimbabwe?",
            answer = "Plunge pools start from US$3,500, a standard family 5×3m gunite pool is US$6,500, and grand resort pools with water features start from US$11,500. Our prices are typically 32% below market competitors for the exact same high-strength engineering spec.",
            category = "Pricing"
        ),
        FAQItem(
            question = "How long does pool construction take?",
            answer = "A typical gunite family pool takes 3 to 6 weeks from excavation to first swim. Fiberglass pools take 7 to 10 days once the shell arrives on site. Landscaping and decking additions usually require 1 to 2 weeks.",
            category = "Timeline"
        ),
        FAQItem(
            question = "Do you handle town council plans and approvals?",
            answer = "Yes! We handle structural drawings and submission to local councils (Norton Town Council, City of Harare, etc.). Approvals typically take 3 to 10 working days and are covered under our fixed-price service.",
            category = "Approvals"
        ),
        FAQItem(
            question = "What warranty do you provide?",
            answer = "We provide a 10-year structural warranty on all gunite/concrete pool shells, a 2-year warranty on filtration equipment and electrical pumps, and 6 months of complimentary aftercare maintenance.",
            category = "Warranty"
        ),
        FAQItem(
            question = "How do payment milestones work?",
            answer = "Payment is split transparently across 3 milestones: 50% deposit upon contract signing, 40% when the concrete shell and plumbing are complete, and 10% on handover after final commissioning. We accept USD cash, bank transfer, ZiG, and EcoCash.",
            category = "Payment"
        ),
        FAQItem(
            question = "What happens during the US$50 professional site survey?",
            answer = "A senior engineer visits your property in Norton, Harare, or surrounding areas. We assess soil type, ground levels, water pressure, machine access, and sun angles. On the spot, we sketch a custom 3D concept and provide a locked fixed-price quotation within 48 hours.",
            category = "Site Survey"
        ),
        FAQItem(
            question = "Can you bundle landscaping and paving with the pool?",
            answer = "Yes! Dzimbabwe Pools is a unified pool & landscaping contractor. Bundling your pool build with lawns, paving, timber decking, or irrigation gives you a 10% to 15% discount, with one crew, one contract, and one warranty.",
            category = "Services"
        )
    )
}
