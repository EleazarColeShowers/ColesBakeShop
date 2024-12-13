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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.colesbakeshop.ui.theme.ColesBakeShopTheme

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
                        horizontalAlignment = Alignment.CenterHorizontally,
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
    val navController = rememberNavController()
    val selectedCategory = remember { mutableStateOf("Cakes") }
    val currentScreen = remember { mutableStateOf("home") }
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
            if (!isCakeDetailsPage(navController)) {
                WelcomeBar()
                Spacer(modifier = Modifier.height(14.dp))

                SearchBar(
                    hint = "Search for Cupcakes, Cakes ...",
                    textState = searchQuery,
                    onSearchClicked = {
                        println("Search for: ${searchQuery.value}")
                    }
                )
                Spacer(modifier = Modifier.height(14.dp))
                Categories(selectedCategory)


                when (selectedCategory.value) {
                    "Cakes" -> CakePage(navController, searchQuery.value)
                    "Dessert" -> DessertPage(navController, searchQuery.value)
                    "Pastries" -> PastriesPage(navController, searchQuery.value)
                }
            }

        }
    }
}

@Composable
fun isCakeDetailsPage(navController: NavController): Boolean {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    return currentBackStackEntry?.destination?.route?.startsWith("CakeDetails") == true
}

@Composable
fun WelcomeBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(130.dp)
            .padding(top = 20.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.carrot),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RoundedCornerShape(12.dp)),
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
                    text = "Home of Sweet Treats",
                color = Color.White,
                style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                        )
            )
        }
    }
}

@Composable
fun SearchBar(
    hint: String = "Search for Cupcakes, Cakes ...",
    textState: MutableState<String>,
    onSearchClicked: (String) -> Unit
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
            )
            .border(2.dp, Color(0xff9facdc), RoundedCornerShape(18.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                textState.value = it
                onSearchClicked(it)
            },
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
                    onSearchClicked(text)
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
fun Categories(selectedCategory: MutableState<String>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Categories",
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff000000),
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryItem(
                text = "Cakes",
                isSelected = selectedCategory.value == "Cakes",
                onClick = { selectedCategory.value = "Cakes" }
            )
            CategoryItem(
                text = "Dessert",
                isSelected = selectedCategory.value == "Dessert",
                onClick = { selectedCategory.value = "Dessert" }
            )
            CategoryItem(
                text = "Pastries",
                isSelected = selectedCategory.value == "Pastries",
                onClick = { selectedCategory.value = "Pastries" }
            )
        }
    }
}

@Composable
fun CategoryItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(74.dp)
            .background(
                color = if (isSelected) Color(0xFFFF91A4) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(width = 1.dp, color = Color(0xff9facdc), shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable { onClick() }
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
fun CakePage(navController: NavController, searchQuery: String) {
    val cakes = listOf(
        CakeData("Basket Cake", "₦40,000", R.drawable.basketcake),
        CakeData("Butter Icing Cake", "₦60,000", R.drawable.buttericingcake),
        CakeData("Top Forward Cake", "₦35,000", R.drawable.topforwardcake),
        CakeData("Love-Shaped Cake", "₦60,000", R.drawable.lovecake),
        CakeData("Customized Cake", "₦50,000", R.drawable.customizedcake),
        CakeData("Floral Butter Cream Cake", "₦30,000", R.drawable.floralbuttercream),
        CakeData("Classic Cream Cake", "₦55,000", R.drawable.classiccreamcake),
        CakeData("Whipped Cream Birthday Cake", "₦60,000", R.drawable.fullywhipped),
        CakeData("Budget Cake", "₦30,000", R.drawable.budgetcake),
        CakeData("Custom 3 Layer Cake", "₦60,000", R.drawable.basketcake)
    )
    val filteredCakes = if (searchQuery.isEmpty()) cakes else cakes.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(horizontal = 16.dp)
    ) {
        items(filteredCakes.chunked(2)) { cakeRow ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                cakeRow.forEach { cake ->
                    CakeItem(
                        painter = painterResource(id = cake.imageResId),
                        text = cake.name,
                        price = cake.price,
                        description = "A beautifully crafted cake perfect for celebrations!",
                        navController = navController,
                        imageResId = cake.imageResId
                    )
                }
                // Add a spacer for uneven rows
                if (cakeRow.size < 2) {
                    Spacer(modifier = Modifier.width(130.dp))
                }
            }
        }
    }
}


data class CakeData(val name: String, val price: String, val imageResId: Int)

@Composable
fun CakeItem(painter: Painter, text:String, price: String, navController: NavController, description: String, imageResId: Int) {
    val context= LocalContext.current
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(130.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                    putExtra("itemName", text)
                    putExtra("itemPrice", price)
                    putExtra("itemDescription", description)
                    putExtra("itemImage", imageResId)
                }
                context.startActivity(intent)
            }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .height(80.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .background(Color.White)
                .height(50.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = price,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun DessertPage(navController: NavController, searchQuery: String) {
    val desserts = listOf(
        DessertData("Carrot Cake", "₦4,000", R.drawable.carrotcakedessert),
        DessertData("Cake Parfait", "₦4,500", R.drawable.cakeparfait),
        DessertData("Mini Foil Cake", "₦3,000", R.drawable.carrotcakedessert),
        DessertData("Cake Tub", "₦3,800", R.drawable.carrotcakedessert)
    )

    val filteredDesserts = if (searchQuery.isEmpty()) desserts else desserts.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(filteredDesserts.chunked(2)) { dessertRow ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dessertRow.forEach { dessert ->
                    DessertItem(
                        painter = painterResource(id = dessert.imageResId),
                        text = dessert.name,
                        price = dessert.price,
                        description = "A sweet mouth-watering dessert!",
                        navController = navController,
                        imageResId = dessert.imageResId
                    )
                }
                if (dessertRow.size < 2) {
                    Spacer(modifier = Modifier.width(130.dp))
                }
            }
        }
    }
}
data class DessertData(val name: String, val price: String, val imageResId: Int)

@Composable
fun DessertItem(painter: Painter, text:String, price: String, navController: NavController, description: String, imageResId: Int) {
    val context= LocalContext.current
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(130.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                    putExtra("itemName", text)
                    putExtra("itemPrice", price)
                    putExtra("itemDescription", description)
                    putExtra("itemImage", imageResId)
                }
                context.startActivity(intent)
            }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .height(80.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .background(Color.White)
                .height(50.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = price,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun PastriesPage(navController: NavController, searchQuery: String) {
    val pastries = listOf(
        PastryData("Box Of 4 Milky Donuts", "₦7,000", R.drawable.boxoffourdonuts),
        PastryData("Box Of 6 Milky Donuts", "₦11,000", R.drawable.boxoffourdonuts),
        PastryData("Box Of 8 Milky Donuts", "₦15,000", R.drawable.boxoffourdonuts),
        PastryData("Cake Tub", "₦3,800", R.drawable.boxoffourdonuts)
    )

    val filteredPastries = if (searchQuery.isEmpty()) pastries else pastries.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(filteredPastries.chunked(2)) { pastryRow ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                pastryRow.forEach { pastry ->
                    PastriesItem(
                        painter = painterResource(id = pastry.imageResId),
                        text = pastry.name,
                        price = pastry.price
                    )
                }
                if (pastryRow.size < 2) {
                    Spacer(modifier = Modifier.width(130.dp))
                }
            }
        }
    }
}

data class PastryData(val name: String, val price: String, val imageResId: Int)

@Composable
fun PastriesItem(painter: Painter, text:String, price: String) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(130.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .height(80.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .background(Color.White)
                .height(50.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = price,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    currentScreen: String, // Pass the current screen identifier
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onMailClick: () -> Unit,
    onPersonClick: () -> Unit
) {
    val home = painterResource(id = R.drawable.homeicon)
    val cart = painterResource(id = R.drawable.carticon)
    val mail = painterResource(id = R.drawable.mail)
    val person = painterResource(id = R.drawable.person)

    Column(
        Modifier
            .fillMaxWidth()
            .height(97.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .height(53.dp)
                .fillMaxWidth(0.9f)
                .border(width = 1.dp, color = Color(0xff9facdc), shape = RoundedCornerShape(25.dp))
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = home,
                    contentDescription = "Home Icon",
                    tint = if (currentScreen == "home") Color(0xFFFF91A4) else Color(0xff9facdc),
                    modifier = Modifier.clickable {
                        onHomeClick()
                    }
                )
                Icon(
                    painter = cart,
                    contentDescription = "Cart Icon",
                    tint = if (currentScreen == "cart") Color(0xFFFF91A4) else Color(0xff9facdc),
                    modifier = Modifier.clickable {
                        onCartClick()
                    }
                )
                Icon(
                    painter = mail,
                    contentDescription = "Mail Icon",
                    tint = if (currentScreen == "mail") Color(0xFFFF91A4) else Color(0xff9facdc),
                    modifier = Modifier.clickable {
                        onMailClick()
                    }
                )
                Icon(
                    painter = person,
                    contentDescription = "Person Icon",
                    tint = if (currentScreen == "person") Color(0xFFFF91A4) else Color(0xff9facdc),
                    modifier = Modifier.clickable {
                        onPersonClick()
                    }
                )
            }
        }
    }
}