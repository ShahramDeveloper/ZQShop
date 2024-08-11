package com.matinsamedani.zqshop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.matinsamedani.zqshop.ui.features.welcome.WelcomeScreen
import com.matinsamedani.zqshop.ui.navigation.ModalNavigationDrawerContent
import com.matinsamedani.zqshop.ui.navigation.PermanentNavigationDrawerContent
import com.matinsamedani.zqshop.ui.navigation.ZQShopBottomNavigationBar
import com.matinsamedani.zqshop.ui.navigation.ZQShopNavigationActions
import com.matinsamedani.zqshop.ui.navigation.ZQShopNavigationRail
import com.matinsamedani.zqshop.ui.navigation.ZQShopRoute
import com.matinsamedani.zqshop.ui.navigation.ZQShopTopLevelDestination
import com.matinsamedani.zqshop.utils.DevicePosture
import com.matinsamedani.zqshop.utils.ZQShopContentType
import com.matinsamedani.zqshop.utils.ZQShopNavigationContentPosition
import com.matinsamedani.zqshop.utils.ZQShopNavigationType
import com.matinsamedani.zqshop.utils.isBookPosture
import com.matinsamedani.zqshop.utils.isSeparating
import dev.burnoo.cokoin.navigation.KoinNavHost
import kotlinx.coroutines.launch

@Composable
fun ZQShopApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>
) {

    val navigationType: ZQShopNavigationType
    val contentType: ZQShopContentType

    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = ZQShopNavigationType.BOTTOM_NAVIGATION
            contentType = ZQShopContentType.SINGLE_PANE
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = ZQShopNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ZQShopContentType.DUAL_PANE
            } else {
                ZQShopContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                ZQShopNavigationType.NAVIGATION_RAIL
            } else {
                ZQShopNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ZQShopContentType.DUAL_PANE
        }

        else -> {
            navigationType = ZQShopNavigationType.BOTTOM_NAVIGATION
            contentType = ZQShopContentType.SINGLE_PANE
        }
    }

    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            ZQShopNavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            ZQShopNavigationContentPosition.CENTER
        }

        else -> {
            ZQShopNavigationContentPosition.TOP
        }
    }
    ZQShopNavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition,
    )
}

@Composable
fun ZQShopNavigationWrapper(
    navigationType: ZQShopNavigationType,
    contentType: ZQShopContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: ZQShopNavigationContentPosition,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        ZQShopNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: ZQShopRoute.HOME

    // Determine whether to show navigation components
    val showNavigationComponents = selectedDestination !in listOf(
        ZQShopRoute.WELCOME,
        ZQShopRoute.SIGN_UP,
        ZQShopRoute.SIGN_IN
    )

    if (showNavigationComponents) {
        if (navigationType == ZQShopNavigationType.PERMANENT_NAVIGATION_DRAWER) {
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentNavigationDrawerContent(
                        selectedDestination = selectedDestination,
                        navigationContentPosition = navigationContentPosition,
                        navigateToTopLevelDestination = navigationActions::navigateTo,
                    )
                }
            ) {
                ZQShopAppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                )
            }
        } else {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalNavigationDrawerContent(
                        selectedDestination = selectedDestination,
                        navigationContentPosition = navigationContentPosition,
                        navigateToTopLevelDestination = navigationActions::navigateTo,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                },
                drawerState = drawerState
            ) {
                ZQShopAppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                ) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        }
    } else {
        ZQShopAppContent(
            navigationType = ZQShopNavigationType.NULL,
            contentType = contentType,
            displayFeatures = displayFeatures,
            navigationContentPosition = navigationContentPosition,
            navController = navController,
            selectedDestination = selectedDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo,
        )
    }
}

@Composable
fun ZQShopAppContent(
    modifier: Modifier = Modifier,
    navigationType: ZQShopNavigationType,
    contentType: ZQShopContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: ZQShopNavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (ZQShopTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    if (navigationType != ZQShopNavigationType.NULL) {
        Row(modifier = modifier.fillMaxSize()) {
            AnimatedVisibility(visible = navigationType == ZQShopNavigationType.NAVIGATION_RAIL) {
                ZQShopNavigationRail(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigateToTopLevelDestination,
                    onDrawerClicked = onDrawerClicked,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                ZQShopNavHost(
                    navController = navController,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationType = navigationType,
                    modifier = Modifier.weight(1f),
                )
                AnimatedVisibility(visible = navigationType == ZQShopNavigationType.BOTTOM_NAVIGATION) {
                    ZQShopBottomNavigationBar(
                        selectedDestination = selectedDestination,
                        navigateToTopLevelDestination = navigateToTopLevelDestination
                    )
                }
            }
        }
    } else {
        ZQShopNavHost(
            navController = navController,
            contentType = contentType,
            displayFeatures = displayFeatures,
            navigationType = navigationType,
        )
    }
}

@Composable
private fun ZQShopNavHost(
    navController: NavHostController,
    contentType: ZQShopContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: ZQShopNavigationType,
    modifier: Modifier = Modifier,
) {
    KoinNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ZQShopRoute.WELCOME,
    ) {
        composable(ZQShopRoute.WELCOME) {
            WelcomeScreen(
                contentType = contentType,
                displayFeatures = displayFeatures,
            )
        }
        composable(ZQShopRoute.SIGN_UP) {

        }
        composable(ZQShopRoute.SIGN_IN) {

        }
        composable(ZQShopRoute.HOME) {

        }
        composable(ZQShopRoute.EXPLORE) {

        }
        composable(ZQShopRoute.PROFILE) {

        }
        composable(ZQShopRoute.CART) {

        }
        composable(ZQShopRoute.CATEGORY) {

        }
        composable(ZQShopRoute.PRODUCT) {

        }
    }
}
