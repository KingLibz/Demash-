package com.example.data.model

import java.util.UUID

data class AiEmployee(
    val id: String,
    val name: String,
    val role: String,
    val department: String,
    val yearsExperience: Int,
    val specialties: List<String>,
    val activeLeadsHandled: Int,
    val conversionRatePercent: Double,
    val responseTimeSec: Double,
    val avatarInitial: String,
    val status: String = "Active (24/7)"
)

data class LiveClientSession(
    val sessionId: String = UUID.randomUUID().toString(),
    val clientName: String,
    val suburb: String,
    val assignedEmployee: AiEmployee,
    val lastMessage: String,
    val lastReply: String,
    val timestamp: Long = System.currentTimeMillis(),
    val estimatedProjectValueUsd: Int,
    val isHijackedByOwner: Boolean = false,
    val chatHistory: List<Pair<String, String>> // List of (Sender, Message)
)

object AiWorkforceData {

    val employees: List<AiEmployee> = listOf(
        AiEmployee(
            id = "tinashe_moyo",
            name = "Eng. Tinashe Moyo",
            role = "Senior Structural & Geotechnical Engineer",
            department = "Engineering & Excavation",
            yearsExperience = 12,
            specialties = listOf("Monolithic Gunite Shells", "Norton Red Clays", "Borrowdale Granite Bedrock", "Cantilevered Infinity Edges", "10-Year Structural Calculations"),
            activeLeadsHandled = 342,
            conversionRatePercent = 41.8,
            responseTimeSec = 0.35,
            avatarInitial = "TM"
        ),
        AiEmployee(
            id = "ruvimbo_chidemo",
            name = "Arch. Ruvimbo Chidemo",
            role = "Principal Landscape & 3D Aesthetics Designer",
            department = "Landscaping, Decks & 3D Modeling",
            yearsExperience = 8,
            specialties = listOf("3D Concept Sketches", "Teak & Saligna Timber Decks", "Indigenous Harare Planting", "Pergolas & Braai Pavilions", "Sheer Descent Waterfalls"),
            activeLeadsHandled = 289,
            conversionRatePercent = 38.5,
            responseTimeSec = 0.40,
            avatarInitial = "RC"
        ),
        AiEmployee(
            id = "farai_mupfumi",
            name = "QS Farai Mupfumi",
            role = "Chief Quantity Surveyor & Procurement Lead",
            department = "Quotation Engine & Materials",
            yearsExperience = 10,
            specialties = listOf("Micro Bill of Quantities", "34–40% Profit Optimization", "Cement & Rebar Sourcing", "Zero Hidden Extras", "Fixed-Price Cost Control"),
            activeLeadsHandled = 398,
            conversionRatePercent = 44.2,
            responseTimeSec = 0.28,
            avatarInitial = "FM"
        ),
        AiEmployee(
            id = "tatenda_chiwara",
            name = "Tatenda Chiwara",
            role = "VIP Concierge & Commercial Director",
            department = "High-Net-Worth Sales & Diaspora Relations",
            yearsExperience = 9,
            specialties = listOf("High-Net-Worth Client Psychology", "Diaspora Video Updates (UK/SA/Dubai)", "Site Visit Scheduling", "Contract Milestone Payments", "Rapid Closing"),
            activeLeadsHandled = 420,
            conversionRatePercent = 46.5,
            responseTimeSec = 0.25,
            avatarInitial = "TC"
        )
    )

    fun getInitialClientSessions(): List<LiveClientSession> = emptyList()
}
