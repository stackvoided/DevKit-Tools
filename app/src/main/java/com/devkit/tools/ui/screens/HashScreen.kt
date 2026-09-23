package com.devkit.tools.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import com.devkit.tools.ui.components.OutputCard
import java.security.MessageDigest

@Composable
fun HashScreen() {
    var input by remember { mutableStateOf("") }
    var sha256 by remember { mutableStateOf("") }
    var md5 by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { valStr ->
                input = valStr
                if (valStr.isNotEmpty()) {
                    sha256 = digest(valStr, "SHA-256")
                    md5 = digest(valStr, "MD5")
                } else {
                    sha256 = ""
                    md5 = ""
                }
            },
            label = { Text("Data stream") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        AnimatedVisibility(visible = sha256.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutputCard(
                    title = "SHA-256",
                    output = sha256,
                    onCopy = { clipboardManager.setText(AnnotatedString(sha256)) }
                )
                OutputCard(
                    title = "MD5",
                    output = md5,
                    onCopy = { clipboardManager.setText(AnnotatedString(md5)) }
                )
            }
        }
    }
}

private fun digest(source: String, algo: String): String {
    return MessageDigest.getInstance(algo)
        .digest(source.toByteArray())
        .joinToString("") { "%02x".format(it) }
}
