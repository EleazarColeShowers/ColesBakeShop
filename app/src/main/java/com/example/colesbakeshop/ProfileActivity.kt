package com.example.colesbakeshop

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class ProfileActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ColesBakeShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
//                    val context = LocalContext.current
//                    val database = Room.databaseBuilder(
//                        context.applicationContext,
//                        OrderDatabase::class.java,
//                        "app_database"
//                    ).build()
//                    val repository = OrderRepository(database.orderDao())
//                    val orderViewModel = remember {
//                        ViewModelProvider(
//                            context as ViewModelStoreOwner,
//                            OrderViewModelFactory(repository)
//                        )[OrderViewModel::class.java]
//                    }
                   ProfilePage()
                }
            }
        }
    }
}

@Composable
fun ProfilePage() {
    val currentScreen = remember { mutableStateOf("person") }
    val context= LocalContext.current
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
            ProfileHeader()
        }
    }
}

@Composable
fun ProfileHeader(){
    val context = LocalContext.current as? Activity
    val returnArrow = painterResource(id = R.drawable.returnarrow)
    val poppinsBold= FontFamily(
        Font(R.font.poppinsbold)
    )
    Column (
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier
                    .size(28.dp)
                    .clickable { context?.finish() }
                    .background(color = Color(0xFFFF91A4), shape = RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = returnArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .width(12.6.dp)
                        .height(19.6.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .height(30.dp)
                    .width(160.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFF91A4),
                        shape = RoundedCornerShape(12.dp)
                    )
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
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Account",
            fontFamily = FontFamily(Font(R.font.poppinsbold)),
            style = TextStyle(fontSize = 15.sp),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(13.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                .padding(8.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(45.dp)
                    .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Color(0xff9facdc)),
                        verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Customer Support",
                    style = TextStyle(fontSize = 13.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp)

                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(45.dp)
                    .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Color(0xff9facdc)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Privacy Policy",
                    style = TextStyle(fontSize = 13.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp)

                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(45.dp)
                    .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Color(0xff9facdc)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment Methods",
                    style = TextStyle(fontSize = 13.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp)

                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(45.dp)
                    .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Color(0xff9facdc)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order History",
                    style = TextStyle(fontSize = 13.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp)

                )
            }
        }

    }
}
