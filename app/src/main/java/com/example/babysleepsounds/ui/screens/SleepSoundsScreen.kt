package com.example.babysleepsounds.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.babysleepsounds.domain.model.SleepSound
import com.example.babysleepsounds.domain.model.SleepSoundsUiState
import com.example.babysleepsounds.domain.model.SleepTimerOption
import com.example.babysleepsounds.ui.components.DreamBottomNavigation
import com.example.babysleepsounds.ui.components.SleepingBabyMoonIllustration
import com.example.babysleepsounds.ui.components.SoundCard
import com.example.babysleepsounds.ui.components.TimerSection
import com.example.babysleepsounds.ui.theme.DeepMidnightBlue
import com.example.babysleepsounds.ui.theme.LightPurple
import com.example.babysleepsounds.ui.theme.MidnightBlue
import com.example.babysleepsounds.ui.theme.SoftLavender

@Composable
fun SleepSoundsScreen(uiState: SleepSoundsUiState, onToggleSound: (SleepSound) -> Unit, onVolumeChange: (SleepSound, Float) -> Unit, onSelectTimer: (SleepTimerOption) -> Unit, onClearTimerSelection: () -> Unit) {
    Scaffold(containerColor = Color.Transparent, bottomBar = { DreamBottomNavigation(Modifier.navigationBarsPadding()) }) { padding ->
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(DeepMidnightBlue, MidnightBlue, Color(0xFF241E4F))))) {
            BedtimeBackground(Modifier.fillMaxSize())
            LazyVerticalGrid(
                columns = GridCells.Adaptive(170.dp),
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(start = 18.dp, top = 44.dp, end = 18.dp, bottom = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) { HeroHeader() }
                items(uiState.sounds, key = { it.sound.name }) { soundState ->
                    SoundCard(state = soundState, onToggle = { onToggleSound(soundState.sound) }, onVolumeChange = { onVolumeChange(soundState.sound, it) })
                }
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
                    TimerSection(selectedTimerOption = uiState.selectedTimerOption, isTimerPreviewActive = uiState.isTimerPreviewActive, onSelectTimer = onSelectTimer, onClearTimerSelection = onClearTimerSelection, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun HeroHeader() {
    Row(Modifier.fillMaxWidth().padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Baby Sleep Sounds", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
            Text("Mix soothing sounds to help your little one sleep peacefully.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        SleepingBabyMoonIllustration(Modifier.size(116.dp))
        IconButton(onClick = {}) { Icon(Icons.Rounded.Settings, contentDescription = "Settings", tint = Color.White) }
    }
}

@Composable
private fun BedtimeBackground(modifier: Modifier = Modifier) = Canvas(modifier) {
    drawCircle(SoftLavender.copy(alpha = .16f), size.width * .42f, Offset(size.width * .92f, size.height * .12f))
    drawCircle(LightPurple.copy(alpha = .10f), size.width * .36f, Offset(size.width * .05f, size.height * .75f))
    val stars = listOf(.12f to .14f, .28f to .09f, .48f to .16f, .72f to .08f, .88f to .28f, .18f to .42f, .64f to .38f, .36f to .62f, .82f to .66f)
    stars.forEachIndexed { i, (x, y) -> drawCircle(Color.White.copy(alpha = if (i % 2 == 0) .72f else .38f), radius = (1.6f + (i % 3)).dp.toPx(), center = Offset(size.width * x, size.height * y)) }
    repeat(3) { i ->
        val y = size.height * (.18f + i * .22f)
        drawOval(Color.White.copy(alpha = .045f), Offset(size.width * (.06f + i * .25f), y), Size(size.width * .42f, 52.dp.toPx()))
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun SleepSoundsScreenPreview() {
    com.example.babysleepsounds.ui.theme.BabySleepSoundsTheme {
        SleepSoundsScreen(com.example.babysleepsounds.data.mock.MockSleepSoundsData.initialState, {}, { _, _ -> }, {}, {})
    }
}
