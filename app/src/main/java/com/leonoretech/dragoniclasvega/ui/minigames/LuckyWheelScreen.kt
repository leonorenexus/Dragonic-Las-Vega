package com.leonoretech.dragoniclasvegas.ui.minigames

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.components.NeonButton
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPink
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPurple
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel
import kotlin.random.Random

private val REWARDS = listOf(10, 25, 50, 100, 20, 75, 15, 200)
private val WHEEL_COLORS = listOf(NeonPurple, NeonBlue, NeonPink, NeonGold)

@Composable
fun LuckyWheelScreen(viewModel: PlayerViewModel, onExit: () -> Unit) {
    var rotation by remember { mutableFloatStateOf(0f) }
    var spinning by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf<String?>(null) }
    val canSpin = remember { viewModel.canSpinToday() }

    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(2200),
        label = "wheelSpin",
        finishedListener = {
            if (spinning) {
                spinning = false
                val sliceAngle = 360f / REWARDS.size
                val normalized = (360f - (rotation % 360f)) % 360f
                val index = (normalized / sliceAngle).toInt().coerceIn(0, REWARDS.size - 1)
                val reward = REWARDS[index]
                viewModel.recordSpin(reward)
                resultText = "You won \uD83E\uDE99 $reward!"
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
        ) {
            Text(
                "\u2190 EXIT",
                color = NeonBlue,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onExit() }.padding(bottom = 12.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("LUCKY WHEEL", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
                Text("One free spin per day \u2014 virtual coins only", color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp, bottom = 32.dp))

                Box(contentAlignment = Alignment.Center) {
                    Canvas(
                        modifier = Modifier
                            .size(240.dp)
                            .rotate(animatedRotation)
                    ) {
                        val sliceAngle = 360f / REWARDS.size
                        REWARDS.forEachIndexed { i, _ ->
                            drawArc(
                                color = WHEEL_COLORS[i % WHEEL_COLORS.size].copy(alpha = 0.8f),
                                startAngle = i * sliceAngle,
                                sweepAngle = sliceAngle,
                                useCenter = true
                            )
                        }
                    }
                    // pointer
                    Canvas(modifier = Modifier.size(240.dp)) {
                        drawCircle(
                            color = Color.White,
                            radius = 8f,
                            center = Offset(size.width / 2f, 6f)
                        )
                    }
                }

                resultText?.let {
                    Text(it, color = NeonGold, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 28.dp))
                }

                Box(modifier = Modifier.padding(top = 32.dp)) {
                    if (canSpin && resultText == null) {
                        NeonButton(
                            text = if (spinning) "SPINNING..." else "SPIN",
                            enabled = !spinning,
                            onClick = {
                                if (!spinning) {
                                    spinning = true
                                    rotation += 1440f + Random.nextInt(0, 360)
                                }
                            }
                        )
                    } else if (resultText != null) {
                        NeonButton(text = "EXIT", onClick = onExit)
                    } else {
                        Text("Come back tomorrow for another spin!", color = TextSecondary, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
