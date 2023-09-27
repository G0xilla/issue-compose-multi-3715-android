package com.example.issue_compose_multi_3715

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val content = MutableStateFlow<(@Composable () -> Unit)?>(null)

    private fun updateContent() {
        // Simulate some time to update content
        Thread.sleep(1000)
        content.value = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Second Screen")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init content
        content.value = {
            val scope = rememberCoroutineScope()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "First Screen")
                    Button(
                        onClick = {
                            scope.launch { updateContent() }
                        }
                    ) {
                        Text("Update Content")
                    }
                }
            }
        }

        setContent {
            val contentState = content.collectAsState()
            contentState.value!!.invoke()
        }
    }
}

