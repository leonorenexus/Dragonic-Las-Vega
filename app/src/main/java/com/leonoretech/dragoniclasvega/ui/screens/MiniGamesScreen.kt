package com.leonoretech.dragoniclasvegas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.components.GlassCard
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary

private data class GameEntry(val title: String, val desc: String, val icon: String, val route: String)

@Composable
fun MiniGamesScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val games = listOf(
        GameEntry("Dragon Runner", "Endless runner \u2013 dodge, collect, survive", "\uD83D\uDC09", "game_dragon_runner"),
        GameEntry("Neon Memory", "Flip cards, match pairs, beat the clock", "\uD83E\uDDE0", "game_neon_memory"),
        GameEntry("Cyber Reflex", "Tap targets as fast as you can", "\u26A1", "game_cyber_reflex"),
        GameEntry("Dragon Dodge", "Avoid lasers, collect energy orbs", "\uD83D\uDEE1\uFE0F", "game_dragon_dodge"),
        GameEntry("Lucky Wheel", "Free daily spin for bonus coins", "\uD83C\uDFA1", "game_lucky_wheel")
    )

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text(
                text = "\u2190 BACK",
                color = NeonBlue,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onBack() }.padding(bottom = 12.dp)
            )
            Text("MINI GAMES", color = androidx.compose.ui.graphics.Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
            Text("Choose your battlefield", color = TextSecondary, fontSize = 13.sp, modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items(games) { game ->
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp)
                            .clickable { onNavigate(game.route) }
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                                Text("${game.icon}  ${game.title}", color = androidx.compose.ui.graphics.Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                                Text(game.desc, color = TextSecondary, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
