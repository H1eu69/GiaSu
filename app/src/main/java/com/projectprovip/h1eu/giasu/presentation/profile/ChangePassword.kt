package com.projectprovip.h1eu.giasu.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun PreviewChangePassword() {
    ChangePasswordScreen(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            null)
                    }
                },
                title = {
                    Text(
                        text = "Change password",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var textField1 = remember { mutableStateOf("") }
            var textField2 = remember { mutableStateOf("") }
            var textField3 = remember { mutableStateOf("") }

//            MainTextField(
//                value = textField1.value, label = "Old password",
//                onValueChange = {
//                    textField1.value = it
//                },
//                modifier = Modifier.fillMaxWidth().padding(16.dp)
//            )
//            MainTextField(
//                value = textField2.value, label = "New password",
//                onValueChange = {
//                    textField2.value = it
//                },
//                modifier = Modifier.fillMaxWidth().padding(16.dp)
//            )
//            MainTextField(
//                value = textField3.value, label = "Confirm password",
//                onValueChange = {
//                    textField3.value = it
//                },
//                modifier = Modifier.fillMaxWidth().padding(16.dp)
//            )

        }
    }
}