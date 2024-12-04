package com.example.colesbakeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
    var buttonColor by remember { mutableStateOf(Color(0xff9facdc)) }
    val googleIcon= painterResource(id = R.drawable.googleicon)

    val context= LocalContext.current
    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.web_client_id))
                .requestEmail()
                .build()
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        Toast.makeText(context, "Firebase Auth Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Firebase Auth Failed: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: ApiException) {
            Toast.makeText(context, "Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
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
                            text = "**********",
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
                    textStyle = TextStyle(fontSize = 14.sp),
                    visualTransformation = PasswordVisualTransformation()

                )
                Spacer(modifier = Modifier.height(35.dp))
                //TODO: add remember me checkbox, and google login functionality(optional)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .background(color = buttonColor, shape = RoundedCornerShape(15.dp))
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            buttonColor = Color(0xFFFF91A4)
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                val auth = FirebaseAuth.getInstance()
                                auth
                                    .signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Log In successful!",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                            val intent = Intent(context, MainActivity::class.java)
                                            context.startActivity(intent)
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    task.exception?.message ?: "Log In failed!",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    }
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "Please fill in both email and password fields.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Log In",
                        color = Color.White,
                        fontFamily = poppinsRegular,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Spacer(modifier = Modifier.height(72.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.width(84.02.dp)
                    )

                    BasicText(
                        text = "Or login with",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = poppinsRegular
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.width(84.02.dp)
                    )
                }
                Spacer(modifier = Modifier.height(33.dp))
                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .background(color = White, shape = RoundedCornerShape(15.dp))
                        .align(Alignment.CenterHorizontally)
                        .border(
                            width = 1.dp,
                            color = Color(0xff9facdc),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            launcher.launch(googleSignInClient.signInIntent) // Launch Google Sign-In
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = googleIcon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                        )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Continue with Google",
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = Color.Black,
                            fontFamily = poppinsRegular
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                }
                Spacer(modifier = Modifier.height(40.dp))
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
