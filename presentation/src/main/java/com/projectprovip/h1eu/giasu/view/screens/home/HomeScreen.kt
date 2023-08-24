package com.projectprovip.h1eu.giasu.view.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.ui.composes.SubjectCategoryItem
import com.projectprovip.h1eu.giasu.ui.theme.primaryColor

@Preview
@Composable
fun Preview() {
    HomeScreen(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val searchTextField = remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = primaryColor
                ),
                title = {
                    Column() {
                        Text(
                            text = "Hello",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.White,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                        Text(
                            text = "{Username}",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                    }
                },
            )
        }
    ) {
        Column(
            Modifier
                .background(primaryColor)
                .padding(it)
                .fillMaxSize() ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                value = searchTextField.value,
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, "")
                },
                label = {
                    Text(text = "Search")
                },
                shape = RoundedCornerShape(30),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedLabelColor = Color.Blue,
                    focusedBorderColor = Color.Blue,
                    focusedLeadingIconColor = Color.Blue
                ),
                onValueChange = {
                    searchTextField.value = it
                })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            ) {
                Row(
                    Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Category",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    Text(
                        text = "View all",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                }
                Spacer(modifier = Modifier.height(0.dp))
                val list = listOf<Int>(1,2,3,4,5,5,5,5,5,55,5,5)
                LazyRow(
                    Modifier.fillMaxWidth(),
                    userScrollEnabled = true,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
                ){
                    items(list.size) {
                        SubjectCategoryItem()
                    }
                }
                Row(
                    Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Newest classes",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    Text(
                        text = "View all",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                }
            }
        }
    }
}


