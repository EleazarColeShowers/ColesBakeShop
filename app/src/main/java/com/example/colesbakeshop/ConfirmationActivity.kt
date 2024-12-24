package com.example.colesbakeshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.colesbakeshop.data.Order
import com.example.colesbakeshop.data.OrderDatabase
import com.example.colesbakeshop.data.OrderRepository
import com.example.colesbakeshop.data.OrderStatus
import com.example.colesbakeshop.data.OrderViewModel
import com.example.colesbakeshop.data.OrderViewModelFactory
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class ConfirmationActivity : androidx.activity.ComponentActivity() {
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
                    ConfirmationAppNavigation(
                        itemName = intent.getStringExtra("itemName") ?: "Unknown Cake",
                        itemPrice = intent.getStringExtra("itemPrice") ?: "Unknown Price",
                        itemDescription = intent.getStringExtra("itemDescription") ?: "No Description",
                        itemImage = intent.getIntExtra("itemImage", R.drawable.carrot),
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmationAppNavigation(
    itemName: String,
    itemPrice: String,
    itemDescription: String,
    itemImage: Int,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "confirmation"
    ) {
        composable("confirmation") {
            ConfirmationPage(
                itemName,
                itemPrice,
                itemDescription,
                itemImage,
                onCheckoutClicked = { navController.navigate("checkout") }
            )
        }
        composable("checkout") {
            CheckoutFrag()
        }
    }
}

@Composable
fun ConfirmationPage(
    itemName: String,
    itemPrice: String,
    itemDescription: String,
    itemImage: Int,
    onCheckoutClicked: () -> Unit

) {
    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    val currentScreen = remember { mutableStateOf("cart") }
    val navController = rememberNavController()
    val comforter1 = FontFamily(Font(R.font.comforter1))
    val poppinsBold = FontFamily(Font(R.font.poppinsbold))
    val poppinsRegular = FontFamily(Font(R.font.poppinsregular))

    Scaffold(
        bottomBar = {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Use CardDefaults for elevation
                shape = RoundedCornerShape(16.dp), // Optional: Rounded corners
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp) // Padding around the card
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White) // Background color for the Row
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: $itemPrice",
                        fontFamily = poppinsBold,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        onClick = onCheckoutClicked,
                        modifier = Modifier
                            .background(Color(0xFFFF91A4), shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Checkout",
                            color = Color.White,
                            fontFamily = poppinsBold,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    ){ innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cole's BakeShop",
                color = Color.Black,
                fontFamily = comforter1,
                style = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Please Double Check Your Order",
                fontFamily = poppinsBold,
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(23.dp))
            SearchBar(
                hint = "Search My Order History",
                textState = searchQuery,
                onSearchClicked = { println("Search for: ${searchQuery.value}") }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "My Order",
                fontFamily = poppinsBold,
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Apply elevation
                shape = RoundedCornerShape(16.dp), // Rounded corners
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xff9facdc)
                    )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        ) // Background for the column
                        .padding(16.dp), // Padding inside the column
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = itemImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = itemName,
                        fontFamily = poppinsBold,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Price: $itemPrice",
                        fontFamily = poppinsRegular,
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = itemDescription,
                        fontFamily = poppinsRegular,
                        style = TextStyle(fontSize = 14.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun CheckoutFrag(){
    Column(
        Modifier.fillMaxSize()
            .background(color = Color.Yellow)
    ) {
        Text(text = "This the page for payment")

    }
}