package com.projectprovip.h1eu.giasu.presentation.common.composes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.projectprovip.h1eu.giasu.presentation.common.navigation.BottomBarScreens
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screens.InApp.Home,
        Screens.InApp.Courses,
        Screens.InApp.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    //Do this so bottom bar will hide when navigate to any screen doesn't need it
    val bottomBarDestination = screens.any {
        it.route == currentDestination?.route
    }
    val modifier = Modifier
        .fillMaxWidth()
        .requiredHeight(72.dp)
        .shadow(
            1.dp
        )
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
                        navController: NavController
) {
    val background = if(selected) EDSColors.primaryColor else Color.Transparent
    val contentColor = if(selected) EDSColors.primaryColor else Color.LightGray
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
            AnimatedVisibility(visible = selected) {
                Text(text = screen.title, color = contentColor)
            }
        }
    }
}