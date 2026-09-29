package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.core.PerformanceEngine
import com.example.data.model.AiEmployee
import com.example.data.model.BookingRequest
import com.example.data.model.BuyingIntentData
import com.example.data.model.BuyingIntentLead
import com.example.data.model.ConstructionContact
import com.example.data.model.LiveClientSession
import com.example.data.model.MachineryMobilizationItem
import com.example.data.model.MarketingCampaign
import com.example.data.model.MaterialItem
import com.example.data.model.SuburbIntelligenceData
import com.example.data.model.SuburbProfile
import com.example.data.model.TradeHiringData
import com.example.data.model.TradeJobVacancy
import com.example.data.model.WorkerLaborAllocation
import com.example.ui.components.openDialer
import com.example.ui.components.openWhatsApp
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolBorderGold
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolGreen
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolSurfaceElevated
import com.example.ui.theme.PoolSurfaceHighlight
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary
import com.example.ui.theme.PoolTextSecondary
import com.example.viewmodel.MainViewModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun AdminControlRoomScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var adminSubTab by remember { mutableIntStateOf(0) }

    val aiEmployees by viewModel.aiEmployees.collectAsState()
    val liveSessions by viewModel.liveClientSessions.collectAsState()
    val selectedSessionForHijack by viewModel.selectedSessionForHijack.collectAsState()
    val currentBOQ by viewModel.currentBOQ.collectAsState()
    val targetMargin by viewModel.targetProfitMargin.collectAsState()
    val selectedDirCat by viewModel.selectedDirectoryCategory.collectAsState()
    val directoryContacts = viewModel.constructionContacts
    val campaigns = viewModel.activeCampaigns

    val memoryUsageMb by PerformanceEngine.memoryUsageMb.collectAsState()
    val cacheSizeKb by PerformanceEngine.cacheSizeKb.collectAsState()
    val calculationLatencyMicros by PerformanceEngine.realCalculationLatencyMicros.collectAsState()
    val volatileLogs by PerformanceEngine.volatileAuditLog.collectAsState()
    val realClients by viewModel.savedBookings.collectAsState()
    val buyingLeads by viewModel.buyingLeads.collectAsState()
    val tradeVacancies by viewModel.tradeVacancies.collectAsState()
    val accountsPromoCreditUsd by viewModel.accountsPromoCreditUsd.collectAsState()
    val accountsPromotionsLocked by viewModel.accountsPromotionsLocked.collectAsState()
    val accountsMinDepositPercent by viewModel.accountsMinContractDepositPercent.collectAsState()
    val syncTelemetry by viewModel.syncTelemetry.collectAsState()
    val systemHealthScore by viewModel.systemHealthScore.collectAsState()
    val isImmuneShieldActive by viewModel.isImmuneShieldActive.collectAsState()
    val healingIncidentsCount by viewModel.healingIncidentsCount.collectAsState()
    val performanceCapacityMultiplier by viewModel.performanceCapacityMultiplier.collectAsState()
    val diagnosticAudit by viewModel.diagnosticAudit.collectAsState()

    var showAddClientDialog by remember { mutableStateOf(false) }
    var showAddBuyingLeadDialog by remember { mutableStateOf(false) }
    var showAddJobDialog by remember { mutableStateOf(false) }
    var clientFilterStage by remember { mutableStateOf("All") }

    val currencyFmt = remember {
        NumberFormat.getNumberInstance(Locale.US).apply { maximumFractionDigits = 0 }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PoolDarkBg)
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Executive Command Banner
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, PoolGold, RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(PoolGold),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = PoolDarkBg,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "DZ EXECUTIVE CONTROL ROOM",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PoolGold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Owner Command Center · Liberman Magaya",
                                    fontSize = 11.sp,
                                    color = PoolTextPrimary
                                )
                            }
                        }

                        IconButton(
                            onClick = { viewModel.toggleAdminMode(false) },
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(PoolSurface)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Exit Admin",
                                tint = PoolTextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Live Engine Performance Grid
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(PoolSurface)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = PoolGreen, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Compute Latency", fontSize = 10.sp, color = PoolTextMuted)
                            }
                            Text("${calculationLatencyMicros}µs (Instant)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolGreen)
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = PoolGold, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Real Client Deals", fontSize = 10.sp, color = PoolTextMuted)
                            }
                            Text("${realClients.size} in Room DB", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolGold)
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = PoolAqua, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Heap/Cache", fontSize = 10.sp, color = PoolTextMuted)
                            }
                            Text("${memoryUsageMb}MB / ${cacheSizeKb}KB", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolAqua)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Self-Clean Trigger
                    Button(
                        onClick = { viewModel.triggerSelfClean() },
                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .testTag("admin_clean_memory_button")
                    ) {
                        Icon(imageVector = Icons.Default.CleaningServices, contentDescription = null, tint = PoolAqua, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Zero-Bloat Self Clean · 60–120 FPS Locked", color = PoolAqua, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Self-Healing Immunity & Diagnostic Sentinel (V60 Shield)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, PoolGreen, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(PoolGreen)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SELF-HEALING IMMUNITY SENTINEL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PoolGreen,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Text(
                            text = "HEALTH: $systemHealthScore%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Real-Time System Auto-Diagnosis & Immunity",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = "Guarantees zero-lockout of client bookings, auto-repairs missing directories, and shields against Chromium renderer code -1 crashes.",
                        fontSize = 11.sp,
                        color = PoolTextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = PoolSurface),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Capacity Multiplier", fontSize = 9.sp, color = PoolTextMuted)
                                Text(performanceCapacityMultiplier, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolAqua)
                            }
                        }
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = PoolSurface),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Shield Status", fontSize = 9.sp, color = PoolTextMuted)
                                Text("Active · $healingIncidentsCount Healed", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolGreen)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { viewModel.triggerSelfDiagnostic() },
                        colors = ButtonDefaults.buttonColors(containerColor = PoolGreen.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .border(1.dp, PoolGreen, RoundedCornerShape(10.dp))
                    ) {
                        Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = PoolGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Run Self-Diagnostic & Verify Immunity", color = PoolGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    if (diagnosticAudit.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PoolDarkBg, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            diagnosticAudit.take(3).forEach { log ->
                                Text(
                                    text = log,
                                    fontSize = 9.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                    color = PoolTextMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // Room-to-Firestore Cloud Synchronization & Live Lead Pipeline
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (syncTelemetry.isFirestoreAvailable) PoolGreen else PoolGold)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = syncTelemetry.statusBadge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (syncTelemetry.isFirestoreAvailable) PoolGreen else PoolTextPrimary
                            )
                            Text(
                                text = "Two-Way Room ↔ Firestore · Leads Synced: ${syncTelemetry.totalLeadsSynced} · Estimates: ${syncTelemetry.totalEstimatesSynced}",
                                fontSize = 10.sp,
                                color = PoolTextMuted
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.triggerFirestoreSync() },
                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceElevated),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.border(1.dp, PoolBorderGold, RoundedCornerShape(8.dp))
                    ) {
                        Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = PoolGold, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sync Cloud", color = PoolGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Sub-tabs (5 Executive Panels)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    0 to "Real Clients & CRM",
                    1 to "🎯 Fresh Buying Leads",
                    2 to "BOQ, Labor & Machinery",
                    3 to "👷 Trade Hiring & Jobs",
                    4 to "📢 Strategic Adverts",
                    5 to "📞 Trade Call Directory",
                    6 to "💎 Rich Suburbs Intel",
                    7 to "📲 Crowd App Promotion"
                ).forEach { (index, title) ->
                    val isSel = adminSubTab == index
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSel) PoolGold else PoolSurface)
                            .border(1.dp, if (isSel) PoolGold else PoolBorder, RoundedCornerShape(10.dp))
                            .clickable { adminSubTab = index }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSel) PoolDarkBg else PoolTextPrimary
                        )
                    }
                }
            }
        }

        when (adminSubTab) {
            0 -> {
                // Section 0: Real Client CRM & WhatsApp Deal Dispatch
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "REAL CLIENT DEALS PIPELINE (${realClients.size})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PoolGold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Live prospective homeowners stored in Room Database",
                                fontSize = 11.sp,
                                color = PoolTextMuted
                            )
                        }

                        Button(
                            onClick = { showAddClientDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("admin_add_real_client_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+ Add Client", color = PoolDarkBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Filter chips
                item {
                    val stages = listOf("All", "New Inquiry", "Survey Booked ($50)", "BOQ Quoted", "Contract Signed", "Deposit Paid (50%)", "Under Construction", "Handover & 10-Yr Warranty")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        stages.forEach { stage ->
                            val isSel = clientFilterStage == stage
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) PoolAqua else PoolSurface)
                                    .border(1.dp, if (isSel) PoolAqua else PoolBorder, RoundedCornerShape(8.dp))
                                    .clickable { clientFilterStage = stage }
                                    .padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = stage,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) PoolDarkBg else PoolTextMuted
                                )
                            }
                        }
                    }
                }

                val filteredClients = if (clientFilterStage == "All") realClients else realClients.filter { it.status == clientFilterStage }

                if (filteredClients.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = PoolSurface),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("No clients currently in stage: $clientFilterStage", color = PoolTextMuted, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { showAddClientDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold)
                                ) {
                                    Text("Log New Client", color = PoolDarkBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    items(filteredClients) { client ->
                        RealClientDealCard(
                            client = client,
                            onAdvanceStage = {
                                val nextStage = when (client.status) {
                                    "New Inquiry", "Submitted" -> "Survey Booked ($50)"
                                    "Survey Booked ($50)" -> "BOQ Quoted"
                                    "BOQ Quoted" -> "Contract Signed"
                                    "Contract Signed" -> "Deposit Paid (50%)"
                                    "Deposit Paid (50%)" -> "Under Construction"
                                    "Under Construction" -> "Handover & 10-Yr Warranty"
                                    else -> "Handover & 10-Yr Warranty"
                                }
                                viewModel.updateClientStatus(client.id, nextStage)
                            },
                            onCall = { openDialer(context, client.phone) },
                            onWhatsAppSurvey = {
                                openWhatsApp(context, viewModel.getClientWhatsAppMessage(client, "SURVEY"), client.phone)
                            },
                            onWhatsAppBOQ = {
                                openWhatsApp(context, viewModel.getClientWhatsAppMessage(client, "BOQ"), client.phone)
                            },
                            onWhatsAppPayment = {
                                openWhatsApp(context, viewModel.getClientWhatsAppMessage(client, "PAYMENT"), client.phone)
                            },
                            onWhatsAppWarranty = {
                                openWhatsApp(context, viewModel.getClientWhatsAppMessage(client, "WARRANTY"), client.phone)
                            },
                            onDelete = { viewModel.deleteClient(client) }
                        )
                    }
                }

                // Real Demash Key Management & Field Foremen
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "DEMASH CORE MANAGEMENT & FIELD FOREMEN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolAqua,
                        letterSpacing = 1.sp
                    )
                }

                items(realDemashDirectors) { person ->
                    RealTeamMemberCard(
                        person = person,
                        onCall = { openDialer(context, person.phone) },
                        onWhatsApp = { openWhatsApp(context, "Hello ${person.name}, Liberman here. Job site update needed.", person.phone) }
                    )
                }

                // Volatile Audit Stream
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "VOLATILE ENGINE AUDIT LOG (ZERO DISK STORAGE)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextMuted,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(PoolSurface)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        volatileLogs.take(5).forEach { log ->
                            Text(text = log, fontSize = 10.sp, color = PoolTextSecondary)
                        }
                    }
                }
            }

            1 -> {
                // Section 1: Fresh Buying Intent Leads (High-Value Pipeline)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "🎯 FRESH BUYING INTENT LEADS (${buyingLeads.size})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PoolGold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "High-urgency homeowners & developers with verified funding ready for immediate deposits",
                                fontSize = 11.sp,
                                color = PoolTextMuted
                            )
                        }

                        Button(
                            onClick = { showAddBuyingLeadDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("admin_add_buying_lead_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+ Fresh Lead", color = PoolDarkBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Lead Metrics Summary
                item {
                    val totalBuyingPower = buyingLeads.sumOf { it.budgetUsd }
                    val avgIntent = if (buyingLeads.isNotEmpty()) buyingLeads.map { it.intentScorePct }.average().toInt() else 0
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = PoolSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Total Buying Power", fontSize = 10.sp, color = PoolTextMuted)
                                Text("US$${currencyFmt.format(totalBuyingPower)}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = PoolGold)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Avg Intent Score", fontSize = 10.sp, color = PoolTextMuted)
                                Text("$avgIntent%", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = PoolGreen)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Fast-Track Close", fontSize = 10.sp, color = PoolTextMuted)
                                Text("48 Hours", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = PoolAqua)
                            }
                        }
                    }
                }

                if (buyingLeads.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(PoolGreen.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Shield,
                                        contentDescription = null,
                                        tint = PoolGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Zero Simulated Leads · Clean Pipeline",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PoolTextPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "All simulated mockup leads have been purged. Real incoming inquiries from your website, WhatsApp, and site survey bookings will populate this live pipeline.",
                                    fontSize = 12.sp,
                                    color = PoolTextMuted,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { showAddBuyingLeadDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Log Real Client Inquiry", color = PoolDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                } else {
                    items(buyingLeads) { lead ->
                        BuyingIntentLeadCard(
                            lead = lead,
                            onCall = { openDialer(context, lead.phone) },
                            onWhatsAppClose = {
                                val script = BuyingIntentData.getClosingScript(lead)
                                openWhatsApp(context, script, lead.phone)
                            },
                            onConvertToDeal = {
                                viewModel.convertBuyingLeadToDeal(lead)
                            },
                            onCopyScript = {
                                viewModel.copyBuyingLeadClosingScript(lead)
                            }
                        )
                    }
                }
            }

            2 -> {
                // Section 2: Deep Trade BOQ, Worker Labor & Machinery Mobilization
                item {
                    Column {
                        Text(
                            text = "DEEP TRADE BILL OF QUANTITIES & PROFIT SHIELD",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Full itemized worker payments, job categories, days, excavator mobilization, and materials down to the screw — locking 34%–40% profit.",
                            fontSize = 12.sp,
                            color = PoolTextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Profit Margin Selectors (34%, 36.5%, 38%, 40%)
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = PoolSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, PoolBorder, RoundedCornerShape(16.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("Target Corporate Profit Margin", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PoolTextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(34.0, 36.5, 38.0, 40.0).forEach { margin ->
                                    val isSel = (targetMargin == margin)
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (isSel) PoolGold else PoolSurfaceElevated)
                                            .clickable { viewModel.setTargetProfitMargin(margin) }
                                            .padding(vertical = 10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${margin}%",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if (isSel) PoolDarkBg else PoolTextPrimary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Master Financial Summary Card
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(PoolSurfaceElevated)
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("1. Materials Cost", fontSize = 11.sp, color = PoolTextMuted)
                                    Text("US$${currencyFmt.format(currentBOQ.subtotalMaterialsCostUsd.roundToInt())}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("2. Worker Wages & Labor", fontSize = 11.sp, color = PoolTextMuted)
                                    Text("US$${currencyFmt.format(currentBOQ.subtotalLaborCostUsd.roundToInt())}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolAqua)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("3. Machinery & Mobilization", fontSize = 11.sp, color = PoolTextMuted)
                                    Text("US$${currencyFmt.format(currentBOQ.subtotalMachineryCostUsd.roundToInt())}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolGold)
                                }
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(PoolBorder))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Total Direct Base Cost", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                                    Text("US$${currencyFmt.format(currentBOQ.totalBaseCostUsd.roundToInt())}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Client Quotation", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolGold)
                                    Text("US$${currencyFmt.format(currentBOQ.clientQuotationUsd.roundToInt())}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = PoolGold)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Corporate Profit (${currentBOQ.targetProfitMarginPercent}%)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolGreen)
                                    Text("+US$${currencyFmt.format(currentBOQ.grossProfitUsd.roundToInt())}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = PoolGreen)
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.copyBOQToClipboard() },
                                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f).testTag("admin_copy_boq_button")
                                    ) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = null, tint = PoolAqua, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Copy Full BOQ", fontSize = 11.sp, color = PoolAqua, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = {
                                            openWhatsApp(context, viewModel.getFormattedBOQWhatsAppText())
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1.3f).testTag("admin_whatsapp_boq_button")
                                    ) {
                                        Icon(Icons.Default.Send, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Send Quote via WA", fontSize = 11.sp, color = PoolDarkBg, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                            }
                        }
                    }
                }

                // Worker Labor Allocation breakdown
                item {
                    Text(
                        text = "WORKER TEAMS, ROLES, PAYMENTS & DURATION (${currentBOQ.laborAllocations.size} TEAMS)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolAqua,
                        letterSpacing = 1.sp
                    )
                }

                items(currentBOQ.laborAllocations) { labor ->
                    LaborAllocationCard(labor = labor)
                }

                // Machinery Mobilization breakdown
                item {
                    Text(
                        text = "MACHINERY, MOBILIZATION & HAULAGE (${currentBOQ.machineryMobilizations.size} UNITS)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold,
                        letterSpacing = 1.sp
                    )
                }

                items(currentBOQ.machineryMobilizations) { mach ->
                    MachineryItemCard(mach = mach)
                }

                // Materials items
                item {
                    Text(
                        text = "MATERIALS BREAKDOWN (${currentBOQ.materials.size} ITEMS)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextMuted,
                        letterSpacing = 1.sp
                    )
                }

                items(currentBOQ.materials) { item ->
                    MaterialItemCard(item = item)
                }
            }

            3 -> {
                // Section 3: Trade Hiring & Artisan Job Creation Roster
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "👷 TRADE HIRING & ARTISAN ROSTER (${tradeVacancies.size})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PoolAqua,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Speedup site mobilization by hiring vetted Zimbabwe trade artisans at transparent USD daily rates",
                                fontSize = 11.sp,
                                color = PoolTextMuted
                            )
                        }

                        Button(
                            onClick = { showAddJobDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PoolAqua),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("admin_post_job_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+ Post Job", color = PoolDarkBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Accounts Labor Disbursement Rule Banner
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = PoolSurface),
                        modifier = Modifier.fillMaxWidth().border(1.dp, PoolBorder, RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = PoolGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "ACCOUNTS DEPT LABOR DISBURSEMENT GATE",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PoolGold
                                )
                                Text(
                                    text = "Mandate: We cannot pay people when we don't have funds. Artisan daily wages are disbursed strictly upon verified milestone stage signoff from cleared client deposits. Zero unbacked advance wage releases.",
                                    fontSize = 11.sp,
                                    color = PoolTextSecondary,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }

                items(tradeVacancies) { job ->
                    TradeJobVacancyCard(
                        job = job,
                        onBroadcastWhatsApp = {
                            val broadcast = TradeHiringData.getHiringBroadcastText(job)
                            openWhatsApp(context, broadcast)
                        },
                        onCopySpec = {
                            viewModel.copyHiringBroadcastToClipboard(job)
                        }
                    )
                }
            }

            4 -> {
                // Section 4: Strategic Adverts & Growth Marketing Engine
                item {
                    Column {
                        Text(
                            text = "STRATEGIC OVERNIGHT SALES ADVERTS & SCRIPTS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Pre-written, psych-tested ad campaigns in English, ChiShona & IsiNdebele for WhatsApp status, Instagram, Facebook, and Diaspora channels.",
                            fontSize = 12.sp,
                            color = PoolTextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                items(campaigns) { campaign ->
                    CampaignCard(
                        campaign = campaign,
                        onCopy = { viewModel.copyCampaignToClipboard(campaign) }
                    )
                }
            }

            5 -> {
                // Section 5: Trade Call Directory & Supplier Contacts
                item {
                    Column {
                        Text(
                            text = "CONSTRUCTION TRADE CALL DIRECTORY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Verified suppliers, excavator owners, cement depots, rebar yards, and town councils across Zimbabwe with one-tap dialing.",
                            fontSize = 12.sp,
                            color = PoolTextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Directory Categories
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("All", "Excavator", "Cement", "Steel", "Plant", "Coping", "Council").forEach { cat ->
                            val isSel = selectedDirCat == cat
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) PoolGold else PoolSurface)
                                    .clickable { viewModel.setDirectoryCategory(cat) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = cat,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) PoolDarkBg else PoolTextPrimary
                                )
                            }
                        }
                    }
                }

                items(directoryContacts) { contact ->
                    ConstructionContactCard(contact = contact)
                }
            }

            6 -> {
                // Section 6: Wealthy Suburbs Targeting Intelligence
                item {
                    Column {
                        Text(
                            text = "HIGH-NET-WORTH TERRITORY INTEL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Geological soil analysis, zoning rules, property wealth brackets, and tailored sales psychology hooks across Zimbabwe.",
                            fontSize = 12.sp,
                            color = PoolTextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                items(SuburbIntelligenceData.affluentSuburbs) { suburb ->
                    SuburbIntelCard(
                        suburb = suburb,
                        onTargetClick = {
                            viewModel.selectSuburb(suburb)
                            viewModel.setTab(1)
                        }
                    )
                }
            }

            7 -> {
                // Section 7: Crowd App Promotion & Referral Virality Engine
                item {
                    Column {
                        Text(
                            text = "📲 CROWD APP PROMOTION & REFERRAL VIRALITY ENGINE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Multi-channel broadcast scripts to drive mass downloads across WhatsApp groups, church/school committees, kombi flyers, and diaspora family networks.",
                            fontSize = 12.sp,
                            color = PoolTextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Section 7: Accounts Department Promotion Clearance & Crowd Viral Desk
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, PoolBorderGold, RoundedCornerShape(16.dp))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = PoolGold, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("ACCOUNTS CLEARANCE DESK", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolGold)
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (accountsPromotionsLocked) PoolBorder else PoolGreen.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = if (accountsPromotionsLocked) "PROMOTIONS LOCKED 🔒" else "PROMOTIONS ACTIVE ✅",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (accountsPromotionsLocked) PoolTextMuted else PoolGreen
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Mandatory Accounts Rule Banner
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(PoolDarkBg)
                                    .border(1.dp, PoolBorder, RoundedCornerShape(10.dp))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "⚖️ ACCOUNTS DEPARTMENT GOVERNANCE MANDATE",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PoolAqua
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "\"Promotions of money must be desired from accounts department. We cannot pay people when we don't have anything.\"",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = PoolTextPrimary
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "• Zero Cash Handouts: Promotional codes (DZ-APP-${accountsPromoCreditUsd.toInt()}) are non-cash invoice credits applied strictly on turnkey contracts ($4,500+).\n• 60% Deposit Clearance Rule: Credits and contractor wage payouts release ONLY after the client's 60% mobilization deposit clears in Demash accounts.\n• Protects material procurement funds (PPC cement, rebar steel, river sand) from cash depletion.",
                                        fontSize = 11.sp,
                                        color = PoolTextSecondary,
                                        lineHeight = 15.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Accounts Promotion Controls
                            Text("1. Accounts Authorized Credit Cap:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf(100.0, 150.0, 200.0).forEach { credit ->
                                    val isSelected = accountsPromoCreditUsd == credit
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) PoolGold else PoolSurface)
                                            .border(1.dp, if (isSelected) PoolGold else PoolBorder, RoundedCornerShape(8.dp))
                                            .clickable { viewModel.updateAccountsPromotionPolicy(credit, accountsPromotionsLocked) }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "US$${credit.toInt()} Credit",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) PoolDarkBg else PoolTextPrimary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Cashflow Protection Lock Switch
                            Button(
                                onClick = {
                                    viewModel.updateAccountsPromotionPolicy(accountsPromoCreditUsd, !accountsPromotionsLocked)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (accountsPromotionsLocked) PoolGreen else PoolSurface
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, if (accountsPromotionsLocked) PoolGreen else PoolGold, RoundedCornerShape(10.dp))
                            ) {
                                Icon(
                                    imageVector = if (accountsPromotionsLocked) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (accountsPromotionsLocked) PoolDarkBg else PoolGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (accountsPromotionsLocked) "Unlock Promotions (Accounts Cleared)" else "🔒 Freeze All Promotions (Zero Cash Outflow Mode)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (accountsPromotionsLocked) PoolDarkBg else PoolGold
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = PoolSurface),
                        modifier = Modifier.fillMaxWidth().border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("1. WhatsApp Community & Neighborhood Group Blast", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "🏊 Mhoroi vana vana! Planning to build a pool in Harare, Norton or Bulawayo? Avoid contractors charging 40% hidden markups. Download Demash Pools App — offline 10s estimator, verified 50kg PPC cement & 6m rebar BOQ, 10-year warranty, and an instant US$150 voucher for downloading! Check it out: https://ais-dev-hovuo3yz75o2jlyzziavm3-161436339311.europe-west2.run.app",
                                fontSize = 11.sp,
                                color = PoolTextSecondary,
                                lineHeight = 15.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        openWhatsApp(
                                            context,
                                            "🏊 Mhoroi vana vana! Planning to build a pool in Harare, Norton or Bulawayo? Avoid contractors charging 40% hidden markups. Download Demash Pools App — offline 10s estimator, verified 50kg PPC cement & 6m rebar BOQ, 10-year warranty, and an instant US$150 voucher for downloading! Check it out: https://ais-dev-hovuo3yz75o2jlyzziavm3-161436339311.europe-west2.run.app"
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f).height(34.dp)
                                ) {
                                    Text("Broadcast on WhatsApp", color = PoolDarkBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = PoolSurface),
                        modifier = Modifier.fillMaxWidth().border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("2. UK / SA Diaspora Forum Invitation", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "🇬🇧 🇿🇼 Building back home in Zimbabwe from UK or SA? Never get shortchanged again. Demash Pools App provides weekly WhatsApp 4K drone video updates, locked fixed-price USD contracts, and on-site engineering in Norton & Harare. Download the app today and claim US$150 off: https://ais-dev-hovuo3yz75o2jlyzziavm3-161436339311.europe-west2.run.app",
                                fontSize = 11.sp,
                                color = PoolTextSecondary,
                                lineHeight = 15.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        openWhatsApp(
                                            context,
                                            "🇬🇧 🇿🇼 Building back home in Zimbabwe from UK or SA? Never get shortchanged again. Demash Pools App provides weekly WhatsApp 4K drone video updates, locked fixed-price USD contracts, and on-site engineering in Norton & Harare. Download the app today and claim US$150 off: https://ais-dev-hovuo3yz75o2jlyzziavm3-161436339311.europe-west2.run.app"
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PoolAqua),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f).height(34.dp)
                                ) {
                                    Text("Share Diaspora Script", color = PoolDarkBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Hijack Dialog Modal
    selectedSessionForHijack?.let { session ->
        HijackChatDialog(
            session = session,
            onDismiss = { viewModel.selectSessionForHijack(null) },
            onSendMessage = { ownerMsg ->
                viewModel.hijackAndSendMessage(session.sessionId, ownerMsg)
            },
            onRelease = {
                viewModel.releaseHijack(session.sessionId)
            }
        )
    }

    // Add Real Client Modal Dialog
    if (showAddClientDialog) {
        AddRealClientDialog(
            onDismiss = { showAddClientDialog = false },
            onAddClient = { name, phone, suburb, poolType, dimensions, notes ->
                viewModel.addRealClient(name, phone, suburb, poolType, dimensions, notes)
                showAddClientDialog = false
            }
        )
    }

    // Add Fresh Buying Lead Modal Dialog
    if (showAddBuyingLeadDialog) {
        AddBuyingLeadDialog(
            onDismiss = { showAddBuyingLeadDialog = false },
            onAddLead = { name, suburb, phone, score, signal, budget, poolType, dims, timeline, funding ->
                viewModel.addBuyingIntentLead(name, suburb, phone, score, signal, budget, poolType, dims, timeline, funding)
                showAddBuyingLeadDialog = false
            }
        )
    }

    // Add Trade Job Vacancy Modal Dialog
    if (showAddJobDialog) {
        AddJobVacancyDialog(
            onDismiss = { showAddJobDialog = false },
            onAddJob = { title, cat, pay, days, pos, loc, exp, urg ->
                viewModel.addJobVacancy(title, cat, pay, days, pos, loc, exp, urg)
                showAddJobDialog = false
            }
        )
    }
}

@Composable
private fun LaborAllocationCard(labor: WorkerLaborAllocation) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(PoolAqua)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${labor.numberOfWorkers} Worker${if (labor.numberOfWorkers > 1) "s" else ""}",
                                color = PoolDarkBg,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = labor.category,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolTextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "${labor.daysExpected} Days on site @ US$${labor.dailyRateUsd.roundToInt()}/day per worker",
                        fontSize = 11.sp,
                        color = PoolTextMuted
                    )
                }

                Text(
                    text = "US$${labor.totalPaymentUsd.roundToInt()}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolAqua
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = labor.responsibilities,
                fontSize = 11.sp,
                color = PoolTextSecondary,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun MachineryItemCard(mach: MachineryMobilizationItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mach.equipmentName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = "Mob Fee: US$${mach.mobilizationFeeUsd.roundToInt()} + ${mach.unitsRequired.roundToInt()} ${mach.unitType} @ US$${mach.unitRateUsd.roundToInt()}/${mach.unitType.dropLast(1)}",
                        fontSize = 11.sp,
                        color = PoolGold
                    )
                }

                Text(
                    text = "US$${mach.totalMachineryCostUsd.roundToInt()}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolGold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = mach.operationalScope,
                fontSize = 11.sp,
                color = PoolTextMuted,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun CampaignCard(
    campaign: MarketingCampaign,
    onCopy: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PoolGold)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = campaign.platform,
                        color = PoolDarkBg,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Text(
                    text = campaign.targetArea,
                    fontSize = 11.sp,
                    color = PoolAqua,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = campaign.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )

            Text(
                text = "Hook: \"${campaign.hook}\"",
                fontSize = 12.sp,
                color = PoolGold,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(PoolSurfaceElevated)
                    .padding(10.dp)
            ) {
                Text(
                    text = campaign.adCopy,
                    fontSize = 11.sp,
                    color = PoolTextSecondary,
                    lineHeight = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Reach: ${campaign.estimatedReach}", fontSize = 10.sp, color = PoolTextMuted)
                    Text("Est. Conversion: ${campaign.conversionRateEstimate}", fontSize = 10.sp, color = PoolGreen, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onCopy,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Copy Script", color = PoolDarkBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ConstructionContactCard(contact: ConstructionContact) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = contact.companyName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(PoolSurfaceElevated)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = contact.priorityLevel,
                            fontSize = 9.sp,
                            color = PoolGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Text(
                    text = "${contact.tradeCategory} · ${contact.contactPerson}",
                    fontSize = 11.sp,
                    color = PoolAqua
                )

                Text(
                    text = "📍 ${contact.location}",
                    fontSize = 11.sp,
                    color = PoolTextMuted
                )

                Text(
                    text = contact.productsSupplied,
                    fontSize = 10.sp,
                    color = PoolTextSecondary,
                    maxLines = 1
                )
            }

            IconButton(
                onClick = { openDialer(context, contact.phone) },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PoolGold)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call Supplier",
                    tint = PoolDarkBg,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun EmployeeAdminCard(employee: AiEmployee) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(PoolSurfaceElevated)
                    .border(1.5.dp, PoolGold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = employee.avatarInitial,
                    color = PoolGold,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = employee.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PoolGreen.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = employee.status,
                            color = PoolGreen,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "${employee.role} · ${employee.department}",
                    fontSize = 11.sp,
                    color = PoolAqua
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Leads: ${employee.activeLeadsHandled}", fontSize = 10.sp, color = PoolTextMuted)
                    Text("Conversion: ${employee.conversionRatePercent}%", fontSize = 10.sp, color = PoolGold, fontWeight = FontWeight.SemiBold)
                    Text("Latency: ${employee.responseTimeSec}s", fontSize = 10.sp, color = PoolGreen)
                }
            }
        }
    }
}

@Composable
private fun LiveSessionCard(
    session: LiveClientSession,
    onSelectForHijack: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectForHijack() }
            .border(
                width = if (session.isHijackedByOwner) 1.5.dp else 1.dp,
                color = if (session.isHijackedByOwner) PoolGold else PoolBorder,
                shape = RoundedCornerShape(14.dp)
            )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = session.clientName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${session.suburb})",
                        fontSize = 11.sp,
                        color = PoolAqua
                    )
                }

                if (session.isHijackedByOwner) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PoolGold)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("HIJACKED BY YOU", color = PoolDarkBg, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PoolSurfaceElevated)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(session.assignedEmployee.name.substringBefore(" "), color = PoolTextMuted, fontSize = 9.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Client: \"${session.lastMessage}\"",
                fontSize = 11.sp,
                color = PoolTextSecondary,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Project Value: ~US$${session.estimatedProjectValueUsd}",
                    fontSize = 11.sp,
                    color = PoolGold,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Tap to Hijack Chat →",
                    fontSize = 11.sp,
                    color = PoolAqua,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun MaterialItemCard(item: MaterialItem) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = "${item.category} · ${String.format(Locale.US, "%.1f", item.quantity)} ${item.unit} @ US$${String.format(Locale.US, "%.2f", item.unitCostUsd)}/${item.unit}",
                        fontSize = 10.sp,
                        color = PoolAqua
                    )
                }

                Text(
                    text = "US$${item.totalCostUsd.roundToInt()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolGold
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.technicalNote,
                fontSize = 10.sp,
                color = PoolTextMuted,
                lineHeight = 13.sp
            )
        }
    }
}

@Composable
private fun SuburbIntelCard(
    suburb: SuburbProfile,
    onTargetClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = suburb.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = "${suburb.tier} · ${suburb.city}",
                        fontSize = 11.sp,
                        color = PoolGold,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PoolSurfaceElevated)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = suburb.typicalBudgetRange,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolAqua
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Soil: ${suburb.soilCharacteristics}",
                fontSize = 11.sp,
                color = PoolTextSecondary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Style: ${suburb.recommendedPoolStyle}",
                fontSize = 11.sp,
                color = PoolTextPrimary,
                fontWeight = FontWeight.Medium
            )

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PoolSurfaceElevated)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Psychology Hook: ${suburb.salesPsychologyHook}",
                        fontSize = 11.sp,
                        color = PoolGold,
                        lineHeight = 15.sp
                    )
                    Text(
                        text = "Competitor Flaw: ${suburb.competitorWeaknessExploited}",
                        fontSize = 11.sp,
                        color = PoolTextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "Less intel ▲" else "View deep sales intel ▼",
                    fontSize = 11.sp,
                    color = PoolTextMuted,
                    modifier = Modifier.clickable { expanded = !expanded }
                )

                Button(
                    onClick = onTargetClick,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text("Target Spec", color = PoolDarkBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun HijackChatDialog(
    session: LiveClientSession,
    onDismiss: () -> Unit,
    onSendMessage: (String) -> Unit,
    onRelease: () -> Unit
) {
    var ownerInput by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PoolSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "👑 Live Chat Hijack Console",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PoolGold
                        )
                        Text(
                            text = "${session.clientName} (${session.suburb})",
                            fontSize = 11.sp,
                            color = PoolTextPrimary
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PoolTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PoolSurfaceElevated)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(session.chatHistory) { (sender, msg) ->
                            val isOwner = sender.contains("Owner")
                            val isClient = sender == session.clientName
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                horizontalAlignment = if (isClient) Alignment.Start else Alignment.End
                            ) {
                                Text(
                                    text = sender,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isOwner) PoolGold else if (isClient) PoolAqua else PoolTextMuted
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isOwner) PoolGold.copy(alpha = 0.2f) else if (isClient) PoolSurfaceHighlight else PoolSurface)
                                        .padding(horizontal = 8.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = msg,
                                        fontSize = 11.sp,
                                        color = PoolTextPrimary
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = ownerInput,
                        onValueChange = { ownerInput = it },
                        placeholder = { Text("Speak directly as Owner...", fontSize = 11.sp, color = PoolTextMuted) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold,
                            unfocusedIndicatorColor = PoolBorder
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("owner_hijack_input")
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = {
                            if (ownerInput.isNotBlank()) {
                                onSendMessage(ownerInput)
                                ownerInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(PoolGold)
                            .testTag("owner_hijack_send")
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (session.isHijackedByOwner) {
                        Button(
                            onClick = onRelease,
                            colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Release Back to AI Employee", color = PoolAqua, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Text("Active Agent: ${session.assignedEmployee.name}", fontSize = 11.sp, color = PoolTextMuted)
                    }
                }
            }
        }
    }
}
