package com.projectprovip.h1eu.giasu.presentation.authentication.view

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.authentication.model.AuthState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.LoginState
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import kotlinx.coroutines.launch

@Preview
@Composable
fun Preview() {
    LoginScreen(
        rememberNavController(),
        { s1, s2 -> {} },
        state = LoginState()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    onLoginClick: (String, String) -> Unit,
    state: LoginState
) {
    val emailTextField = remember {
        mutableStateOf("")
    }

    val passTextField = remember {
        mutableStateOf("")
    }
    val fontFamily = FontFamily(
        Font(R.font.mont_bold, FontWeight.Bold),
        Font(R.font.mont_regular, FontWeight.Normal)
    )
    val interactionSource = remember { MutableInteractionSource() }
    val tokenKey = stringPreferencesKey(Constant.TOKEN_STRING)
    val usernameKey = stringPreferencesKey(Constant.USERNAME_STRING)
    val useridKey = stringPreferencesKey(Constant.USERID_STRING)
    val userImageKey = stringPreferencesKey(Constant.USER_IMAGE_STRING)

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    if (state.user != null) {
        LaunchedEffect(key1 = "") {
            coroutine.launch {
                context.dataStore.edit { preferences ->
                    preferences[tokenKey] = state.token.toString()
                    preferences[usernameKey] = state.user.fullName
                    preferences[useridKey] = state.user.id.toString()
                    preferences[userImageKey] = state.user.image
                }
                navController.navigate(Screens.InApp.route) {
                    popUpTo(Screens.Authentication.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    val isError =
        (state.auth.name == AuthState.WRONG_EMAIL_FORMAT.name) || (state.auth.name == AuthState.ACCOUNT_NOT_FOUND.name)

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
                        color = EDSColors.primaryColor,
                        fontSize = 24.sp,
                        fontFamily = fontFamily
                    ),
                    textAlign = TextAlign.Center,
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = emailTextField.value,
                    onValueChange = {
                        emailTextField.value = it
                    },
                    placeholder = {
                        Text("Email", color = EDSColors.lightGray)
                    },
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.auth.text!!,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor
                    ),
                    modifier = Modifier
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = passTextField.value,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        passTextField.value = it
                    },
                    placeholder = {
                        Text("Password", color = EDSColors.lightGray)
                    },
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor
                    ),
                    modifier = Modifier
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(Screens.Authentication.ForgetPassword.route)
                        },
                    text = "Forget password?",
                    style = TextStyle(
                        color = EDSColors.primaryColor,
                        fontSize = 12.sp,
                    ),
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = {
                        onLoginClick(emailTextField.value, passTextField.value)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
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
                        navController.navigate(Screens.Authentication.Signup.route)
                    },
                text = "Register new account",
                style = TextStyle(
                    color = EDSColors.primaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}
