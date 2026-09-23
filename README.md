# 🛠️ DevKit — Pocket Developer Utilities

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API_24+-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Material3-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-blue.style=flat-square)](LICENSE)

**DevKit** is a lightweight, offline-first Android application designed for developers, reverse engineers, and UI designers. Built using modern Android architecture, Jetpack Compose, and Material 3 design principles, it delivers a high-density, compact toolbox directly to your mobile device.

---

## ✨ Features

- 📝 **JSON Formatter & Validator** — Pretty-print, format, and validate raw JSON strings and arrays instantly.
- 🔐 **Base64 Encoder / Decoder** — Fast two-way string and raw payload conversion.
- ⚡ **Crypto Hash Generator** — Real-time live hashing engine computing SHA-256 and MD5 outputs on the fly.
- 🎲 **UUID v4 Generator** — Generate single keys or batch arrays (x5/x8) for quick test mocks.
- 📜 **Lua Script Sandbox** — Light sandbox environment to dry-run small scripts with `print` stdout interception.
- 🎨 **Color & Unit Engine** — Live HEX (`#AARRGGBB`) visualizer paired with real-time `dp`-to-`px` screen density converter.

---

## 🎨 UI & Architecture Highlights

- **Pure Jetpack Compose & Material 3** — Built completely declarative with custom dark color schemes (`DevKitTheme`).
- **Clean Architecture** — Scalable structure separating presentation, state management, and functional domains into modular Kotlin files (`.kt`).
- **Compact & High-Density UI** — Designed specifically for quick navigation using a compact `ScrollableTabRow` and optimized code blocks for maximum screen real estate.
- **Offline & Private** — Zero external API calls, zero tracking, zero background battery drain.

---

## 📂 Project Structure

```text
app/src/main/java/com/example/kotlingame/
├── MainActivity.kt               # Entry point and tab scaffold
├── domain/
│   └── DevTool.kt                # Utility enum mapping & navigation icons
├── ui/
│   ├── theme/
│   │   ├── Color.kt              # App palette definition
│   │   └── Theme.kt              # Material 3 dark scheme config
│   ├── components/
│   │   └── OutputCard.kt         # Monospaced code result viewer component
│   └── screens/
│       ├── JsonScreen.kt         # JSON formatting layout
│       ├── Base64Screen.kt       # Base64 encoder/decoder layout
│       ├── HashScreen.kt         # SHA-256 / MD5 generator layout
│       ├── UuidScreen.kt         # UUID v4 generator layout
│       ├── LuaSandboxScreen.kt   # Lua script runner layout
│       └── ColorUnitScreen.kt    # HEX & DP screen metrics layout