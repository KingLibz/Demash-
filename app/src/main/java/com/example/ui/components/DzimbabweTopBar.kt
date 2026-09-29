package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.AppLanguage
import com.example.core.LanguageManager
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolBorderGold
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolGreen
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolSurfaceElevated
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DzimbabweTopBar(
    onBookClick: () -> Unit,
    currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    onLanguageChange: (AppLanguage) -> Unit = {},
    isAdminActive: Boolean = false,
    onLogoLongClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Surface(
        color = PoolDarkBg,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            // Live urgency alert banner with Zimbabwe language support
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                PoolSurfaceElevated,
                                PoolSurface,
                                PoolSurfaceElevated
                            )
                        )
                    )
                    .clickable { onBookClick() }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(PoolGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.SHONA -> "🎉 3D sketch yemahara paUS$50 survey · Maslot 3 asara svondo rino"
                            AppLanguage.NDEBELE -> "🎉 Isilinganiso se-3D sketch mahhala ku-US$50 survey · Izikhala ezi-3 ezisele"
                            AppLanguage.ENGLISH -> "🎉 3D Concept Sketch included with every survey · 3 slots left"
                        },
                        color = PoolGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }

                Text(
                    text = when (currentLanguage) {
                        AppLanguage.SHONA -> "Bhuka →"
                        AppLanguage.NDEBELE -> "Bhuka →"
                        AppLanguage.ENGLISH -> "Book →"
                    },
                    color = PoolAqua,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Main Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand logo & title with direct and long-press staff entry
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .combinedClickable(
                            onClick = { onLogoLongClick() },
                            onLongClick = onLogoLongClick
                        )
                        .testTag("brand_logo_header")
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(PoolSurfaceElevated, PoolSurface)
                                )
                            )
                            .border(
                                width = if (isAdminActive) 1.5.dp else 1.dp,
                                color = if (isAdminActive) PoolGold else PoolBorder,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isAdminActive) Icons.Default.Security else Icons.Default.Pool,
                            contentDescription = "Demash Pools Logo",
                            tint = PoolGold,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Demash",
                                color = PoolTextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Pools",
                                color = PoolGold,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            if (isAdminActive) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(PoolGold)
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "MANAGER",
                                        color = PoolDarkBg,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                        }
                        Text(
                            text = if (isAdminActive) "Owner Control Active" else "Tap for DZ Manager",
                            color = if (isAdminActive) PoolAqua else PoolTextMuted,
                            fontSize = 10.sp
                        )
                    }
                }

                // Right actions: Fast Language Switcher (EN | SN | ND), Admin Toggle, WhatsApp, Call
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    // Quick Admin Room Access Icon
                    IconButton(
                        onClick = onLogoLongClick,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(if (isAdminActive) PoolGold else PoolSurfaceElevated)
                            .border(1.dp, if (isAdminActive) PoolGold else PoolBorder, CircleShape)
                            .testTag("admin_toggle_quick_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Admin Control Room",
                            tint = if (isAdminActive) PoolDarkBg else PoolGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Language switcher pills
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PoolSurfaceElevated)
                            .border(1.dp, PoolBorder, RoundedCornerShape(8.dp))
                            .padding(2.dp)
                    ) {
                        AppLanguage.values().forEach { lang ->
                            val isSel = lang == currentLanguage
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSel) PoolGold else PoolSurfaceElevated)
                                    .clickable { onLanguageChange(lang) }
                                    .padding(horizontal = 6.dp, vertical = 4.dp)
                                    .testTag("lang_${lang.code}")
                            ) {
                                Text(
                                    text = lang.shortLabel,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) PoolDarkBg else PoolTextMuted
                                )
                            }
                        }
                    }

                    // WhatsApp action
                    IconButton(
                        onClick = {
                            openWhatsApp(
                                context,
                                when (currentLanguage) {
                                    AppLanguage.SHONA -> "Mhoroi Dzimbabwe Pools! Ndiri kushandisa Android app yenyu, ndinoda quotation yePool."
                                    AppLanguage.NDEBELE -> "Salibonani Dzimbabwe Pools! Ngisebenzisa i-Android app yenu, ngicela i-quotation yechibi."
                                    AppLanguage.ENGLISH -> "Hi Dzimbabwe Pools! I am using your Android app and would like a pool quotation."
                                }
                            )
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PoolSurfaceElevated)
                            .testTag("whatsapp_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Chat on WhatsApp",
                            tint = PoolGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Direct Call action
                    IconButton(
                        onClick = {
                            openDialer(context, "+263784219178")
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PoolGold)
                            .testTag("call_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call Engineer",
                            tint = PoolDarkBg,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

fun openWhatsApp(context: Context, message: String, targetPhone: String = "263784219178") {
    try {
        val cleanPhone = targetPhone.replace("+", "").replace(" ", "").replace("-", "")
        val encodedMsg = Uri.encode(message)
        val uri = Uri.parse("https://wa.me/$cleanPhone?text=$encodedMsg")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (_: Exception) {
        openDialer(context, targetPhone)
    }
}

fun openDialer(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
    } catch (_: Exception) {}
}
