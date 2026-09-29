package com.example.data.model

data class BuyingIntentLead(
    val id: String,
    val clientName: String,
    val suburb: String,
    val phone: String,
    val intentScorePct: Int, // e.g. 98%
    val intentSignal: String,
    val budgetUsd: Int,
    val preferredPoolType: String,
    val preferredDimensions: String,
    val timeline: String,
    val fundingStatus: String // "Cash Ready", "Nostro / Diaspora Funds Verified", "Ready for 50% Deposit"
)

object BuyingIntentData {

    val freshBuyingLeads: List<BuyingIntentLead> = emptyList()

    fun getClosingScript(lead: BuyingIntentLead): String {
        return """
            Mhoroi / Good day ${lead.clientName}!
            This is Eng. Liberman Magaya, Director at Demash Dzimbabwe Pools.
            We received your high-priority inquiry for a ${lead.preferredPoolType} (${lead.preferredDimensions}) at your property in ${lead.suburb}.
            Our team has analyzed your site requirements and we are ready with our CAT 320 excavator and shotcrete crew for immediate mobilization.
            We lock fixed-price contracts with a 10-Year structural guarantee.
            Can we dispatch our structural survey engineer to your property today to finalize your 3D layout?
            Direct Office: K17412 Katanga, Norton | WhatsApp/Call: +263 78 421 9178
        """.trimIndent()
    }
}
