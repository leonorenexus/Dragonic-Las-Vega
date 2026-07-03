<div align="center">

# 🐉 DRAGONIC LAS VEGA

### *A Fully Offline Cyberpunk Arcade Collection*

![Kotlin](https://img.shields.io/badge/Kotlin-100%25-B026FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-00E5FF?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Build](https://img.shields.io/badge/Build-GitHub%20Actions-FFD700?style=for-the-badge&logo=githubactions&logoColor=black)
![License](https://img.shields.io/badge/License-MIT-FF2E9A?style=for-the-badge)

```
██████╗ ██████╗  █████╗  ██████╗  ██████╗ ███╗   ██╗██╗ ██████╗
██╔══██╗██╔══██╗██╔══██╗██╔════╝ ██╔═══██╗████╗  ██║██║██╔════╝
██║  ██║██████╔╝███████║██║  ███╗██║   ██║██╔██╗ ██║██║██║
██║  ██║██╔══██╗██╔══██║██║   ██║██║   ██║██║╚██╗██║██║██║
██████╔╝██║  ██║██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██║╚██████╗
╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═╝ ╚═════╝
                  L A S   V E G A
```

*Built 100% from a phone. No Android Studio. No PC. Just GitHub.*

</div>

---

## ⚡ Overview

**DRAGONIC LAS VEGA** is an offline, cyberpunk-themed arcade collection for Android — five mini games, a full progression system, and a neon glassmorphism UI, all written in pure Kotlin + Jetpack Compose.

> **This is NOT a gambling app.** All coins, spins, and rewards are virtual, hold no real-world value, and cannot be purchased or cashed out.

## 🕹️ Features

| Category | Details |
|---|---|
| 🎮 Mini Games | Dragon Runner, Neon Memory, Cyber Reflex, Dragon Dodge, Lucky Wheel |
| 👤 Profile | Username, Level, XP, Coins, Games Played, Achievements |
| 🏆 Achievements | 10 unlockable achievements tied to real gameplay milestones |
| 🛍️ Shop | Avatars, Themes, Backgrounds, Trails, Icons — bought with in-game coins |
| 🎁 Daily Rewards | Daily coin bonus + free Lucky Wheel spin |
| ⚙️ Settings | Music, Sound, Vibration, High FPS toggle, Reset Save |
| 💾 Offline Save | 100% local persistence via Jetpack DataStore — no login, no server |
| ✨ Visuals | Glassmorphism, neon glow, floating particles, animated gradients |

## 🎯 Mini Games

- **Dragon Runner** — 3-lane endless runner. Dodge obstacles, collect coins, survive as speed ramps up.
- **Neon Memory** — Flip-and-match card game with a scoring penalty for mistakes.
- **Cyber Reflex** — 30-second reaction test. Tap targets, build combos, shrink the hitbox.
- **Dragon Dodge** — Drag to dodge falling lasers while collecting energy orbs.
- **Lucky Wheel** — One free daily spin for a bonus coin reward.

## 🏗️ Architecture

```
        ┌─────────────────────┐
        │     UI (Compose)     │   Screens + Mini Games + Components
        └──────────┬───────────┘
                    │ StateFlow
        ┌──────────▼───────────┐
        │   PlayerViewModel     │   MVVM — business logic, game rewards
        └──────────┬───────────┘
                    │
        ┌──────────▼───────────┐
        │  PlayerRepository     │   Repository Pattern
        └──────────┬───────────┘
                    │
        ┌──────────▼───────────┐
        │  Jetpack DataStore    │   Local, offline, persistent storage
        └────────────────────────┘
```

- **Pattern:** MVVM + Repository
- **Navigation:** Navigation Compose (single-activity)
- **State:** StateFlow + Coroutines
- **Persistence:** DataStore Preferences (no database, no login)
- **Design System:** Material 3 + custom neon/glassmorphism components

## 🛠️ Tech Stack

- Kotlin 1.9 · Jetpack Compose · Material 3
- Navigation Compose · DataStore Preferences · Kotlin Coroutines
- Kotlin DSL Gradle (`build.gradle.kts`)
- 100% built and released via **GitHub Actions** — no local IDE required

## 🚀 CI/CD

Every push to `main` automatically:
1. Sets up JDK 21 + Android SDK
2. Builds a **Debug APK** and a **Release APK**
3. Uploads both as workflow artifacts
4. On a version tag (`v1.0.0`), auto-publishes a **GitHub Release** with the APKs attached

## 📸 Screenshots

> _Screenshots coming soon — capture from a running build and drop them in `/screenshots`._

| Splash | Main Menu | Dragon Runner |
|---|---|---|
| `screenshots/splash.png` | `screenshots/menu.png` | `screenshots/runner.png` |

## 📦 Getting an APK

1. Push to `main`, or push a tag like `v1.0.0`
2. Open the **Actions** tab → latest run → download the artifact
3. Or, for tagged releases, grab the APK directly from the **Releases** page

## 📄 License

MIT License © Leonore Tech Team — see [LICENSE](LICENSE).

---

<div align="center">

**Developed by Leonore Tech Team**
Lead Developer: **Pai Leonore**

*Built entirely from a mobile device.*

</div>
