package com.projectprovip.h1eu.giasu.presentation.authentication.view

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.auth0.android.jwt.JWT
import com.chargemap.compose.numberpicker.NumberPicker
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput
import com.projectprovip.h1eu.giasu.presentation.authentication.model.ProvinceItem
import com.projectprovip.h1eu.giasu.presentation.authentication.model.ProvinceState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.SignUpState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.Validate
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import kotlinx.coroutines.launch


@Preview
@Composable
fun PreviewSignUp() {
    SignUpScreen(
        rememberNavController(),
        { s1, s2, s3, s4, s5, s6, s7, s8 -> false },
        { bundle -> },
        SignUpState(),
        ProvinceState()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    validate: (String?, String?, String?, String?, String?, String?, String?, String?) -> Boolean,
    onRegisterClicked: (
        UserSignUpInput
    ) -> Unit,
    signUpState: SignUpState,
    provinceState: ProvinceState
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

    if (signUpState.user != null) {
        LaunchedEffect(key1 = "") {
            coroutine.launch {
                val jwt = JWT(signUpState.token!!)

                val isExpired = jwt.isExpired(0) // Do time validation with 10 seconds leeway
                Log.d("isExpired", isExpired.toString())
                context.dataStore.edit { preferences ->
                    preferences[tokenKey] = signUpState.token.toString()
                    preferences[usernameKey] = signUpState.user!!.fullName
                    preferences[useridKey] = signUpState.user!!.id.toString()
                    preferences[userImageKey] = signUpState.user!!.avatar

                    Log.d("Token in Sign up", signUpState.token.toString())
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
        ) {
            Spacer(modifier = Modifier.weight(1f))

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
                        state = signUpState,
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
                        state = signUpState,
                        provinceState = provinceState,
                        userNameText = usernameText,
                        phoneText = phoneNumberText,
                        birthYearText = birthYearText,
                        cityText = cityText,
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
    validate: (String?, String?, String?, String?, String?, String?, String?, String?) -> Boolean,
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
                        null,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Phase2(
    userNameText: MutableState<String>,
    phoneText: MutableState<String>,
    birthYearText: MutableState<String>,
    cityText: MutableState<String>,
    navController: NavController,
    state: SignUpState,
    provinceState: ProvinceState,
    validate: (String?, String?, String?, String?, String?, String?, String?, String?) -> Boolean,
    onDone: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    val interactionSource = remember { MutableInteractionSource() }
    val isUsernameError = state.validate.name == Validate.USERNAME.name
    val isPhoneError = state.validate.name == Validate.PHONE.name
    val isBirthYearError = state.validate.name == Validate.BIRTH_YEAR.name
    val isCityError = state.validate.name == Validate.CITY.name

    val openProvinceDialog = remember { mutableStateOf(false) }
    val openDatePicker = remember { mutableStateOf(false) }
    val initValue = remember {
        mutableStateOf(2024)
    }

    if (openProvinceDialog.value) {
        val list = remember {
            mutableStateOf(provinceState.province)
        }
        ProvinceDialog(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            list,
            onDisMiss = {
                openProvinceDialog.value = false
            },
            onConfirm = {
                cityText.value = it
                openProvinceDialog.value = false
            })
    }
    if (openDatePicker.value) {
        NumberPickerDialog(
            initValue = initValue,
            onDisMiss = {
                openDatePicker.value = false
            },
            onConfirm = {
                birthYearText.value = it
                initValue.value = it.toInt()
                openDatePicker.value = false
            }
        )
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        OutlinedTextField(
            value = userNameText.value,
            onValueChange = {
                userNameText.value = it
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
            value = phoneText.value,
            onValueChange = {
                phoneText.value = it
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
            value = birthYearText.value,
            onValueChange = {
                if (it.length <= 4)
                    birthYearText.value = it
            },
            placeholder = {
                Text("Birth year", color = EDSColors.grayX2)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            enabled = false,
            isError = isBirthYearError,
            supportingText = {
                if (isBirthYearError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = EDSColors.gray,
                disabledContainerColor = EDSColors.transparent,
                disabledTextColor = EDSColors.blackColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openDatePicker.value = true
                }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = cityText.value,
            onValueChange = {
                cityText.value = it
            },
            placeholder = {
                Text("City", color = EDSColors.grayX2)
            },
            shape = RoundedCornerShape(8.dp),
            isError = isCityError,
            supportingText = {
                if (isCityError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.validate.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = EDSColors.gray,
                disabledContainerColor = EDSColors.transparent,
                disabledTextColor = EDSColors.blackColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openProvinceDialog.value = true
                }
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
                        userNameText.value,
                        phoneText.value,
                        birthYearText.value,
                        cityText.value
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvinceDialog(
    modifier: Modifier = Modifier,
    listProvince: MutableState<List<ProvinceItem>>,
    onDisMiss: () -> Unit = {},
    onConfirm: (String) -> Unit = {}
) {
    val searchText = remember { mutableStateOf("") }
    var selectedProvince = remember { mutableStateOf(listProvince.value[0]) }
    Log.d("Test select", selectedProvince.toString())

    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = "Province")
        },
        containerColor = EDSColors.white,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(30),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = EDSColors.primaryColor,
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLeadingIconColor = EDSColors.primaryColor,
                    ),
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    listProvince.value.forEachIndexed { index, province ->
                        item {
                            FilterChip(
                                modifier = Modifier.fillMaxWidth(),
                                selected = province.isSelected,
                                onClick = {
                                    val newList = listProvince.value.toMutableList()
                                    newList.forEach {
                                        it.isSelected = false
                                    }
                                    newList[index] =
                                        province.copy(isSelected = !province.isSelected)
                                    selectedProvince.value = newList[index]
                                    Log.d("Test select", selectedProvince.toString())
                                    listProvince.value = newList
                                },
                                label = {
                                    Text(
                                        province.provinceName, fontWeight = FontWeight.W300,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .fillMaxWidth(.85f)
                                    )
                                },
                                trailingIcon = {
                                    if (province.isSelected)
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null
                                        )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EDSColors.greenCheck,
                                    selectedLabelColor = EDSColors.white,
                                    selectedTrailingIconColor = EDSColors.white
                                )
                            )
                        }
                    }
                }
            }
        },

        onDismissRequest = {
            onDisMiss()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    selectedProvince.value.provinceName
                )
            }) {
                Text("Accept")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDisMiss()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun NumberPickerDialog(
    modifier: Modifier = Modifier,
    initValue: MutableState<Int>,
    onDisMiss: () -> Unit = {},
    onConfirm: (String) -> Unit = {}
) {

    AlertDialog(
        modifier = Modifier.padding(16.dp),
        onDismissRequest = {
            onDisMiss()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(initValue.value.toString())
            }) {
                Text("Accept")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDisMiss()
                }
            ) {
                Text("Cancel")
            }
        },
        title = {
            Text("Your birth year")
        },
        text = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                NumberPicker(
                    value = initValue.value,
                    range = 1960..2024,
                    onValueChange = {
                        initValue.value = it
                    }
                )
            }
        }
    )
}