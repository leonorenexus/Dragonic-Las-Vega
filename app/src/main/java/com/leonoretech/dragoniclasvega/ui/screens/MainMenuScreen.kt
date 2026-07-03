package com.leonoretech.dragoniclasvegas.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.components.AnimatedTitle
import com.leonoretech.dragoniclasvegas.ui.components.GlassCard
import com.leonoretech.dragoniclasvegas.ui.components.NeonButton
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel

@Composable
fun MainMenuScreen(
    viewModel: PlayerViewModel,
    onPlay: () -> Unit,
    onAchievements: () -> Unit,
    onShop: () -> Unit,
    onProfile: () -> Unit,
    onSettings: () -> Unit,
    onAbout: () -> Unit
) {
    val profile by viewModel.profile.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(profile.username, color = androidx.compose.ui.graphics.Color.White, fontSize = 16.sp)
                        Text("Lv.${profile.level}", color = TextSecondary, fontSize = 12.sp)
                    }
                    Text("🪙 ${profile.coins}", color = NeonGold, fontSize = 16.sp)
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedTitle(text = "DRAGONIC\nLAS VEGA", fontSize = 34.sp)

                Column(
                    modifier = Modifier.padding(top = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NeonButton(text = "▶  PLAY", onClick = onPlay, modifier = Modifier.fillMaxWidth(0.7f))
                    NeonButton(text = "🏆  ACHIEVEMENTS", onClick = onAchievements, modifier = Modifier.fillMaxWidth(0.7f))
                    NeonButton(text = "🛍  SHOP", onClick = onShop, modifier = Modifier.fillMaxWidth(0.7f))
                    NeonButton(text = "👤  PROFILE", onClick = onProfile, modifier = Modifier.fillMaxWidth(0.7f))
                    NeonButton(text = "⚙  SETTINGS", onClick = onSettings, modifier = Modifier.fillMaxWidth(0.7f))
                    NeonButton(text = "ℹ  ABOUT", onClick = onAbout, modifier = Modifier.fillMaxWidth(0.7f))
                }
            }
        }
    }
}
