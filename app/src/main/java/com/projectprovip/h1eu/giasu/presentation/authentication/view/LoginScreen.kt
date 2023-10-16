package com.projectprovip.h1eu.giasu.presentation.authentication.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.AuthViewModel
import com.projectprovip.h1eu.giasu.presentation.common.composes.MainTextField
import com.projectprovip.h1eu.giasu.presentation.common.theme.primaryColor
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens

@Preview
@Composable
fun Preview() {
    LoginScreen(
        rememberNavController(),
        hiltViewModel()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController,
                vm: AuthViewModel) {
    val emailTextField = remember{
        mutableStateOf("")
    }

    val passTextField = remember{
        mutableStateOf("")
    }

    val state = vm.loginState.value

    val fontFamily = FontFamily(
        Font(R.font.mont_bold, FontWeight.Bold),
        Font(R.font.mont_regular, FontWeight.Normal)
    )

    val interactionSource = remember { MutableInteractionSource() }

    if(state.user.fullName.isNotBlank())
        navController.navigate(Screens.InApp.route)

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                Modifier.width(IntrinsicSize.Min)
            ) {
                Spacer(
                    modifier = Modifier.fillMaxHeight(.2f)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally),
                    text = "EduSmart",
                    style = TextStyle(
                        color = primaryColor,
                        fontSize = 24.sp,
                        fontFamily = fontFamily
                    ),
                    textAlign = TextAlign.Center,
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                MainTextField(
                    value = emailTextField.value,
                    label = "Email") {
                    emailTextField.value = it
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                MainTextField(
                    value = passTextField.value,
                    label = "Password") {
                    passTextField.value = it
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )

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

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = {
                        loginByEmail(vm, emailTextField.value, passTextField.value)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    if(state.isLoading) {
                        CircularProgressIndicator()
                    }
                    else {
                        Text(
                            text = "Login"
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate(Screens.InApp.route) {
                            popUpTo(Screens.Splash.route) {
                                inclusive = true
                            }
                        }
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

private fun loginByEmail(vm: AuthViewModel, email: String, password: String) {
    vm.loginByEmail(email, password)
}
