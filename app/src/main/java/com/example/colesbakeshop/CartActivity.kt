package com.example.colesbakeshop

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    color = Color.White
                ) {
                    CartPage(
                        itemName = intent.getStringExtra("itemName") ?: "Unknown Cake",
                        itemPrice = intent.getStringExtra("itemPrice") ?: "Unknown Price",
                        itemDescription = intent.getStringExtra("itemDescription") ?: "No Description",
                        itemImage = intent.getIntExtra("itemImage", R.drawable.carrot)
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
    itemImage: Int
) {
    val searchQuery = remember { mutableStateOf("") }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                Modifier.fillMaxWidth()
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
                itemName = itemName,
                itemPrice = itemPrice,
                itemDescription = itemDescription,
                itemImage = itemImage
            )
            Spacer(modifier = Modifier.height(29.dp))
        }
    }
}

@Composable
fun OrderHistory(
    itemName: String,
    itemPrice: String,
    itemDescription: String,
    itemImage: Int
) {
    val poppinsBold = FontFamily(Font(R.font.poppinsbold))
    val poppinsRegular = FontFamily(Font(R.font.poppinsregular))

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "My Order History",
            fontFamily = poppinsBold,
            style = TextStyle(fontSize = 15.sp),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

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
                    painter = painterResource(id = itemImage),
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
                        text = itemName,
                        fontFamily = poppinsRegular,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Price: $itemPrice",
                        fontFamily = poppinsRegular,
                        style = TextStyle(fontSize = 12.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Total: $itemPrice",
                        fontFamily = poppinsRegular,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}
