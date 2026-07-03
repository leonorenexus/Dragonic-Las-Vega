package com.leonoretech.dragoniclasvegas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonoretech.dragoniclasvegas.ui.components.AnimatedTitle
import com.leonoretech.dragoniclasvegas.ui.components.NeonButton
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel

@Composable
fun LoginScreen(
    viewModel: PlayerViewModel,
    onLoggedIn: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedTitle(text = "WELCOME", fontSize = 36.sp)
            Text(
                text = "Enter the neon underground.\nNo account needed.",
                color = TextSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp, bottom = 48.dp)
            )
            NeonButton(
                text = "CONTINUE AS GUEST",
                onClick = {
                    viewModel.loginAsGuest()
                    onLoggedIn()
                }
            )
        }
    }
}
