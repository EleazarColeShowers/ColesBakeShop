package com.example.colesbakeshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

class LogInActivity : androidx.activity.ComponentActivity() {
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
                        LogInPage()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInPage(){
    val comforter1 = FontFamily(
        Font(R.font.comforter1)
    )
    val poppinsBold= FontFamily(
        Font(R.font.poppinsbold)
    )
    val poppinsRegular= FontFamily(
        Font(R.font.poppinsregular)
    )
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context= LocalContext.current
    Column (
        modifier= Modifier
            .fillMaxSize(0.9f)
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Cole's BakeShop",
            color = Color.Black,
            fontFamily = comforter1,
            style = TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(21.dp))

        Column(
            modifier = Modifier.width(300.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Welcome back!",
                    fontFamily = poppinsBold,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Log in to continue",
                    fontFamily = poppinsRegular,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start
                    )
                )
            }
            Spacer(modifier = Modifier.height(39.dp))

            Column(
                modifier= Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Email",
                    fontFamily = poppinsRegular,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = "example@gmail.com",
                            color = Color(0xff9facdc),
                            fontFamily = poppinsRegular,
                            style = TextStyle(fontSize = 14.sp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xff9facdc),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .background(color = Color.White),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(17.dp))
                Text(
                    text = "Password",
                    fontFamily = poppinsRegular,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "***********",
                            color = Color(0xff9facdc),
                            fontFamily = poppinsRegular,
                            style = TextStyle(fontSize = 14.sp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xff9facdc),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .background(color = Color.White),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(19.dp))
                //TODO: add remember me checkbox, and google login functionality(optional)
                Column(
                    modifier= Modifier
                        .fillMaxWidth()
                        .height(38.dp)
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
                        text= "Log In",
                        color= Color.White,
                        fontFamily = poppinsRegular,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Spacer(modifier = Modifier.height(22.dp))
                SignUpText()
            }
        }
    }
}

@Composable
fun SignUpText() {
    val poppinsRegular = FontFamily(
        Font(R.font.poppinsregular)
    )
    val context = LocalContext.current

    val annotatedString = buildAnnotatedString {
        append("Don't have an account? ")

        pushStringAnnotation(tag = "SIGNUP", annotation = "signup://action")
        withStyle(
            style = SpanStyle(
                color = Color(0xFFFF91A4),
                fontFamily = poppinsRegular,
                fontSize = 14.sp
            )
        ) {
            append("Sign Up")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "SIGNUP", start = offset, end = offset)
                .firstOrNull()?.let {
                    context.startActivity(Intent(context, SignUpActivity::class.java))
                }
        },
        style = TextStyle(
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = poppinsRegular
        )
    )
}
