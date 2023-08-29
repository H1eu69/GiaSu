@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.view.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.ui.theme.primaryColor
import com.projectprovip.h1eu.giasu.view.screens.authentication.ForgetPasswordScreen
import com.projectprovip.h1eu.giasu.view.screens.authentication.LoginScreen
import com.projectprovip.h1eu.giasu.view.screens.authentication.SignUpScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.ClassManagementScreen
import com.projectprovip.h1eu.giasu.view.screens.in_app.home.ClassDetailScreen
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
    val modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
    if (bottomBarDestination) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            screens.forEach { screen ->
                val selected = currentDestination?.route == screen.route
                CustomBottomNavItem(modifier.weight(1f), selected, screen, navController)
            }
        }
    }
}

@Composable
fun CustomBottomNavItem(modifier: Modifier,
                        selected: Boolean,
                        screen: BottomBarScreens,
                        navController: NavController) {
    val background = if(selected) primaryColor else Color.Transparent
    val contentColor = if(selected) primaryColor else Color.LightGray
    val selectedIconColor = if(selected) Color.White else Color.LightGray

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                navController.navigate(screen.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // re selecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = screen.icon, contentDescription = null,
                tint = selectedIconColor,
                modifier = Modifier
                    .background(background, shape = CircleShape)
                    .padding(4.dp)
            )
            Text(text = screen.title, color = contentColor)
        }
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
