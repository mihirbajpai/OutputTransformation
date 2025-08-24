package com.example.outputtransformation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outputtransformation.ui.theme.OutputTransformationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OutputTransformationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DateInputField(innerPadding)
                }
            }
        }
    }
}

// Date formatter transformation: DDMMYYYY -> DD/MM/YYYY
val dateTransformation = OutputTransformation {
    // Add "/" after DD
    if (length >= 2 && length <= 8) {
        if (length > 2 && 2 <= length) {
            insert(2, "/")
        }
    }

    // Add "/" after MM
    if (length >= 4 && length <= 9) {
        if (length > 4 && 5 <= length + 1) {
            insert(5, "/")
        }
    }

    // Style slashes
    val gray = Color.Gray
    if (asCharSequence().length > 2) {
        addStyle(SpanStyle(color = gray), 2, 3)
    }
    if (asCharSequence().length > 5) {
        addStyle(SpanStyle(color = gray), 5, 6)
    }
}

@Composable
fun DateInputField(innerPadding: PaddingValues) {
    Column(modifier = Modifier
        .padding(innerPadding)
        .padding(16.dp)) {

        val dateState = rememberTextFieldState()

        BasicTextField(
            state = dateState,
            outputTransformation = dateTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            // Limit input to 8 characters
            inputTransformation = InputTransformation {
                if (asCharSequence().length > 8) {
                    delete(8, asCharSequence().length)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please enter date in DDMMYYYY format (e.g. 01012025 → 01/01/2025).",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}
