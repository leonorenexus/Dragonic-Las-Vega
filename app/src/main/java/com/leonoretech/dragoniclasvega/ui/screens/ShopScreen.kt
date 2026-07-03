package com.leonoretech.dragoniclasvegas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.leonoretech.dragoniclasvegas.data.model.ShopCatalog
import com.leonoretech.dragoniclasvegas.ui.components.GlassCard
import com.leonoretech.dragoniclasvegas.ui.components.ParticleBackground
import com.leonoretech.dragoniclasvegas.ui.theme.NeonBlue
import com.leonoretech.dragoniclasvegas.ui.theme.NeonGold
import com.leonoretech.dragoniclasvegas.ui.theme.SuccessGreen
import com.leonoretech.dragoniclasvegas.ui.theme.TextSecondary
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel

@Composable
fun ShopScreen(viewModel: PlayerViewModel, onBack: () -> Unit) {
    val profile by viewModel.profile.collectAsState()
    var message by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("\u2190 BACK", color = NeonBlue, fontSize = 14.sp, modifier = Modifier.clickable { onBack() }.padding(bottom = 12.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Text("SHOP", color = androidx.compose.ui.graphics.Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
            }
            Row2(coins = profile.coins, message = message)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(ShopCatalog.ALL) { item ->
                    val owned = profile.unlockedItems.contains(item.id)
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clickable(enabled = !owned) {
                                viewModel.buyItem(item.id, item.cost) { success ->
                                    message = if (success) "Unlocked ${item.name}!" else "Not enough coins"
                                }
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(item.icon, fontSize = 30.sp)
                            Text(item.name, color = androidx.compose.ui.graphics.Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(
                                if (owned) "OWNED" else "\uD83E\uDE99 ${item.cost}",
                                color = if (owned) SuccessGreen else NeonGold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Row2(coins: Int, message: String?) {
    Column {
        Text("\uD83E\uDE99 $coins", color = NeonGold, fontSize = 16.sp, modifier = Modifier.padding(top = 6.dp))
        message?.let {
            Text(it, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
