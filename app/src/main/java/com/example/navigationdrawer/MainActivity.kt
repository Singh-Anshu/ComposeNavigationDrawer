package com.example.navigationdrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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


                NavigationDrawerDemo(items)
            }

        }
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

        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Black),
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

