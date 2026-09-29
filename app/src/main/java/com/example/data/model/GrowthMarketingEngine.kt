package com.example.data.model

data class MarketingCampaign(
    val id: String,
    val title: String,
    val platform: String, // "WhatsApp Status", "Instagram / Facebook Ads", "Diaspora UK/SA/Dubai", "Radio & Press", "Estate Gate Blitz"
    val targetArea: String,
    val language: String,
    val hook: String,
    val adCopy: String,
    val estimatedReach: String,
    val conversionRateEstimate: String,
    val callToAction: String
)

object GrowthMarketingEngine {

    val activeCampaigns: List<MarketingCampaign> = listOf(
        MarketingCampaign(
            id = "wa_status_blitz",
            title = "WhatsApp Status Overnight Sales Blitz",
            platform = "WhatsApp Status & Broadcast",
            targetArea = "Harare, Norton & Bulawayo Homeowners",
            language = "English & ChiShona",
            hook = "🏊 Ndiani asati aine swimming pool mumba make gore rino? (Save 32% with Demash Pools)",
            adCopy = """
                🌊 Vaka Swimming Pool yeumbozha pamba penyu neUS$3,500 chete ne Demash Dzimbabwe Pools! 
                ✅ Monolithic Gunite shell yakasimba zvekuti inogara makore 50+
                🛡️ 10-Year structural guarantee
                ⚡ 3D concept sketch yegarden yenyu paUS$50 site visit
                💰 Typically 32% cheaper than typical Harare contractors!
                📲 Bata Engineer wacho paWhatsApp: +263 78 421 9178
            """.trimIndent(),
            estimatedReach = "45,000+ Status Views",
            conversionRateEstimate = "6.8% Inquiries",
            callToAction = "Tap to Copy & Share to WhatsApp"
        ),
        MarketingCampaign(
            id = "meta_borrowdale_glenlorne",
            title = "Borrowdale & Glen Lorne Rim-Flow Sponsor",
            platform = "Instagram / Facebook Ads",
            targetArea = "Borrowdale Brooke, Glen Lorne, Shawasha Hills",
            language = "English",
            hook = "Turn your hillside slope into an architectural vanishing-edge paradise.",
            adCopy = """
                Perched on the kopjes of Glen Lorne or overlooking the Brooke fairway? 
                Demash Pools engineers cantilevered rim-flow infinity pools with integrated teak decking and variable-speed solar pumps. 
                Zero noise, 100% ZESA load-shedding proof, locked fixed-price contract.
                🎉 Book your structural survey + 3D sketch today.
                Direct WhatsApp: +263 78 421 9178
            """.trimIndent(),
            estimatedReach = "85,000 High-Income Feeds",
            conversionRateEstimate = "4.2% Bookings",
            callToAction = "Tap to Copy Meta Ad Copy"
        ),
        MarketingCampaign(
            id = "diaspora_uk_sa",
            title = "UK & SA Diaspora Building Trust Campaign",
            platform = "Diaspora UK/SA/Dubai Channels",
            targetArea = "Zimbabweans in UK, South Africa, Australia & UAE",
            language = "English & ChiShona & IsiNdebele",
            hook = "Building back home in Zimbabwe? Never get short-changed again.",
            adCopy = """
                UK / SA Diaspora Special: Build your family's dream resort pool in Harare or Norton with zero stress!
                Demash Dzimbabwe Pools delivers weekly 4K Drone video updates sent directly to your phone.
                💳 Transparent 3-stage milestone payments: 50% deposit, 40% shell, 10% on handover.
                📄 Formal BOQ down to the cement bag and screw. 10-Year structural warranty.
                Vaka newaranti yemakore gumi! 
                WhatsApp our VIP Diaspora Concierge: +263 78 421 9178
            """.trimIndent(),
            estimatedReach = "120,000 Diaspora Expats",
            conversionRateEstimate = "8.4% Conversions",
            callToAction = "Tap to Copy Diaspora Pitch"
        ),
        MarketingCampaign(
            id = "ndebele_bulawayo",
            title = "Bulawayo & Matabeleland Regional Launch",
            platform = "Radio & Social Media",
            targetArea = "Bulawayo (Burnside, Hillside, Suburbs)",
            language = "IsiNdebele",
            hook = "Sakha amachibi okuqubha aphambili eBulawayo ngentengo ephansi ngo-32%.",
            adCopy = """
                Salibonani Bulawayo! Ufuna ichibi lokuqubha elihle emzini wakho?
                I-Demash Pools isilapha eBulawayo. Sakha amachibi e-gunite le-fiberglass ale-10 year warranty.
                Kuhlanganisa ukuhlolwa kwensimu le-3D concept sketch nge-US$50 kuphela.
                Xoxa lomphathi wethu ku-WhatsApp: +263 78 421 9178
            """.trimIndent(),
            estimatedReach = "60,000 Listeners & Users",
            conversionRateEstimate = "5.5% Inquiries",
            callToAction = "Tap to Copy Ndebele Copy"
        ),
        MarketingCampaign(
            id = "norton_home_territory",
            title = "Norton Hometown Champion Campaign",
            platform = "Estate Gate & Local Blitz",
            targetArea = "Norton (Katanga, Galloway, Knowe, Marshlands)",
            language = "ChiShona & English",
            hook = "Tiri muNorton! Hapana mari dzekufambira kure (Zero transport surcharges).",
            adCopy = """
                Vagari vemuNorton: Knowe, Galloway, Katanga neLakeside!
                Demash Pools ndeyemuno muNorton paKatanga. Makambani ekuHarare anokubhadharisai 'transport' yakawanda.
                Isu tiri pano, tinosvika mumaminitsi gumi tichiverenga mutengo wepool yenyu.
                Zviri nyore: Plunge pool kubva paUS$3,500, family pool US$6,500.
                Bata timu yemuno: +263 78 421 9178
            """.trimIndent(),
            estimatedReach = "30,000 Norton Residents",
            conversionRateEstimate = "11.2% Conversion",
            callToAction = "Tap to Copy Norton Local Ad"
        )
    )
}
