package com.shahram.zqshop.ui.features.signUp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.shahram.zqshop.ui.features.welcome.WelcomeFirstPaneContent
import com.shahram.zqshop.ui.features.welcome.WelcomeSecondPaneContent
import com.shahram.zqshop.ui.features.welcome.WelcomeSinglePaneContent
import com.shahram.zqshop.utils.ZQShopContentType

@Composable
fun SignUpScreen(
    navController: NavHostController,
    contentType: ZQShopContentType,
    displayFeatures: List<DisplayFeature>
) {
    Scaffold { innerPadding ->
        if (contentType == ZQShopContentType.DUAL_PANE) {
            TwoPane(
                first = { },
                second = { },
                strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
                displayFeatures = displayFeatures
            )
        } else {
            SignUpSinglePaneContent(innerPadding, navController)
        }
    }
}

@Composable
fun SignUpSinglePaneContent(innerPadding: PaddingValues, navController: NavHostController) {

}