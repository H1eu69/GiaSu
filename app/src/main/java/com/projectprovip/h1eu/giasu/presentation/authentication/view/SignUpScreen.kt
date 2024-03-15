package com.projectprovip.h1eu.giasu.presentation.authentication.view

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.auth0.android.jwt.JWT
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput
import com.projectprovip.h1eu.giasu.presentation.authentication.model.SignUpState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.Validate
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import kotlinx.coroutines.launch


@Preview
@Composable
fun PreviewSignUp() {
    SignUpScreen(
        rememberNavController(), { s1, s2, s3, s4, s5, s6 -> false }, { bundle -> }, SignUpState()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    validate: (String?, String?, String?, String?, String?, String?) -> Boolean,
    onRegisterClicked: (
        UserSignUpInput
    ) -> Unit,
    state: SignUpState
) {

    val interactionSource = remember { MutableInteractionSource() }

    val tokenKey = stringPreferencesKey(Constant.TOKEN_STRING)
    val usernameKey = stringPreferencesKey(Constant.USERNAME_STRING)
    val useridKey = stringPreferencesKey(Constant.USERID_STRING)
    val userImageKey = stringPreferencesKey(Constant.USER_IMAGE_STRING)

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    val firstNameText = remember {
        mutableStateOf("")
    }
    val lastNameText = remember {
        mutableStateOf("")
    }

    val emailText = remember {
        mutableStateOf("")
    }

    val passText = remember {
        mutableStateOf("")
    }
    val usernameText = remember {
        mutableStateOf("")
    }
    val phoneNumberText = remember {
        mutableStateOf("")
    }

    val birthYearText = remember {
        mutableStateOf("")
    }

    val cityText = remember {
        mutableStateOf("")
    }
    val pagerState = rememberPagerState(pageCount = {
        2
    })
    val fontFamily = FontFamily(
        Font(R.font.mont_bold, FontWeight.Bold),
        Font(R.font.mont_regular, FontWeight.Normal)
    )

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
                    preferences[userImageKey] = state.user!!.avatar

                    Log.d("Token in Sign up", state.token.toString())
                }
            }
        }
        navController.navigate(Screens.InApp.route) {
            popUpTo(Screens.Splash.route) {
                inclusive = true
            }
        }
    }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
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

            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                // Our page content
                if (page == 0) {
                    Phase1(
                        navController = navController,
                        value1 = firstNameText,
                        value2 = lastNameText,
                        value3 = emailText,
                        value4 = passText,
                        state = state,
                        validate = validate,
                        onDone = {
                            coroutine.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1,
                                    animationSpec = spring(
                                        stiffness = Spring.StiffnessVeryLow
                                    )
                                )
                            }
                        }
                    )
                } else {
                    Phase2(navController = navController,
                        state = state,
                        value1 = usernameText,
                        value2 = phoneNumberText,
                        value3 = birthYearText,
                        value4 = cityText,
                        validate = validate,
                        onDone = {
                            onRegisterClicked(
                                UserSignUpInput(
                                    firstNameText.value,
                                    lastNameText.value,
                                    emailText.value,
                                    passText.value,
                                    usernameText.value,
                                    phoneNumberText.value,
                                    birthYearText.value,
                                    cityText.value
                                )
                            )
                        })
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.popBackStack()
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

@Composable
fun Phase1(
    value1: MutableState<String>,
    value2: MutableState<String>,
    value3: MutableState<String>,
    value4: MutableState<String>,
    navController: NavController,
    state: SignUpState,
    validate: (String?, String?, String?, String?, String?, String?) -> Boolean,
    onDone: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFirstNameError = state.validate.name == Validate.FIRST_NAME_LENGTH.name
    val isLastNameError = state.validate.name == Validate.LAST_NAME_LENGTH.name
    val isEmailError = state.validate.name == Validate.EMAIL_FORMAT.name
    val isPasswordError = state.validate.name == Validate.PASSWORD.name

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        OutlinedTextField(
            value = value1.value,
            onValueChange = {
                value1.value = it
            },
            placeholder = {
                Text("First name", color = EDSColors.grayX2)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            isError = isFirstNameError,
            supportingText = {
                if (isFirstNameError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor,
                cursorColor = EDSColors.primaryColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = value2.value,
            onValueChange = {
                value2.value = it
            },
            placeholder = {
                Text("Last name", color = EDSColors.grayX2)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            isError = isLastNameError,
            supportingText = {
                if (isLastNameError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor, cursorColor = EDSColors.primaryColor

            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = value3.value,
            onValueChange = {
                value3.value = it
            },
            placeholder = {
                Text("Email", color = EDSColors.grayX2)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            isError = isEmailError,
            supportingText = {
                if (isEmailError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor, cursorColor = EDSColors.primaryColor

            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = value4.value,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                value4.value = it
            },
            placeholder = {
                Text("Password", color = EDSColors.grayX2)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            isError = isPasswordError,
            supportingText = {
                if (isPasswordError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor, cursorColor = EDSColors.primaryColor

            ),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validate(
                        value1.value,
                        value2.value,
                        value3.value,
                        value4.value,
                        null,
                        null
                    )
                ) {
                    onDone()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else
                Text(text = "Go next")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun Phase2(
    value1: MutableState<String>,
    value2: MutableState<String>,
    value3: MutableState<String>,
    value4: MutableState<String>,
    navController: NavController,
    state: SignUpState,
    validate: (String?, String?, String?, String?, String?, String?) -> Boolean,
    onDone: () -> Unit
) {
    val fontFamily = FontFamily(
        Font(R.font.mont_bold, FontWeight.Bold),
        Font(R.font.mont_regular, FontWeight.Normal)
    )
    val focusRequester = remember { FocusRequester() }

    val interactionSource = remember { MutableInteractionSource() }
    val isUsernameError = state.validate.name == Validate.USERNAME.name
    val isPhoneError = state.validate.name == Validate.PHONE.name

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        OutlinedTextField(
            value = value1.value,
            onValueChange = {
                value1.value = it
            },
            placeholder = {
                Text("Username", color = EDSColors.grayX2)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            isError = isUsernameError,
            supportingText = {
                if (isUsernameError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor, cursorColor = EDSColors.primaryColor

            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = value2.value,
            onValueChange = {
                value2.value = it
            },
            placeholder = {
                Text("Phone", color = EDSColors.grayX2)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            isError = isPhoneError,
            supportingText = {
                if (isPhoneError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor, cursorColor = EDSColors.primaryColor

            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = value3.value,
            onValueChange = {
                if (it.length <= 4)
                    value3.value = it
            },
            placeholder = {
                Text("Birth year", color = EDSColors.grayX2)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor, cursorColor = EDSColors.primaryColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = value4.value,
            onValueChange = {
                value4.value = it
            },
            placeholder = {
                Text("City", color = EDSColors.grayX2)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EDSColors.primaryColor,
                focusedLabelColor = EDSColors.primaryColor, cursorColor = EDSColors.primaryColor

            ),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validate(
                        null,
                        null,
                        null,
                        null,
                        value1.value,
                        value2.value
                    )
                )
                    onDone()
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

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}