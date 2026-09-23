package com.devkit.tools.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.devkit.tools.ui.components.OutputCard

@Composable
fun ColorUnitScreen() {
    var hexInput by remember { mutableStateOf("#6C5CE7") }
    var dpValue by remember { mutableStateOf("16") }
    val density = LocalDensity.current

    val parsedColor = remember(hexInput) {
        try {
            val cleanHex = hexInput.removePrefix("#")
            val colorInt = cleanHex.toLong(16)
            if (cleanHex.length == 6) Color(colorInt or 0xFF000000)
            else Color(colorInt)
        } catch (_: Exception) {
            Color.Transparent
        }
    }

    val calculatedPx = remember(dpValue) {
        val dp = dpValue.toFloatOrNull() ?: 0f
        with(density) { dp.dp.toPx() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = hexInput,
            onValueChange = { hexInput = it },
            label = { Text("HEX Color (#AARRGGBB)") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(parsedColor)
            )
            Text(
                text = "Preview",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        OutlinedTextField(
            value = dpValue,
            onValueChange = { dpValue = it },
            label = { Text("Density-Independent Pixels (dp)") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        OutputCard(
            title = "Screen Metrics Engine Output",
            output = "Target Screen PX: ${calculatedPx}px\nDensity Factor: ${density.density}",
            onCopy = {}
        )
    }
}
