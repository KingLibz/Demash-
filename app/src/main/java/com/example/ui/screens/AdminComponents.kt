package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.BookingRequest
import com.example.data.model.BuyingIntentLead
import com.example.data.model.TradeJobVacancy
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolGreen
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolSurfaceElevated
import com.example.ui.theme.PoolSurfaceHighlight
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary
import com.example.ui.theme.PoolTextSecondary
import java.text.NumberFormat
import java.util.Locale

data class RealTeamMember(
    val name: String,
    val role: String,
    val department: String,
    val phone: String,
    val location: String
)

val realDemashDirectors = listOf(
    RealTeamMember(
        name = "Liberman Magaya",
        role = "Managing Director & Site Principal",
        department = "Executive Leadership",
        phone = "+263784219178",
        location = "K17412 Katanga, Norton"
    ),
    RealTeamMember(
        name = "Eng. Tinashe Moyo",
        role = "Senior Structural & Geotechnical Lead",
        department = "Monolithic Gunite Shells",
        phone = "+263772114520",
        location = "Harare / Nationwide Sites"
    ),
    RealTeamMember(
        name = "QS Farai Mupfumi",
        role = "Lead Quantity Surveyor & Cost Controller",
        department = "BOQ & Procurement",
        phone = "+263712889045",
        location = "Commercial Division"
    ),
    RealTeamMember(
        name = "Tariro Zhou",
        role = "Earthmoving & Machinery Logistics Foreman",
        department = "Excavator & Tipper Fleet",
        phone = "+263773400821",
        location = "Norton & Manyame Yard"
    )
)

@Composable
fun RealClientDealCard(
    client: BookingRequest,
    onAdvanceStage: () -> Unit,
    onCall: () -> Unit,
    onWhatsAppSurvey: () -> Unit,
    onWhatsAppBOQ: () -> Unit,
    onWhatsAppPayment: () -> Unit,
    onWhatsAppWarranty: () -> Unit,
    onDelete: () -> Unit
) {
    var showWhatsAppMenu by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header with Name, Suburb and Stage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = client.clientName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationCity, contentDescription = null, tint = PoolGold, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = client.suburb,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PoolGold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "📞 ${client.phone}",
                            fontSize = 11.sp,
                            color = PoolTextMuted
                        )
                    }
                }

                // Stage Badge
                val (stageBg, stageColor) = when {
                    client.status.contains("Signed") || client.status.contains("Deposit") || client.status.contains("Handover") -> PoolGreen.copy(alpha = 0.2f) to PoolGreen
                    client.status.contains("Quoted") || client.status.contains("Survey") -> PoolGold.copy(alpha = 0.2f) to PoolGold
                    else -> PoolAqua.copy(alpha = 0.2f) to PoolAqua
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(stageBg)
                        .border(1.dp, stageColor, RoundedCornerShape(6.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = client.status,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = stageColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Specs
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(PoolSurface)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Build: ${client.poolType} (${client.poolDimensions})",
                        fontSize = 11.sp,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = client.preferredDate,
                        fontSize = 10.sp,
                        color = PoolTextMuted
                    )
                }
            }

            if (client.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Notes: ${client.notes}",
                    fontSize = 10.sp,
                    color = PoolTextSecondary,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Real Business Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onAdvanceStage,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = PoolGold, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Advance Stage", fontSize = 10.sp, color = PoolGold, fontWeight = FontWeight.Bold)
                }

                IconButton(
                    onClick = onCall,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(PoolGold)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                }

                Box {
                    IconButton(
                        onClick = { showWhatsAppMenu = !showWhatsAppMenu },
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(PoolGreen)
                    ) {
                        Icon(imageVector = Icons.Default.Chat, contentDescription = "WhatsApp", tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                    }

                    DropdownMenu(
                        expanded = showWhatsAppMenu,
                        onDismissRequest = { showWhatsAppMenu = false },
                        modifier = Modifier.background(PoolSurfaceElevated)
                    ) {
                        DropdownMenuItem(
                            text = { Text("1. Send Survey Confirmation", color = PoolTextPrimary, fontSize = 12.sp) },
                            onClick = {
                                showWhatsAppMenu = false
                                onWhatsAppSurvey()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("2. Send Official BOQ Quotation", color = PoolGold, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                            onClick = {
                                showWhatsAppMenu = false
                                onWhatsAppBOQ()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("3. Send Milestone Schedule (50/40/10)", color = PoolAqua, fontSize = 12.sp) },
                            onClick = {
                                showWhatsAppMenu = false
                                onWhatsAppPayment()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("4. Send 10-Yr Warranty Certificate", color = PoolGreen, fontSize = 12.sp) },
                            onClick = {
                                showWhatsAppMenu = false
                                onWhatsAppWarranty()
                            }
                        )
                    }
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(PoolSurface)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = PoolTextMuted, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun RealTeamMemberCard(
    person: RealTeamMember,
    onCall: () -> Unit,
    onWhatsApp: () -> Unit
) {
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = person.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                Text(text = person.role, fontSize = 11.sp, color = PoolGold)
                Text(text = "📍 ${person.location} · ${person.phone}", fontSize = 10.sp, color = PoolTextMuted)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IconButton(
                    onClick = onCall,
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(PoolGold)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = PoolDarkBg, modifier = Modifier.size(14.dp))
                }

                IconButton(
                    onClick = onWhatsApp,
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(PoolGreen)
                ) {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = "WhatsApp", tint = PoolDarkBg, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
fun BuyingIntentLeadCard(
    lead: BuyingIntentLead,
    onCall: () -> Unit,
    onWhatsAppClose: () -> Unit,
    onConvertToDeal: () -> Unit = {},
    onCopyScript: () -> Unit = {}
) {
    val currencyFmt = remember {
        NumberFormat.getNumberInstance(Locale.US).apply { maximumFractionDigits = 0 }
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, PoolGold, RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Name, Score & Budget
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lead.clientName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "📍 ${lead.suburb} · 📞 ${lead.phone}",
                        fontSize = 11.sp,
                        color = PoolGold
                    )
                }

                // Intent Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PoolGreen.copy(alpha = 0.2f))
                        .border(1.dp, PoolGreen, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "🔥 ${lead.intentScorePct}% Intent",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PoolGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Buying Intent Signal Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(PoolSurface)
                    .padding(10.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = null, tint = PoolGold, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Budget: US$${currencyFmt.format(lead.budgetUsd)} · ${lead.fundingStatus}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold
                        )
                    }
                    Text(
                        text = "Signal: ${lead.intentSignal}",
                        fontSize = 11.sp,
                        color = PoolTextPrimary,
                        lineHeight = 15.sp
                    )
                    Text(
                        text = "Preferred: ${lead.preferredPoolType} (${lead.preferredDimensions}) · ${lead.timeline}",
                        fontSize = 10.sp,
                        color = PoolAqua
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Primary Contact & WhatsApp Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onCall,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = PoolGold, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call Buyer", color = PoolGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onWhatsAppClose,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGreen),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1.4f)
                ) {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Close on WhatsApp", color = PoolDarkBg, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Deal Conversion & Script Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onCopyScript,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).height(34.dp)
                ) {
                    Text("📋 Copy Closing Pitch", color = PoolTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onConvertToDeal,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold.copy(alpha = 0.25f)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1.4f).height(34.dp)
                ) {
                    Text("⚡ Convert to Active Deal", color = PoolGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun TradeJobVacancyCard(
    job: TradeJobVacancy,
    onBroadcastWhatsApp: () -> Unit,
    onCopySpec: () -> Unit = {}
) {
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
                        text = job.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = "Trade: ${job.tradeCategory} · 📍 ${job.location}",
                        fontSize = 11.sp,
                        color = PoolGold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PoolAqua.copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "US$${job.dailyPayUsd.toInt()}/day",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolAqua
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Required: ${job.experienceRequirement}",
                fontSize = 11.sp,
                color = PoolTextMuted,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${job.positionsNeeded} Positions · ${job.expectedDays} Days work",
                    fontSize = 10.sp,
                    color = PoolTextSecondary
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(
                        onClick = onCopySpec,
                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Copy Spec", color = PoolTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = onBroadcastWhatsApp,
                        colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Campaign, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Broadcast Job", color = PoolDarkBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun AddRealClientDialog(
    onDismiss: () -> Unit,
    onAddClient: (name: String, phone: String, suburb: String, poolType: String, dimensions: String, notes: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+263") }
    var suburb by remember { mutableStateOf("Borrowdale Brooke") }
    var poolType by remember { mutableStateOf("Gunite Concrete Pool") }
    var dimensions by remember { mutableStateOf("7.0m x 3.5m") }
    var notes by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PoolSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💼 Log Real Client Deal",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PoolTextMuted)
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Client Full Name", fontSize = 11.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number (+263...)", fontSize = 11.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = suburb,
                    onValueChange = { suburb = it },
                    label = { Text("Suburb / Location", fontSize = 11.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = poolType,
                        onValueChange = { poolType = it },
                        label = { Text("Pool Type", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = dimensions,
                        onValueChange = { dimensions = it },
                        label = { Text("Dimensions", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Project Notes & Requirements", fontSize = 11.sp) },
                    maxLines = 2,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank() && phone.isNotBlank()) {
                            onAddClient(name, phone, suburb, poolType, dimensions, notes)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Text("Save Client to Room Database", color = PoolDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AddBuyingLeadDialog(
    onDismiss: () -> Unit,
    onAddLead: (
        clientName: String,
        suburb: String,
        phone: String,
        intentScorePct: Int,
        intentSignal: String,
        budgetUsd: Int,
        poolType: String,
        dimensions: String,
        timeline: String,
        fundingStatus: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+263") }
    var suburb by remember { mutableStateOf("Borrowdale Brooke") }
    var budgetStr by remember { mutableStateOf("18500") }
    var poolType by remember { mutableStateOf("Infinity-Edge (Rim-Flow)") }
    var dimensions by remember { mutableStateOf("8.0m x 4.0m") }
    var timeline by remember { mutableStateOf("Immediate Break Ground") }
    var fundingStatus by remember { mutableStateOf("Ready for 50% Deposit") }
    var intentSignal by remember { mutableStateOf("House finished. Wants luxury pool before summer.") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PoolSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🎯 Log Fresh Buying Intent Lead",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PoolTextMuted)
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Buyer Name / Developer", fontSize = 11.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1.2f)
                    )

                    OutlinedTextField(
                        value = budgetStr,
                        onValueChange = { budgetStr = it },
                        label = { Text("Budget (US$)", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = suburb,
                        onValueChange = { suburb = it },
                        label = { Text("Suburb", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = fundingStatus,
                        onValueChange = { fundingStatus = it },
                        label = { Text("Funding Status", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1.2f)
                    )
                }

                OutlinedTextField(
                    value = intentSignal,
                    onValueChange = { intentSignal = it },
                    label = { Text("Buying Signal & Urgency", fontSize = 11.sp) },
                    maxLines = 2,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (name.isNotBlank() && phone.isNotBlank()) {
                            val budget = budgetStr.toIntOrNull() ?: 15000
                            onAddLead(
                                name, suburb, phone, 97, intentSignal, budget,
                                poolType, dimensions, timeline, fundingStatus
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Text("Register Buying Lead", color = PoolDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AddJobVacancyDialog(
    onDismiss: () -> Unit,
    onAddJob: (
        title: String,
        tradeCategory: String,
        dailyPay: Double,
        expectedDays: Int,
        positions: Int,
        location: String,
        experience: String,
        urgency: String
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var tradeCategory by remember { mutableStateOf("Shotcrete & Concrete Application") }
    var dailyPayStr by remember { mutableStateOf("65") }
    var expectedDaysStr by remember { mutableStateOf("12") }
    var positionsStr by remember { mutableStateOf("2") }
    var location by remember { mutableStateOf("Harare & Norton Sites") }
    var experience by remember { mutableStateOf("3+ years trade experience required.") }
    var urgency by remember { mutableStateOf("Immediate Hire (Breaking Ground)") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PoolSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "👷 Post Trade Job Vacancy",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PoolTextMuted)
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Job Title / Artisan Role", fontSize = 11.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = dailyPayStr,
                        onValueChange = { dailyPayStr = it },
                        label = { Text("Daily Pay (US$)", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = expectedDaysStr,
                        onValueChange = { expectedDaysStr = it },
                        label = { Text("Expected Days", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = positionsStr,
                        onValueChange = { positionsStr = it },
                        label = { Text("Positions", fontSize = 10.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PoolSurfaceElevated,
                            unfocusedContainerColor = PoolSurfaceElevated,
                            focusedTextColor = PoolTextPrimary,
                            unfocusedTextColor = PoolTextPrimary,
                            focusedIndicatorColor = PoolGold
                        ),
                        modifier = Modifier.weight(0.9f)
                    )
                }

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Site Location / Depot", fontSize = 11.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = experience,
                    onValueChange = { experience = it },
                    label = { Text("Experience & License Requirements", fontSize = 11.sp) },
                    maxLines = 2,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurfaceElevated,
                        unfocusedContainerColor = PoolSurfaceElevated,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            val pay = dailyPayStr.toDoubleOrNull() ?: 60.0
                            val days = expectedDaysStr.toIntOrNull() ?: 10
                            val pos = positionsStr.toIntOrNull() ?: 2
                            onAddJob(title, tradeCategory, pay, days, pos, location, experience, urgency)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Text("Publish Vacancy & Broadcast", color = PoolDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
