package com.leonoretech.dragoniclasvegas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.leonoretech.dragoniclasvegas.ui.theme.GlassBorder
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonPurple

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 24,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NeonPurple.copy(alpha = 0.16f),
                        NeonBlue.copy(alpha = 0.06f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(GlassBorder, NeonBlue.copy(alpha = 0.4f))
                ),
                shape = RoundedCornerShape(cornerRadius.dp)
            )
    ) {
        content()
    }
}
