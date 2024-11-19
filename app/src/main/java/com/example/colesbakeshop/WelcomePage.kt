package com.example.colesbakeshop

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class WelcomePage : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColesBakeShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xffffffff)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Welcome()
                    }
                }
            }
        }
    }
}

@Composable
fun Welcome(){
    Text(text= "Page for sign up and log in")
}