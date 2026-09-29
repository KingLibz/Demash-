package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary

@Composable
fun StatCountersRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PoolSurface)
            .border(1.dp, PoolBorder, RoundedCornerShape(16.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem(value = "120+", label = "delivered")
        StatDivider()
        StatItem(value = "9 yrs", label = "in trade", highlightGold = true)
        StatDivider()
        StatItem(value = "40+", label = "suburbs")
        StatDivider()
        StatItem(value = "~90m", label = "reply time")
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    highlightGold: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (highlightGold) PoolGold else PoolAqua
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = PoolTextMuted,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .background(PoolBorder)
    )
}
