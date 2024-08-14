package com.matinsamedani.zqshop.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.matinsamedani.zqshop.R
import com.matinsamedani.zqshop.ui.theme.ZQShopTheme
import com.matinsamedani.zqshop.utils.ZQShopContentType

@Composable
fun WelcomeScreen(contentType: ZQShopContentType, displayFeatures: List<DisplayFeature>) {
    if (contentType == ZQShopContentType.DUAL_PANE) {
        TwoPane(
            first = { /*TODO*/ },
            second = { /*TODO*/ },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = displayFeatures
        )
    } else {
        WelcomeSinglePaneContent()
    }
}

@Composable
fun WelcomeSinglePaneContent() {

    val animationDuration = 1000

    var showContent by remember { mutableStateOf(false) }

    val textAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )
    val buttonAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDuration / 2
        ), label = ""
    )

    LaunchedEffect(Unit) {
        showContent = true
    }

    Scaffold { innerPadding ->

        Image(
            painter = painterResource(id = R.drawable.welcome_bg),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .alpha(textAlpha),
                    text = "ZQ",
                    fontSize = 220.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.gugi_regular))
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .alpha(textAlpha),
                    text = "Shop",
                    fontSize = 60.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.gugi_regular))
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.BottomCenter)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(buttonAlpha),
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        Color.White
                    )
                ) {
                    Text(
                        text = "Sign up",
                        fontFamily = FontFamily(Font(R.font.vazirmatn_medium)),
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(buttonAlpha),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Sign in",
                        fontFamily = FontFamily(Font(R.font.vazirmatn_medium)),
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}