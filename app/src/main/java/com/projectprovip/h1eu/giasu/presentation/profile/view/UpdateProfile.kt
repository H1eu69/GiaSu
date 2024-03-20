@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.domain.profile.model.Profile
import com.projectprovip.h1eu.giasu.presentation.authentication.view.NumberPickerDialog
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonRadioButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.MultiColorText
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectChip
import com.projectprovip.h1eu.giasu.presentation.profile.model.UpdateProfileState


@Composable
@Preview
fun UpdateProfilePreview() {
    UpdateProfile(
        navController = rememberNavController(),
        UpdateProfileState()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfile(navController: NavController, state: UpdateProfileState) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isTutor = state.data.role == "Tutor"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                AppBarTitle(text = "Personal Information")
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = EDSColors.white,
                titleContentColor = EDSColors.primaryColor
            ), navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        "",
                        tint = EDSColors.primaryColor
                    )
                }
            })
        },
        containerColor = EDSColors.white,
    ) {
        if (state.isLoading)
            CircularLoading()
        else
            Box(
                Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(EDSColors.white)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null    // this gets rid of the ripple effect
                    ) {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    }
            ) {
                if (isTutor)
                    TutorRole(
                        navController = navController,
                        modifier = Modifier.fillMaxHeight(.9f),
                        profile = state.data
                    ) else
                    LearnerRole(
                        navController = navController,
                        modifier = Modifier.fillMaxHeight(.9f),
                        profile = state.data
                    )
                EduSmartButton(
                    text = "Update", onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                )
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnerRole(
    navController: NavController, modifier: Modifier = Modifier,
    profile: Profile
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val genderOptions = listOf("Male", "Female", "Other")

    val (userSelectedOptions, userOnOptionSelected) = remember {
        mutableStateOf(genderOptions[genderOptions.indexOf(profile.gender)])
    }

    val firstName = remember { mutableStateOf(profile.firstName) }
    val lastName = remember { mutableStateOf(profile.lastName) }

    val emailText = remember { mutableStateOf(profile.email) }

    val addressText = remember { mutableStateOf(profile.city) }
    val birthYearText = remember { mutableStateOf(profile.birthYear.toString()) }
    val descriptionText = remember { mutableStateOf(profile.description) }
    val phoneText = remember { mutableStateOf(profile.phoneNumber.toString()) }

    val openDatePicker = remember { mutableStateOf(false) }
    val initValue = remember {
        mutableStateOf(profile.birthYear)
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
    val avatarLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {

            }
        }

    LazyColumn(modifier = modifier.background(EDSColors.white)) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)

                ) {
                    AsyncImage(
                        profile.avatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                            .clickable {
                                avatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            })

                    Icon(
                        imageVector = Icons.Default.AddAPhoto, contentDescription = null,
                        tint = EDSColors.blackColor,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(2.dp)
                            .align(Alignment.BottomCenter)
                            .clickable {
                                avatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                    )
                }
                MultiColorText(
                    text1 = "Role: ", color1 = EDSColors.primaryColor,
                    text2 = "Learner", color2 = EDSColors.myBlackColor,
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )

                Button(
                    onClick = {
                        navController.navigate(Screens.InApp.Profile.TutorRegistration.route)
                    },
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    androidx.compose.material3.Text(
                        text = "Enroll as a tutor",
                        color = EDSColors.white
                    )
                }
            }
        }
        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                label = {
                    Text(
                        text = "First name",
                    )
                },
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    firstName.value = value
                },
                singleLine = true, value = firstName.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Last name",
                    )
                },
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    lastName.value = value
                },
                singleLine = true, value = lastName.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Description",
                    )
                },
                maxLines = 4,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    descriptionText.value = value
                },
                value = descriptionText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Email",
                    )
                },
                singleLine = true, shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    emailText.value = value
                },
                value = emailText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Address",
                    )
                },
                maxLines = 4,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    addressText.value = value
                },
                value = addressText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Phone",
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    phoneText.value = value
                },
                value = phoneText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
                ),
            )
        }
        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp)
                    .clickable(
                        interactionSource, null
                    ) {
                        openDatePicker.value = true
                    },
                label = {
                    Text(
                        text = "Birth Year",
                    )
                },
                enabled = false,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    if (value.length <= 4) {
                        birthYearText.value = value
                    }
                },
                value = birthYearText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = EDSColors.gray,
                    disabledContainerColor = EDSColors.transparent,
                    disabledTextColor = EDSColors.blackColor
                ),
            )
        }
        item {
            CommonRadioButton(
                title = "Gender",
                radioOptions = genderOptions,
                selectedOption = userSelectedOptions,
                onOptionSelected = userOnOptionSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.White)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TutorRole(
    navController: NavController, modifier: Modifier = Modifier,
    profile: Profile
) {
    val genderOptions = listOf("Male", "Female", "Other")

    val (userSelectedOptions, userOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val nameText = remember { mutableStateOf("Hieu") }

    val emailText = remember { mutableStateOf(profile.email) }

    val addressText = remember { mutableStateOf(profile.city) }
    val birthYearText = remember { mutableStateOf(profile.birthYear.toString()) }
    val descriptionText =
        remember { mutableStateOf(profile.description) }
    val phoneText = remember { mutableStateOf(profile.phoneNumber.toString()) }
    val openEditSubjectDialog = remember { mutableStateOf(false) }

    val avatarLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {

            }
        }

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
        SubjectDialog(dummySubjects, onDisMiss = {
            openEditSubjectDialog.value = false
        },
            onConfirm = {

            })
    }

    LazyColumn(modifier = modifier.background(EDSColors.white)) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    AsyncImage(
                        "https://media.istockphoto.com/id/1322220448/photo/abstract-digital-futuristic-eye.jpg?s=612x612&w=0&k=20&c=oAMmGJxyTTNW0XcttULhkp5IxfW9ZTaoVdVwI2KwK5s=",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                            .clickable {
                                avatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            })
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(EDSColors.grayX2)
                            .border(1.dp, EDSColors.white, CircleShape)
                            .padding(2.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto, contentDescription = null,
                            tint = EDSColors.blackColor,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    avatarLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                        )
                    }

                }
                MultiColorText(
                    text1 = "Role: ", color1 = EDSColors.primaryColor,
                    text2 = "Tutor", color2 = EDSColors.myBlackColor,
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subjects", fontWeight = FontWeight.Bold)
                    Text(
                        text = "Edit",
                        color = EDSColors.primaryColor,
                        modifier = Modifier.clickable {
                            openEditSubjectDialog.value = true
                        })
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Java", fontWeight = FontWeight.W300) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ }, colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Python", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ }, colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("c++", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ }, colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = {
                            Text(
                                "Lap trinh cho nguoi mat goc",
                                fontWeight = FontWeight.W400
                            )
                        }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ }, colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Toan cao cap", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ }, colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Ly dai cuong", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ }, colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Hoa hoc dai cuong", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ }, colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Tieng anh", fontWeight = FontWeight.W400) }
                    )

                }

            }

        }
        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                label = {
                    Text(
                        text = "Name",
                    )
                },
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    nameText.value = value
                },
                singleLine = true, value = nameText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Description",
                    )
                },
                maxLines = 4,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    descriptionText.value = value
                },
                value = descriptionText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Email",
                    )
                },
                singleLine = true, shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    emailText.value = value
                },
                value = emailText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Address",
                    )
                },
                maxLines = 4,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    addressText.value = value
                },
                value = addressText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Phone",
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    phoneText.value = value
                },
                value = phoneText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
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
                    Text(
                        text = "Birth Year",
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    if (value.length <= 4)
                        birthYearText.value = value
                },
                value = birthYearText.value,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = EDSColors.primaryColor,
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
                ),
            )
        }
        item {
            CommonRadioButton(
                title = "Gender",
                radioOptions = genderOptions,
                selectedOption = userSelectedOptions,
                onOptionSelected = userOnOptionSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.White)
            )
        }
        item {
            val images = remember {
                mutableStateOf(
                    listOf(
                        "".toUri(),
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(

                ) {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "",
                        tint = EDSColors.primaryColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    androidx.compose.material3.Text(text = "Verification", fontSize = 16.sp)
                    androidx.compose.material3.Text(
                        text = "(*)", fontSize = 16.sp,
                        color = EDSColors.notScheduleTextColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    androidx.compose.material3.Text(
                        text = "Add more", fontSize = 16.sp,
                        color = EDSColors.primaryColor,
                        modifier = Modifier.clickable {
                            val newList = images.value.toMutableList()
                            newList.add(Uri.EMPTY)
                            images.value = newList
                        }
                    )

                }
                ImagePicker(
                    images.value,
                    onImageUriChanged = { uri, index ->
                        val newList = images.value.toMutableList()
                        newList.removeAt(index)
                        newList.add(index, uri)
                        images.value = newList
                        //uploadImage(uri)
                    },
                    onDeleteUri = { index ->
                        val newList = images.value.toMutableList()
                        val emptyUri = Uri.EMPTY

                        newList.removeAt(index)
                        newList.add(index, emptyUri)
                        images.value = newList
                    },
                )
            }
        }

    }
}

@Preview
@Composable
fun SubjectDialogPreview() {
    SubjectDialog(
        remember {
            mutableStateOf(
                listOf(
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                    SubjectChip(),
                )
            )

        })
}

@Composable
fun SubjectDialog(
    subjects: MutableState<List<SubjectChip>>,
    onDisMiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    val searchText = remember { mutableStateOf("") }
    AlertDialog(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            Text(text = "Subjects")
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

                    subjects.value.forEachIndexed { index, chip ->
                        item {
                            FilterChip(
                                modifier = Modifier.fillMaxWidth(),
                                selected = chip.selected,
                                onClick = {
                                    val newList = subjects.value.toMutableList()
                                    newList[index] = chip.copy(selected = !chip.selected)
                                    subjects.value = newList
                                },
                                label = {
                                    Text(
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
                onConfirm()
            }) {
                Text("Accept", color = EDSColors.primaryColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDisMiss()
                }
            ) {
                Text("Dismiss", color = EDSColors.notScheduleTextColor)
            }
        }
    )
}

