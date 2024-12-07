package com.example.colesbakeshop

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.room.Room
import com.example.colesbakeshop.data.OrderDatabase
import com.example.colesbakeshop.data.OrderRepository
import com.example.colesbakeshop.data.OrderViewModel
import com.example.colesbakeshop.data.OrderViewModelFactory
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class MailActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            ColesBakeShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    Text(text = "This is the mail activity")
                }
            }
        }
    }
}