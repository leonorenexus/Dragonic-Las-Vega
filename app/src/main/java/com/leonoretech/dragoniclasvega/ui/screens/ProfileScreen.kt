package com.leonoretech.dragoniclasvegas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.data.model.AchievementCatalog
import com.leonoretech.dragoniclasvegas.ui.components.GlassCard
import com.leonoretech.dragoniclasvegas.ui.components.NeonProgressBar
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel

@Composable
fun ProfileScreen(viewModel: PlayerViewModel, onBack: () -> Unit) {
    val profile by viewModel.profile.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("\u2190 BACK", color = NeonBlue, fontSize = 14.sp, modifier = Modifier.clickable { onBack() }.padding(bottom = 16.dp))
            Text("PROFILE", color = androidx.compose.ui.graphics.Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(bottom = 16.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(profile.username, color = androidx.compose.ui.graphics.Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Level ${profile.level}", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp, bottom = 8.dp))
                    NeonProgressBar(progress = profile.xpProgress, modifier = Modifier.fillMaxWidth())
                    Text("${profile.xp} / ${profile.xpToNextLevel} XP", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(top = 6.dp))
                }
            }

            Row2Stat(coins = profile.coins, gamesPlayed = profile.gamesPlayed)

            Text("ACHIEVEMENTS  (${profile.achievements.size}/${AchievementCatalog.ALL.size})", color = androidx.compose.ui.graphics.Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 24.dp, bottom = 10.dp))
        }
    }
}

@Composable
private fun Row2Stat(coins: Int, gamesPlayed: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlassCard(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("\uD83E\uDE99 COINS", color = TextSecondary, fontSize = 11.sp)
                Text("$coins", color = NeonGold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        GlassCard(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("\uD83C\uDFAE GAMES", color = TextSecondary, fontSize = 11.sp)
                Text("$gamesPlayed", color = NeonBlue, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
