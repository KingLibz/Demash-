package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.data.model.FAQItem
import com.example.data.model.PoolSchoolGuide
import com.example.ui.components.openWhatsApp
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolGreen
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolSurfaceElevated
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary
import com.example.ui.theme.PoolTextSecondary
import com.example.viewmodel.MainViewModel

@Composable
fun GuidesScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val repository = viewModel.repository
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val guides = repository.poolSchoolGuides
    val faqs = repository.faqItems.filter {
        searchQuery.isBlank() ||
                it.question.contains(searchQuery, ignoreCase = true) ||
                it.answer.contains(searchQuery, ignoreCase = true)
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
                    text = "POOL SCHOOL & KNOWLEDGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolGold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Building Smart in Zimbabwe",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolTextPrimary
                )
                Text(
                    text = "Expert engineering advice on red clay soil, load shedding solar pumps, town council bylaws, and clear water care.",
                    fontSize = 13.sp,
                    color = PoolTextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Sub tabs: Pool School vs FAQ
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = PoolSurface,
                contentColor = PoolGold,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = PoolGold
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, PoolBorder, RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Pool School", fontWeight = FontWeight.Bold)
                        }
                    },
                    selectedContentColor = PoolGold,
                    unselectedContentColor = PoolTextMuted
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Questions & Answers", fontWeight = FontWeight.Bold)
                        }
                    },
                    selectedContentColor = PoolGold,
                    unselectedContentColor = PoolTextMuted
                )
            }
        }

        if (selectedTab == 0) {
            // Pool School Guides
            items(guides) { guide ->
                PoolGuideCard(guide = guide)
            }
        } else {
            // FAQ Section
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search questions (e.g., warranty, council, cost)", color = PoolTextMuted, fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = PoolAqua)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PoolSurface,
                        unfocusedContainerColor = PoolSurface,
                        focusedTextColor = PoolTextPrimary,
                        unfocusedTextColor = PoolTextPrimary,
                        focusedIndicatorColor = PoolGold,
                        unfocusedIndicatorColor = PoolBorder
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(faqs) { faq ->
                FAQCard(faq = faq)
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PoolGuideCard(guide: PoolSchoolGuide) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .border(1.dp, PoolBorder, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PoolSurfaceElevated)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = guide.category,
                        color = PoolAqua,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = guide.readTime,
                    color = PoolTextMuted,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = guide.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PoolTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = guide.summary,
                fontSize = 12.sp,
                color = PoolTextSecondary,
                lineHeight = 16.sp
            )

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PoolSurfaceElevated)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    guide.content.forEach { paragraph ->
                        Text(
                            text = paragraph,
                            fontSize = 13.sp,
                            color = PoolTextPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "Show less" else "Read full guide",
                    fontSize = 11.sp,
                    color = PoolGold,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandMore else Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = PoolGold,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun FAQCard(faq: FAQItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .border(1.dp, PoolBorder, RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.question,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolTextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandMore else Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = PoolAqua,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = faq.answer,
                        fontSize = 13.sp,
                        color = PoolTextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
