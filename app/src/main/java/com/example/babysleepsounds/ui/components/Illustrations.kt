package com.example.babysleepsounds.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.babysleepsounds.domain.model.SleepSound
import com.example.babysleepsounds.ui.theme.CloudWhite
import com.example.babysleepsounds.ui.theme.DreamPink
import com.example.babysleepsounds.ui.theme.FanBlue
import com.example.babysleepsounds.ui.theme.LightPurple
import com.example.babysleepsounds.ui.theme.OceanBlue
import com.example.babysleepsounds.ui.theme.SoftLavender
import com.example.babysleepsounds.ui.theme.SoftYellowMoon

@Composable
fun SleepingBabyMoonIllustration(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "moonGlow")
    val glow by transition.animateFloat(0.55f, 1f, infiniteRepeatable(tween(2600, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "glow")
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        drawCircle(SoftYellowMoon.copy(alpha = 0.18f * glow), radius = w * 0.38f, center = Offset(w * 0.58f, h * 0.43f))
        drawCircle(SoftYellowMoon, radius = w * 0.23f, center = Offset(w * 0.58f, h * 0.43f))
        drawCircle(Color(0xFF182044), radius = w * 0.21f, center = Offset(w * 0.67f, h * 0.35f))
        drawOval(CloudWhite, topLeft = Offset(w * 0.28f, h * 0.53f), size = Size(w * 0.45f, h * 0.2f))
        drawCircle(Color(0xFFFFC6A8), radius = w * 0.07f, center = Offset(w * 0.44f, h * 0.50f))
        drawOval(LightPurple, topLeft = Offset(w * 0.43f, h * 0.51f), size = Size(w * 0.28f, h * 0.14f))
        drawArc(Color(0xFF5B4FB9), 15f, 150f, false, topLeft = Offset(w * 0.40f, h * 0.48f), size = Size(w * 0.08f, h * 0.04f), style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
        repeat(6) { i ->
            val x = w * (0.12f + i * 0.14f)
            val y = h * (0.14f + (i % 3) * 0.12f)
            drawCircle(Color.White.copy(alpha = 0.7f), 2.4.dp.toPx(), Offset(x, y))
        }
    }
}

@Composable
fun SoundIllustration(sound: SleepSound, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        when (sound) {
            SleepSound.Rain -> {
                drawCloud(w, h, CloudWhite)
                repeat(4) { drawLine(OceanBlue, Offset(w*(0.28f+it*0.14f), h*0.62f), Offset(w*(0.23f+it*0.14f), h*0.78f), 3.dp.toPx(), cap = StrokeCap.Round) }
                drawArc(Color(0xFF445070), 0f, 180f, false, Offset(w*.38f,h*.38f), Size(w*.08f,h*.05f), style=Stroke(1.5.dp.toPx(), cap=StrokeCap.Round))
            }
            SleepSound.Fan -> {
                drawCircle(FanBlue.copy(alpha=.25f), w*.34f, Offset(w*.5f,h*.45f))
                repeat(3) { i -> rotate(i*120f, Offset(w*.5f,h*.45f)) { drawOval(FanBlue, Offset(w*.49f,h*.13f), Size(w*.13f,h*.32f)) } }
                drawCircle(Color.White, w*.07f, Offset(w*.5f,h*.45f)); drawLine(FanBlue, Offset(w*.5f,h*.52f), Offset(w*.5f,h*.82f), 4.dp.toPx(), cap=StrokeCap.Round)
            }
            SleepSound.Ocean -> repeat(3) { i -> drawArc(OceanBlue.copy(alpha=1f-i*.2f), 180f, 180f, false, Offset(w*(.12f+i*.08f), h*(.32f+i*.13f)), Size(w*.65f,h*.25f), style=Stroke(5.dp.toPx(), cap=StrokeCap.Round)) }
            SleepSound.WhiteNoise -> repeat(5) { i -> drawRoundRect(LightPurple.copy(alpha=.65f+i*.06f), Offset(w*(.18f+i*.13f), h*(.25f+(i%2)*.12f)), Size(w*.07f, h*(.46f-(i%2)*.18f)), cornerRadius=androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())) }
            SleepSound.BrownNoise -> drawCloud(w, h, Color(0xFFD6B08A))
            SleepSound.Heartbeat -> {
                val p=Path().apply{ moveTo(w*.5f,h*.75f); cubicTo(w*.18f,h*.52f,w*.16f,h*.22f,w*.38f,h*.25f); cubicTo(w*.48f,h*.26f,w*.5f,h*.38f,w*.5f,h*.38f); cubicTo(w*.5f,h*.38f,w*.56f,h*.22f,w*.72f,h*.25f); cubicTo(w*.95f,h*.31f,w*.82f,h*.58f,w*.5f,h*.75f)}
                drawPath(p, DreamPink); drawLine(Color.White, Offset(w*.25f,h*.52f), Offset(w*.38f,h*.52f), 2.dp.toPx()); drawLine(Color.White, Offset(w*.38f,h*.52f), Offset(w*.44f,h*.43f), 2.dp.toPx()); drawLine(Color.White, Offset(w*.44f,h*.43f), Offset(w*.52f,h*.60f), 2.dp.toPx()); drawLine(Color.White, Offset(w*.52f,h*.60f), Offset(w*.60f,h*.50f), 2.dp.toPx()); drawLine(Color.White, Offset(w*.60f,h*.50f), Offset(w*.75f,h*.50f), 2.dp.toPx())
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCloud(w: Float, h: Float, color: Color) {
    drawOval(color, Offset(w*.18f,h*.42f), Size(w*.64f,h*.26f))
    drawCircle(color, w*.14f, Offset(w*.34f,h*.42f)); drawCircle(color, w*.18f, Offset(w*.52f,h*.36f)); drawCircle(color, w*.12f, Offset(w*.66f,h*.45f))
}

@Composable
fun EqualizerBars(isPlaying: Boolean, modifier: Modifier = Modifier) {
    val t = rememberInfiniteTransition(label = "eq")
    Row(modifier, verticalAlignment = Alignment.Bottom) {
        repeat(4) { index ->
            val height by t.animateFloat(if (isPlaying) 8f else 4f, if (isPlaying) (16 + index * 4).toFloat() else 4f, infiniteRepeatable(tween(650 + index * 110), RepeatMode.Reverse), label = "bar$index")
            Canvas(Modifier.size(width = 5.dp, height = 24.dp)) { drawRoundRect(SoftLavender, topLeft = Offset(0f, size.height - height.dp.toPx()), size = Size(size.width, height.dp.toPx()), cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx())) }
        }
    }
}
