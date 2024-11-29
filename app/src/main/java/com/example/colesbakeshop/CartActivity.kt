package com.example.colesbakeshop

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class CartActivity : androidx.activity.ComponentActivity() {
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
                        CartPage()
                    }
                }
            }
        }
    }
}

@Composable
fun CartPage() {
    val searchQuery = remember { mutableStateOf("") }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                Modifier
                    .fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isCakeDetailsPage(navController)) {
                WelcomeBar()
                Spacer(modifier = Modifier.height(14.dp))

                SearchBar(
                    hint = "Search My Order History",
                    textState = searchQuery,
                    onSearchClicked = {
                        println("Search for: ${searchQuery.value}")
                    }
                )
                Spacer(modifier = Modifier.height(28.dp))
                OrderHistory()
                Spacer(modifier = Modifier.height(29.dp))
            }

        }
    }
}

@Composable
fun OrderHistory(){
    Text(
        text= "My Order History"
    )
}
