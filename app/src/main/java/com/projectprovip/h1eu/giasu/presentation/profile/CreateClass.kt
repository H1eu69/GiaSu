package com.projectprovip.h1eu.giasu.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonRadioButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonTextField
import com.projectprovip.h1eu.giasu.presentation.common.theme.primaryColor

@Preview
@Composable
fun CreateClassScreenPreview() {
    CreateClassScreen(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateClassScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            null,
                            tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primaryColor
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
        containerColor = Color.LightGray
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize(),) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues( bottom = 100.dp)
            ) {
                item {
                    CreateClassFields()
                }
            }
            EduSmartButton(
                onClick = {},
                text = "Request",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter))
        }
    }
}
@Composable
fun CreateClassFields() {
    val genderOptions = listOf("Male", "Female", "Other")
    val (studentSelectedOptions, studentOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val (tutorSelectedOptions, tutorOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    Text(text = "Class description",
        textAlign = TextAlign.Left,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Title",
        icon = {
               Icon(imageVector = Icons.Default.Favorite,
                   contentDescription = null,
                   tint = primaryColor
               )
        },
        modifier = Modifier.fillMaxWidth()
    )


    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Fee",
        singleLine = false,
        icon = {
            Icon(imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = primaryColor
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Address",
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Number of students",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Contact number",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Subject",
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )


    //Student gender radio button
    CommonRadioButton(title = "Student Gender",
        radioOptions = genderOptions,
        selectedOption = studentSelectedOptions,
        onOptionSelected = studentOnOptionSelected,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White))

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Description",
        singleLine = false,
        icon = {
               Icon(imageVector = Icons.Default.Create,
                   contentDescription = null,
                   tint = primaryColor
               )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )

    Text(text = "Tutor requirement",
        textAlign = TextAlign.Left,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

    //Tutor gender requirement

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Minute per session",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Session per week",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )

    CommonTextField(
        remember { mutableStateOf("") },
        hint = "Academic level",
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )

    //Student gender radio button
    CommonRadioButton(title = "Tutor Gender",
        radioOptions = genderOptions,
        selectedOption = tutorSelectedOptions,
        onOptionSelected = tutorOnOptionSelected,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White))
}