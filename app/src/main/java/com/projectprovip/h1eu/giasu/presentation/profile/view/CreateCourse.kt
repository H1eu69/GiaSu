package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.style.TextOverflow
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
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectChip

@Preview
@Composable
fun CreateClassScreenPreview() {
    CreateClassScreen(rememberNavController(), CreateCourseState(), {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateClassScreen(
    navController: NavController,
    state: CreateCourseState,
    onButtonClick: (CreateCourseInput) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state) {
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
                        text = "Create course",
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
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            ClassRequestBody(

                onButtonClick = onButtonClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassRequestBody(
    modifier: Modifier = Modifier,
    onButtonClick: (CreateCourseInput) -> Unit
) {
    val title = remember {
        mutableStateOf("")
    }
    val fee = remember {
        mutableStateOf("")
    }
    val chargeFee = remember {
        mutableStateOf("")
    }
    val address = remember {
        mutableStateOf("")
    }
    val numOfStudent = remember {
        mutableStateOf("")
    }
    val contactNumber = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val minutePerSession = remember {
        mutableStateOf("")
    }
    val sessionPerWeek = remember {
        mutableStateOf("")
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
    val openEditSubjectDialog = remember { mutableStateOf(false) }


    val localFocus = LocalFocusManager.current
    Box(modifier = modifier) {
        if (openEditSubjectDialog.value) {
            val dummySubjects = remember {
                mutableStateOf(
                    listOf(
                        SubjectChip("Java"),
                        SubjectChip("C++"),
                        SubjectChip("Python"),
                        SubjectChip("Piano"),
                        SubjectChip("Thu dao"),
                        SubjectChip("English Advanced"),
                        SubjectChip("Toan cao cap"),
                        SubjectChip("1235 anh danh roi so 4"),
                        SubjectChip("You just want attention"),
                        SubjectChip("You dont want my heart"),
                        SubjectChip("Ngay co ay di theo chan me cha"),
                        SubjectChip("1 2 3 zo"),
                        SubjectChip(),
                        SubjectChip(),
                        SubjectChip(),
                    )

                )
            }
            PickSubjectDialog(dummySubjects, onDisMiss = {
                openEditSubjectDialog.value = false
            },
                onConfirm = {
                    subject.value = it
                    openEditSubjectDialog.value = false

                })
        }

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
        ) {
            item {
                MultiColorText(
                    text1 = "Course description",
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
                    placeholder = {
                        androidx.compose.material3.Text(text = "Title", color = EDSColors.lightGray)
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
                    placeholder = {
                        androidx.compose.material3.Text(text = "Fee", color = EDSColors.lightGray)
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
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Charge Fee",
                            color = EDSColors.lightGray
                        )
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
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Number of students",
                            color = EDSColors.lightGray
                        )
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
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Contact Number",
                            color = EDSColors.lightGray
                        )
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
                        .padding(horizontal = 20.dp)
                        .clickable {
                            openEditSubjectDialog.value = true
                        },
                    enabled = false,
                    placeholder = {
                        Text(
                            text = "Subject Name",
                            color = EDSColors.lightGray
                        )
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
                        disabledBorderColor = EDSColors.myBlackColor,
                        disabledTextColor = EDSColors.myBlackColor
                    ),
                )
            }
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 20.dp),
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Location",
                            color = EDSColors.lightGray
                        )
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

            item {
                CommonRadioButton(
                    title = "Student Gender",
                    radioOptions = genderOptions,
                    selectedOption = studentSelectedOptions,
                    onOptionSelected = studentOnOptionSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .background(Color.White)
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Description",
                            color = EDSColors.lightGray
                        )
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Minute per session",
                            color = EDSColors.lightGray
                        )
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
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Session per week",
                            color = EDSColors.lightGray
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

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
                    placeholder = {
                        androidx.compose.material3.Text(
                            text = "Academic Level",
                            color = EDSColors.lightGray
                        )
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

            item {
                CommonRadioButton(
                    title = "Tutor Gender",
                    radioOptions = genderOptions,
                    selectedOption = tutorSelectedOptions,
                    onOptionSelected = tutorOnOptionSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .background(Color.White)
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
            text = "Create",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickSubjectDialog(
    subjects: MutableState<List<SubjectChip>>,
    onDisMiss: () -> Unit = {},
    onConfirm: (String) -> Unit = {}
) {
    val searchText = remember { mutableStateOf("") }
    val selectedText = remember { mutableStateOf("") }
    AlertDialog(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            androidx.compose.material3.Text(text = "Subjects")
        },
        containerColor = EDSColors.white,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                androidx.compose.material3.OutlinedTextField(
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(30),
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLeadingIconColor = EDSColors.primaryColor,
                        cursorColor = EDSColors.primaryColor
                    ),
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    subjects.value.forEachIndexed { index, chip ->
                        item {
                            FilterChip(
                                modifier = Modifier.fillMaxWidth(),
                                selected = chip.selected,
                                onClick = {
                                    val newList = subjects.value.toMutableList()
                                    newList.forEach {
                                        it.selected = false
                                    }
                                    newList[index] = chip.copy(selected = !chip.selected)

                                    subjects.value = newList
                                    selectedText.value = newList[index].name
                                },
                                label = {
                                    androidx.compose.material3.Text(
                                        chip.name, fontWeight = FontWeight.W300,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .fillMaxWidth(.85f)
                                    )
                                },
                                trailingIcon = {
                                    if (chip.selected)
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
                onConfirm(selectedText.value)
            }) {
                androidx.compose.material3.Text("Accept", color = EDSColors.primaryColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDisMiss()
                }
            ) {
                androidx.compose.material3.Text("Dismiss", color = EDSColors.notScheduleTextColor)
            }
        }
    )
}