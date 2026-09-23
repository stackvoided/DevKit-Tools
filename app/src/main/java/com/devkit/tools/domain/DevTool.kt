package com.devkit.tools.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector

enum class DevTool(val title: String, val icon: ImageVector) {
    JSON("JSON", Icons.Default.Edit),
    BASE64("Base64", Icons.Default.Lock),
    HASH("Crypto Hash", Icons.Default.Info),
    UUID("UUID", Icons.Default.Refresh),
    LUA("Lua Sandbox", Icons.Default.PlayArrow),
    DESIGN("Color & Units", Icons.Default.Build)
}
