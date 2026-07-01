package com.example.babysleepsounds.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.babysleepsounds.ui.theme.CardNight
import com.example.babysleepsounds.ui.theme.SoftLavender

private data class NavItem(val label: String, val icon: ImageVector)

@Composable
fun DreamBottomNavigation(modifier: Modifier = Modifier) {
    val items = listOf(NavItem("Mixer", Icons.Rounded.GraphicEq), NavItem("Favorites", Icons.Rounded.Favorite), NavItem("Timer", Icons.Rounded.Bedtime), NavItem("More", Icons.Rounded.MoreHoriz))
    Row(
        modifier.fillMaxWidth().shadow(18.dp, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp), spotColor = SoftLavender.copy(alpha = .35f)).background(CardNight.copy(alpha = .96f), RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)).padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val active = index == 0
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.background(if (active) SoftLavender.copy(alpha = .18f) else Color.Transparent, RoundedCornerShape(18.dp)).padding(horizontal = 10.dp, vertical = 6.dp).height(48.dp)) {
                Icon(item.icon, contentDescription = item.label, tint = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                Text(item.label, style = MaterialTheme.typography.labelMedium, color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
