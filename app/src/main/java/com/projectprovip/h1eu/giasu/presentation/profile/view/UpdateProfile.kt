@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.presentation.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonRadioButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.MultiColorText
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectChip


@Composable
@Preview
fun PreviewScreen() {
    UpdateProfile(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfile(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                AppBarTitle(text = "Personal Information")
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = EDSColors.primaryColor,
                titleContentColor = Color.White
            ), navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        "",
                        tint = Color.White
                    )
                }
            })
        }
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TutorRole(navController = navController, modifier = Modifier.fillMaxHeight(.9f))
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
fun LearnerRole(navController: NavController, modifier: Modifier = Modifier) {
    val genderOptions = listOf("Male", "Female", "Other")

    val (userSelectedOptions, userOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val nameText = remember { mutableStateOf("Hieu") }

    val emailText = remember { mutableStateOf("anrayno200@gmail.com") }

    val addressText = remember { mutableStateOf("So 35 duong B KP 1 phuong 3") }
    val birthYearText = remember { mutableStateOf("2002") }
    val descriptionText = remember { mutableStateOf("Tao la 1 thang gia su ngu hoc") }
    val phoneText = remember { mutableStateOf("0967075340") }

    LazyColumn(modifier = modifier) {
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
                        "https://media.istockphoto.com/id/1322220448/photo/abstract-digital-futuristic-eye.jpg?s=612x612&w=0&k=20&c=oAMmGJxyTTNW0XcttULhkp5IxfW9ZTaoVdVwI2KwK5s=",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                            .clickable {

                            })
                    Icon(
                        imageVector = Icons.Default.Create, contentDescription = null,
                        tint = EDSColors.primaryColor,
                        modifier = Modifier
                            .offset(0.dp, 16.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(2.dp)
                            .align(Alignment.BottomCenter)
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
                        text = "Name",
                    )
                },
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    nameText.value = value
                },
                singleLine = true, value = nameText.value,
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
                    Text(
                        text = "Email",
                    )
                },
                singleLine = true, shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    emailText.value = value
                },
                value = emailText.value,
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
                    Text(
                        text = "Birth Year",
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    birthYearText.value = value
                },
                value = birthYearText.value,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
                    cursorColor = EDSColors.primaryColor,
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
fun TutorRole(navController: NavController, modifier: Modifier = Modifier) {
    val genderOptions = listOf("Male", "Female", "Other")

    val (userSelectedOptions, userOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val nameText = remember { mutableStateOf("Hieu") }

    val emailText = remember { mutableStateOf("anrayno200@gmail.com") }

    val addressText = remember { mutableStateOf("So 35 duong B KP 1 phuong 3") }
    val birthYearText = remember { mutableStateOf("2002") }
    val descriptionText = remember { mutableStateOf("Tao la 1 thang gia su ngu hoc") }
    val phoneText = remember { mutableStateOf("0967075340") }
    val openEditSubjectDialog = remember { mutableStateOf(false) }

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

    LazyColumn(modifier = modifier) {
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
                        "https://media.istockphoto.com/id/1322220448/photo/abstract-digital-futuristic-eye.jpg?s=612x612&w=0&k=20&c=oAMmGJxyTTNW0XcttULhkp5IxfW9ZTaoVdVwI2KwK5s=",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                            .clickable {

                            })
                    Icon(
                        imageVector = Icons.Default.Create, contentDescription = null,
                        tint = EDSColors.primaryColor,
                        modifier = Modifier
                            .offset(0.dp, 16.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(2.dp)
                            .align(Alignment.BottomCenter)
                    )
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
                        onClick = { /*TODO*/ },                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Python", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("c++", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("1235 anh co danh roi", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Toan cao cap", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Ly cui cap", fontWeight = FontWeight.W400 )}
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EDSColors.greenCheck,
                            selectedLabelColor = EDSColors.white,
                            selectedTrailingIconColor = EDSColors.white
                        ),
                        label = { Text("Hoa hoc cho tre em", fontWeight = FontWeight.W400) }
                    )

                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },                        colors = FilterChipDefaults.filterChipColors(
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
                    Text(
                        text = "Email",
                    )
                },
                singleLine = true, shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    emailText.value = value
                },
                value = emailText.value,
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
                    Text(
                        text = "Birth Year",
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { value ->
                    birthYearText.value = value
                },
                value = birthYearText.value,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = EDSColors.primaryColor,
                    focusedLabelColor = EDSColors.primaryColor,
                    cursorColor = EDSColors.primaryColor,
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
                    colors = TextFieldDefaults.outlinedTextFieldColors(
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

