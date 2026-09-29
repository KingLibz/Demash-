package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PoolType(
    val id: String,
    val displayName: String,
    val basePrice: Double,
    val ratePerSqm: Double,
    val description: String
) {
    GUNITE(
        id = "gunite",
        displayName = "Gunite (Concrete)",
        basePrice = 2500.0,
        ratePerSqm = 265.0,
        description = "Resort-grade shotcrete shell built on-site. Unlimited custom shapes, 10-year structural warranty, marbelite or mosaic finish."
    ),
    FIBERGLASS(
        id = "fiberglass",
        displayName = "Fiberglass Shell",
        basePrice = 2700.0,
        ratePerSqm = 280.0,
        description = "Pre-formed smooth composite shell. Rapid 7–10 day install, algae-resistant surface, excellent for stable soil."
    ),
    VINYL(
        id = "vinyl",
        displayName = "Vinyl-Lined",
        basePrice = 2000.0,
        ratePerSqm = 210.0,
        description = "Engineered steel/polymer walls with heavy-gauge pool liner. Smooth to touch, cost-effective maintenance."
    ),
    PLUNGE(
        id = "plunge",
        displayName = "Plunge Pool",
        basePrice = 2000.0,
        ratePerSqm = 235.0,
        description = "Compact luxury footprint for townhouse yards and smaller garden courtyards. Fast 2–3 week build."
    ),
    INFINITY(
        id = "infinity",
        displayName = "Infinity-Edge (Rim-Flow)",
        basePrice = 5000.0,
        ratePerSqm = 420.0,
        description = "Architectural vanishing-edge with catch basin, surge tank, and dual-speed filtration. Stunning on slopes."
    ),
    SPLASH(
        id = "splash",
        displayName = "Splash / Semi-Inground",
        basePrice = 1000.0,
        ratePerSqm = 120.0,
        description = "Economical splash pool designed for young families, braai zones, or shallow relaxation patios."
    ),
    BRICK(
        id = "brick",
        displayName = "Brick / Masonry Pool",
        basePrice = 2200.0,
        ratePerSqm = 210.0,
        description = "Reinforced brickwork with concrete core and ring beams. Engineer-approved structural specs for tough terrain."
    ),
    SCHOOL(
        id = "school",
        displayName = "School / Training Pool",
        basePrice = 6000.0,
        ratePerSqm = 195.0,
        description = "Institutional semi-Olympic training facility (15m–25m) with anti-turbulent lane ropes, starting blocks & heavy filtration."
    ),
    COMMERCIAL(
        id = "commercial",
        displayName = "Commercial / Lodge Pool",
        basePrice = 5000.0,
        ratePerSqm = 220.0,
        description = "Heavy-traffic resort & safari lodge pools engineered for high bather loads with automated commercial filtration."
    )
}

enum class PoolShape(
    val id: String,
    val displayName: String,
    val multiplier: Double
) {
    RECTANGULAR("rectangular", "Rectangular (Classic)", 1.0),
    FREEFORM("freeform", "Freeform / Lagoon Curve", 1.08),
    GEOMETRIC("geometric", "Geometric / Roman End", 1.05),
    LAP("lap", "Lap Pool (Long & Sleek)", 1.02),
    L_SHAPED("lshape", "L-Shaped", 1.10)
}

data class PoolPreset(
    val id: String,
    val name: String,
    val length: Double,
    val width: Double,
    val poolType: PoolType,
    val shape: PoolShape,
    val subtitle: String,
    val isPopular: Boolean = false
)

data class EstimateResult(
    val length: Double,
    val width: Double,
    val poolType: PoolType,
    val shape: PoolShape,
    val surfaceAreaM2: Double,
    val estimatedVolumeLiters: Int,
    val estimatedDepthM: String,
    val recommendedPumpHp: String,
    val priceUsd: Double,
    val marketPriceUsd: Double,
    val savingsUsd: Double,
    val savingsPct: Int
)

@Entity(tableName = "saved_estimates")
data class SavedEstimate(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val poolType: String,
    val shape: String,
    val length: Double,
    val width: Double,
    val priceUsd: Double,
    val marketPriceUsd: Double,
    val savingsUsd: Double,
    val savingsPct: Int,
    val dateCreated: Long = System.currentTimeMillis(),
    val notes: String = ""
)

@Entity(tableName = "booking_requests")
data class BookingRequest(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientName: String,
    val phone: String,
    val suburb: String,
    val poolType: String,
    val poolDimensions: String,
    val surveyType: String = "Site Survey & 3D Concept Sketch (US$50)",
    val preferredDate: String = "Immediate / This Week",
    val status: String = "Submitted",
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class PoolService(
    val id: String,
    val title: String,
    val startingPrice: String,
    val description: String,
    val keyFeatures: List<String>,
    val iconName: String
)

data class PricingPackage(
    val id: String,
    val name: String,
    val dimensions: String,
    val buildTime: String,
    val priceUsd: Int,
    val marketPriceUsd: Int,
    val savePercent: Int,
    val highlights: List<String>,
    val isFeatured: Boolean = false,
    val imageUrl: String
)

data class ShowcaseProject(
    val id: String,
    val title: String,
    val location: String,
    val category: String,
    val dimensions: String,
    val buildTime: String,
    val approxPrice: String,
    val description: String,
    val imageUrl: String,
    val tags: List<String>
)

data class PoolSchoolGuide(
    val id: String,
    val title: String,
    val category: String,
    val readTime: String,
    val summary: String,
    val content: List<String>
)

data class FAQItem(
    val question: String,
    val answer: String,
    val category: String
)
