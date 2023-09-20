@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.view.screens.in_app.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.ui.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.ui.theme.primaryColor
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
@Preview
fun PreviewScreen() {
    PersonalInformation(navController = rememberNavController())
}

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
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(text = "Update")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationFields(modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier) {
        item {
            InformationTextField("First name")
        }
        item {
            InformationTextField("Last name")
        }
        item {
            InformationTextField("Address")
        }
        item {
            InformationTextField("anrayno200@gmail.com")
        }
        item {
            GenderOptions()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationTextField(hintText: String) {
    val text = remember { mutableStateOf("") }

    TextField(value = text.value,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = primaryColor,
            unfocusedIndicatorColor = Color.LightGray,
            cursorColor = primaryColor
        ),
        singleLine = true,
        placeholder = {
            Text(text = hintText)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = primaryColor)
        },
        onValueChange = { name ->
            text.value = name
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun GenderOptions() {
    val radioOptions = listOf("Male", "Female")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1] ) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        radioOptions.forEach { text ->
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = primaryColor
                    ),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}