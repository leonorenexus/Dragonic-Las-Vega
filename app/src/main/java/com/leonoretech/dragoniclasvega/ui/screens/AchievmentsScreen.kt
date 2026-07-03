package com.leonoretech.dragoniclasvegas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.data.model.AchievementCatalog
import com.leonoretech.dragoniclasvegas.ui.components.GlassCard
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel

@Composable
fun AchievementsScreen(viewModel: PlayerViewModel, onBack: () -> Unit) {
    val profile by viewModel.profile.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("\u2190 BACK", color = NeonBlue, fontSize = 14.sp, modifier = Modifier.clickable { onBack() }.padding(bottom = 16.dp))
            Text("ACHIEVEMENTS", color = androidx.compose.ui.graphics.Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(AchievementCatalog.ALL) { achievement ->
                    val unlocked = profile.achievements.contains(achievement.id)
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "${achievement.icon}  ${achievement.title}",
                                    color = if (unlocked) androidx.compose.ui.graphics.Color.White else TextSecondary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(achievement.description, color = TextSecondary, fontSize = 12.sp)
                            }
                            Text(
                                text = if (unlocked) "\u2713" else "\uD83D\uDD12",
                                color = if (unlocked) NeonGold else TextSecondary,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
