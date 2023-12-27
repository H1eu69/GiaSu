package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseInput
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonRadioButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.MultiColorText
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.CreateCourseState
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.CreateClassViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateClassScreen(navController: NavController,
                      state: CreateCourseState,
                      onButtonClick: (CreateCourseInput) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state, ) {
        when {
            state.isLoading -> {

            }
            state.isSuccessful -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            state.message.isNotEmpty() -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.primaryColor
                ),
                title = {
                    Text(
                        text = "Class request",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
        containerColor = EDSColors.white
    ) {
        ClassRequestBody(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            onButtonClick = onButtonClick
        )
    }
}

@Composable
fun ClassRequestBody(modifier: Modifier = Modifier,
                     onButtonClick: (CreateCourseInput) -> Unit) {
    val title = remember {
        mutableStateOf("")
    }
    val fee = remember {
        mutableStateOf("0")
    }
    val chargeFee = remember {
        mutableStateOf("0")
    }
    val address = remember {
        mutableStateOf("")
    }
    val numOfStudent = remember {
        mutableStateOf("1")
    }
    val contactNumber = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val minutePerSession = remember {
        mutableStateOf("60")
    }
    val sessionPerWeek = remember {
        mutableStateOf("2")
    }
    val academicLevel = remember {
        mutableStateOf("")
    }
    val subject = remember {
        mutableStateOf("")
    }
    val genderOptions = listOf("Male", "Female", "Other")
    val (studentSelectedOptions, studentOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val (tutorSelectedOptions, tutorOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val learningModeOptions = listOf("Offline", "Online", "Hybrid")
    val (learningModeSelectedOptions, learningModeOnOptionSelected) = remember {
        mutableStateOf(learningModeOptions[0])
    }

    val localFocus = LocalFocusManager.current
    Box(modifier = modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = PaddingValues(bottom = 100.dp),
            modifier = Modifier
                .pointerInput("") {
                    detectTapGestures {
                        localFocus.clearFocus()

                    }
                }
//                .clickable(
//                interactionSource = remember {
//                MutableInteractionSource()
//            },
//                indication = null,
//                onClick = {
//                    localFocus.clearFocus()
//                })
        ) {
            item {
                MultiColorText(
                    text1 = "Class description",
                    color1 = EDSColors.myBlackColor,
                    text2 = "*",
                    color2 = EDSColors.notScheduleTextColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Title")
                    },
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        title.value = value
                    },
                    value = title.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Fee")
                    },
                    keyboardActions = KeyboardActions(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        fee.value = value
                    },
                    value = fee.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Charge Fee")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        chargeFee.value = value
                    },
                    value = chargeFee.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Number of students")
                    },
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { value ->
                        numOfStudent.value = value
                    },
                    value = numOfStudent.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Contact Number")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        contactNumber.value = value
                    },
                    value = contactNumber.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Subject Name")
                    },
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        subject.value = value
                    },
                    value = subject.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Location")
                    },
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        address.value = value
                    },
                    value = address.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }

//            item {
//                OutlinedTextField(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp),
//                    label = {
//                        androidx.compose.material3.Text(text = "Subject")
//                    },
//                    keyboardActions = KeyboardActions(),
//                    shape = RoundedCornerShape(12.dp),
//                    onValueChange = { value ->
//                        contactNumber.value = value
//                    },
//                    value = contactNumber.value,
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = EDSColors.primaryColor,
//                        focusedLabelColor = EDSColors.primaryColor,
//                        cursorColor = EDSColors.primaryColor,
//                    ),
//                )
//            }

            item {
                CommonRadioButton(
                    title = "Student Gender",
                    radioOptions = genderOptions,
                    selectedOption = studentSelectedOptions,
                    onOptionSelected = studentOnOptionSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .background(Color.White)
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Description")
                    },
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        description.value = value
                    },
                    value = description.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }

            item {
                CommonRadioButton(
                    title = "Learning Mode",
                    radioOptions = learningModeOptions,
                    selectedOption = learningModeSelectedOptions,
                    onOptionSelected = learningModeOnOptionSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .background(Color.White)
                )
            }

            item {
                MultiColorText(
                    text1 = "Tutor requirement",
                    color1 = EDSColors.myBlackColor,
                    text2 = "*",
                    color2 = EDSColors.notScheduleTextColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }

            item {
                CommonRadioButton(
                    title = "Tutor Gender",
                    radioOptions = genderOptions,
                    selectedOption = tutorSelectedOptions,
                    onOptionSelected = tutorOnOptionSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .background(Color.White)
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Minute per session")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        minutePerSession.value = value
                    },
                    value = minutePerSession.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }


            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Session per week")
                    },
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        sessionPerWeek.value = value
                    },
                    value = sessionPerWeek.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    label = {
                        androidx.compose.material3.Text(text = "Academic Level")
                    },
                    keyboardActions = KeyboardActions(),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { value ->
                        academicLevel.value = value
                    },
                    value = academicLevel.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLabelColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor,
                    ),
                )
            }
        }
        EduSmartButton(
            onClick = {
                onButtonClick(
                    CreateCourseInput(
                        title = title.value,
                        fee = fee.value.toInt(),
                        chargeFee = chargeFee.value.toInt(),
                        numberOfLearner = numOfStudent.value.toInt(),
                        contactNumber = contactNumber.value,
                        subjectName = subject.value,
                        address = address.value,
                        academicLevel = academicLevel.value,
                        sessionPerWeek = sessionPerWeek.value.toInt(),
                        minutePerSession = minutePerSession.value.toInt(),
                        description = description.value,
                        learningMode = learningModeSelectedOptions,
                        learnerGender = studentSelectedOptions,
                        genderRequirement = tutorSelectedOptions
                    )
                )
            },
            text = "Request",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }
}