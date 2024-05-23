package com.projectprovip.h1eu.giasu.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Navigation
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.common.theme.GiaSuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                EDSColors.white.value.toInt(),
                EDSColors.white.value.toInt()
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            GiaSuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                            return@OnCompleteListener
                        }

                        // Get new FCM registration token
                        val token = task.result

                        // Log and toast
                        Log.d(TAG, token)
                    })
                    val bundle = intent?.extras
                    var skipSplash = false
                    if (bundle != null) {
                        // Handle other key-value pairs as needed
                        skipSplash = bundle.getBoolean("skipSplash")
                    }
                    Log.d("MainActivity", "skipSplash: $skipSplash")

                    Navigation(skipSplash)
                }
            }
        }
    }
}
