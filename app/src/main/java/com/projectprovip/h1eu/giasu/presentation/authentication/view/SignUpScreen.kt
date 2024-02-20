package com.projectprovip.h1eu.giasu.presentation.authentication.view

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.auth0.android.jwt.JWT
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.authentication.model.SignUpState
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import kotlinx.coroutines.launch


@Preview
@Composable
fun PreviewSignUp() {
    SignUpScreen(
        {}, {}, {}, {firstName, lastName, email, pwd ->  }, SignUpState()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigateInApp: () -> Unit,
    navigateForgotPassword: () -> Unit,
    pop: () -> Unit,
    buttonClick: (
        firstName: String, lastName: String, email: String, pwd: String
    ) -> Unit,
    state: SignUpState
) {
    val fontFamily = FontFamily(
        Font(R.font.mont_bold, FontWeight.Bold),
        Font(R.font.mont_regular, FontWeight.Normal)
    )

    val firstNameTextField = remember {
        mutableStateOf("")
    }
    val lastNameTextField = remember {
        mutableStateOf("")
    }

    val emailTextField = remember {
        mutableStateOf("")
    }

    val passTextField = remember {
        mutableStateOf("")
    }
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
                val jwt = JWT(state.token!!)

                val isExpired = jwt.isExpired(0) // Do time validation with 10 seconds leeway
                Log.d("isExpired", isExpired.toString())
                context.dataStore.edit { preferences ->
                    preferences[tokenKey] = state.token.toString()
                    preferences[usernameKey] = state.user!!.fullName
                    preferences[useridKey] = state.user!!.id.toString()
                    preferences[userImageKey] = state.user!!.image

                    Log.d("Token in Sign up", state.token.toString())
                }
            }
        }
        navigateInApp()
    }

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
                        color = EDSColors.primaryColor,
                        fontSize = 24.sp,
                        fontFamily = fontFamily
                    ),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = firstNameTextField.value,
                    onValueChange = {
                        firstNameTextField.value = it
                    },
                    label = {
                        Text("First name")
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
                    modifier = Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = lastNameTextField.value,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        lastNameTextField.value = it
                    },
                    label = {
                        Text("Last name")
                    },
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor
                    ),
                    modifier = Modifier
                )


                OutlinedTextField(
                    value = emailTextField.value,
                    onValueChange = {
                        emailTextField.value = it
                    },
                    label = {
                        Text("Email")
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
                    modifier = Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = passTextField.value,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        passTextField.value = it
                    },
                    label = {
                        Text("Password")
                    },
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor
                    ),
                    modifier = Modifier
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navigateForgotPassword()
                        },
                    text = "Forget password?",
                    style = TextStyle(
                        color = EDSColors.primaryColor,
                        fontSize = 12.sp,
                    ),
                    textAlign = TextAlign.End,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        buttonClick(
                            firstNameTextField.value,
                            lastNameTextField.value,
                            emailTextField.value,
                            passTextField.value
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else
                        Text(text = "Register")
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
                        pop()
                    },
                text = "Already have account? Login here",
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