package com.example.colesbakeshop

import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.VideoView
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : androidx.activity.ComponentActivity() {
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HomePage()
                    }
                }
            }
        }
    }
}

@Composable
fun HomePage() {
    val searchQuery = remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeBar()
        Spacer(modifier = Modifier.height((-25).dp))

        SearchBar(
            hint = "Search for Cupcakes, Cakes ...",
            onTextChange = { query ->
                searchQuery.value = query
            },
            onSearchClicked = {
                println("Search for: ${searchQuery.value}")
            }

        )
        Spacer(modifier =Modifier.height(28.dp))
        Categories()
        Spacer(modifier = Modifier.height(29.dp))
        Recommendation()
    }
}

@Composable
fun WelcomeBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(127.dp)
            .padding(top=20.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
    ) {
        // Load the "carrot" drawable image as the background
        Image(
            painter = painterResource(id = R.drawable.carrot), // Make sure "carrot" is in res/drawable
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000), shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun SearchBar(
    hint: String = "Search for Cupcakes, Cakes ...",
    onTextChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    textState: MutableState<String> = remember { mutableStateOf("") }
) {
    val searchIcon = painterResource(id = R.drawable.search)
    val text = textState.value

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(
                Color(0xffffffff),
                shape = RoundedCornerShape(12.dp)
            ) // Light pink background
            .border(2.dp, Color(0xff000000), RoundedCornerShape(12.dp)) // Darker pink border
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = text,
            onValueChange = { textState.value = it },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier.weight(1f),
            singleLine = true,

            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = hint,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked()
                }
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = searchIcon,
            contentDescription = "Search Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun Categories() {
    val cupcakes = painterResource(id = R.drawable.cupcake)
    val cakes = painterResource(id = R.drawable.cake)
    val pastries = painterResource(id = R.drawable.pastries)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Categories",
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xff000000),
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryItem( text = "Cakes")
            CategoryItem( text = "Dessert")
            CategoryItem( text = "Pastries")
        }
    }
}

@Composable
fun CategoryItem(text: String) {
    // Track selection state
    var isSelected by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(74.dp)
            .background(
                color = if (isSelected) Color.Black else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable { isSelected = !isSelected } // Toggle state on click
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = if (isSelected) Color.White else Color(0xff000000),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            ),
        )
    }
}

@Composable
fun Recommendation(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "This week's recommendation",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xff000000),
                textAlign = TextAlign.Start,
            ),
            modifier = Modifier.padding(start = 17.dp)
        )
    }
}
