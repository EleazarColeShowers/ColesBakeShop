package com.example.colesbakeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColesBakeShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        SignUpPage()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage() {
    val comforter1 = FontFamily(
        Font(R.font.comforter1)
    )
    val poppinsBold = FontFamily(
        Font(R.font.poppinsbold)
    )
    val poppinsRegular = FontFamily(
        Font(R.font.poppinsregular)
    )
    var buttonColor by remember { mutableStateOf(Color(0xff9facdc)) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize(0.9f)
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Cole's BakeShop",
            color = MaterialTheme.colorScheme.onBackground,
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
                    text = "Welcome!",
                    fontFamily = poppinsBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Register to continue",
                    fontFamily = poppinsRegular,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start
                    )
                )
            }
            Spacer(modifier = Modifier.height(39.dp))

            // Email TextField
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
                    .background(color = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(fontSize = 14.sp)
            )

            Spacer(modifier = Modifier.height(17.dp))

            // Password TextField
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
                    .background(color = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(15.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    cursorColor = Color.Black
//                ),
                textStyle = TextStyle(fontSize = 14.sp),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(17.dp))

            // Confirm Password TextField
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
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
                    .background(color = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(fontSize = 14.sp),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(97.dp))
            TermsOfServiceText()
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .background(color = buttonColor, shape = RoundedCornerShape(15.dp))
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        buttonColor = Color(0xFFFF91A4)
                        if (password == confirmPassword) {
                            auth
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Registration Successful!",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        val intent = Intent(context, MainActivity::class.java)
                                        context.startActivity(intent)
                                    } else {
                                        Toast
                                            .makeText(
                                                context,
                                                "Registration Failed: ${task.exception?.message}",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                }
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "Passwords do not match!",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Register",
                    color = Color.White,
                    fontFamily = poppinsRegular,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
            LoginText()
        }
    }
}
@Composable
fun TermsOfServiceText() {
    val poppinsRegular= FontFamily(
        Font(R.font.poppinsregular)
    )
    val annotatedString = buildAnnotatedString {
        append("By continuing, you agree to our ")

        pushStringAnnotation(tag = "TERMS", annotation = "terms://service")
        withStyle(
            style = SpanStyle(
                color = Color(0xFFFF91A4),
                fontFamily = poppinsRegular
            )
        ) {
            append("Terms of Service")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                .firstOrNull()?.let {
                    println("Terms of Service clicked!")
                }
        },
        style = TextStyle(
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            fontFamily = poppinsRegular
        )
    )
}

@Composable
fun LoginText() {
    val poppinsRegular = FontFamily(
        Font(R.font.poppinsregular)
    )
    val context = LocalContext.current

    val annotatedString = buildAnnotatedString {
        append("Already a member? ")

        pushStringAnnotation(tag = "LOGIN", annotation = "login://action")
        withStyle(
            style = SpanStyle(
                color = Color(0xFFFF91A4),
                fontFamily = poppinsRegular,
                fontSize = 14.sp
            )
        ) {
            append("Login")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                .firstOrNull()?.let {
                    context.startActivity(Intent(context, LogInActivity::class.java))
                }
        },
        style = TextStyle(
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = poppinsRegular
        )
    )
}
