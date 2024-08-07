@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.common.toEDSStringGender
import com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto.Major
import com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto.toSubjectItem
import com.projectprovip.h1eu.giasu.presentation.authentication.view.NumberPickerDialog
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonRadioButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.MultiColorText
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.GetProfileState
import com.projectprovip.h1eu.giasu.presentation.profile.model.GetTutorInfoState
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectItem
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectState
import com.projectprovip.h1eu.giasu.presentation.profile.model.UpdateProfileState
import com.projectprovip.h1eu.giasu.presentation.profile.model.toMajor
import kotlinx.coroutines.launch
import kotlin.math.ceil


@Composable
@Preview
fun UpdateProfilePreview() {
    UpdateProfile(
        navController = rememberNavController(),
        GetProfileState(),
        GetTutorInfoState(),
        UpdateProfileState(),
        SubjectState(),
        false,
        { a, b, c, d, e, f, g, h, i, k ,re,asd,dsad-> }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfile(
    navController: NavController,
    getProfileState: GetProfileState,
    getTutorInfoState: GetTutorInfoState,
    updateProfileState: UpdateProfileState,
    subjectState: SubjectState,
    isTutor: Boolean,
    onUpdateBtnClick: (String, String, Int, String, String, String, String, String, String, String, String, String,  List<Major>) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    val profile = getProfileState.data
    val tutor = getTutorInfoState.data

    val firstName = remember { mutableStateOf(profile.firstName) }
    val lastName = remember { mutableStateOf(profile.lastName) }

    val emailText = remember { mutableStateOf(profile.email) }

    val addressText = remember { mutableStateOf(profile.city) }
    val birthYearText = remember { mutableStateOf(profile.birthYear.toString()) }
    val descriptionText = remember { mutableStateOf(profile.description) }
    val academicLevel = remember { mutableStateOf(tutor.academicLevel) }
    val university = remember { mutableStateOf(tutor.university) }
    val phoneText = remember { mutableStateOf(profile.phoneNumber.toString()) }
    val avatar = remember { mutableStateOf(Uri.parse(profile.avatar)) }

    val openDatePickerDialog = remember { mutableStateOf(false) }
    val openEditSubjectDialog = remember { mutableStateOf(false) }

    val initValue = remember {
        mutableStateOf(profile.birthYear)
    }
    val genderOptions = listOf("Male", "Female", "Other")

    var (genderSelectedOption, onGenderSelect) = remember {
        mutableStateOf(genderOptions[0])
    }

    val academicLevels = listOf("Ungraduated", "Graduated", "Lecturer")

    var (academicLevelSelectedOption, academicOnOptionSelected) = remember {
        mutableStateOf(academicLevels[0])
    }

    val userMajors = remember {
        mutableStateOf(tutor.majors)
    }

    val subject = remember {
        mutableStateOf(subjectState.data)
    }
    var userListMajor: List<SubjectItem>
    val images = remember {
        mutableStateOf(
            listOf("".toUri()))
    }

    LaunchedEffect(profile) {
        Log.d("Get profile", profile.toString())
        firstName.value = profile.firstName
        lastName.value = profile.lastName
        emailText.value = profile.email
        addressText.value = profile.city
        birthYearText.value = profile.birthYear.toString()
        descriptionText.value = profile.description
        phoneText.value = profile.phoneNumber
        avatar.value = Uri.parse(profile.avatar)
        initValue.value = profile.birthYear


        var genderIndex = genderOptions.indexOf(profile.gender.toEDSStringGender() )
        if(genderIndex == -1)
            genderIndex = 0
        onGenderSelect(genderOptions[genderIndex])
    }
    LaunchedEffect(tutor) {
        academicLevel.value = tutor.academicLevel
        university.value = tutor.university
        userMajors.value = tutor.majors
        userListMajor = userMajors.value.map {
            it.toSubjectItem()
        }

        subject.value = subjectState.data
        subject.value.map {
            if (it in userListMajor) {
                it.isSelected = true
            }
        }
        images.value = tutor.verificationDtos.map {
            it.image.toUri()
        }

        var acaIndex = academicLevels.indexOf(tutor.academicLevel )
        if(acaIndex == -1)
            acaIndex = 0
        academicOnOptionSelected(academicLevels[acaIndex])
    }

    LaunchedEffect(subjectState) {
        userMajors.value = tutor.majors
        userListMajor = userMajors.value.map {
            it.toSubjectItem()
        }

        subject.value = subjectState.data
        subject.value.map {
            if (it in userListMajor) {
                it.isSelected = true
            }
        }
    }


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
        getProfileState.apply {
            when {
                this.isLoading -> {
                    CircularLoading()
                }

                else -> {
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
                                firstName,
                                lastName,
                                emailText,
                                addressText,
                                birthYearText,
                                descriptionText,
                                phoneText,
                                openEditSubjectDialog,
                                avatar,
                                academicLevel,
                                university,
                                subject,
                                userMajors,
                                images,
                                genderOptions,
                                genderSelectedOption,
                                onGenderSelect,
                                academicLevels,
                                academicLevelSelectedOption,
                                academicOnOptionSelected,
                                initValue,
                                openDatePickerDialog
                            ) else
                            LearnerRole(
                                navController = navController,
                                modifier = Modifier.fillMaxHeight(.9f),
                                firstName,
                                lastName,
                                emailText,
                                addressText,
                                birthYearText,
                                descriptionText,
                                phoneText,
                                openDatePickerDialog,
                                avatar,
                                initValue,
                                genderOptions,
                                genderSelectedOption,
                                onGenderSelect
                            )
                        EduSmartButton(
                            text = "Update", onClick = {
                                onUpdateBtnClick(
                                    avatar.value.toString(),
                                    emailText.value,
                                    birthYearText.value.toInt(),
                                    addressText.value,
                                    "Vietnam",
                                    descriptionText.value,
                                    firstName.value,
                                    genderSelectedOption,
                                    lastName.value,
                                    phoneText.value,
                                    academicLevel.value,
                                    university.value,
                                    userMajors.value
                                )
                            },
                            isLoading = updateProfileState.isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
        updateProfileState.apply {
            when {
                this.isUpdateSuccess -> {
                    val userAvatar = this.data.avatar
                    val fullName = this.data.fullName
                    val email = this.data.email
                    val context = LocalContext.current
                    val coroutine = rememberCoroutineScope()
                    LaunchedEffect(key1 = "") {
                        coroutine.launch {
                            Log.d("test preference", userAvatar)
                            context.dataStore.edit { preference ->
                                preference[stringPreferencesKey(Constant.USER_IMAGE_STRING)] =
                                    userAvatar
                                preference[stringPreferencesKey(Constant.USERNAME_STRING)] =
                                    fullName
                                preference[stringPreferencesKey(Constant.USER_EMAIL_STRING)] =
                                    email
                            }
                        }
                        Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnerRole(
    navController: NavController,
    modifier: Modifier = Modifier,
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    emailText: MutableState<String>,
    addressText: MutableState<String>,
    birthYearText: MutableState<String>,
    descriptionText: MutableState<String>,
    phoneText: MutableState<String>,
    openDatePicker: MutableState<Boolean>,
    avatar: MutableState<Uri>,
    initValue: MutableState<Int>,
    genderOptions: List<String>,
    genderSelectedOption: String,
    onGenderSelect: (String) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
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
                avatar.value = it
            }
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
                        avatar.value,
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
                selectedOption = genderSelectedOption,
                onOptionSelected = onGenderSelect,
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
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    emailText: MutableState<String>,
    addressText: MutableState<String>,
    birthYearText: MutableState<String>,
    descriptionText: MutableState<String>,
    phoneText: MutableState<String>,
    openEditSubjectDialog: MutableState<Boolean>,
    avatar: MutableState<Uri>,
    academicLevel: MutableState<String>,
    university: MutableState<String>,
    subject: MutableState<List<SubjectItem>>,
    userMajors: MutableState<List<Major>>,
    verificationImages: MutableState<List<Uri>>,
    genderOptions: List<String>,
    genderSelectedOption: String,
    onGenderSelect: (String) -> Unit,
    academicOptions: List<String>,
    academicSelectedOptions: String,
    onAcademicSelect: (String) -> Unit,
    initValue: MutableState<Int>,
    openDatePicker: MutableState<Boolean>,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val avatarLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                avatar.value = it
            }
        }

    if (openEditSubjectDialog.value) {
        Log.d("subject", subject.value.toString())

        SubjectDialog(subject, onDisMiss = {
            openEditSubjectDialog.value = false
        },
            onConfirm = {
                val newList = userMajors.value.toMutableList()
                newList.clear()
                subject.value.forEach {
                    if (it.isSelected) {
                        newList.add(it.toMajor())
                    }
                }
                userMajors.value = newList
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
                        avatar.value,
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
                    userMajors.value.forEach {
                        FilterChip(
                            selected = true,
                            onClick = { /*TODO*/ },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EDSColors.greenCheck,
                                selectedLabelColor = EDSColors.white,
                                selectedTrailingIconColor = EDSColors.white
                            ),
                            label = { Text(it.subjectName, fontWeight = FontWeight.W300) }
                        )
                    }
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
                singleLine = true,
                value = firstName.value,
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
                singleLine = true,
                value = lastName.value,
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
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 12.dp)
//                    .padding(horizontal = 20.dp),
//                label = {
//                    Text(
//                        text = "Academic level",
//                    )
//                },
//                singleLine = true,
//                shape = RoundedCornerShape(12.dp),
//                onValueChange = { value ->
//                    academicLevel.value = value
//                },
//                value = academicLevel.value,
//                colors = OutlinedTextFieldDefaults.colors(
//                    cursorColor = EDSColors.primaryColor,
//                    focusedBorderColor = EDSColors.primaryColor,
//                    focusedLabelColor = EDSColors.primaryColor,
//                ),
//            )
            CommonRadioButton(
                title = "Academic Requirement",
                radioOptions = academicOptions,
                selectedOption = academicSelectedOptions,
                onOptionSelected = onAcademicSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(Color.White)
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
                        text = "Univeristy",
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    university.value = value
                },
                value = university.value,
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
                selectedOption = genderSelectedOption,
                onOptionSelected = onGenderSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.White)
            )
        }
        item {
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
                            val newList = verificationImages.value.toMutableList()
                            newList.add(Uri.EMPTY)
                            verificationImages.value = newList
                        }
                    )

                }
                ImagePicker(
                    verificationImages.value,
                    onImageUriChanged = { uri, index ->
                        val newList = verificationImages.value.toMutableList()
                        newList.removeAt(index)
                        newList.add(index, uri)
                        verificationImages.value = newList
                        //uploadImage(uri)
                    },
                    onDeleteUri = { index ->
                        val newList = verificationImages.value.toMutableList()
                        val emptyUri = Uri.EMPTY

                        newList.removeAt(index)
                        newList.add(index, emptyUri)
                        verificationImages.value = newList
                    },
                )
            }
        }

    }
}

@Composable
@Preview
fun TutorPreview() {
    val firstName = remember { mutableStateOf("profile.firstName") }
    val lastName = remember { mutableStateOf("profile.lastName") }

    val emailText = remember { mutableStateOf("profile.email") }

    val addressText = remember { mutableStateOf("profile.city") }
    val birthYearText = remember { mutableStateOf("profile.birthYear.toString()") }
    val descriptionText = remember { mutableStateOf("profile.description") }
    val phoneText = remember { mutableStateOf("profile.phoneNumber.toString()") }
    val avatar = remember { mutableStateOf(Uri.parse("")) }
    val academicLevel = remember { mutableStateOf("profile.description") }
    val university = remember { mutableStateOf("profile.phoneNumber.toString()") }

    val openDialog = remember { mutableStateOf(false) }
    val initValue = remember {
        mutableStateOf(20)
    }
    val genderOptions = listOf("Male", "Female", "Other")

    val (genderSelectedOption, onGenderSelect) = remember {
        mutableStateOf(genderOptions[1])
    }
    val academicOptions = listOf("Male", "Female", "Other")

    val (academicSelectedOption, onAcademicSelected) = remember {
        mutableStateOf(genderOptions[1])
    }

    val userMajors = remember {
        mutableStateOf<List<Major>>(emptyList())
    }

    val subject = remember {
        mutableStateOf<List<SubjectItem>>(emptyList())
    }
    val images = remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    TutorRole(
        navController = rememberNavController(),
        modifier = Modifier,
        firstName,
        lastName,
        emailText,
        addressText,
        birthYearText,
        descriptionText,
        phoneText,
        openDialog,
        avatar,
        academicLevel,
        university,
        subject,
        userMajors,
        images,
        genderOptions,
        genderSelectedOption,
        onGenderSelect,
        academicOptions,
        academicSelectedOption,
        onAcademicSelected,
        initValue,
        openDialog
    )
}

@Composable
@Preview
fun LearnerPreview() {
    val firstName = remember { mutableStateOf("profile.firstName") }
    val lastName = remember { mutableStateOf("profile.lastName") }

    val emailText = remember { mutableStateOf("profile.email") }

    val addressText = remember { mutableStateOf("profile.city") }
    val birthYearText = remember { mutableStateOf("profile.birthYear.toString()") }
    val descriptionText = remember { mutableStateOf("profile.description") }
    val phoneText = remember { mutableStateOf("profile.phoneNumber.toString()") }
    val avatar = remember { mutableStateOf(Uri.parse("")) }

    val openDialog = remember { mutableStateOf(false) }
    val initValue = remember {
        mutableStateOf(20)
    }
    val genderOptions = listOf("Male", "Female", "Other")

    val (genderSelectedOption, onGenderSelect) = remember {
        mutableStateOf(genderOptions[1])
    }

    LearnerRole(
        navController = rememberNavController(),
        modifier = Modifier,
        firstName,
        lastName,
        emailText,
        addressText,
        birthYearText,
        descriptionText,
        phoneText,
        openDialog,
        avatar,
        initValue,
        genderOptions,
        genderSelectedOption,
        onGenderSelect
    )
}

@Preview
@Composable
fun SubjectDialogPreview() {
    SubjectDialog(
        remember {
            mutableStateOf(
                listOf(
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectItem(),
//                    SubjectChip(),
//                    SubjectChip(),
//                    SubjectChip(),
//                    SubjectChip(),
                )
            )

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectDialog(
    subjects: MutableState<List<SubjectItem>>,
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

                    subjects.value.forEachIndexed { index, subject ->
                        item {
                            FilterChip(
                                modifier = Modifier.fillMaxWidth(),
                                selected = subject.isSelected,
                                onClick = {
                                    val newList = subjects.value.toMutableList()
                                    newList[index] = subject.copy(isSelected = !subject.isSelected)
                                    subjects.value = newList
                                },
                                label = {
                                    Text(
                                        subject.name, fontWeight = FontWeight.W300,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .fillMaxWidth(.85f)
                                    )
                                },
                                trailingIcon = {
                                    if (subject.isSelected)
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
                onDisMiss()
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImagePicker(
    images: List<Uri>, onImageUriChanged: ((uri: Uri, index: Int) -> Unit),
    onDeleteUri: ((index: Int) -> Unit)
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val imageWidth = (screenWidth - 40) / 3
    val rowCount = ceil(images.count().toDouble() / 3).toInt()
    val listHeight = rowCount * imageWidth + ((rowCount - 1) * 8)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(top = 20.dp)
            .height(listHeight.dp)
    ) {
        images.forEachIndexed { index, u ->
            Log.d("Test uri ", u.toString())
            item {
                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
                        if (it != null) {
                            onImageUriChanged(it, index)
                            Log.d("Test last path uri ", u.lastPathSegment.toString())
                        }
                    }

                if (u.toString().isNotEmpty()) {
                    Box {
                        GlideImage(model = u, contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(imageWidth.dp)
                                .clip(
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.close_with_bg),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 2.dp, end = 2.dp)
                                .background(
                                    EDSColors.imageBackground,
                                    CircleShape
                                )
                                .clickable {
                                    onDeleteUri(index)
                                    Log.d("Test uri on delete", u.lastPathSegment.toString())
                                }
                        )
                    }


                } else {
                    Box(modifier = Modifier
                        .size(imageWidth.dp)
                        .background(
                            EDSColors.imageBackground,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "",
                            tint = EDSColors.plusBackground,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

}
