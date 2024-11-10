package com.example.colesbakeshop

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                        modifier= Modifier.verticalScroll(rememberScrollState()),
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

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isCakeDetailsPage(navController)) {
            WelcomeBar()
            Spacer(modifier = Modifier.height(14.dp))

            SearchBar(
                hint = "Search for Cupcakes, Cakes ...",
                onTextChange = { query ->
                    searchQuery.value = query
                },
                onSearchClicked = {
                    println("Search for: ${searchQuery.value}")
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
            Categories(navController)
            Spacer(modifier = Modifier.height(29.dp))
        }

        NavHost(navController = navController, startDestination = "Cake") {
            composable("Cake") { CakePage(navController) }
            composable("Dessert") { DessertPage(navController) }
            composable("Pastries") { PastriesPage(navController) }

            composable(
                route = "CakeDetails/{itemName}/{itemPrice}/{itemDescription}/{itemImage}",
                arguments = listOf(
                    navArgument("itemName") { type = NavType.StringType },
                    navArgument("itemPrice") { type = NavType.StringType },
                    navArgument("itemDescription") { type = NavType.StringType },
                    navArgument("itemImage") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                // Retrieve arguments
                val itemName = backStackEntry.arguments?.getString("itemName") ?: ""
                val itemPrice = backStackEntry.arguments?.getString("itemPrice") ?: ""
                val itemDescription = backStackEntry.arguments?.getString("itemDescription") ?: ""
                val itemImage = backStackEntry.arguments?.getInt("itemImage") ?: R.drawable.carrot

                // Full screen for CakeDetailsPage
                CakeDetailsPage(
                    itemName = itemName,
                    itemPrice = itemPrice,
                    itemDescription = itemDescription,
                    itemImage = itemImage,
                    navController
                )
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
            .height(127.dp)
            .padding(top = 20.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.carrot),
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
                text = "Welcome to Cole's Bakeshop",
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
fun SearchBar(hint: String = "Search for Cupcakes, Cakes ...", onTextChange: (String) -> Unit, onSearchClicked: () -> Unit, textState: MutableState<String> = remember { mutableStateOf("") }) {
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
fun Categories(navController: NavController) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

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
                navController = navController,
                route = "cake",
                isSelected = selectedCategory == "Cakes",
                onClick = { selectedCategory = "Cakes" }
            )
            CategoryItem(
                text = "Dessert",
                navController = navController,
                route = "dessert",
                isSelected = selectedCategory == "Dessert",
                onClick = { selectedCategory = "Dessert" }
            )
            CategoryItem(
                text = "Pastries",
                navController = navController,
                route = "pastries",
                isSelected = selectedCategory == "Pastries",
                onClick = { selectedCategory = "Pastries" }
            )
        }
    }
}

@Composable
fun CategoryItem(text: String, navController: NavController, route: String, isSelected: Boolean, onClick: () -> Unit) {
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
            .clickable {
                onClick()
                navController.navigate(route)
            }
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
fun CakePage(navController: NavController) {
    val whippedCreamCake = painterResource(id = R.drawable.whippedcreamcake)
    val basketCake= painterResource(id = R.drawable.basketcake)
    val butterIcing= painterResource(id = R.drawable.buttericingcake)
    val topForwardCake= painterResource(id = R.drawable.topforwardcake)
    val customizedCake= painterResource(id = R.drawable.customizedcake)
    val floralButterCream= painterResource(id = R.drawable.floralbuttercream)
    val classicCreamCake= painterResource(id = R.drawable.classiccreamcake)
    val fullyWhippedCreamCake= painterResource(id = R.drawable.fullywhipped)
    val budgetCake= painterResource(id = R.drawable.budgetcake)
    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CakeItem(basketCake, text = "Basket Cake", price = "₦40,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.basketcake,
                navController = navController
            )
            CakeItem(butterIcing, text = "Butter Icing Cake", price = "₦60,000" ,description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.buttericingcake,
                navController = navController)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CakeItem(topForwardCake, text = "Top Forward Cake", price = "₦35,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.topforwardcake,
                navController = navController)
            CakeItem(whippedCreamCake, text = "Whipped Cream Cake", price = "₦60,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.whippedcreamcake,
                navController = navController)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CakeItem(customizedCake, text = "Customized Cake", price = "₦50,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.customizedcake,
                navController = navController)
            CakeItem(floralButterCream, text = "Floral Butter Cream Cake", price = "₦30,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.floralbuttercream,
                navController = navController)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CakeItem(classicCreamCake, text = "Classic Cream Cake", price = "₦55,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.classiccreamcake,
                navController = navController)
            CakeItem(fullyWhippedCreamCake, text = "Whipped Cream Birthday Cake", price = "₦60,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.fullywhipped,
                navController = navController)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CakeItem(budgetCake, text = "Budget Cake", price = "₦30,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.budgetcake,
                navController = navController)
            CakeItem(basketCake, text = "Custom 3 Layer Cake", price = "₦60,000",description = "A beautifully crafted basket cake perfect for celebrations!", imageResId = R.drawable.basketcake,
                navController = navController)
        }
    }
}

@Composable
fun CakeItem(painter: Painter, text:String, price: String, navController: NavController, description: String, imageResId: Int) {
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
                navController.navigate("CakeDetails/$text/$price/$description/$imageResId")
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
fun DessertPage(navController: NavController){
    val carrotDessert = painterResource(id = R.drawable.carrotcakedessert)
    val cakeParfait= painterResource(id = R.drawable.cakeparfait)
    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DessertItem(carrotDessert, text = "Carrot Cake", price = "₦4,000")
            DessertItem(cakeParfait, text = "Cake Parfait", price = "₦4,500")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DessertItem(carrotDessert, text = "Mini Foil Cake", price = "₦3,000")
            DessertItem(carrotDessert, text = "Cake Tub", price = "₦3,800")
        }
    }
}

@Composable
fun DessertItem(painter: Painter, text:String, price: String) {
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
fun PastriesPage(navController: NavController){
    val boxOfFour= painterResource(id = R.drawable.boxoffourdonuts)
    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PastriesItem(boxOfFour, text = "Box Of 4 Milky Donuts", price = "₦7,000")
            PastriesItem(boxOfFour, text = "Box Of 6 Milky Donuts", price = "₦11,000")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PastriesItem(boxOfFour, text = "Box of 8 Milky Donuts", price = "₦15,000")
            PastriesItem(boxOfFour, text = "Cake Tub", price = "₦3,800")
        }
    }
}

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
fun CakeDetailsPage(itemName: String, itemPrice: String, itemDescription: String, itemImage: Int, navController: NavController) {
    val returnArrow= painterResource(id = R.drawable.returnarrow)
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
                Column(
                    Modifier
                        .size(28.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                        .background(color = Color.Black, shape = RoundedCornerShape(12.dp)),                    verticalArrangement = Arrangement.Center,
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
                        .border(width = 1.dp, color = Color.Black)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.CenterVertically), // Aligns column vertically at the center of the row
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
                contentDescription = "Cake Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(205.dp)
                    .align(Alignment.CenterHorizontally)
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

