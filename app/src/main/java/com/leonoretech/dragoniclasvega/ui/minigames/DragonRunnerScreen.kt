package com.leonoretech.dragoniclasvegas.ui.minigames

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPurple
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.ui.theme.VoidBlack
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

private class Obstacle(var x: Float, val lane: Int, val isCoin: Boolean)

@Composable
fun DragonRunnerScreen(viewModel: PlayerViewModel, onExit: () -> Unit) {
    var playerLane by remember { mutableIntStateOf(1) } // 0=top,1=mid,2=bottom
    var score by remember { mutableIntStateOf(0) }
    var coinsCollected by remember { mutableIntStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    var speed by remember { mutableFloatStateOf(0.012f) }
    val obstacles = remember { mutableStateListOf<Obstacle>() }
    var spawnTimer by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        while (!gameOver) {
            delay(16)
            spawnTimer += 0.016f
            val spawnInterval = (0.9f - speed * 10f).coerceAtLeast(0.35f)
            if (spawnTimer > spawnInterval) {
                spawnTimer = 0f
                val lane = Random.nextInt(3)
                val isCoin = Random.nextFloat() < 0.35f
                obstacles.add(Obstacle(1.15f, lane, isCoin))
            }
            val iterator = obstacles.iterator()
            while (iterator.hasNext()) {
                val obs = iterator.next()
                obs.x -= speed
                if (obs.x < -0.15f) {
                    iterator.remove()
                    if (!obs.isCoin) score += 1
                    continue
                }
                if (obs.x in 0.02f..0.22f && obs.lane == playerLane) {
                    if (obs.isCoin) {
                        coinsCollected += 5
                        iterator.remove()
                    } else {
                        gameOver = true
                    }
                }
            }
            speed = (speed + 0.0000015f).coerceAtMost(0.03f)
        }
    }

    LaunchedEffect(gameOver) {
        if (gameOver) {
            val xpEarned = (score * 2 + coinsCollected)
            viewModel.onGameFinished(coinsEarned = coinsCollected, xpEarned = xpEarned)
            viewModel.unlockIfScoreAbove("runner_master", score, 100)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VoidBlack)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    if (!gameOver) {
                        playerLane = when {
                            offset.y < size.height / 3f -> 0
                            offset.y < size.height * 2f / 3f -> 1
                            else -> 2
                        }
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val laneHeight = size.height / 3f
            for (i in 0..3) {
                drawLine(
                    color = NeonPurple.copy(alpha = 0.15f),
                    start = Offset(0f, laneHeight * i),
                    end = Offset(size.width, laneHeight * i),
                    strokeWidth = 2f
                )
            }
            val playerY = laneHeight * playerLane + laneHeight / 2f
            drawCircle(
                color = NeonBlue,
                radius = 26f,
                center = Offset(size.width * 0.12f, playerY)
            )
            obstacles.forEach { obs ->
                val y = laneHeight * obs.lane + laneHeight / 2f
                val x = obs.x * size.width
                drawCircle(
                    color = if (obs.isCoin) NeonGold else Color(0xFFFF3860),
                    radius = if (obs.isCoin) 18f else 24f,
                    center = Offset(x, y)
                )
            }
        }

        Text(
            "SCORE $score   \uD83E\uDE99 $coinsCollected",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            "Tap top / middle / bottom to switch lane",
            color = TextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.padding(top = 44.dp, start = 16.dp)
        )

        if (gameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("GAME OVER", color = Color(0xFFFF3860), fontSize = 28.sp, fontWeight = FontWeight.Black)
                    Text("Score: $score  \u2022  Coins: $coinsCollected", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp, bottom = 24.dp))
                    Text(
                        "EXIT",
                        color = NeonBlue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onExit() }.padding(12.dp)
                    )
                }
            }
        }
    }
}
