package com.leonoretech.dragoniclasvegas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.components.GlassCard
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.DangerRed
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPurple
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel

@Composable
fun SettingsScreen(viewModel: PlayerViewModel, onBack: () -> Unit, onAbout: () -> Unit) {
    val settings by viewModel.settings.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("\u2190 BACK", color = NeonBlue, fontSize = 14.sp, modifier = Modifier.clickable { onBack() }.padding(bottom = 16.dp))
            Text("SETTINGS", color = androidx.compose.ui.graphics.Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(bottom = 20.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(8.dp)) {
                    SettingRow("\uD83C\uDFB5 Music", settings.musicEnabled) { viewModel.setMusic(it) }
                    SettingRow("\uD83D\uDD0A Sound Effects", settings.soundEnabled) { viewModel.setSound(it) }
                    SettingRow("\uD83D\uDCF3 Vibration", settings.vibrationEnabled) { viewModel.setVibration(it) }
                    SettingRow("\u26A1 High FPS Mode", settings.highFpsEnabled) { viewModel.setHighFps(it) }
                }
            }

            Text(
                "RESET SAVE DATA",
                color = DangerRed,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .clickable { showResetDialog = true }
            )

            Text(
                "ABOUT DRAGONIC LAS VEGA",
                color = NeonPurple,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { onAbout() }
            )
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset Save?") },
            text = { Text("This will erase all progress, coins, and unlocks. This cannot be undone.") },
            confirmButton = {
                androidx.compose.material3.Text(
                    "RESET",
                    color = DangerRed,
                    modifier = Modifier
                        .clickable {
                            viewModel.resetSave()
                            showResetDialog = false
                        }
                        .padding(8.dp)
                )
            },
            dismissButton = {
                androidx.compose.material3.Text(
                    "CANCEL",
                    modifier = Modifier
                        .clickable { showResetDialog = false }
                        .padding(8.dp)
                )
            }
        )
    }
}

@Composable
private fun SettingRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = androidx.compose.ui.graphics.Color.White, fontSize = 15.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedTrackColor = NeonPurple)
        )
    }
}
