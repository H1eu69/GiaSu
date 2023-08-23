package com.projectprovip.h1eu.giasu.view.screens.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.ui.composes.MainTextField
import com.projectprovip.h1eu.giasu.ui.theme.primaryColor
import com.projectprovip.h1eu.giasu.view.navigation.NavControllerProvider
import com.projectprovip.h1eu.giasu.view.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen(@PreviewParameter(NavControllerProvider::class)
                navController: NavController = rememberNavController()) {
    val fontFamily = FontFamily(
        Font(R.font.mont_bold, FontWeight.Bold),
        Font(R.font.mont_regular, FontWeight.Normal)
    )
    val emailTextField = remember{
        mutableStateOf("")
    }

    val passTextField = remember{
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(Modifier.width(IntrinsicSize.Min)) {
                Spacer(modifier = Modifier.height(200.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "EduSmart",
                    style = TextStyle(
                        color = primaryColor,
                        fontSize = 24.sp,
                        fontFamily = fontFamily
                    ),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                MainTextField(value = emailTextField.value, label = "Email", onValueChange = {
                    emailTextField.value = it
                })

                Spacer(modifier = Modifier.height(12.dp))

                MainTextField(value = passTextField.value, label = "Password", onValueChange = {
                    passTextField.value = it
                })
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(Screens.Authentication.ForgetPassword.route)
                        },
                    text = "Forget password?",
                    style = TextStyle(
                        color = primaryColor,
                        fontSize = 12.sp,
                    ),
                    textAlign = TextAlign.End,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(text = "Login")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate(Screens.Authentication.Signup.route)
                    },
                text = "Register new account",
                style = TextStyle(
                    color = primaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
            )

        }
    }
}