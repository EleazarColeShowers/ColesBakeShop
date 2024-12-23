package com.example.colesbakeshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
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
                    val context = LocalContext.current
                    val database = Room.databaseBuilder(
                        context.applicationContext,
                        OrderDatabase::class.java,
                        "app_database"
                    ).build()
                    val repository = OrderRepository(database.orderDao())
                    val orderViewModel = remember {
                        ViewModelProvider(
                            context as ViewModelStoreOwner,
                            OrderViewModelFactory(repository)
                        )[OrderViewModel::class.java]
                    }
                    MailPage(
                        itemName = intent.getStringExtra("itemName") ?: "Unknown Cake",
                        itemPrice = intent.getStringExtra("itemPrice") ?: "Unknown Price",
                        itemDescription = intent.getStringExtra("itemDescription") ?: "No Description",
                        itemImage = intent.getIntExtra("itemImage", R.drawable.carrot),
                        viewModel = orderViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MailPage(
    itemName: String,
    itemPrice: String,
    itemDescription: String,
    itemImage: Int,
    viewModel: OrderViewModel
) {
    val context= LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    val currentScreen = remember { mutableStateOf("mail") }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Apply elevation
                shape = RoundedCornerShape(16.dp), // Optional: Rounded corners for the BottomBar
                modifier = Modifier.fillMaxWidth()
            ) {
                BottomBar(
                    Modifier.fillMaxWidth(),
                    currentScreen = currentScreen.value,
                    onHomeClick = {
                        currentScreen.value = "home"
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    },
                    onCartClick = {
                        currentScreen.value = "cart"
                        val intent = Intent(context, CartActivity::class.java)
                        context.startActivity(intent)
                    },
                    onMailClick = {
                        currentScreen.value = "mail"
                        val intent = Intent(context, MailActivity::class.java)
                        context.startActivity(intent)
                    },
                    onPersonClick = {
                        currentScreen.value = "person"
                        val intent = Intent(context, ProfileActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    )  { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeBar()
            Spacer(modifier = Modifier.height(14.dp))
            SearchBar(
                hint = "Search My Inbox",
                textState = searchQuery,
                onSearchClicked = {
                    println("Search for: ${searchQuery.value}")
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}