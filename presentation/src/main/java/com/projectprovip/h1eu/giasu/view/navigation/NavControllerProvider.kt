package com.projectprovip.h1eu.giasu.view.navigation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavControllerProvider(override val values: Sequence<NavHostController>) : PreviewParameterProvider<NavHostController> {
}