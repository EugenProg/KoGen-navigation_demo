package com.koGen.kogen_navigation_demo.forthScreen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.koGen.kogen_navigation_demo.NavigationResultValues
import com.koGen.kogen_navigation_demo.navigation.popBackSafety
import kz.evko.navigation.annotation.KoGenScreen
import kz.evko.navigation.helpers.BackStackData

@KoGenScreen
@Composable
fun ForthScreen(
    navController: NavHostController,
    nullableParam: String? = null,
) {
    Scaffold(
        containerColor = Color.LightGray,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Here is a nullable parameter. It's value is $nullableParam",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    navController.popBackSafety(
                        BackStackData(NavigationResultValues.ShowToast, false)
                    )
                }
            ) {
                Text(text = "Go back without params")
            }
            Button(
                onClick = {
                    navController.popBackSafety(
                        BackStackData(NavigationResultValues.ShowToast, true)
                    )
                }
            ) {
                Text(text = "Go back and show a toast")
            }
        }
    }
}