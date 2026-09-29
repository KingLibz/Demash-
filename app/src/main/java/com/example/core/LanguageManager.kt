package com.example.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppLanguage(val code: String, val displayName: String, val shortLabel: String) {
    ENGLISH("en", "English", "EN"),
    SHONA("sn", "ChiShona", "SN"),
    NDEBELE("nd", "IsiNdebele", "ND")
}

object LanguageManager {
    private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun setLanguage(lang: AppLanguage) {
        _currentLanguage.value = lang
    }

    // High-speed phrase translator
    fun t(key: String, lang: AppLanguage = _currentLanguage.value): String {
        return translations[key]?.get(lang) ?: translations[key]?.get(AppLanguage.ENGLISH) ?: key
    }

    private val translations = mapOf(
        "app_title" to mapOf(
            AppLanguage.ENGLISH to "Dzimbabwe Pools",
            AppLanguage.SHONA to "Dzimbabwe Pools",
            AppLanguage.NDEBELE to "Dzimbabwe Pools"
        ),
        "tagline" to mapOf(
            AppLanguage.ENGLISH to "Resort-Grade Pools & Landscapes",
            AppLanguage.SHONA to "Madziva eKutuhwina eMhando Yepamusoro",
            AppLanguage.NDEBELE to "Amachibi Okuqubha Asezingeni Eliphezulu"
        ),
        "hero_headline" to mapOf(
            AppLanguage.ENGLISH to "Resort-grade pools & landscapes, built for Zimbabwe.",
            AppLanguage.SHONA to "Madziva eumbozha nemapindu, akavakwa muZimbabwe.",
            AppLanguage.NDEBELE to "Amachibi aphambili lezivande, ezakhelwe iZimbabwe."
        ),
        "hero_sub" to mapOf(
            AppLanguage.ENGLISH to "Gunite, fiberglass & vinyl pools — typically 32% below typical market rates. Fixed-price contracts & 10-year structural warranty.",
            AppLanguage.SHONA to "Madziva eGunite, fiberglass nevinyl — mutengo wakaderera ne32%. Chibvumirano chisingachinje newaranti yemakore gumi.",
            AppLanguage.NDEBELE to "Amachibi eGunite, fiberglass le-vinyl — ngentengo ephansi ngo-32%. Isivumelwano esingantshinsthiyo lesiqiniseko seminyaka elitshumi."
        ),
        "estimate_cost" to mapOf(
            AppLanguage.ENGLISH to "Estimate Cost",
            AppLanguage.SHONA to "Verenga Mutengo",
            AppLanguage.NDEBELE to "Bala Intengo"
        ),
        "book_survey" to mapOf(
            AppLanguage.ENGLISH to "Book Survey",
            AppLanguage.SHONA to "Kumbira Kushanyirwa",
            AppLanguage.NDEBELE to "Cela Ukuhlolwa"
        ),
        "instant_estimator" to mapOf(
            AppLanguage.ENGLISH to "Your Price in 10 Seconds",
            AppLanguage.SHONA to "Mutengo Wenyu Mumasekonzi Gumi",
            AppLanguage.NDEBELE to "Intengo Yakho Emizuzwini Elishumi"
        ),
        "popular_builds" to mapOf(
            AppLanguage.ENGLISH to "Most Popular Builds Across Zimbabwe",
            AppLanguage.SHONA to "Madziva Anodiwa Zvikuru muZimbabwe",
            AppLanguage.NDEBELE to "Amachibi Athandwa Kakhulu eZimbabwe"
        ),
        "fixed_price" to mapOf(
            AppLanguage.ENGLISH to "Fixed-Price Contract",
            AppLanguage.SHONA to "Mutengo Wakavharwa",
            AppLanguage.NDEBELE to "Intengo Evaliweyo"
        ),
        "warranty_10yr" to mapOf(
            AppLanguage.ENGLISH to "10-Year Warranty",
            AppLanguage.SHONA to "Waranti yeMakore 10",
            AppLanguage.NDEBELE to "Isiqiniseko Seminyaka 10"
        ),
        "aftercare_free" to mapOf(
            AppLanguage.ENGLISH to "6 Months Free Care",
            AppLanguage.SHONA to "Mwedzi 6 Yekuchengetwa Pachena",
            AppLanguage.NDEBELE to "Inyanga ezi-6 Zokunakekelwa Mahhala"
        ),
        "nav_explore" to mapOf(
            AppLanguage.ENGLISH to "Explore",
            AppLanguage.SHONA to "Kutsvaga",
            AppLanguage.NDEBELE to "Hlola"
        ),
        "nav_estimator" to mapOf(
            AppLanguage.ENGLISH to "Estimator",
            AppLanguage.SHONA to "Mutengo",
            AppLanguage.NDEBELE to "Isilinganiso"
        ),
        "nav_showcase" to mapOf(
            AppLanguage.ENGLISH to "Showcase",
            AppLanguage.SHONA to "Mifananidzo",
            AppLanguage.NDEBELE to "Imisebenzi"
        ),
        "nav_guides" to mapOf(
            AppLanguage.ENGLISH to "Pool School",
            AppLanguage.SHONA to "Dzidzo yePool",
            AppLanguage.NDEBELE to "Isikolo Samachibi"
        ),
        "nav_booking" to mapOf(
            AppLanguage.ENGLISH to "Book / Chat",
            AppLanguage.SHONA to "Kubhuka / Kutaura",
            AppLanguage.NDEBELE to "Bhuka / Xoxa"
        ),
        "demash_title" to mapOf(
            AppLanguage.ENGLISH to "Demash Dzimbabwe Pools",
            AppLanguage.SHONA to "Demash Dzimbabwe Pools",
            AppLanguage.NDEBELE to "Demash Dzimbabwe Pools"
        ),
        "excavation_labor" to mapOf(
            AppLanguage.ENGLISH to "Excavation & Earthwork Labor",
            AppLanguage.SHONA to "Kuchera & Vashandi vekuVhura Pasi",
            AppLanguage.NDEBELE to "Ukumba & Izisebenzi zoMhlabathi"
        ),
        "machinery_haulage" to mapOf(
            AppLanguage.ENGLISH to "Machinery Hire & Site Mobilization",
            AppLanguage.SHONA to "Kuhaya ma-Excavator neKutakura Michina",
            AppLanguage.NDEBELE to "Ukuhaya ama-Excavator lokuThwala Imitshina"
        ),
        "rebar_steel" to mapOf(
            AppLanguage.ENGLISH to "High-Tensile Rebar Steel Grid",
            AppLanguage.SHONA to "Simbi dzeRebar neWayawaya",
            AppLanguage.NDEBELE to "Izinsimbi zeRebar eziQinileyo"
        ),
        "gunite_shotcrete" to mapOf(
            AppLanguage.ENGLISH to "30MPa Pneumatic Gunite Shotcrete",
            AppLanguage.SHONA to "Simende yeGunite Inopfapfaidzwa neMhepo",
            AppLanguage.NDEBELE to "I-Gunite eTshaywa ngoMoya oQinileyo"
        ),
        "solar_pump" to mapOf(
            AppLanguage.ENGLISH to "Solar DC Eco-Pump (ZESA-Free)",
            AppLanguage.SHONA to "Pombi yeZuva yeSolar (Hapana ZESA)",
            AppLanguage.NDEBELE to "I-Pump yeLanga (Akula ZESA)"
        ),
        "contractor_margin" to mapOf(
            AppLanguage.ENGLISH to "Fair Corporate Margin (34–40%)",
            AppLanguage.SHONA to "Mubairo weKambani Wakachena (34–40%)",
            AppLanguage.NDEBELE to "Inzuzo yeNkampani eLungileyo (34–40%)"
        ),
        "instant_whatsapp_quote" to mapOf(
            AppLanguage.ENGLISH to "Send Detailed Quote via WhatsApp",
            AppLanguage.SHONA to "Tumira Quotation yakazara paWhatsApp",
            AppLanguage.NDEBELE to "Thumela i-Quotation epheleleyo ku-WhatsApp"
        )
    )

    fun getLanguageGreeting(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.ENGLISH -> "Welcome to Demash Dzimbabwe Pools! World-class resort-grade construction across Zimbabwe."
            AppLanguage.SHONA -> "Mauya kuDemash Dzimbabwe Pools! Tinovaka madziva eumbozha emazuva ano muZimbabwe yose."
            AppLanguage.NDEBELE -> "Samukele kuDemash Dzimbabwe Pools! Sakha amachibi aphambili kuyo yonke iZimbabwe."
        }
    }
}
