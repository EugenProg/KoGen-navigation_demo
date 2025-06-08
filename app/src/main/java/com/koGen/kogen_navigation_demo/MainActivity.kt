package com.koGen.kogen_navigation_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.koGen.kogen_navigation_demo.navigation.AppNavHost
import com.koGen.kogen_navigation_demo.ui.theme.KoGennavigationDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoGennavigationDemoTheme {
                AppNavHost(
                    navController = rememberNavController(),
                )
            }
        }
    }
}