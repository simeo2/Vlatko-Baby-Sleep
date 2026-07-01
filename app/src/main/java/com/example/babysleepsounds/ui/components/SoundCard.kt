package com.example.babysleepsounds.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.babysleepsounds.domain.model.SoundPlaybackState
import com.example.babysleepsounds.ui.theme.CardNight
import com.example.babysleepsounds.ui.theme.LightPurple
import com.example.babysleepsounds.ui.theme.SoftLavender

@Composable
fun SoundCard(
    state: SoundPlaybackState,
    onToggle: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val elevation by animateDpAsState(if (state.isPlaying) 12.dp else 4.dp, label = "soundElevation")
    val borderColor by animateColorAsState(if (state.isPlaying) SoftLavender else Color.White.copy(alpha = 0.08f), label = "soundBorder")
    Card(
        modifier = modifier.shadow(elevation, RoundedCornerShape(28.dp), spotColor = SoftLavender.copy(alpha = if (state.isPlaying) 0.55f else 0.18f)),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            Modifier.background(Brush.verticalGradient(listOf(CardNight.copy(alpha = 0.98f), Color(0xFF252C54)))).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                SoundIllustration(state.sound, Modifier.size(64.dp))
                Spacer(Modifier.weight(1f))
                if (state.isPlaying) EqualizerBars(true, Modifier.padding(end = 6.dp))
                IconButton(onClick = onToggle, modifier = Modifier.background(SoftLavender.copy(alpha = 0.18f), RoundedCornerShape(16.dp))) {
                    Icon(if (state.isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow, contentDescription = if (state.isPlaying) "Pause ${state.sound.displayName}" else "Play ${state.sound.displayName}", tint = Color.White)
                }
            }
            Text(state.sound.displayName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Volume", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.weight(1f))
                Text("${(state.volume * 100).toInt()}%", style = MaterialTheme.typography.labelMedium, color = LightPurple)
            }
            Slider(
                value = state.volume,
                onValueChange = onVolumeChange,
                colors = SliderDefaults.colors(thumbColor = Color.White, activeTrackColor = SoftLavender, inactiveTrackColor = Color.White.copy(alpha = 0.16f))
            )
        }
    }
}
