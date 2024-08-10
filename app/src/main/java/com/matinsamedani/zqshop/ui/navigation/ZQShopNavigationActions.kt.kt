package com.matinsamedani.zqshop.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.matinsamedani.zqshop.R

object ZQShopRoute {
    //Welcome screen
    const val WELCOME = "Welcome"

    //Register user screens
    const val SIGN_UP = "SignUp"
    const val SIGN_IN = "SignIn"

    //Main screens
    const val HOME = "Home"
    const val EXPLORE = "Explore"
    const val PROFILE = "Profile"
    const val CART = "Cart"
    const val CATEGORY = "Category"
    const val PRODUCT = "Product"
}

data class ZQShopTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class ZQShopNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: ZQShopTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    ZQShopTopLevelDestination(
        route = ZQShopRoute.HOME,
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Default.Home,
        iconTextId = R.string.tab_home
    ),
    ZQShopTopLevelDestination(
        route = ZQShopRoute.EXPLORE,
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Default.Search,
        iconTextId = R.string.tab_explore
    ),
    ZQShopTopLevelDestination(
        route = ZQShopRoute.PROFILE,
        selectedIcon = Icons.Outlined.Person,
        unselectedIcon = Icons.Outlined.Person,
        iconTextId = R.string.tab_profile
    )
)