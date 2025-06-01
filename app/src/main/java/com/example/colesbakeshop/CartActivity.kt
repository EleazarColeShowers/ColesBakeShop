package com.example.colesbakeshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.colesbakeshop.data.Order
import com.example.colesbakeshop.data.OrderDatabase
import com.example.colesbakeshop.data.OrderRepository
import com.example.colesbakeshop.data.OrderStatus
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
                    color = MaterialTheme.colorScheme.background
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
    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    val currentScreen = remember { mutableStateOf("cart") }

    Scaffold(
        bottomBar = {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp),
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
                viewModel = viewModel,
                searchQuery = searchQuery.value
            )
            Spacer(modifier = Modifier.height(29.dp))
        }
    }
}


@Composable
fun OrderHistory(viewModel: OrderViewModel, searchQuery: String) {
    val ordersState = viewModel.allOrders.observeAsState(initial = emptyList())
    val orders = ordersState.value.reversed()
    val filteredOrders = if (searchQuery.isEmpty()) {
        orders
    } else {
        orders.filter { it.itemName.contains(searchQuery, ignoreCase = true) }
    }

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

        if (filteredOrders.isEmpty()) {
            Text(
                text = "No orders match your search.",
                style = TextStyle(fontSize = 14.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn {
                items(filteredOrders) { order ->
                    OrderCard(order, viewModel)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order, viewModel: OrderViewModel) {
    val poppinsRegular = FontFamily(Font(R.font.poppinsregular))

    val statusColor = when (order.orderStatus) {
        OrderStatus.ONGOING -> Color(0xFFEE7424)
        OrderStatus.DELIVERED -> Color(0xFF00FF00)
        OrderStatus.CANCELED -> Color(0xFFFF0000)
    }

    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
            .padding(8.dp)
            .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Color(0xff9facdc))
            .clickable { openDialog.value = true }
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
            Column(modifier= Modifier.padding(vertical=5.dp)) {
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
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = order.orderStatus.name,
                    fontFamily = poppinsRegular,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier
                        .background(color = statusColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .width(70.dp)
                        .height(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = "Proceed with Order?",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    )
            },
            text = {
                Text(
                    text = "Would you like to proceed with your order?",
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.updateOrderStatus(order.orderNumber, OrderStatus.ONGOING)
                        val intent = Intent(context, ConfirmationActivity::class.java).apply {
                            putExtra("itemName", order.itemName)
                            putExtra("itemPrice", order.itemPrice)
                            putExtra("itemDescription", order.itemDescription) // Add this field in your Order class if not present
                            putExtra("itemImage", order.itemImage)
                        }
                        context.startActivity(intent)

                    }
                ) {
                    Text("Proceed", color = Color(0xFFFF91A4))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.updateOrderStatus(order.orderNumber, OrderStatus.CANCELED)
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel", color = Color(0xff9facdc))
                }
            },
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                .height(200.dp)

        )
    }
}
