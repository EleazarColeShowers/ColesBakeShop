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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class WelcomePage : androidx.activity.ComponentActivity() {
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
                        Welcome()
                    }
                }
            }
        }
    }
}

@Composable
fun Welcome(){
    val context = LocalContext.current
    val comforter1 = FontFamily(
        Font(R.font.comforter1)
    )
    val poppinsBold= FontFamily(
        Font(R.font.poppinsbold)
    )
    val welcomeCake= painterResource(id = R.drawable.customizedcake)
    Column (
        modifier= Modifier
            .fillMaxSize(0.9f)
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text= "Cole's BakeShop",
            color= MaterialTheme.colorScheme.onBackground,
            fontFamily = comforter1,
            style = TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(26.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(513.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Image(
                painter = welcomeCake,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 1f }
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = size.height / 2,
                                endY = size.height
                            )
                        )
                    },
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Home of Sweet Treats",
                color = Color.White,
                fontFamily = poppinsBold,
                style = TextStyle(
                    fontSize = 27.sp,
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(41.dp))
        Column(
            modifier= Modifier
                .width(245.dp)
                .height(46.dp)
                .background(color = Color(0xFFFF91A4), shape = RoundedCornerShape(15.dp))
                .align(Alignment.CenterHorizontally)
                .clickable {
                    val intent = Intent(context, LogInActivity::class.java)
                    context.startActivity(intent)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text= "Login",
                color= MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Spacer(modifier = Modifier.height(21.dp))
        //TODO: FIX SIGNUP NOT DISPLAYING ON PHYSICAL DEVICE"
        Column(
            modifier= Modifier
                .width(245.dp)
                .height(46.dp)
                .background(color = Color(0xff9facdc), shape = RoundedCornerShape(15.dp))
                .align(Alignment.CenterHorizontally)
                .clickable {
                    val intent = Intent(context, SignUpActivity::class.java)
                    context.startActivity(intent)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text= "Sign Up",
                color= MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }

}