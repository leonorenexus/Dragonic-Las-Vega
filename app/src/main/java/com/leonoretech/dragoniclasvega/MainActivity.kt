package com.leonoretech.dragoniclasvegas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.leonoretech.dragoniclasvegas.navigation.DragonicNavGraph
import com.leonoretech.dragoniclasvegas.ui.theme.DragonicLasVegaTheme
import com.leonoretech.dragoniclasvegas.ui.theme.VoidBlack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DragonicLasVegaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = VoidBlack
                ) {
                    DragonicNavGraph()
                }
            }
        }
    }
}
