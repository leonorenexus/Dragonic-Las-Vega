package com.leonoretech.dragoniclasvegas.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPurple

@Composable
fun AnimatedTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: androidx.compose.ui.unit.TextUnit = 40.sp
) {
    val transition = rememberInfiniteTransition(label = "titleGlow")
    val glow by transition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Black,
        textAlign = TextAlign.Center,
        style = androidx.compose.ui.text.TextStyle(
            brush = Brush.linearGradient(listOf(NeonPurple, NeonBlue, NeonGold))
        ),
        modifier = modifier.graphicsLayer { alpha = glow }
    )
}
