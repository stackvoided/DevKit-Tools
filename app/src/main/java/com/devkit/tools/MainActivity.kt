package com.devkit.tools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devkit.tools.domain.DevTool
import com.devkit.tools.ui.screens.Base64Screen
import com.devkit.tools.ui.screens.ColorUnitScreen
import com.devkit.tools.ui.screens.HashScreen
import com.devkit.tools.ui.screens.JsonScreen
import com.devkit.tools.ui.screens.LuaSandboxScreen
import com.devkit.tools.ui.screens.UuidScreen
import com.devkit.tools.ui.theme.DevKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            DevKitTheme {
                MainLayout()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {
    var currentTab by rememberSaveable { mutableStateOf(DevTool.JSON) }
    val saveableStateHolder = rememberSaveableStateHolder()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column {
                        Text(
                            text = currentTab.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                tonalElevation = 0.dp
            ) {
                DevTool.entries.forEach { tool ->
                    val selected = currentTab == tool
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) currentTab = tool
                        },
                        icon = { Icon(tool.icon, contentDescription = null) },
                        label = { Text(tool.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Crossfade(
            targetState = currentTab,
            animationSpec = tween(durationMillis = 150),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            label = "tab_fade"
        ) { tab ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                saveableStateHolder.SaveableStateProvider(key = tab) {
                    when (tab) {
                        DevTool.JSON -> JsonScreen()
                        DevTool.BASE64 -> Base64Screen()
                        DevTool.HASH -> HashScreen()
                        DevTool.UUID -> UuidScreen()
                        DevTool.LUA -> LuaSandboxScreen()
                        DevTool.DESIGN -> ColorUnitScreen()
                    }
                }
            }
        }
    }
}
