package com.projectprovip.h1eu.giasu.view.screens.in_app.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun PreviewScreen() {
    ClassDetail(rememberNavController())
}

@Composable
fun ClassDetail(navController: NavController) {
    Text(text = "Detail")
}