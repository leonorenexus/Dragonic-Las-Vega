package com.leonoretech.dragoniclasvegas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPurple
import com.leonoretech.dragoniclasvegas.ui.theme.VoidBlack
import kotlinx.coroutines.delay
import kotlin.random.Random

private data class Particle(
    var x: Float,
    var y: Float,
    val radius: Float,
    val speed: Float,
    val drift: Float,
    val color: androidx.compose.ui.graphics.Color
)

@Composable
fun ParticleBackground(modifier: Modifier = Modifier) {
    val particles = remember {
        List(36) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 3f + 1f,
                speed = Random.nextFloat() * 0.0007f + 0.0002f,
                drift = (Random.nextFloat() - 0.5f) * 0.0004f,
                color = if (Random.nextBoolean()) NeonPurple else NeonBlue
            )
        }
    }
    var tick by remember { mutableStateOf(0) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            particles.forEach { p ->
                p.y -= p.speed
                p.x += p.drift
                if (p.y < 0f) p.y = 1f
                if (p.x < 0f) p.x = 1f
                if (p.x > 1f) p.x = 0f
            }
            tick++
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(VoidBlack, com.leonoretech.dragoniclasvegas.ui.theme.DeepPurple))
            )
    ) {
        // reference tick so recomposition happens
        @Suppress("UNUSED_EXPRESSION") tick
        particles.forEach { p ->
            drawCircle(
                color = p.color.copy(alpha = 0.55f),
                radius = p.radius * size.minDimension * 0.01f + p.radius,
                center = Offset(p.x * size.width, p.y * size.height)
            )
        }
    }
}
