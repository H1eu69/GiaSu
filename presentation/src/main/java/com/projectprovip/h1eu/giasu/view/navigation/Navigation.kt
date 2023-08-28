@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.view.navigation

import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.view.screens.authentication.ForgetPasswordScreen
import com.projectprovip.h1eu.giasu.view.screens.authentication.LoginScreen
import com.projectprovip.h1eu.giasu.view.screens.authentication.SignUpScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.ClassManagementScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.home.ClassDetail
import com.projectprovip.h1eu.giasu.view.screens.in_app.home.HomeScreen
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InAppScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        InAppNavGraph(navController)
    }
}

@Composable
fun InAppNavGraph(navController: NavHostController) {
    NavHost(
        navController, startDestination = Screens.InApp.HomeBottomBar.route,
        route = Screens.InApp.route
    ) {
        composable(Screens.InApp.HomeBottomBar.route) { HomeScreen(navController) }
        composable(Screens.InApp.ClassDetail.route) { ClassDetail(navController) }
        composable(Screens.InApp.ClassBottomBar.route) { ClassManagementScreen(navController) }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screens.InApp.HomeBottomBar,
        Screens.InApp.ClassBottomBar,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    //Do this so bottom bar will hide when navigate to any screen doesn't need it
    val bottomBarDestination = screens.any {
        it.route == currentDestination?.route
    }
    if (bottomBarDestination) {
        BottomNavigation {
            screens.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        if (screen.route == Screens.InApp.HomeBottomBar.route)
                            Icon(Icons.Filled.Home, contentDescription = null)
                        if (screen.route == Screens.InApp.ClassBottomBar.route)
                            Icon(Icons.Filled.Clear, contentDescription = null)
                    },
                    label = { Text(stringResource(screen.resId)) },
                    selected = currentDestination?.route == screen.route,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.LightGray,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
