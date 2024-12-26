package com.example.colesbakeshop

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.room.Room
import com.example.colesbakeshop.data.Order
import com.example.colesbakeshop.data.OrderDatabase
import com.example.colesbakeshop.data.OrderRepository
import com.example.colesbakeshop.data.OrderViewModel
import com.example.colesbakeshop.data.OrderViewModelFactory
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme
import kotlin.text.*

class ProductDetailsActivity : androidx.activity.ComponentActivity() {
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
                        modifier= Modifier.verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
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
                        ProductDetailsScreen(
                            itemName = intent.getStringExtra("itemName") ?: "",
                            itemPrice = intent.getStringExtra("itemPrice") ?: "",
                            itemDescription = intent.getStringExtra("itemDescription") ?: "",
                            itemImage = intent.getIntExtra("itemImage", R.drawable.carrot),
                            viewModel = orderViewModel
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ProductDetailsScreen(
    itemName: String,
    itemPrice: String,
    itemDescription: String,
    itemImage: Int,
    viewModel: OrderViewModel

) {
    val context = LocalContext.current as? Activity
    val add = painterResource(id = R.drawable.add)
    val reduce = painterResource(id = R.drawable.reduce)

    val initialPrice = itemPrice.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: 0.0
    var quantity by remember { mutableStateOf(1) }
    var calculatedPrice by remember { mutableStateOf(initialPrice) }

    calculatedPrice = initialPrice * quantity
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .height(30.dp)
                        .width(160.dp)
                        .border(width = 1.dp, color = Color(0xFFFF91A4), shape = RoundedCornerShape(12.dp))
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Product Details",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(48.dp))

            Image(
                painter = painterResource(id = itemImage),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(205.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(53.dp))

            Text(
                text = itemName,
                style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier
                            .size(33.85.dp)
                            .background(color = Color(0xFFFF91A4), shape = CircleShape)
                            .clickable {
                                if (quantity > 1) {
                                    quantity--
                                    calculatedPrice = initialPrice * quantity
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = reduce, contentDescription = null)
                    }

                    Text(
                        text = quantity.toString(),
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Column(
                        Modifier
                            .size(33.85.dp)
                            .background(color = Color(0xFFFF91A4), shape = CircleShape)
                            .clickable {
                                quantity++
                                calculatedPrice = initialPrice * quantity
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = add, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "₦${String.format("%.2f", calculatedPrice)}",
                    style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(35.dp))
            Column (
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(54.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color(0xFFFF91A4), shape = RoundedCornerShape(25.dp))
                    .clickable {
                        val intent = Intent(context, CartActivity::class.java).apply {
                            putExtra("itemName", itemName)
                            putExtra("itemPrice", calculatedPrice.toString())
                            putExtra("itemDescription", itemDescription)
                            putExtra("itemQuantity", quantity)
                            putExtra("itemImage", itemImage)
                        }
                        viewModel.insert(Order(itemName = itemName, itemPrice = itemPrice, itemDescription = itemDescription, itemImage = itemImage))

                        context?.startActivity(intent)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Add to Cart",
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = itemDescription,
                style = TextStyle(fontSize = 16.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
