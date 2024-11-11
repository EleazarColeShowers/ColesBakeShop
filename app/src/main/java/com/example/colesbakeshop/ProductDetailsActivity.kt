package com.example.colesbakeshop

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

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
                        ProductDetailsScreen(
                            itemName = intent.getStringExtra("itemName") ?: "",
                            itemPrice = intent.getStringExtra("itemPrice") ?: "",
                            itemDescription = intent.getStringExtra("itemDescription") ?: "",
                            itemImage = intent.getIntExtra("itemImage", R.drawable.carrot) // Replace with a default image if necessary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(itemName: String, itemPrice: String, itemDescription: String, itemImage: Int) {
    val context= LocalContext.current
    Box(modifier = Modifier.fillMaxSize().padding(top=10.dp)) {
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
                // Back button logic in activity instead of NavController popBackStack
                Image(
                    painter = painterResource(id = R.drawable.returnarrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { (context as Activity).finish() }
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Product Details",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
            Image(
                painter = painterResource(id = itemImage),
                contentDescription = "Cake Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(205.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(53.dp))
            Text(text = itemName, style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold))
            Text(text = itemPrice, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = itemDescription,
                style = TextStyle(fontSize = 16.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
