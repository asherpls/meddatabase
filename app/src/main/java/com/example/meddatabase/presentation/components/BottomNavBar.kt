package com.example.meddatabase.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.meddatabase.R
import com.example.meddatabase.presentation.navigation.NavScreen

@Composable
fun BottomNavBar(navController: NavController) {
    BottomNavigation(
        modifier = Modifier.semantics { contentDescription = "bottom_nav"},
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by
        navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        createListOfItems().forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon),
                    contentDescription = item.route) },
                label = { Text(text = item.route,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                                screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
@Composable
private fun createListOfItems(): List<NavScreen> {
    return listOf(
        NavScreen.Home,
        NavScreen.Stats,
        NavScreen.Exit
    )}
