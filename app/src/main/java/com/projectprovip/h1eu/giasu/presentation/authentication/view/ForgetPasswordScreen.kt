package com.projectprovip.h1eu.giasu.presentation.authentication.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.MainTextField
import com.projectprovip.h1eu.giasu.presentation.common.theme.primaryColor


@Preview
@Composable
fun PreviewScreen() {
    ForgetPasswordScreen(navController = rememberNavController())
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(navController: NavController) {
    val emailTextField = remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize() ,
        topBar = {
        CenterAlignedTopAppBar(
            title = {
                AppBarTitle("Forget password?")
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Rounded.ArrowBack, "")
                }
            }
        )
    },
    content = { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                Text(
                    text = "Please fill in email to receive OTP",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                MainTextField(
                    value = emailTextField.value, label = "Email"
                ) {
                    emailTextField.value = it
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(text = "Send OTP")
                }
            }
        }
    })

}