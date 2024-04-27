package com.example.navigationdrawer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.navigationdrawer.ui.theme.MyGrey
import com.example.navigationdrawer.ui.theme.MyRed
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val items = listOf(
                NavigationItem(
                    title = "All",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                ),
                NavigationItem(
                    title = "Urgent",
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Outlined.Info,
                    badgeCount = 45
                ),
                NavigationItem(
                    title = "Settings",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings,
                ),
            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background

            ) {
//                NavigationDrawerDemo(items)
                DashBoard()

            }

        }
    }
}

@Composable
fun DashBoard() {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MyGrey),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ConstraintLayout {
            val (redBox) = createRefs()

            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(70.dp)
                    .constrainAs(redBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }.background(color = MyRed)
            )

            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(start = 8.dp)
                        .weight(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "Zomato",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                }

                Image(painter = painterResource(R.drawable.frame_8), contentDescription = "person",
                    modifier = Modifier.height(50.dp).clickable { })

            }
        }

        /*var searchText by rememberSaveable() { mutableStateOf("") }-
        val isFocused = remember { mutableStateOf(false) }

        TextField(
            value = searchText, onValueChange = { searchText = it },
            label = { if (isFocused.value) null else Text(text = "Search restaurants...") },
            trailingIcon = {
                Image(
                    imageVector = Icons.Filled.Search, contentDescription = "Search",
                    modifier = Modifier.size(38.dp).padding(end = 8.dp)
                )
            },

            textStyle = TextStyle(
                color = if (searchText.isEmpty()) Color.Gray else Color.Black,
                fontSize = if (searchText.isEmpty()) 16.sp else 18.sp,

            ),
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedPlaceholderColor = Color.White,
                cursorColor = Color(0xFFCB202D),
            ),
            singleLine = true,
            maxLines = 1,

            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = true),
            modifier = Modifier.fillMaxWidth().height(70.dp)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .shadow(3.dp, shape = RoundedCornerShape(50.dp))
                .background(color = Color.White, CircleShape)
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                }
        )*/

        Spacer(modifier = Modifier.height(24.dp))
        MySearchableComponent()

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().height(150.dp)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .shadow(5.dp, shape = RoundedCornerShape(20.dp))
                .background(color = MyRed)

        ) {
            val (bannerImage, flatText, freeText, couponText) = createRefs()

            Image(
                modifier = Modifier.padding(start = 50.dp).constrainAs(bannerImage) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.value(130.dp)
                },
                contentDescription = "Banner Image",
                painter = painterResource(R.drawable.frame_8)
            )
            Text(text = "FLAT 50% OFF",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 32.dp).constrainAs(flatText) {
                    top.linkTo(parent.top)
                    end.linkTo(bannerImage.start)
                    start.linkTo(parent.start)
                })

            Text(text = "Free Deliver + 10% Cashback",
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(freeText) {
                    top.linkTo(flatText.bottom)
                    end.linkTo(flatText.end)
                    start.linkTo(flatText.start)
                })

            Text(text = "Coupon F00D50",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp).constrainAs(couponText) {
                    top.linkTo(freeText.bottom)
                    end.linkTo(freeText.end)
                    start.linkTo(freeText.start)
                })

        }
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val isTextEmpty = searchText.isEmpty()

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(it)
        },
        singleLine = true,
        maxLines = 1,
        placeholder = { Text("Search...") },
        trailingIcon = {
            if (!isTextEmpty) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Clear Text",
                    modifier = Modifier
                        .size(38.dp)
                        .padding(end = 8.dp)
                        .clickable {
                            searchText = ""
                            onSearch("")
                        }
                )
            } else {
                Image(
                    imageVector = Icons.Filled.Search, contentDescription = "Search Icon",
                    modifier = Modifier.size(38.dp).padding(end = 8.dp)
                )
            }
        },
        textStyle = TextStyle(
            color = if (!isTextEmpty) Color.Black else Color.Gray,
            fontSize = 18.sp,
        ),

        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedPlaceholderColor = Color.White,
            cursorColor = Color(0xFFCB202D),
            focusedIndicatorColor = Color.Transparent, // Hide the underline when focused
            unfocusedIndicatorColor = Color.Transparent // Hide the underline always
        ),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier.fillMaxWidth().height(56.dp)
            .padding(start = 24.dp, end = 24.dp)
            .shadow(3.dp, shape = RoundedCornerShape(50.dp))
            .background(color = Color.White, CircleShape),

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            autoCorrect = true
        ),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchText) // Handle the search action (e.g., querying a database or filtering a list)
            defaultKeyboardAction(ImeAction.Search)
        })
    )
}

@Composable
fun MySearchableComponent() {
    SearchBar { searchText ->
        // Here you would typically handle the search query, such as filtering a list
        Log.d(
            "MySearchableComponent",
            "Searching for: $searchText"
        ) // Placeholder for actual search logic
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerDemo(items: List<NavigationItem> = listOf()) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {

            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->

                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        selected = index == selectedItemIndex,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }

        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "TODO App")

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(Color.Cyan)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                LearnCostraintLayout()
            }

        }

    }
}

@Composable
fun LearnCostraintLayout() {

    ConstraintLayout {

        val (redButton, greenButton, blueButton, blackButton) = createRefs()

        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.constrainAs(redButton) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.matchParent

            }) {
            Text(text = "Red Button")
        }

        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Green),
            modifier = Modifier.constrainAs(greenButton) {
                top.linkTo(redButton.bottom)
                start.linkTo(parent.start)

                // end.linkTo(blueButton.start)

            }) {
            Text(text = "Green Button")
        }

        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier.constrainAs(blueButton) {
                top.linkTo(redButton.bottom)
                // start.linkTo(greenButton.end)
                end.linkTo(parent.end)

            }) {
            Text(text = "Blue Button")
        }

        Button(onClick = {}, colors = ButtonDefaults.buttonColors(MyRed),
            modifier = Modifier.constrainAs(blackButton) {

                top.linkTo(blueButton.bottom)
            }) {
            Text(text = "Black Button")
        }
    }
}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

