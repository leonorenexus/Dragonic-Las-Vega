package com.leonoretech.dragoniclasvegas.ui.minigames

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.theme.DeepPurple
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPurple
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.ui.theme.VoidBlack
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel
import kotlinx.coroutines.delay

private data class MemoryCard(val id: Int, val symbol: String, var isFlipped: Boolean = false, var isMatched: Boolean = false)

private val SYMBOLS = listOf("\uD83D\uDC09", "\u26A1", "\uD83C\uDFB2", "\uD83D\uDC7E", "\uD83D\uDD2E", "\uD83D\uDE80", "\uD83D\uDC80", "\uD83D\uDD2B")

@Composable
fun NeonMemoryScreen(viewModel: PlayerViewModel, onExit: () -> Unit) {
    val cards = remember {
        val pairs = (SYMBOLS + SYMBOLS).shuffled()
        mutableStateListOf(*pairs.mapIndexed { i, s -> MemoryCard(i, s) }.toTypedArray())
    }
    var firstPick by remember { mutableStateOf<Int?>(null) }
    var secondPick by remember { mutableStateOf<Int?>(null) }
    var moves by remember { mutableIntStateOf(0) }
    var mistakes by remember { mutableIntStateOf(0) }
    var locked by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }

    LaunchedEffect(secondPick) {
        val first = firstPick
        val second = secondPick
        if (first != null && second != null) {
            locked = true
            moves++
            delay(600)
            if (cards[first].symbol == cards[second].symbol) {
                cards[first] = cards[first].copy(isMatched = true)
                cards[second] = cards[second].copy(isMatched = true)
            } else {
                mistakes++
                cards[first] = cards[first].copy(isFlipped = false)
                cards[second] = cards[second].copy(isFlipped = false)
            }
            firstPick = null
            secondPick = null
            locked = false
            if (cards.all { it.isMatched }) {
                finished = true
            }
        }
    }

    LaunchedEffect(finished) {
        if (finished) {
            val coinsEarned = (60 - moves * 2).coerceAtLeast(15)
            val xpEarned = (40 - mistakes * 3).coerceAtLeast(10)
            viewModel.onGameFinished(coinsEarned = coinsEarned, xpEarned = xpEarned)
            if (mistakes == 0) viewModel.unlockAchievement("memory_master")
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(VoidBlack)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "\u2190 EXIT",
                color = NeonBlue,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onExit() }.padding(bottom = 8.dp)
            )
            Text("NEON MEMORY", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
            Text("Moves: $moves   Mistakes: $mistakes", color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cards) { card ->
                    val index = cards.indexOf(card)
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.8f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (card.isFlipped || card.isMatched) DeepPurple else NeonPurple.copy(alpha = 0.25f))
                            .clickable(enabled = !locked && !card.isFlipped && !card.isMatched) {
                                if (firstPick == null) {
                                    cards[index] = card.copy(isFlipped = true)
                                    firstPick = index
                                } else if (secondPick == null && firstPick != index) {
                                    cards[index] = card.copy(isFlipped = true)
                                    secondPick = index
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (card.isFlipped || card.isMatched) card.symbol else "?",
                            fontSize = 26.sp,
                            color = if (card.isMatched) NeonGold else Color.White
                        )
                    }
                }
            }
        }

        if (finished) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("CLEARED!", color = NeonGold, fontSize = 28.sp, fontWeight = FontWeight.Black)
                    Text("Moves: $moves  \u2022  Mistakes: $mistakes", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp, bottom = 24.dp))
                    Text("EXIT", color = NeonBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onExit() }.padding(12.dp))
                }
            }
        }
    }
}
