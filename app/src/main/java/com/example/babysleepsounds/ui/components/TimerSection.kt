package com.example.babysleepsounds.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.babysleepsounds.domain.model.SleepTimerOption
import com.example.babysleepsounds.ui.theme.CardNight
import com.example.babysleepsounds.ui.theme.CloudWhite
import com.example.babysleepsounds.ui.theme.SoftLavender
import com.example.babysleepsounds.ui.theme.SoftYellowMoon

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimerSection(selectedTimerOption: SleepTimerOption?, isTimerPreviewActive: Boolean, onSelectTimer: (SleepTimerOption) -> Unit, onClearTimerSelection: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(30.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent), border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))) {
        Column(Modifier.background(Brush.linearGradient(listOf(CardNight, Color(0xFF2C2758)))).padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                AlarmCloudIllustration(Modifier.size(76.dp))
                Column {
                    Text("Sleep Timer", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("Sounds will gently stop after the timer ends.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                SleepTimerOption.entries.forEach { option ->
                    val selected = selectedTimerOption == option
                    OutlinedButton(onClick = { onSelectTimer(option) }, shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, if (selected) SoftLavender else Color.White.copy(alpha = 0.24f)), colors = ButtonDefaults.outlinedButtonColors(containerColor = if (selected) SoftLavender else Color.Transparent, contentColor = Color.White)) { Text(option.label.replace("m", " min")) }
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = { onSelectTimer(selectedTimerOption ?: SleepTimerOption.Thirty) }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = SoftLavender, contentColor = Color.White)) { Icon(Icons.Rounded.Bedtime, null); Spacer(Modifier.size(8.dp)); Text("Start Timer", fontWeight = FontWeight.Bold) }
                OutlinedButton(onClick = onClearTimerSelection, enabled = isTimerPreviewActive, shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, disabledContentColor = Color.White.copy(alpha = .38f))) { Text("Clear Timer") }
            }
        }
    }
}

@Composable
private fun AlarmCloudIllustration(modifier: Modifier = Modifier) = Canvas(modifier) {
    val w = size.width; val h = size.height
    drawOval(CloudWhite.copy(alpha = .9f), Offset(w*.08f,h*.58f), Size(w*.84f,h*.28f))
    drawCircle(SoftYellowMoon, w*.24f, Offset(w*.5f,h*.42f))
    drawCircle(Color(0xFF332B58), w*.17f, Offset(w*.5f,h*.42f))
    drawLine(SoftYellowMoon, Offset(w*.5f,h*.42f), Offset(w*.5f,h*.31f), 3.dp.toPx())
    drawLine(SoftYellowMoon, Offset(w*.5f,h*.42f), Offset(w*.6f,h*.48f), 3.dp.toPx())
    drawCircle(SoftLavender, w*.08f, Offset(w*.31f,h*.2f)); drawCircle(SoftLavender, w*.08f, Offset(w*.69f,h*.2f))
}
