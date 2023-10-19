package com.projectprovip.h1eu.giasu.presentation.common.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.projectprovip.h1eu.giasu.R

sealed class Screens(val route: String){
    data object Splash : Screens(route = "splash")
    data object Authentication : Screens(route = "authentication"){
        data object Login : Screens(route = "login")
        object Signup : Screens(route = "signup")
        object ForgetPassword : Screens(route = "forget_password")
    }
    data object InApp : Screens(route = "in_app"){
        object Home : BottomBarScreens(route = "home", R.string.bottom_home, icon = Icons.Outlined.Home, title = "Home")
        object ClassDetail : Screens(route = "new_class_detail")
        object Class : BottomBarScreens(route = "class", R.string.bottom_class, icon = Icons.Outlined.Warning, title = "Class")
        object Profile : BottomBarScreens(route = "profile", R.string.bottom_profile, icon = Icons.Outlined.AccountCircle, title = "Profile")
        object PersonalInformation : Screens(route = "personal_information")

    }
}

sealed class BottomBarScreens(val route: String, @StringRes val resId: Int, val icon: ImageVector, val title: String)
