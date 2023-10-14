@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.view.screens.in_app.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.common.composes.CommonRadioButton
import com.projectprovip.h1eu.giasu.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.common.composes.CommonTextField
import com.projectprovip.h1eu.giasu.common.theme.primaryColor


@Composable
@Preview
fun PreviewScreen() {
    PersonalInformation(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInformation(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                AppBarTitle(text = "Personal Information")
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = primaryColor,
                titleContentColor = Color.White
            ), navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Rounded.ArrowBack,
                        "",
                        tint = Color.White)
                }
            })
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()){
            InformationFields(modifier = Modifier.fillMaxHeight(.9f))
            EduSmartButton(text = "Update", onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationFields(modifier: Modifier = Modifier) {
    val genderOptions = listOf("Male", "Female", "Other")

    val (userSelectedOptions, userOnOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }
    LazyColumn(modifier = modifier) {
        item {
            CommonTextField(remember {
                mutableStateOf("Hieu")
            }, modifier = Modifier.fillMaxWidth())
        }
        item {
            CommonTextField(remember {
                mutableStateOf("Email")
            }, modifier = Modifier.fillMaxWidth())
        }
        item {
            CommonTextField(remember {
                mutableStateOf("Address")
            }, modifier = Modifier.fillMaxWidth())
        }
        item {
            CommonTextField(remember {
                mutableStateOf("Birthday")
            }, modifier = Modifier.fillMaxWidth())
        }
        item{
            CommonRadioButton(title = "Gender",
                radioOptions = genderOptions,
                selectedOption = userSelectedOptions,
                onOptionSelected = userOnOptionSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White))
        }
    }
}

