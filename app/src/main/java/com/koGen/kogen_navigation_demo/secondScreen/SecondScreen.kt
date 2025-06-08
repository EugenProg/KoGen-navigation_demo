package com.koGen.kogen_navigation_demo.secondScreen

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
import com.koGen.kogen_navigation_demo.navigation.ActionToThird
import com.koGen.kogen_navigation_demo.navigation.navigateSafety
import kz.evko.navigation.annotation.KoGenScreen

@KoGenScreen
@Composable
fun SecondScreen(
    navController: NavHostController,
    name: String,
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
                text = "Hi, my name is $name",
                color = Color.DarkGray,
            )
            Button(
                onClick = {
                    navController.navigateSafety(ActionToThird(screenColor = Color.LightGray, textColor = Color.Black))
                }
            ) {
                Text(text = "Navigate to third screen")
            }
        }
    }
}