package com.leonoretech.dragoniclasvegas.ui.minigames

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.ui.theme.VoidBlack
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

private class Laser(var x: Float, var y: Float, val speed: Float)
private class Orb(var x: Float, var y: Float)

@Composable
fun DragonDodgeScreen(viewModel: PlayerViewModel, onExit: () -> Unit) {
    var playerX by remember { mutableFloatStateOf(0.5f) }
    var survivalTime by remember { mutableFloatStateOf(0f) }
    var energyCollected by remember { mutableIntStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    val lasers = remember { mutableStateListOf<Laser>() }
    val orbs = remember { mutableStateListOf<Orb>() }
    var laserSpawnTimer by remember { mutableFloatStateOf(0f) }
    var orbSpawnTimer by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        while (!gameOver) {
            delay(16)
            survivalTime += 0.016f
            laserSpawnTimer += 0.016f
            orbSpawnTimer += 0.016f

            val difficulty = (survivalTime / 20f).coerceAtMost(1.5f)
            val spawnInterval = (0.85f - difficulty * 0.45f).coerceAtLeast(0.28f)
            if (laserSpawnTimer > spawnInterval) {
                laserSpawnTimer = 0f
                lasers.add(Laser(Random.nextFloat() * 0.9f + 0.05f, -0.05f, 0.012f + difficulty * 0.01f))
            }
            if (orbSpawnTimer > 1.6f) {
                orbSpawnTimer = 0f
                orbs.add(Orb(Random.nextFloat() * 0.85f + 0.075f, -0.05f))
            }

            val laserIt = lasers.iterator()
            while (laserIt.hasNext()) {
                val l = laserIt.next()
                l.y += l.speed
                if (l.y > 1.1f) {
                    laserIt.remove()
                    continue
                }
                if (l.y in 0.82f..0.96f && kotlin.math.abs(l.x - playerX) < 0.07f) {
                    gameOver = true
                }
            }

            val orbIt = orbs.iterator()
            while (orbIt.hasNext()) {
                val o = orbIt.next()
                o.y += 0.01f
                if (o.y > 1.1f) {
                    orbIt.remove()
                    continue
                }
                if (o.y in 0.82f..0.96f && kotlin.math.abs(o.x - playerX) < 0.08f) {
                    energyCollected += 10
                    orbIt.remove()
                }
            }
        }
    }

    LaunchedEffect(gameOver) {
        if (gameOver) {
            val coinsEarned = energyCollected + survivalTime.toInt() * 2
            val xpEarned = survivalTime.toInt() * 3
            viewModel.onGameFinished(coinsEarned = coinsEarned, xpEarned = xpEarned)
            viewModel.unlockIfScoreAbove("dodge_survivor", survivalTime.toInt(), 60)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VoidBlack)
            .pointerInput(gameOver) {
                detectDragGestures { change, _ ->
                    if (!gameOver) {
                        playerX = (change.position.x / size.width).coerceIn(0.06f, 0.94f)
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            lasers.forEach { l ->
                drawRect(
                    color = Color(0xFFFF3860),
                    topLeft = Offset(l.x * size.width - 4f, 0f),
                    size = androidx.compose.ui.geometry.Size(8f, l.y * size.height)
                )
            }
            orbs.forEach { o ->
                drawCircle(
                    color = NeonGold,
                    radius = 16f,
                    center = Offset(o.x * size.width, o.y * size.height)
                )
            }
            drawCircle(
                color = NeonBlue,
                radius = 24f,
                center = Offset(playerX * size.width, size.height * 0.89f)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("SURVIVED ${survivalTime.toInt()}s", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("\u26A1 Energy: $energyCollected", color = NeonGold, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
        }
        Text(
            "Drag anywhere to move",
            color = TextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.padding(top = 52.dp, start = 16.dp)
        )

        if (gameOver) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("HIT!", color = Color(0xFFFF3860), fontSize = 28.sp, fontWeight = FontWeight.Black)
                    Text("Survived ${survivalTime.toInt()}s  \u2022  Energy: $energyCollected", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp, bottom = 24.dp))
                    Text("EXIT", color = NeonBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onExit() }.padding(12.dp))
                }
            }
        }
    }
}
