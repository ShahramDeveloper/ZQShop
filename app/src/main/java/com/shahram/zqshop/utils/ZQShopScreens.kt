package com.shahram.zqshop.utils

sealed class ZQShopScreens(val route: String) {
    //Welcome screen
    data object Welcome : ZQShopScreens("Welcome")

    //Login screens
    data object SignUpScreen : ZQShopScreens("SignUpScreen")
    data object SignInScreen : ZQShopScreens("SignInScreen")

    //Main screens
    data object HomeScreen : ZQShopScreens("HomeScreen")
    data object ExploreScreen : ZQShopScreens("ExploreScreen")
    data object ProfileScreen : ZQShopScreens("ProfileScreen")
    data object CartScreen : ZQShopScreens("CartScreen")
    data object CategoryScreen : ZQShopScreens("CategoryScreen")
    data object ProductScreen : ZQShopScreens("ProductScreen")
}
