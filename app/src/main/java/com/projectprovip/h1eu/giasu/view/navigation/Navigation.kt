@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.view.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.common.composes.BottomBar
import com.projectprovip.h1eu.giasu.view.screens.authentication.ForgetPasswordScreen
import com.projectprovip.h1eu.giasu.view.screens.authentication.LoginScreen
import com.projectprovip.h1eu.giasu.view.screens.authentication.SignUpScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.ClassManagementScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.home.ClassDetailScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.home.HomeScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.profile.ProfileScreen
import com.projectprovip.h1eu.giasu.view.screens.splash.SplashScreen

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
    navigation(startDestination = Screens.Authentication.Login.route, route = Screens.Authentication.route) {
        composable(Screens.Authentication.Login.route) {
            LoginScreen(navController)
        }
        composable(Screens.Authentication.Signup.route) {
            SignUpScreen(navController)
        }
        composable(Screens.Authentication.ForgetPassword.route) {
            ForgetPasswordScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InAppScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        InAppNavGraph(Modifier.padding(it),navController)
    }
}

@Composable
fun InAppNavGraph(modifier: Modifier, navController: NavHostController) {
    NavHost(
        navController, startDestination = Screens.InApp.HomeBottomBar.route,
        route = Screens.InApp.route,
        modifier = modifier,
    ) {
        composable(Screens.InApp.HomeBottomBar.route) { HomeScreen(navController) }
        composable(Screens.InApp.ClassDetail.route) { ClassDetailScreen(navController) }
        composable(Screens.InApp.ClassBottomBar.route) { ClassManagementScreen(navController) }
        composable(Screens.InApp.ProfileBottomBar.route) { ProfileScreen(navController) }
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
