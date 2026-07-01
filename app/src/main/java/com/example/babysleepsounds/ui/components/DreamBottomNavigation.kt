package com.example.babysleepsounds.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.babysleepsounds.ui.theme.CardNight
import com.example.babysleepsounds.ui.theme.LightPurple
import com.example.babysleepsounds.ui.theme.SoftLavender

private data class NavItem(val tab: DreamNavTab, val icon: ImageVector)

enum class DreamNavTab(val label: String) {
    Mixer("Mixer"),
    Favorites("Favorites"),
    Timer("Timer"),
    More("More")
}

@Composable
fun DreamBottomNavigation(
    selectedTab: DreamNavTab,
    onTabSelected: (DreamNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(DreamNavTab.Mixer, Icons.Rounded.GraphicEq),
        NavItem(DreamNavTab.Favorites, Icons.Rounded.Favorite),
        NavItem(DreamNavTab.Timer, Icons.Rounded.Bedtime),
        NavItem(DreamNavTab.More, Icons.Rounded.MoreHoriz)
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .shadow(24.dp, RoundedCornerShape(32.dp), spotColor = SoftLavender.copy(alpha = .42f)),
        shape = RoundedCornerShape(32.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, Color.White.copy(alpha = .10f)),
        tonalElevation = 6.dp
    ) {
        Row(
            Modifier
                .background(Brush.linearGradient(listOf(CardNight.copy(alpha = .98f), Color(0xFF171D38).copy(alpha = .98f))))
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val active = item.tab == selectedTab
                val container by animateColorAsState(if (active) SoftLavender.copy(alpha = .24f) else Color.Transparent, label = "navContainer")
                val iconSize by animateDpAsState(if (active) 26.dp else 23.dp, label = "navIcon")
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 58.dp)
                        .background(container, RoundedCornerShape(22.dp))
                        .clickable(role = Role.Tab) { onTabSelected(item.tab) }
                        .padding(horizontal = 6.dp, vertical = 8.dp)
                ) {
                    Icon(
                        item.icon,
                        contentDescription = item.tab.label,
                        tint = if (active) Color.White else LightPurple.copy(alpha = .78f),
                        modifier = Modifier.size(iconSize)
                    )
                    Text(
                        item.tab.label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (active) FontWeight.ExtraBold else FontWeight.SemiBold,
                        color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
