package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookingRequest
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
import com.example.viewmodel.ChatMessage
import com.example.viewmodel.MainViewModel

@Composable
fun BookingContactScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedSection by remember { mutableIntStateOf(0) }

    val savedBookings by viewModel.savedBookings.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PoolDarkBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(
                    text = "GET IN TOUCH",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolGold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Book Survey & Consult",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolTextPrimary
                )
                Text(
                    text = "Professional site surveys across Zimbabwe for US$50 (3D sketch included). Or chat with our engineer advisor below.",
                    fontSize = 13.sp,
                    color = PoolTextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Section Tabs
        item {
            TabRow(
                selectedTabIndex = selectedSection,
                containerColor = PoolSurface,
                contentColor = PoolGold,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedSection]),
                        color = PoolGold
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, PoolBorder, RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = selectedSection == 0,
                    onClick = { selectedSection = 0 },
                    text = { Text("Book Survey", fontWeight = FontWeight.Bold) },
                    selectedContentColor = PoolGold,
                    unselectedContentColor = PoolTextMuted
                )
                Tab(
                    selected = selectedSection == 1,
                    onClick = { selectedSection = 1 },
                    text = { Text("Engineer Chat", fontWeight = FontWeight.Bold) },
                    selectedContentColor = PoolGold,
                    unselectedContentColor = PoolTextMuted
                )
                Tab(
                    selected = selectedSection == 2,
                    onClick = { selectedSection = 2 },
                    text = { Text("Direct Info", fontWeight = FontWeight.Bold) },
                    selectedContentColor = PoolGold,
                    unselectedContentColor = PoolTextMuted
                )
            }
        }

        when (selectedSection) {
            0 -> {
                // Booking Form
                item {
                    SurveyBookingForm(
                        onSubmit = { name, phone, suburb, poolType, dimensions, preferredDate, notes ->
                            viewModel.submitBooking(
                                name = name,
                                phone = phone,
                                suburb = suburb,
                                poolType = poolType,
                                dimensions = dimensions,
                                surveyType = "Site Survey & 3D Concept (US$50)",
                                preferredDate = preferredDate,
                                notes = notes
                            )
                        }
                    )
                }

                // Existing Bookings or Open Capacity Confirmation
                if (savedBookings.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "MY SURVEY REQUESTS (${savedBookings.size})",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PoolGold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Active Booking Tracker",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PoolTextPrimary
                                )
                            }
                            Button(
                                onClick = { viewModel.purgeSystemErrorBookings() },
                                colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceElevated),
                                modifier = Modifier.height(32.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    text = "Clear All",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }

                    items(savedBookings) { booking ->
                        BookingStatusCard(booking = booking)
                    }
                } else {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = PoolSurface),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, PoolGreen.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                                .padding(top = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("✅", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "CAPACITY 100% OPEN & AVAILABLE",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PoolGreen,
                                        letterSpacing = 0.5.sp
                                    )
                                    Text(
                                        text = "All site inspection slots are open with zero lockouts. Submit your request above for immediate engineer dispatch.",
                                        fontSize = 11.sp,
                                        color = PoolTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // Interactive AI Engineer Advisor
                item {
                    EngineerAdvisorWidget(
                        messages = chatMessages,
                        onSendMessage = { viewModel.sendChatMessage(it) }
                    )
                }
            }

            2 -> {
                // Direct Company Contact Info
                item {
                    CompanyContactInfoCard()
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SurveyBookingForm(
    onSubmit: (name: String, phone: String, suburb: String, poolType: String, dimensions: String, preferredDate: String, notes: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var suburb by remember { mutableStateOf("") }
    var poolType by remember { mutableStateOf("Family Gunite 5×3m") }
    var dimensions by remember { mutableStateOf("5m × 3m") }
    var preferredDate by remember { mutableStateOf("This week (Flexible)") }
    var notes by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PoolGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Engineering,
                        contentDescription = null,
                        tint = PoolDarkBg,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Professional Site Visit (US$50)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = "Includes 3D concept sketch, soil & level check",
                        fontSize = 11.sp,
                        color = PoolAqua
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedFormField(
                label = "Your Full Name *",
                value = name,
                onValueChange = { name = it },
                testTag = "booking_name_input"
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedFormField(
                label = "WhatsApp / Phone Number *",
                value = phone,
                onValueChange = { phone = it },
                placeholder = "+263 7x xxx xxxx",
                testTag = "booking_phone_input"
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedFormField(
                label = "Property Suburb / Town *",
                value = suburb,
                onValueChange = { suburb = it },
                placeholder = "e.g., Norton, Borrowdale, Highlands, Avondale",
                testTag = "booking_suburb_input"
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedFormField(
                label = "Pool Type & Approximate Size",
                value = poolType,
                onValueChange = { poolType = it },
                placeholder = "e.g., 5×3m Gunite or Plunge",
                testTag = "booking_pool_type_input"
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedFormField(
                label = "Preferred Survey Date / Timing",
                value = preferredDate,
                onValueChange = { preferredDate = it },
                placeholder = "e.g., Saturday morning or this week",
                testTag = "booking_date_input"
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedFormField(
                label = "Specific Notes / Yard Access",
                value = notes,
                onValueChange = { notes = it },
                placeholder = "e.g., Sloped lawn, red clay, load shedding solar query",
                singleLine = false,
                testTag = "booking_notes_input"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        onSubmit(name, phone, suburb, poolType, dimensions, preferredDate, notes)
                        submitted = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("submit_survey_button")
            ) {
                Text(
                    text = "Request Site Visit (US$50)",
                    color = PoolDarkBg,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }

            if (submitted) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(PoolGreen.copy(alpha = 0.15f))
                        .border(1.dp, PoolGreen, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PoolGreen, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Request saved! Our team replies in ~90 mins.", color = PoolGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Want instant confirmation? Send this directly to our engineer on WhatsApp now:",
                            color = PoolTextSecondary,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Button(
                            onClick = {
                                val msg = "Hi Dzimbabwe Pools! I would like to confirm a US$50 site visit for $name in $suburb. Phone: $phone. Interested in: $poolType. Preferred time: $preferredDate."
                                openWhatsApp(context, msg)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PoolGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Confirm on WhatsApp", color = PoolDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OutlinedFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    singleLine: Boolean = true,
    testTag: String = ""
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = PoolTextMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = PoolTextMuted.copy(alpha = 0.6f), fontSize = 13.sp) },
            singleLine = singleLine,
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
                .fillMaxWidth()
                .testTag(testTag)
        )
    }
}

@Composable
private fun BookingStatusCard(booking: BookingRequest) {
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
                Text(
                    text = booking.clientName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolTextPrimary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PoolAqua.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = booking.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolAqua
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${booking.suburb} · ${booking.poolType}",
                fontSize = 13.sp,
                color = PoolGold
            )

            Text(
                text = "Preferred date: ${booking.preferredDate} · Phone: ${booking.phone}",
                fontSize = 11.sp,
                color = PoolTextMuted
            )
        }
    }
}

@Composable
private fun EngineerAdvisorWidget(
    messages: List<ChatMessage>,
    onSendMessage: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val promptChips = listOf(
        "Best pool for Norton red clay?",
        "Load shedding solar pump setup?",
        "What's in the US$50 survey?",
        "Do you handle council permits?",
        "How do payments work?"
    )

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PoolAqua),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Engineering,
                        contentDescription = null,
                        tint = PoolDarkBg,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Dzimbabwe Engineering Advisor", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                    Text("Instant technical answers 24/7", fontSize = 11.sp, color = PoolGreen)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Suggestions chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                promptChips.forEach { prompt ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PoolSurfaceElevated)
                            .clickable { onSendMessage(prompt) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(prompt, fontSize = 11.sp, color = PoolGold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Messages container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PoolSurfaceElevated)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages) { msg ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = if (msg.isUser) Alignment.CenterEnd else Alignment.CenterStart
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp,
                                            bottomStart = if (msg.isUser) 12.dp else 2.dp,
                                            bottomEnd = if (msg.isUser) 2.dp else 12.dp
                                        )
                                    )
                                    .background(if (msg.isUser) PoolGold else PoolSurfaceHighlight)
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .widthIn(max = 260.dp)
                            ) {
                                Text(
                                    text = msg.text,
                                    color = if (msg.isUser) PoolDarkBg else PoolTextPrimary,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    fontWeight = if (msg.isUser) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Input field
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Ask engineer anything...", fontSize = 12.sp, color = PoolTextMuted) },
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
                        .testTag("chat_input")
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(PoolGold)
                        .testTag("chat_send_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = PoolDarkBg,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CompanyContactInfoCard() {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Fastest Route: WhatsApp",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )
            Text(
                text = "We typically reply in minutes Mon–Sat, 07:00–18:00 (CAT).",
                fontSize = 12.sp,
                color = PoolTextMuted,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )

            ContactRow(
                icon = Icons.Default.Chat,
                iconTint = PoolGreen,
                title = "WhatsApp",
                subtitle = "+263 78 421 9178",
                actionLabel = "Chat Now",
                onClick = { openWhatsApp(context, "Hi Dzimbabwe Pools! I would like to speak to an engineer.") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ContactRow(
                icon = Icons.Default.Call,
                iconTint = PoolAqua,
                title = "Direct Call",
                subtitle = "+263 78 421 9178",
                actionLabel = "Call Now",
                onClick = { openDialer(context, "+263784219178") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ContactRow(
                icon = Icons.Default.Email,
                iconTint = PoolGold,
                title = "Email",
                subtitle = "libermanking@gmail.com",
                actionLabel = "Send Mail",
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:libermanking@gmail.com"))
                        context.startActivity(intent)
                    } catch (_: Exception) {}
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ContactRow(
                icon = Icons.Default.LocationOn,
                iconTint = PoolAqua,
                title = "Headquarters",
                subtitle = "K17412 Katanga, Norton, Zimbabwe",
                actionLabel = "Zimbabwe",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            ContactRow(
                icon = Icons.Default.Schedule,
                iconTint = PoolTextMuted,
                title = "Operating Hours",
                subtitle = "Mon–Sat 07:00–18:00 (CAT)",
                actionLabel = "Open",
                onClick = {}
            )
        }
    }
}

@Composable
private fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: androidx.compose.ui.graphics.Color,
    title: String,
    subtitle: String,
    actionLabel: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(PoolSurfaceElevated)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(PoolSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                Text(subtitle, fontSize = 12.sp, color = PoolTextSecondary)
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(PoolSurface)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(actionLabel, color = PoolGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}
