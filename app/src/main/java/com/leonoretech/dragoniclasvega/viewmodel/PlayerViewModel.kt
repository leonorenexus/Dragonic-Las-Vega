package com.leonoretech.dragoniclasvegas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.leonoretech.dragoniclasvegas.data.PlayerRepository
import com.leonoretech.dragoniclasvegas.data.model.AchievementCatalog
import com.leonoretech.dragoniclasvegas.data.model.GameSettings
import com.leonoretech.dragoniclasvegas.data.model.PlayerProfile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PlayerRepository(application)

    val profile: StateFlow<PlayerProfile> = repository.profileFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlayerProfile()
    )

    val settings: StateFlow<GameSettings> = repository.settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GameSettings()
    )

    fun loginAsGuest() {
        viewModelScope.launch {
            val id = (1000..9999).random()
            repository.setUsername("GUEST_$id")
        }
    }

    fun onGameFinished(coinsEarned: Int, xpEarned: Int) {
        viewModelScope.launch {
            repository.addCoins(coinsEarned)
            repository.addXp(xpEarned)
            repository.incrementGamesPlayed()
            checkAchievements()
        }
    }

    fun unlockAchievement(id: String) {
        viewModelScope.launch {
            if (!profile.value.achievements.contains(id)) {
                repository.unlockAchievement(id)
            }
        }
    }

    private suspend fun checkAchievements() {
        val p = profile.value
        if (p.gamesPlayed + 1 >= 1) repository.unlockAchievement("first_steps")
        if (p.coins >= 500) repository.unlockAchievement("coin_collector")
        if (p.level >= 5) repository.unlockAchievement("level_5")
        if (p.gamesPlayed + 1 >= 20) repository.unlockAchievement("dedicated")
        if (p.unlockedItems.size >= 4) repository.unlockAchievement("shopaholic")
    }

    fun buyItem(itemId: String, cost: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.spendCoins(cost)
            if (success) {
                repository.unlockItem(itemId)
            }
            onResult(success)
        }
    }

    fun equipAvatar(id: String) {
        viewModelScope.launch { repository.equipAvatar(id) }
    }

    fun canSpinToday(): Boolean {
        val today = LocalDate.now().toEpochDay()
        return profile.value.lastSpinDateEpochDay != today
    }

    fun recordSpin(coinsWon: Int) {
        viewModelScope.launch {
            repository.addCoins(coinsWon)
            repository.setLastSpinDay(LocalDate.now().toEpochDay())
            repository.unlockAchievement("lucky_spinner")
        }
    }

    fun canClaimDailyReward(): Boolean {
        val today = LocalDate.now().toEpochDay()
        return profile.value.lastDailyRewardEpochDay != today
    }

    fun claimDailyReward() {
        viewModelScope.launch {
            repository.addCoins(100)
            repository.addXp(20)
            repository.setLastDailyRewardDay(LocalDate.now().toEpochDay())
        }
    }

    fun setMusic(enabled: Boolean) = viewModelScope.launch { repository.setMusicEnabled(enabled) }
    fun setSound(enabled: Boolean) = viewModelScope.launch { repository.setSoundEnabled(enabled) }
    fun setVibration(enabled: Boolean) = viewModelScope.launch { repository.setVibrationEnabled(enabled) }
    fun setHighFps(enabled: Boolean) = viewModelScope.launch { repository.setHighFpsEnabled(enabled) }

    fun resetSave() {
        viewModelScope.launch { repository.resetSave() }
    }

    fun unlockIfScoreAbove(achievementId: String, score: Int, threshold: Int) {
        if (score >= threshold) unlockAchievement(achievementId)
    }
}
