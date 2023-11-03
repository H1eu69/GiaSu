@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.presentation.common.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projectprovip.h1eu.giasu.presentation.common.composes.BottomBar
import com.projectprovip.h1eu.giasu.presentation.authentication.view.ForgetPasswordScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.LoginScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.SignUpScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.LoginViewModel
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.SignUpViewModel
import com.projectprovip.h1eu.giasu.presentation.class_management.ClassManagementScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.CourseDetailScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.HomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.HomeViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.ProfileScreen
import com.projectprovip.h1eu.giasu.presentation.splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(Screens.Splash.route) {
            SplashScreen(navController)
        }
        authenticationGraph(navController)
        composable(Screens.InApp.route) {
            InAppScreen()
        }
    }
}

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(
        startDestination = Screens.Authentication.Login.route, route = Screens.Authentication.route
    ) {
        composable(Screens.Authentication.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController, viewModel)
        }
        composable(Screens.Authentication.Signup.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(navController, viewModel)
        }
        composable(Screens.Authentication.ForgetPassword.route) {
            ForgetPasswordScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InAppScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(bottomBar = {
        BottomBar(navController)
    }) {
        InAppNavGraph(Modifier.padding(it), navController)
    }
}

@Composable
fun InAppNavGraph(modifier: Modifier, navController: NavHostController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    NavHost(
        navController,
        startDestination = Screens.InApp.Home.route,
        route = Screens.InApp.route,
        modifier = modifier,
    ) {
        composable(Screens.InApp.Home.route) {
            HomeScreen(navController, homeViewModel)
        }
        composable("${Screens.InApp.Home.ClassDetail.route}/{courseId}",
            arguments = listOf(navArgument("courseId") {
                type = NavType.IntType
            })) {backStackEntry ->
            CourseDetailScreen(navController,
                homeViewModel,
                backStackEntry.arguments?.getInt("courseId"))
        }

        composable(Screens.InApp.Class.route) { ClassManagementScreen(navController) }
        composable(Screens.InApp.Profile.route) { ProfileScreen(navController) }
    }
}


/*BottomNavigationItem(
icon = {
    if (screen.route == Screens.InApp.HomeBottomBar.route)
        Icon(Icons.Filled.Home, contentDescription = null)
    if (screen.route == Screens.InApp.ClassBottomBar.route)
        Icon(Icons.Filled.Clear, contentDescription = null)
},
label = { Text(stringResource(screen.resId)) },
selected = ,
selectedContentColor = primaryColor,
unselectedContentColor = Color.LightGray,
onClick = {

}
)*/
