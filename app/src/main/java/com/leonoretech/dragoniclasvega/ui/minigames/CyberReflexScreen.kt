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
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPink
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.ui.theme.VoidBlack
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CyberReflexScreen(viewModel: PlayerViewModel, onExit: () -> Unit) {
    var targetX by remember { mutableFloatStateOf(0.5f) }
    var targetY by remember { mutableFloatStateOf(0.5f) }
    var score by remember { mutableIntStateOf(0) }
    var combo by remember { mutableIntStateOf(0) }
    var maxCombo by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableFloatStateOf(30f) }
    var finished by remember { mutableStateOf(false) }
    var targetRadius by remember { mutableFloatStateOf(70f) }

    fun relocateTarget() {
        targetX = Random.nextFloat() * 0.7f + 0.15f
        targetY = Random.nextFloat() * 0.6f + 0.2f
    }

    LaunchedEffect(Unit) {
        relocateTarget()
        while (timeLeft > 0f) {
            delay(16)
            timeLeft -= 0.016f
        }
        finished = true
    }

    LaunchedEffect(finished) {
        if (finished) {
            val coinsEarned = (score * 2).coerceAtMost(200)
            val xpEarned = (score * 3).coerceAtMost(250)
            viewModel.onGameFinished(coinsEarned = coinsEarned, xpEarned = xpEarned)
            viewModel.unlockIfScoreAbove("reflex_god", maxCombo, 30)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VoidBlack)
            .pointerInput(finished) {
                detectTapGestures { offset ->
                    if (finished) return@detectTapGestures
                    val dx = offset.x - targetX * size.width
                    val dy = offset.y - targetY * size.height
                    val dist = kotlin.math.sqrt(dx * dx + dy * dy)
                    if (dist <= targetRadius) {
                        score += 1
                        combo += 1
                        if (combo > maxCombo) maxCombo = combo
                        targetRadius = (targetRadius - 1.2f).coerceAtLeast(34f)
                        relocateTarget()
                    } else {
                        combo = 0
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (!finished) {
                drawCircle(
                    color = NeonPink.copy(alpha = 0.25f),
                    radius = targetRadius + 14f,
                    center = Offset(targetX * size.width, targetY * size.height)
                )
                drawCircle(
                    color = NeonPink,
                    radius = targetRadius,
                    center = Offset(targetX * size.width, targetY * size.height)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("SCORE $score   COMBO $combo", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Time: ${timeLeft.toInt()}s", color = NeonGold, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
        }

        if (finished) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TIME'S UP", color = NeonBlue, fontSize = 28.sp, fontWeight = FontWeight.Black)
                    Text("Score: $score  \u2022  Max Combo: $maxCombo", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp, bottom = 24.dp))
                    Text("EXIT", color = NeonBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onExit() }.padding(12.dp))
                }
            }
        }
    }
}
