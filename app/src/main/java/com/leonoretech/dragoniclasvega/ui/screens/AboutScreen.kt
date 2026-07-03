package com.leonoretech.dragoniclasvegas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.components.GlassCard
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("\u2190 BACK", color = NeonBlue, fontSize = 14.sp, modifier = Modifier.clickable { onBack() }.padding(bottom = 16.dp))
            Text("ABOUT", color = androidx.compose.ui.graphics.Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(bottom = 20.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("DRAGONIC LAS VEGA", color = androidx.compose.ui.graphics.Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Version 1.0.0", color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))
                    Text(
                        "A fully offline cyberpunk arcade collection. This is not a gambling application \u2014 all coins, spins, and rewards are virtual and hold no real-world value.",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Text("Developer", color = androidx.compose.ui.graphics.Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp))
                    Text("Leonore Tech Team", color = TextSecondary, fontSize = 13.sp)
                    Text("Lead Developer: Pai Leonore", color = TextSecondary, fontSize = 13.sp)
                    Text("License", color = androidx.compose.ui.graphics.Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp))
                    Text("MIT License \u00A9 Leonore Tech Team", color = TextSecondary, fontSize = 13.sp)
                }
            }
        }
    }
}
