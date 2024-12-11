package com.example.colesbakeshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.colesbakeshop.data.Order
import com.example.colesbakeshop.data.OrderDatabase
import com.example.colesbakeshop.data.OrderRepository
import com.example.colesbakeshop.data.OrderViewModel
import com.example.colesbakeshop.data.OrderViewModelFactory
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class CartActivity : androidx.activity.ComponentActivity() {
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
                    CartPage(
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
fun CartPage(
    itemName: String,
    itemPrice: String,
    itemDescription: String,
    itemImage: Int,
    viewModel: OrderViewModel
) {
    val context= LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    val currentScreen = remember { mutableStateOf("cart") }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
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
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            OrderHistory(
                viewModel
            )
            Spacer(modifier = Modifier.height(29.dp))
        }
    }
}

@Composable
fun OrderHistory(viewModel: OrderViewModel) {
    val ordersState = viewModel.allOrders.observeAsState(initial = emptyList())
    val orders = ordersState.value

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "My Order History",
            fontFamily = FontFamily(Font(R.font.poppinsbold)),
            style = TextStyle(fontSize = 15.sp),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (orders.isEmpty()) {
            Text(
                text = "No orders found.",
                style = TextStyle(fontSize = 14.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn {
                items(orders) { order ->
                    OrderCard(order)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    val poppinsRegular = FontFamily(Font(R.font.poppinsregular))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(8.dp)
            .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Color(0xff9facdc))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = order.itemImage),
                contentDescription = null,
                modifier = Modifier
                    .height(130.dp)
                    .width(130.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = order.itemName,
                    fontFamily = poppinsRegular,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: ${order.itemPrice}",
                    fontFamily = poppinsRegular,
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total: ${order.itemPrice}",
                    fontFamily = poppinsRegular,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = order.orderNumber,
                    fontFamily = poppinsRegular,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}