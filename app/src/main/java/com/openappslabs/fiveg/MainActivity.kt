package com.openappslabs.fiveg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.openappslabs.fiveg.ui.navigation.AppNavGraph
import com.openappslabs.fiveg.ui.theme._5GTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5GTheme {
                AppNavGraph()
            }
        }
    }
}