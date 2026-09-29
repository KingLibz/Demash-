package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.ShowcaseProject
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolBorderGold
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolSurfaceElevated
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary
import com.example.ui.theme.PoolTextSecondary
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowcaseScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val selectedCategory by viewModel.selectedGalleryCategory.collectAsState()
    val allProjects = viewModel.repository.showcaseProjects

    val filteredProjects = if (selectedCategory == "All") {
        allProjects
    } else {
        allProjects.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    val categories = listOf("All", "Gunite", "Infinity", "Plunge", "Landscaping", "Commercial")

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
                    text = "THE LOOK",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PoolGold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Builds We Specialise In",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PoolTextPrimary
                )
                Text(
                    text = "Explore actual resort-grade projects completed across Zimbabwe. Tap any pool to inspect build specs and pricing.",
                    fontSize = 13.sp,
                    color = PoolTextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Filter chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) PoolGold else PoolSurface)
                            .border(
                                1.dp,
                                if (isSelected) PoolGold else PoolBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { viewModel.setGalleryCategory(cat) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("filter_$cat")
                    ) {
                        Text(
                            text = cat,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) PoolDarkBg else PoolTextSecondary
                        )
                    }
                }
            }
        }

        // Projects list
        items(filteredProjects) { project ->
            ShowcaseCard(
                project = project,
                onClick = { viewModel.openProjectDetails(project) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ShowcaseCard(
    project: ShowcaseProject,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PoolSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, PoolBorder, RoundedCornerShape(18.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                AsyncImage(
                    model = project.imageUrl,
                    contentDescription = project.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )

                // Dark gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    PoolDarkBg.copy(alpha = 0.2f),
                                    PoolDarkBg.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                // Category pill
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PoolDarkBg.copy(alpha = 0.85f))
                        .border(1.dp, PoolBorderGold, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = project.category,
                        color = PoolGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Price pill
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PoolGold)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = project.approxPrice,
                        color = PoolDarkBg,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                // Title and Location overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Text(
                        text = project.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PoolTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = PoolAqua,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = project.location,
                            fontSize = 12.sp,
                            color = PoolTextSecondary
                        )
                    }
                }
            }

            // Specs bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("DIMENSIONS", fontSize = 9.sp, color = PoolTextMuted, fontWeight = FontWeight.Bold)
                        Text(project.dimensions, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PoolTextPrimary)
                    }
                    Column {
                        Text("BUILD TIME", fontSize = 9.sp, color = PoolTextMuted, fontWeight = FontWeight.Bold)
                        Text(project.buildTime, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PoolTextPrimary)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("View Spec", fontSize = 12.sp, color = PoolAqua, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = PoolAqua,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
