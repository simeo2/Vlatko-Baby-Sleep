package com.example.babysleepsounds.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.babysleepsounds.ui.components.DreamNavTab
import com.example.babysleepsounds.ui.components.SleepingBabyMoonIllustration
import com.example.babysleepsounds.ui.components.SoundCard
import com.example.babysleepsounds.ui.components.TimerSection
import com.example.babysleepsounds.ui.theme.CardNight
import com.example.babysleepsounds.ui.theme.DeepMidnightBlue
import com.example.babysleepsounds.ui.theme.LightPurple
import com.example.babysleepsounds.ui.theme.MidnightBlue
import com.example.babysleepsounds.ui.theme.SoftLavender
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepSoundsScreen(uiState: SleepSoundsUiState, onToggleSound: (SleepSound) -> Unit, onVolumeChange: (SleepSound, Float) -> Unit, onSelectTimer: (SleepTimerOption) -> Unit, onClearTimerSelection: () -> Unit) {
    var selectedTab by remember { mutableStateOf(DreamNavTab.Mixer) }
    var showMore by remember { mutableStateOf(false) }
    val gridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val favoriteSounds = remember(uiState.sounds) { uiState.sounds.filter { it.isPlaying }.ifEmpty { uiState.sounds.take(2) } }
    val visibleSounds = if (selectedTab == DreamNavTab.Favorites) favoriteSounds else uiState.sounds

    LaunchedEffect(selectedTab) {
        if (selectedTab == DreamNavTab.Timer) gridState.animateScrollToItem(uiState.sounds.size + 2)
        if (selectedTab == DreamNavTab.Mixer || selectedTab == DreamNavTab.Favorites) gridState.animateScrollToItem(0)
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            DreamBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    if (tab == DreamNavTab.More) showMore = true else selectedTab = tab
                    if (tab == DreamNavTab.Timer) scope.launch { gridState.animateScrollToItem(uiState.sounds.size + 2) }
                },
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(DeepMidnightBlue, MidnightBlue, Color(0xFF241E4F))))) {
            BedtimeBackground(Modifier.fillMaxSize())
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(168.dp),
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(start = 18.dp, top = 48.dp, end = 18.dp, bottom = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) { HeroHeader(uiState.sounds.count { it.isPlaying }) }
                item(span = { GridItemSpan(maxLineSpan) }) { SectionHeader(selectedTab, visibleSounds.size) }
                items(visibleSounds, key = { it.sound.name }) { soundState ->
                    AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically { it / 8 }, exit = fadeOut()) {
                        SoundCard(state = soundState, onToggle = { onToggleSound(soundState.sound) }, onVolumeChange = { onVolumeChange(soundState.sound, it) })
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    TimerSection(selectedTimerOption = uiState.selectedTimerOption, isTimerPreviewActive = uiState.isTimerPreviewActive, onSelectTimer = onSelectTimer, onClearTimerSelection = onClearTimerSelection, modifier = Modifier.padding(top = 10.dp))
                }
            }
        }
    }

    if (showMore) {
        ModalBottomSheet(onDismissRequest = { showMore = false }, sheetState = sheetState, containerColor = CardNight, contentColor = Color.White) {
            MorePanel(onOpenTimer = { showMore = false; selectedTab = DreamNavTab.Timer }, modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun HeroHeader(activeCount: Int) {
    Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(34.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent), border = BorderStroke(1.dp, Color.White.copy(alpha = .10f))) {
        Row(Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(Color.White.copy(alpha = .10f), SoftLavender.copy(alpha = .10f), Color.Transparent))).padding(20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                AssistChip(onClick = {}, label = { Text("Low-light bedtime mode") }, leadingIcon = { Icon(Icons.Rounded.DarkMode, null, Modifier.size(18.dp)) }, colors = AssistChipDefaults.assistChipColors(containerColor = Color.White.copy(alpha = .10f), labelColor = Color.White, leadingIconContentColor = LightPurple), border = null)
                Text("Baby Sleep Sounds", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("Mix soothing loops for a calmer bedtime — $activeCount playing now.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            SleepingBabyMoonIllustration(Modifier.size(118.dp))
        }
    }
}

@Composable
private fun SectionHeader(selectedTab: DreamNavTab, soundCount: Int) {
    Row(Modifier.fillMaxWidth().padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(if (selectedTab == DreamNavTab.Favorites) "Tonight's favorites" else "Sound mixer", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Text("$soundCount soothing sounds", style = MaterialTheme.typography.bodyMedium, color = LightPurple)
        }
        Icon(if (selectedTab == DreamNavTab.Favorites) Icons.Rounded.Favorite else Icons.Rounded.AutoAwesome, contentDescription = null, tint = LightPurple, modifier = Modifier.size(30.dp))
    }
}

@Composable
private fun MorePanel(onOpenTimer: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.padding(horizontal = 22.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("More", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = Color.White)
        MoreRow(Icons.Rounded.Settings, "Settings", "App preferences and bedtime controls")
        MoreRow(Icons.Rounded.Bedtime, "Sleep timer", "Jump to timer controls", onClick = onOpenTimer)
        MoreRow(Icons.Rounded.DarkMode, "Night comfort", "Dark surfaces, soft contrast, parent-friendly glow")
    }
}

@Composable
private fun MoreRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, onClick: () -> Unit = {}) {
    Card(onClick = onClick, shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .07f)), border = BorderStroke(1.dp, Color.White.copy(alpha = .08f))) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Icon(icon, contentDescription = title, tint = LightPurple, modifier = Modifier.size(28.dp))
            Column { Text(title, style = MaterialTheme.typography.titleMedium, color = Color.White); Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        }
    }
}

@Composable
private fun BedtimeBackground(modifier: Modifier = Modifier) = Canvas(modifier) {
    drawCircle(SoftLavender.copy(alpha = .18f), size.width * .48f, Offset(size.width * .92f, size.height * .10f))
    drawCircle(LightPurple.copy(alpha = .10f), size.width * .38f, Offset(size.width * .04f, size.height * .76f))
    val stars = listOf(.12f to .14f, .28f to .09f, .48f to .16f, .72f to .08f, .88f to .28f, .18f to .42f, .64f to .38f, .36f to .62f, .82f to .66f, .54f to .82f)
    stars.forEachIndexed { i, (x, y) -> drawCircle(Color.White.copy(alpha = if (i % 2 == 0) .78f else .42f), radius = (1.6f + (i % 3)).dp.toPx(), center = Offset(size.width * x, size.height * y)) }
    repeat(4) { i -> drawOval(Color.White.copy(alpha = .045f), Offset(size.width * (.04f + i * .23f), size.height * (.17f + i * .18f)), Size(size.width * .44f, 54.dp.toPx())) }
}
