package com.koGen.kogen_navigation_demo.startScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.koGen.kogen_navigation_demo.navigation.ActionToSecond
import com.koGen.kogen_navigation_demo.navigation.navigateSafety
import kz.evko.navigation.annotation.KoGenScreen

@KoGenScreen(startDestination = true)
@Composable
fun StartScreen(
    navController: NavHostController,
) {
    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "This is start screen",
                color = Color.DarkGray,
            )
            Button(
                onClick = {
                    navController.navigateSafety(ActionToSecond(name = "Eugene"))
                }
            ) {
                Text(text = "Navigate to second screen")
            }
        }
    }
}