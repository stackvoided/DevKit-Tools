package com.devkit.tools.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun JsonScreen() {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Payload") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    try {
                        val raw = input.trim()
                        output = if (raw.startsWith("[")) JSONArray(raw).toString(2)
                        else JSONObject(raw).toString(2)
                        isError = false
                    } catch (e: Exception) {
                        output = e.localizedMessage ?: "Syntax Error"
                        isError = true
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Format")
            }

            OutlinedButton(
                onClick = {
                    input = ""
                    output = ""
                    isError = false
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Reset")
            }
        }

        if (output.isNotEmpty()) {
            OutputCard(
                output = output,
                isError = isError,
                onCopy = { clipboardManager.setText(AnnotatedString(output)) }
            )
        }
    }
}
