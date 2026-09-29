package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.PricingPackage
import com.example.ui.components.StatCountersRow
import com.example.ui.components.openDialer
import com.example.ui.components.openWhatsApp
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolBorderGold
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolGoldDark
import com.example.ui.theme.PoolGreen
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolSurfaceElevated
import com.example.ui.theme.PoolSurfaceHighlight
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary
import com.example.ui.theme.PoolTextSecondary
import com.example.ui.theme.PoolWater
import com.example.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToEstimator: () -> Unit,
    onNavigateToBooking: () -> Unit,
    onNavigateToShowcase: () -> Unit,
    onNavigateToV60Web: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val repository = viewModel.repository
    var showInstallDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PoolDarkBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // V60 Singularity & World Markets Unified Master Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, PoolBorderGold, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(PoolAqua)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "V60 OMNI-SINGULARITY ACTIVE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = PoolAqua,
                                letterSpacing = 0.8.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(PoolGreen.copy(alpha = 0.2f))
                                .border(1.dp, PoolGreen, RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("37% MARGIN LOCKED", fontSize = 8.sp, fontWeight = FontWeight.Black, color = PoolGreen)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Unified V60 & World Markets Platform",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = PoolTextPrimary
                    )
                    Text(
                        text = "The V60 Singularity Engine and World Markets Turnkey Catalog are linked into one seamless system. Experience live multi-currency quoting, 3D monolithic pool simulation, and Titan Leaves™ foliage defense.",
                        fontSize = 12.sp,
                        color = PoolTextSecondary,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onNavigateToV60Web,
                            colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                            modifier = Modifier
                                .weight(1.2f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("🌐 Open V60 Web Platform", fontSize = 11.sp, fontWeight = FontWeight.Black, color = PoolDarkBg)
                        }

                        Button(
                            onClick = onNavigateToEstimator,
                            colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                            modifier = Modifier
                                .weight(0.9f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("📊 Instant BOQ", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                        }
                    }
                }
            }
        }

        // Hero Section
        item {
            Spacer(modifier = Modifier.height(4.dp))
            HeroBanner(
                onEstimateClick = onNavigateToEstimator,
                onBookClick = onNavigateToBooking
            )
        }

        // Live stats counters
        item {
            StatCountersRow()
        }

        // Promote Downloading the App to the Crowd & Claiming Accounts-Approved Credit
        item {
            val promoCreditUsd by viewModel.accountsPromoCreditUsd.collectAsState()
            val isPromoLocked by viewModel.accountsPromotionsLocked.collectAsState()

            AppDownloadPromotionCard(
                promoCreditUsd = promoCreditUsd,
                isPromoLocked = isPromoLocked,
                onShareApp = {
                    openWhatsApp(
                        context,
                        "🏊 Download Demash Dzimbabwe Pools App! Built for Zimbabwean soils & weather (Norton, Harare, Bulawayo). Get instant 10-second quotes with 32% savings compared to Harare contractors, 10-year warranty, and apply an Accounts Department approved US$${promoCreditUsd.toInt()} contract credit on your new pool build upon deposit clearance! Check it out: https://ais-dev-hovuo3yz75o2jlyzziavm3-161436339311.europe-west2.run.app"
                    )
                },
                onClaimVoucher = {
                    try {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Demash Voucher", "DZ-APP-${promoCreditUsd.toInt()}")
                        clipboard.setPrimaryClip(clip)
                    } catch (_: Exception) {}
                    openWhatsApp(
                        context,
                        "Mhoroi / Salibonani Demash Pools! I installed your app and I am requesting to apply the Accounts Dept approved US$${promoCreditUsd.toInt()} Contract Credit (Code: DZ-APP-${promoCreditUsd.toInt()}) toward my turnkey pool build upon 60% deposit clearance. My property is in Zimbabwe and I would like to schedule a site survey."
                    )
                },
                onShowGuide = { showInstallDialog = true }
            )
        }

        // Zimbabwe Market Standards, Packaging & Sizing Specs
        item {
            ZimbabweMarketStandardsCard()
        }

        // Services Overview
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "OUR CRAFT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "One Team. Pool & Garden.",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolTextPrimary
                        )
                    }
                    Text(
                        text = "Save 10–15% Bundled",
                        fontSize = 11.sp,
                        color = PoolAqua,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        items(repository.services) { service ->
            ServiceSummaryCard(
                service = service,
                onClick = { viewModel.openServiceDetails(service) }
            )
        }

        // Titan Leaves™ Major Upgrade Announcement
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, PoolBorderGold, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(PoolGreen.copy(alpha = 0.2f))
                            .border(1.dp, PoolGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🌿", fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("UPGRADED TO TITAN LEAVES™ TODAY", fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = PoolGreen, letterSpacing = 0.5.sp)
                        }
                        Text("Anti-Clog Heavy Foliage System", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
                        Text("All new builds now standard with Titan Leaves™ vortex skimmers & pre-pump canisters — zero clogs during Jacaranda & Msasa seasonal leaf fall.", fontSize = 11.sp, color = PoolTextSecondary, lineHeight = 14.sp)
                    }
                }
            }
        }

        // Featured Pricing Packages
        item {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    text = "TRANSPARENT PRICING",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolGold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Priced to Win Your Business",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolTextPrimary
                )
                Text(
                    text = "Typical Zimbabwe builders charge ~32% more for the same spec. We keep the quality, cut the fat.",
                    fontSize = 13.sp,
                    color = PoolTextMuted,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )
            }
        }

        items(repository.pricingPackages) { pkg ->
            PackageCard(
                pkg = pkg,
                onSelectPackage = {
                    viewModel.prepareQuickBooking(pkg.name, pkg.dimensions)
                },
                onWhatsAppPackage = {
                    openWhatsApp(
                        context,
                        "Hi Dzimbabwe Pools! I am interested in your ${pkg.name} package (${pkg.dimensions}) at US$${pkg.priceUsd}. Please send me the full spec."
                    )
                }
            )
        }

        // Why Us: Straight-Up Comparison Table
        item {
            WhyUsComparisonCard()
        }

        // 4-Step Journey
        item {
            JourneyTimelineCard()
        }

        // Bottom CTA Banner
        item {
            SurveyCTABanner(
                onBookClick = onNavigateToBooking,
                onWhatsAppClick = {
                    openWhatsApp(
                        context,
                        "Hi Demash Pools! I want to book a US$50 site visit and get my 3D concept sketch."
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showInstallDialog) {
        InstallAppGuideDialog(onDismiss = { showInstallDialog = false })
    }
}

@Composable
private fun HeroBanner(
    onEstimateClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Background image asset
            Image(
                painter = painterResource(id = R.drawable.hero_pool_banner),
                contentDescription = "Resort Pool",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            )

            // Gradient veil for text readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                PoolDarkBg.copy(alpha = 0.35f),
                                PoolDarkBg.copy(alpha = 0.85f),
                                PoolDarkBg
                            )
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                // Eyebrow pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PoolSurfaceElevated.copy(alpha = 0.9f))
                        .border(1.dp, PoolBorderGold, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "🏊 DEMASH DZIMBABWE POOLS · 9+ Years · Nationwide Construction",
                        color = PoolGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "World-Class Resort Pools & Landscaping, Built for Zimbabwe.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolTextPrimary,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Demash monolithic gunite, fiberglass & rim-flow pools — typically 32% below market rates. Fixed-price contracts & 10-year structural warranty.",
                    fontSize = 12.sp,
                    color = PoolTextSecondary,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onEstimateClick,
                        colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hero_estimate_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = PoolDarkBg,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Estimate Cost",
                            color = PoolDarkBg,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = onBookClick,
                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceElevated),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hero_book_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = PoolAqua,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Book Survey",
                            color = PoolAqua,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Trust highlights
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("✓ Fixed price", fontSize = 11.sp, color = PoolTextMuted)
                    Text("🛡️ 10-yr warranty", fontSize = 11.sp, color = PoolTextMuted)
                    Text("⚡ Replies ~90 min", fontSize = 11.sp, color = PoolTextMuted)
                }
            }
        }
    }
}

@Composable
private fun ServiceSummaryCard(
    service: com.example.data.model.PoolService,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, PoolBorder, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = service.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PoolSurfaceElevated)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = service.startingPrice,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolGold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.description,
                    fontSize = 12.sp,
                    color = PoolTextSecondary,
                    lineHeight = 16.sp,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Details",
                tint = PoolAqua,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun PackageCard(
    pkg: PricingPackage,
    onSelectPackage: () -> Unit,
    onWhatsAppPackage: () -> Unit
) {
    val isFeatured = pkg.isFeatured

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFeatured) PoolSurfaceElevated else PoolSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isFeatured) 1.5.dp else 1.dp,
                color = if (isFeatured) PoolGold else PoolBorder,
                shape = RoundedCornerShape(18.dp)
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (isFeatured) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PoolGold)
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "★ MOST POPULAR BUILD ACROSS ZIMBABWE ★",
                        color = PoolDarkBg,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = pkg.name,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoolTextPrimary
                        )
                        Text(
                            text = pkg.dimensions,
                            fontSize = 12.sp,
                            color = PoolTextMuted
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "US$${pkg.priceUsd}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PoolGold
                        )
                        Text(
                            text = "market ~US$${pkg.marketPriceUsd}",
                            fontSize = 11.sp,
                            color = PoolTextMuted,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Highlights
                pkg.highlights.forEach { highlight ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = PoolGreen,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = highlight,
                            fontSize = 12.sp,
                            color = PoolTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onWhatsAppPackage,
                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurface),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("WhatsApp", color = PoolAqua, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onSelectPackage,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFeatured) PoolGold else PoolAqua
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Get This Pool",
                            color = PoolDarkBg,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WhyUsComparisonCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "WHY US",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PoolGold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Straight-Up Comparison",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )
            Text(
                text = "Same high-tensile build spec, better pricing — that's the whole pitch.",
                fontSize = 12.sp,
                color = PoolTextMuted,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
            )

            // Table header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(PoolSurfaceElevated)
                    .padding(8.dp)
            ) {
                Text("Feature", modifier = Modifier.weight(1.2f), fontSize = 11.sp, color = PoolTextMuted, fontWeight = FontWeight.Bold)
                Text("Dzimbabwe", modifier = Modifier.weight(1.4f), fontSize = 11.sp, color = PoolGold, fontWeight = FontWeight.Bold)
                Text("Typical Builders", modifier = Modifier.weight(1.3f), fontSize = 11.sp, color = PoolTextMuted, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(4.dp))

            ComparisonRow("Price (same spec)", "Baseline (Fair)", "~32% higher")
            ComparisonRow("Contract", "Locked Fixed Price", "Variations common")
            ComparisonRow("Warranty", "10-Year Structural", "1–5 years")
            ComparisonRow("Aftercare", "6 Months FREE", "Paid-only")
            ComparisonRow("Pool & Garden", "One unified crew", "Split contractors")
            ComparisonRow("Site Survey", "US$50 + 3D sketch", "Often rushed")
        }
    }
}

@Composable
private fun ComparisonRow(
    feature: String,
    dzimbabwe: String,
    typical: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(feature, modifier = Modifier.weight(1.2f), fontSize = 11.sp, color = PoolTextSecondary, fontWeight = FontWeight.Medium)
        Text(dzimbabwe, modifier = Modifier.weight(1.4f), fontSize = 11.sp, color = PoolGreen, fontWeight = FontWeight.SemiBold)
        Text(typical, modifier = Modifier.weight(1.3f), fontSize = 11.sp, color = PoolTextMuted)
    }
}

@Composable
private fun JourneyTimelineCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "THE JOURNEY",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PoolGold,
                letterSpacing = 1.sp
            )
            Text(
                text = "From First Chat to First Swim",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )

            Spacer(modifier = Modifier.height(14.dp))

            JourneyStep(
                stepNum = "1",
                title = "Site Visit & Survey (US$50)",
                desc = "Our engineer measures ground levels, tests soil density & water pressure, and drafts a 3D concept on the spot."
            )
            JourneyStep(
                stepNum = "2",
                title = "Fixed Quote in 48 Hours",
                desc = "Formal locked quotation sent via WhatsApp or email. The price is guaranteed with zero surprise extras."
            )
            JourneyStep(
                stepNum = "3",
                title = "Build in 3–6 Weeks",
                desc = "Excavation, rebar cage, gunite shell, waterline tiles, marbelite, and plumbing — with weekly WhatsApp video updates."
            )
            JourneyStep(
                stepNum = "4",
                title = "First Swim & 6 Months Care",
                desc = "Commissioning, safety handover, chemical balancing, plus 6 months free maintenance visits included."
            )
        }
    }
}

@Composable
private fun JourneyStep(
    stepNum: String,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(PoolGold),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNum,
                color = PoolDarkBg,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = desc,
                fontSize = 12.sp,
                color = PoolTextSecondary,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun SurveyCTABanner(
    onBookClick: () -> Unit,
    onWhatsAppClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorderGold, RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ready to Transform Your Yard?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Book a professional survey for US$50 — 3D concept sketch included. Based in Norton, building across Zimbabwe.",
                fontSize = 12.sp,
                color = PoolTextSecondary,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onWhatsAppClick,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolSurface),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("WhatsApp Us", color = PoolAqua, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Book Visit", color = PoolDarkBg, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AppDownloadPromotionCard(
    promoCreditUsd: Double,
    isPromoLocked: Boolean,
    onShareApp: () -> Unit,
    onClaimVoucher: () -> Unit,
    onShowGuide: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurfaceElevated),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, PoolBorderGold, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PoolGold)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "📲 OFFICIAL ANDROID APP",
                        color = PoolDarkBg,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isPromoLocked) PoolBorder.copy(alpha = 0.3f) else PoolGreen.copy(alpha = 0.2f))
                        .border(1.dp, if (isPromoLocked) PoolTextMuted else PoolGreen, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isPromoLocked) Icons.Default.Lock else Icons.Default.Redeem,
                            contentDescription = null,
                            tint = if (isPromoLocked) PoolTextMuted else PoolGreen,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isPromoLocked) "PROMO PAUSED BY ACCOUNTS" else "US$${promoCreditUsd.toInt()} ACCOUNTS CREDIT",
                            color = if (isPromoLocked) PoolTextMuted else PoolGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Get Demash Pools App on Your Phone",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isPromoLocked) {
                    "Demash Accounts Department policy: Promotions are currently paused to protect material reserves. Standard competitive direct pricing is active across all turnkey packages."
                } else {
                    "Accounts Department Approved Policy: Claim an authorized US$${promoCreditUsd.toInt()} contract credit toward any turnkey pool build ($4,500+) upon clearance of your 60% construction deposit. Non-redeemable for unbacked cash."
                },
                fontSize = 12.sp,
                color = PoolTextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Accounts Prudence Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(PoolSurface)
                    .border(1.dp, PoolBorder, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⚖️ Accounts Rule: We cannot disburse funds or cash handouts without cleared project deposits in hand. 100% of funds go directly into PPC cement, rebar steel, and verified milestone artisan work.",
                        fontSize = 10.sp,
                        color = PoolAqua,
                        lineHeight = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // App benefits pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "⚡ 10s Offline BOQ",
                    "🇿🇼 Zim Market Pricing",
                    "🛡️ 10-Yr Guarantee"
                ).forEach { pill ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(PoolSurface)
                            .border(1.dp, PoolBorder, RoundedCornerShape(6.dp))
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = pill,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PoolAqua
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Dual action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onShareApp,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceHighlight),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.1f)
                        .height(42.dp)
                        .testTag("share_app_whatsapp_button")
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = PoolAqua, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share on WhatsApp", color = PoolAqua, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onClaimVoucher,
                    enabled = !isPromoLocked,
                    colors = ButtonDefaults.buttonColors(containerColor = if (isPromoLocked) PoolBorder else PoolGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.1f)
                        .height(42.dp)
                        .testTag("claim_app_voucher_button")
                ) {
                    Icon(imageVector = Icons.Default.Redeem, contentDescription = null, tint = PoolDarkBg, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isPromoLocked) "Promo Paused" else "Apply US$${promoCreditUsd.toInt()} Credit",
                        color = PoolDarkBg,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quick instruction link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowGuide() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.PhoneAndroid, contentDescription = null, tint = PoolTextMuted, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "How to install / add to phone home screen →",
                    fontSize = 11.sp,
                    color = PoolTextMuted,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
fun ZimbabweMarketStandardsCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
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
                        text = "STANDARDS & PACKAGING",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "The Authentic Zimbabwe Way",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PoolSurfaceElevated)
                        .border(1.dp, PoolBorderGold, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "🇿🇼 Norton & Harare Spec",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "All pricing, Bills of Quantities, and contractor estimates are computed strictly in authentic Zimbabwean trade units — zero confusing overseas sizing.",
                fontSize = 12.sp,
                color = PoolTextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(PoolSurfaceElevated)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ZimStandardRow(
                    item = "Cement Packaging",
                    spec = "50kg Bags PPC Surebuild 42.5R High-Strength",
                    unitPrice = "US$10.50/bag delivered"
                )
                ZimStandardRow(
                    item = "Steel Reinforcement",
                    spec = "6-Meter Standard Lengths Y10 & Y12 Deformed Rebar",
                    unitPrice = "US$8.50/bar (BS4449)"
                )
                ZimStandardRow(
                    item = "River Sand & Stone",
                    spec = "Cubic Meters (m³) Washed Manyame Sand & 19mm Blue Granite",
                    unitPrice = "US$22/ton / US$18/m³"
                )
                ZimStandardRow(
                    item = "Plaster & Coping",
                    spec = "25kg Bags Marble Plaster (Marbelite) & 300x150mm Bullnose Pavers",
                    unitPrice = "US$18/bag Marbelite"
                )
                ZimStandardRow(
                    item = "Pipes & Sand Filter",
                    spec = "Class 9 & 12 50mm Pressure PVC (6m sticks) + 50kg Graded Silica Sand",
                    unitPrice = "2, 3 & 4-Bag Systems"
                )
                ZimStandardRow(
                    item = "Currency & Cash",
                    spec = "USD Cash (crisp clean notes), EcoCash USD, Innbucks, Nostro & ZiG",
                    unitPrice = "No Surcharge / 32% Discount"
                )
            }
        }
    }
}

@Composable
private fun ZimStandardRow(
    item: String,
    spec: String,
    unitPrice: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PoolTextPrimary)
            Text(text = spec, fontSize = 10.sp, color = PoolTextMuted, lineHeight = 13.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = unitPrice, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = PoolGold)
    }
}

@Composable
fun InstallAppGuideDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PoolSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📲 Install Demash Pools App",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolGold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PoolTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Add Demash Pools directly to your phone screen for instant offline quoting and project tracking:",
                    fontSize = 12.sp,
                    color = PoolTextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(PoolSurfaceElevated)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Text("1.", fontWeight = FontWeight.Bold, color = PoolGold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tap the three dots (⋮) in your Chrome / Edge browser top corner.", fontSize = 11.sp, color = PoolTextPrimary)
                    }
                    Row(verticalAlignment = Alignment.Top) {
                        Text("2.", fontWeight = FontWeight.Bold, color = PoolGold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Select 'Install app' or 'Add to Home screen'.", fontSize = 11.sp, color = PoolTextPrimary)
                    }
                    Row(verticalAlignment = Alignment.Top) {
                        Text("3.", fontWeight = FontWeight.Bold, color = PoolGold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Launch instantly anytime without typing links — fully offline supported!", fontSize = 11.sp, color = PoolAqua)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = PoolGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Got It · Return to App", color = PoolDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
