package com.devkit.tools.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.devkit.tools.ui.components.OutputCard

@Composable
fun LuaSandboxScreen() {
    var script by remember { mutableStateOf("local x = 10\nlocal y = 20\nprint('Result: ' .. (x + y))\nfor i=1,3 do\n  print('Iteration: ' .. i)\nend") }
    var consoleOutput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = script,
            onValueChange = { script = it },
            label = { Text("Lua Script Logic") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)
        )

        Button(
            onClick = {
                val execution = executeLuaSimulated(script)
                consoleOutput = execution.first
                isError = execution.second
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Execute Script")
        }

        if (consoleOutput.isNotEmpty()) {
            OutputCard(
                title = "Execution Stdout / Engine Log",
                output = consoleOutput,
                isError = isError,
                onCopy = { clipboardManager.setText(AnnotatedString(consoleOutput)) }
            )
        }
    }
}

private fun executeLuaSimulated(code: String): Pair<String, Boolean> {
    val logs = mutableListOf<String>()
    val lines = code.lines()
    
    for (line in lines) {
        val trimmed = line.trim()
        if (trimmed.startsWith("print(")) {
            val content = trimmed.substringAfter("print(").substringBeforeLast(")")
            val cleanStr = content.replace("'", "").replace("\"", "")
            if (cleanStr.contains("..")) {
                val parts = cleanStr.split("..").map { it.trim() }
                logs.add(parts.joinToString(""))
            } else {
                logs.add(cleanStr)
            }
        }
    }

    return if (logs.isEmpty()) {
        Pair("Script finished with exit code 0 (no stdout capture).", false)
    } else {
        Pair(logs.joinToString("\n"), false)
    }
}
