package com.airdrop.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airdrop.android.ui.theme.AirDropTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirDropTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AirDropApp()
                }
            }
        }
    }
}

@Composable
fun AirDropApp() {
    Text(text = "Welcome to AirDrop - Lightning-fast P2P File Sharing")
}

@Preview(showBackground = true)
@Composable
fun AirDropAppPreview() {
    AirDropTheme {
        AirDropApp()
    }
}
