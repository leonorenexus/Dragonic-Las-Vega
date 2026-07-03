package com.leonoretech.dragoniclasvegas.data.model

data class PlayerProfile(
    val username: String = "GUEST",
    val level: Int = 1,
    val xp: Int = 0,
    val xpToNextLevel: Int = 100,
    val coins: Int = 250,
    val gamesPlayed: Int = 0,
    val achievements: Set<String> = emptySet(),
    val unlockedItems: Set<String> = setOf("avatar_default", "theme_default"),
    val equippedAvatar: String = "avatar_default",
    val lastSpinDateEpochDay: Long = -1L,
    val lastDailyRewardEpochDay: Long = -1L
) {
    val xpProgress: Float
        get() = if (xpToNextLevel == 0) 0f else xp.toFloat() / xpToNextLevel.toFloat()
}

data class GameSettings(
    val musicEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val highFpsEnabled: Boolean = true
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String
)

object AchievementCatalog {
    val ALL = listOf(
        Achievement("first_steps", "First Steps", "Play your first mini game", "🎮"),
        Achievement("coin_collector", "Coin Collector", "Earn 500 total coins", "🪙"),
        Achievement("level_5", "Rising Star", "Reach Level 5", "⭐"),
        Achievement("runner_master", "Runner Master", "Score 100+ in Dragon Runner", "🐉"),
        Achievement("memory_master", "Memory Master", "Clear Neon Memory with no mistakes", "🧠"),
        Achievement("reflex_god", "Reflex God", "Score 30+ combo in Cyber Reflex", "⚡"),
        Achievement("dodge_survivor", "Dodge Survivor", "Survive 60 seconds in Dragon Dodge", "🛡️"),
        Achievement("lucky_spinner", "Lucky Spinner", "Spin the Lucky Wheel", "🎡"),
        Achievement("shopaholic", "Shopaholic", "Unlock 3 shop items", "🛍️"),
        Achievement("dedicated", "Dedicated", "Play 20 games total", "🔥")
    )
}

data class ShopItem(
    val id: String,
    val name: String,
    val category: ShopCategory,
    val cost: Int,
    val icon: String
)

enum class ShopCategory { AVATAR, THEME, BACKGROUND, TRAIL, ICON }

object ShopCatalog {
    val ALL = listOf(
        ShopItem("avatar_dragon", "Dragon Avatar", ShopCategory.AVATAR, 300, "🐲"),
        ShopItem("avatar_cyborg", "Cyborg Avatar", ShopCategory.AVATAR, 350, "🤖"),
        ShopItem("avatar_ghost", "Ghost Avatar", ShopCategory.AVATAR, 250, "👻"),
        ShopItem("theme_gold", "Gold Rush Theme", ShopCategory.THEME, 400, "🟡"),
        ShopItem("theme_ice", "Ice Blue Theme", ShopCategory.THEME, 400, "🔵"),
        ShopItem("bg_skyline", "Neon Skyline", ShopCategory.BACKGROUND, 300, "🌆"),
        ShopItem("bg_grid", "Cyber Grid", ShopCategory.BACKGROUND, 300, "🟪"),
        ShopItem("trail_flame", "Flame Trail", ShopCategory.TRAIL, 200, "🔥"),
        ShopItem("trail_frost", "Frost Trail", ShopCategory.TRAIL, 200, "❄️"),
        ShopItem("icon_crown", "Crown Icon", ShopCategory.ICON, 150, "👑")
    )
}
