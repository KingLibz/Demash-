package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PoolPreset
import com.example.data.model.PoolShape
import com.example.data.model.PoolType
import com.example.data.model.SavedEstimate
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstimatorScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val repository = viewModel.repository

    val estimate by viewModel.currentEstimate.collectAsState()
    val selectedType by viewModel.selectedType.collectAsState()
    val selectedShape by viewModel.selectedShape.collectAsState()
    val lengthM by viewModel.lengthM.collectAsState()
    val widthM by viewModel.widthM.collectAsState()
    val selectedPresetId by viewModel.selectedPresetId.collectAsState()
    val savedEstimates by viewModel.savedEstimates.collectAsState()
    val currentSoilIntel by viewModel.currentSoilIntel.collectAsState()
    val accountsClearance by viewModel.currentAccountsClearance.collectAsState()
    val selectedSuburb by viewModel.selectedSuburb.collectAsState()

    val currencyFmt = remember {
        NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 0
        }
    }

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
                    text = "INSTANT ESTIMATOR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolGold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Your Price in 10 Seconds",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolTextPrimary
                )
                Text(
                    text = "Real transparent numbers. Move the sliders — the market comparison and technical specs update live.",
                    fontSize = 13.sp,
                    color = PoolTextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Suburb Target Selector
        item {
            val selectedSuburb by viewModel.selectedSuburb.collectAsState()
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your Property Suburb",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PoolTextSecondary
                    )
                    Text(
                        text = selectedSuburb.tier,
                        fontSize = 11.sp,
                        color = PoolGold,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    com.example.data.model.SuburbIntelligenceData.affluentSuburbs.forEach { suburb ->
                        val isSel = selectedSuburb.id == suburb.id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSel) PoolAqua else PoolSurface)
                                .border(1.dp, if (isSel) PoolAqua else PoolBorder, RoundedCornerShape(10.dp))
                                .clickable { viewModel.selectSuburb(suburb) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = suburb.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) PoolDarkBg else PoolTextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Suburb Geotechnical Advisory Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(PoolSurfaceElevated)
                        .border(1.dp, PoolBorderGold, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "📍 ${selectedSuburb.name} Engineering Note: ",
                                color = PoolGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = selectedSuburb.typicalBudgetRange,
                                color = PoolAqua,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = selectedSuburb.soilCharacteristics,
                            color = PoolTextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Size Presets Row
        item {
            Column {
                Text(
                    text = "Popular Size Presets",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PoolTextSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repository.presets.forEach { preset ->
                        val isSelected = selectedPresetId == preset.id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) PoolGold else PoolSurface)
                                .border(
                                    1.dp,
                                    if (isSelected) PoolGold else PoolBorder,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { viewModel.applyPreset(preset) }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = preset.name,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) PoolDarkBg else PoolTextPrimary
                                    )
                                    if (preset.isPopular) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "★",
                                            fontSize = 11.sp,
                                            color = if (isSelected) PoolDarkBg else PoolGold
                                        )
                                    }
                                }
                                Text(
                                    text = preset.subtitle,
                                    fontSize = 10.sp,
                                    color = if (isSelected) PoolDarkBg.copy(alpha = 0.8f) else PoolTextMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // Configuration Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Pool Type Dropdown
                    var typeExpanded by remember { mutableStateOf(false) }
                    Text(
                        text = "Pool Construction Type",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PoolTextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    ExposedDropdownMenuBox(
                        expanded = typeExpanded,
                        onExpandedChange = { typeExpanded = !typeExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedType.displayName,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = PoolSurfaceElevated,
                                unfocusedContainerColor = PoolSurfaceElevated,
                                focusedTextColor = PoolTextPrimary,
                                unfocusedTextColor = PoolTextPrimary,
                                focusedIndicatorColor = PoolGold,
                                unfocusedIndicatorColor = PoolBorder
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .testTag("pool_type_dropdown"),
                            shape = RoundedCornerShape(10.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = typeExpanded,
                            onDismissRequest = { typeExpanded = false },
                            modifier = Modifier.background(PoolSurfaceElevated)
                        ) {
                            PoolType.values().forEach { type ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(type.displayName, color = PoolTextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(type.description, color = PoolTextMuted, fontSize = 11.sp, maxLines = 1)
                                        }
                                    },
                                    onClick = {
                                        viewModel.setPoolType(type)
                                        typeExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Shape Selector
                    Text(
                        text = "Pool Shape",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PoolTextMuted
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PoolShape.values().forEach { shape ->
                            val isSel = selectedShape == shape
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) PoolAqua else PoolSurfaceElevated)
                                    .clickable { viewModel.setPoolShape(shape) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = shape.displayName.substringBefore(" ("),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) PoolDarkBg else PoolTextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Length Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Length", fontSize = 13.sp, color = PoolTextPrimary, fontWeight = FontWeight.SemiBold)
                        Text("${String.format(Locale.US, "%.1f", lengthM)} meters", fontSize = 13.sp, color = PoolGold, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = lengthM.toFloat(),
                        onValueChange = { viewModel.updateDimensions(it.toDouble(), widthM) },
                        valueRange = 2f..25f,
                        steps = 45,
                        colors = SliderDefaults.colors(
                            thumbColor = PoolGold,
                            activeTrackColor = PoolGold,
                            inactiveTrackColor = PoolSurfaceElevated
                        ),
                        modifier = Modifier.testTag("length_slider")
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Width Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Width", fontSize = 13.sp, color = PoolTextPrimary, fontWeight = FontWeight.SemiBold)
                        Text("${String.format(Locale.US, "%.1f", widthM)} meters", fontSize = 13.sp, color = PoolAqua, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = widthM.toFloat(),
                        onValueChange = { viewModel.updateDimensions(lengthM, it.toDouble()) },
                        valueRange = 1.5f..10f,
                        steps = 16,
                        colors = SliderDefaults.colors(
                            thumbColor = PoolAqua,
                            activeTrackColor = PoolAqua,
                            inactiveTrackColor = PoolSurfaceElevated
                        ),
                        modifier = Modifier.testTag("width_slider")
                    )
                }
            }
        }

        // Live Result Calculation Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, PoolBorderGold, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "ESTIMATED FULL BUILD",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PoolTextMuted,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "US$${currencyFmt.format(estimate.priceUsd.roundToInt())}",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PoolGold
                            )
                        }

                        // Savings pill
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Typical market: US$${currencyFmt.format(estimate.marketPriceUsd.roundToInt())}",
                                fontSize = 12.sp,
                                color = PoolTextMuted,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PoolGreen.copy(alpha = 0.2f))
                                    .border(1.dp, PoolGreen, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Savings,
                                        contentDescription = null,
                                        tint = PoolGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "You save US$${currencyFmt.format(estimate.savingsUsd.roundToInt())} (32%)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PoolGreen
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Engineering Specs Grid
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(PoolSurface)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Surface Area", fontSize = 12.sp, color = PoolTextMuted)
                            Text("${estimate.surfaceAreaM2} m² (${estimate.shape.displayName.substringBefore(" ")})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Water Capacity", fontSize = 12.sp, color = PoolTextMuted)
                            Text("~${currencyFmt.format(estimate.estimatedVolumeLiters)} Liters", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PoolAqua)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Depth Profile", fontSize = 12.sp, color = PoolTextMuted)
                            Text(estimate.estimatedDepthM, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = PoolTextSecondary)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Pump & Filtration", fontSize = 12.sp, color = PoolTextMuted)
                            Text(estimate.recommendedPumpHp, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = PoolTextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Includes shell, non-slip coping, tiles, pump & sand filter. Final fixed contract confirmed after professional site survey (US$50).",
                        fontSize = 11.sp,
                        color = PoolTextMuted,
                        lineHeight = 15.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Actions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.saveCurrentEstimate() },
                            colors = ButtonDefaults.buttonColors(containerColor = PoolSurface),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("save_estimate_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = PoolAqua,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Save Spec", color = PoolAqua, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                val text = "Hi Demash Pools! Your app calculator estimated a ${estimate.shape.displayName} ${estimate.poolType.displayName} pool (${estimate.length}m x ${estimate.width}m, ~${estimate.surfaceAreaM2}m²) at US$${currencyFmt.format(estimate.priceUsd.roundToInt())}. I'd like a formal quote or site survey."
                                openWhatsApp(context, text)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1.3f)
                                .testTag("whatsapp_quote_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                tint = PoolDarkBg,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Quote on WhatsApp", color = PoolDarkBg, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Intelligent Zimbabwe Geological & Soil Mechanics Profile
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, PoolBorderGold, RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Landscape, contentDescription = null, tint = PoolGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("SOIL & GEOLOGICAL INTEL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolGold)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(PoolAqua.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("${currentSoilIntel.requiredConcreteMpa}MPa Gunite Spec", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PoolAqua)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Site Soil Formation: ${currentSoilIntel.suburbName}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = currentSoilIntel.soilFormation,
                        fontSize = 12.sp,
                        color = PoolTextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Soil Technical Matrix
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(PoolSurface)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Excavation Method", fontSize = 11.sp, color = PoolTextMuted)
                            Text(if (currentSoilIntel.rockHammerRequired) "CAT Rock Hammer Required" else "Mechanical Shoring / Backhoe", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (currentSoilIntel.rockHammerRequired) PoolGold else PoolGreen)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Gunite Shell Thickness", fontSize = 11.sp, color = PoolTextMuted)
                            Text("${currentSoilIntel.recommendedShellThicknessMm}mm Monolithic Wall", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Steel Reinforcement", fontSize = 11.sp, color = PoolTextMuted)
                            Text(currentSoilIntel.rebarSpec, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = PoolAqua)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Hydrostatic Relief Valve", fontSize = 11.sp, color = PoolTextMuted)
                            Text(if (currentSoilIntel.needsHydrostaticRelief) "Mandatory (High Water Table/Slope)" else "Standard Anti-Suction", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = PoolTextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "💡 Engineering Note: ${currentSoilIntel.structuralEngineeringAdvice}",
                        fontSize = 11.sp,
                        color = PoolAqua,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Accounts Department Cashflow Prudence & Deposit Clearance Gate
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = PoolGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ACCOUNTS CLEARANCE GATE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolGold)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(PoolGreen.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Deposit Protected", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PoolGreen)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Required 60% Mobilization Deposit: US$${currencyFmt.format(accountsClearance.requiredDepositUsd.roundToInt())}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = accountsClearance.policyStatement,
                        fontSize = 11.sp,
                        color = PoolTextSecondary,
                        lineHeight = 15.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Gross Margin Floor: ${accountsClearance.grossProfitMarginPercent.roundToInt()}% (Locked)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = PoolAqua)
                        Text("Authorized Credit: US$${accountsClearance.authorizedVoucherUsd.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = PoolGold)
                    }
                }
            }
        }

        // Saved Estimates Section
        if (savedEstimates.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = "SAVED SPECIFICATIONS (${savedEstimates.size})",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Your Shortlisted Configurations",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                }
            }

            items(savedEstimates) { saved ->
                SavedEstimateCard(
                    saved = saved,
                    onDelete = { viewModel.deleteEstimate(saved) },
                    onWhatsApp = {
                        val text = "Hi Dzimbabwe Pools! Here is my saved estimate for a ${saved.shape} ${saved.poolType} (${saved.length}m x ${saved.width}m) priced at US$${currencyFmt.format(saved.priceUsd.roundToInt())}. Can we schedule a site survey?"
                        openWhatsApp(context, text)
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SavedEstimateCard(
    saved: SavedEstimate,
    onDelete: () -> Unit,
    onWhatsApp: () -> Unit
) {
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
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${saved.shape} · ${saved.poolType}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolTextPrimary
                )
                Text(
                    text = "${saved.length}m × ${saved.width}m · Save ${saved.savingsPct}%",
                    fontSize = 12.sp,
                    color = PoolAqua
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "US$${saved.priceUsd.roundToInt()}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolGold
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onWhatsApp) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp",
                        tint = PoolGreen
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = PoolTextMuted
                    )
                }
            }
        }
    }
}
