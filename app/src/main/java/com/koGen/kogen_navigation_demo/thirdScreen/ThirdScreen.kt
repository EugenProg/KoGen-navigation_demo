package com.koGen.kogen_navigation_demo.thirdScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.koGen.kogen_navigation_demo.NavigationResultValues
import com.koGen.kogen_navigation_demo.navigation.ActionToForth
import com.koGen.kogen_navigation_demo.navigation.getResultData
import com.koGen.kogen_navigation_demo.navigation.navigateSafety
import kz.evko.navigation.annotation.KoGenScreen

@KoGenScreen
@Composable
fun ThirdScreen(
    navController: NavHostController,
    screenColor: Color,
    textColor: Color,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (navController.getResultData(NavigationResultValues.ShowToast) == true) {
            Toast.makeText(context, "It's a toast from nav result", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        containerColor = screenColor,
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
                text = "Here is a color parameter",
                color = textColor,
            )
            Button(
                onClick = {
                    navController.navigateSafety(ActionToForth(nullableParam = "now it's not null"))
                }
            ) {
                Text(text = "Navigate to forth screen with not null")
            }

            Button(
                onClick = {
                    navController.navigateSafety(ActionToForth())
                }
            ) {
                Text(text = "Navigate to forth screen with null")
            }
        }
    }
}