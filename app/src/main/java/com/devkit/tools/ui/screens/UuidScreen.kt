package com.devkit.tools.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.devkit.tools.ui.components.OutputCard
import java.util.UUID

@Composable
fun UuidScreen() {
    var list by remember { mutableStateOf(listOf(UUID.randomUUID().toString())) }
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { list = listOf(UUID.randomUUID().toString()) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Single v4")
            }

            OutlinedButton(
                onClick = { list = List(8) { UUID.randomUUID().toString() } },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Batch (x8)")
            }
        }

        val res = list.joinToString("\n")
        OutputCard(
            title = "Generated Keys",
            output = res,
            onCopy = { clipboardManager.setText(AnnotatedString(res)) }
        )
    }
}
