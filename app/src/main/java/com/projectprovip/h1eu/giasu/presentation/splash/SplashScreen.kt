package com.projectprovip.h1eu.giasu.presentation.splash

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {
    val fontFamily = FontFamily(
        Font(R.font.mont_bold, FontWeight.Bold),
        Font(R.font.mont_regular, FontWeight.Normal)
    )

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val tokenKey = stringPreferencesKey(Constant.TOKEN_STRING)
    var tokenValue: String? = null


    LaunchedEffect(key1 = "") {
        coroutine.launch {
            //Check for token if already login before
            async {
                context.dataStore.data
                    .collect { preferences ->
                        tokenValue = preferences[tokenKey]
                        Log.d("Token in Splash", tokenValue.toString())
                    }
            }
            delay(3000)
            if (tokenValue != null) {
                navController.navigate(Screens.InApp.route) {
                    popUpTo(Screens.Splash.route) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Screens.Authentication.route)
            }
        }
    }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "EduSmart",
                style = TextStyle(
                    Color(95, 207, 128, 255),
                    fontSize = 30.sp,
                    fontFamily = fontFamily
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}