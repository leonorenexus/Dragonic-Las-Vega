package com.leonoretech.dragoniclasvegas.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.leonoretech.dragoniclasvegas.data.model.GameSettings
import com.leonoretech.dragoniclasvegas.data.model.PlayerProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "dragonic_save")

class PlayerRepository(private val context: Context) {

    private object Keys {
        val USERNAME = stringPreferencesKey("username")
        val LEVEL = intPreferencesKey("level")
        val XP = intPreferencesKey("xp")
        val XP_TO_NEXT = intPreferencesKey("xp_to_next")
        val COINS = intPreferencesKey("coins")
        val GAMES_PLAYED = intPreferencesKey("games_played")
        val ACHIEVEMENTS = stringSetPreferencesKey("achievements")
        val UNLOCKED_ITEMS = stringSetPreferencesKey("unlocked_items")
        val EQUIPPED_AVATAR = stringPreferencesKey("equipped_avatar")
        val LAST_SPIN_DAY = longPreferencesKey("last_spin_day")
        val LAST_DAILY_REWARD_DAY = longPreferencesKey("last_daily_reward_day")

        val MUSIC = booleanPreferencesKey("music_enabled")
        val SOUND = booleanPreferencesKey("sound_enabled")
        val VIBRATION = booleanPreferencesKey("vibration_enabled")
        val HIGH_FPS = booleanPreferencesKey("high_fps_enabled")
    }

    val profileFlow: Flow<PlayerProfile> = context.dataStore.data.map { prefs ->
        PlayerProfile(
            username = prefs[Keys.USERNAME] ?: "GUEST",
            level = prefs[Keys.LEVEL] ?: 1,
            xp = prefs[Keys.XP] ?: 0,
            xpToNextLevel = prefs[Keys.XP_TO_NEXT] ?: 100,
            coins = prefs[Keys.COINS] ?: 250,
            gamesPlayed = prefs[Keys.GAMES_PLAYED] ?: 0,
            achievements = prefs[Keys.ACHIEVEMENTS] ?: emptySet(),
            unlockedItems = prefs[Keys.UNLOCKED_ITEMS] ?: setOf("avatar_default", "theme_default"),
            equippedAvatar = prefs[Keys.EQUIPPED_AVATAR] ?: "avatar_default",
            lastSpinDateEpochDay = prefs[Keys.LAST_SPIN_DAY] ?: -1L,
            lastDailyRewardEpochDay = prefs[Keys.LAST_DAILY_REWARD_DAY] ?: -1L
        )
    }

    val settingsFlow: Flow<GameSettings> = context.dataStore.data.map { prefs ->
        GameSettings(
            musicEnabled = prefs[Keys.MUSIC] ?: true,
            soundEnabled = prefs[Keys.SOUND] ?: true,
            vibrationEnabled = prefs[Keys.VIBRATION] ?: true,
            highFpsEnabled = prefs[Keys.HIGH_FPS] ?: true
        )
    }

    suspend fun setUsername(name: String) {
        context.dataStore.edit { it[Keys.USERNAME] = name }
    }

    suspend fun addCoins(amount: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.COINS] ?: 250
            prefs[Keys.COINS] = (current + amount).coerceAtLeast(0)
        }
    }

    suspend fun spendCoins(amount: Int): Boolean {
        var success = false
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.COINS] ?: 250
            if (current >= amount) {
                prefs[Keys.COINS] = current - amount
                success = true
            }
        }
        return success
    }

    suspend fun addXp(amount: Int) {
        context.dataStore.edit { prefs ->
            var xp = (prefs[Keys.XP] ?: 0) + amount
            var level = prefs[Keys.LEVEL] ?: 1
            var xpToNext = prefs[Keys.XP_TO_NEXT] ?: 100
            while (xp >= xpToNext) {
                xp -= xpToNext
                level += 1
                xpToNext = 100 + (level - 1) * 40
            }
            prefs[Keys.XP] = xp
            prefs[Keys.LEVEL] = level
            prefs[Keys.XP_TO_NEXT] = xpToNext
        }
    }

    suspend fun incrementGamesPlayed() {
        context.dataStore.edit { prefs ->
            prefs[Keys.GAMES_PLAYED] = (prefs[Keys.GAMES_PLAYED] ?: 0) + 1
        }
    }

    suspend fun unlockAchievement(id: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.ACHIEVEMENTS] ?: emptySet()
            prefs[Keys.ACHIEVEMENTS] = current + id
        }
    }

    suspend fun unlockItem(id: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.UNLOCKED_ITEMS] ?: setOf("avatar_default", "theme_default")
            prefs[Keys.UNLOCKED_ITEMS] = current + id
        }
    }

    suspend fun equipAvatar(id: String) {
        context.dataStore.edit { it[Keys.EQUIPPED_AVATAR] = id }
    }

    suspend fun setLastSpinDay(epochDay: Long) {
        context.dataStore.edit { it[Keys.LAST_SPIN_DAY] = epochDay }
    }

    suspend fun setLastDailyRewardDay(epochDay: Long) {
        context.dataStore.edit { it[Keys.LAST_DAILY_REWARD_DAY] = epochDay }
    }

    suspend fun setMusicEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.MUSIC] = enabled }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.SOUND] = enabled }
    }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.VIBRATION] = enabled }
    }

    suspend fun setHighFpsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.HIGH_FPS] = enabled }
    }

    suspend fun resetSave() {
        context.dataStore.edit { it.clear() }
    }
}
