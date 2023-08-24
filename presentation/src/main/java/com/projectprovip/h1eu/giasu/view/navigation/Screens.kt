package com.projectprovip.h1eu.giasu.view.navigation

import androidx.annotation.StringRes
import com.projectprovip.h1eu.giasu.R

sealed class Screens(val route: String){
    object Splash : Screens(route = "splash")
    object Authentication : Screens(route = "authentication"){
        object Login : Screens(route = "login")
        object Signup : Screens(route = "signup")
        object ForgetPassword : Screens(route = "forget_password")
    }
    object InApp : Screens(route = "in_app"){
        object Home : BottomBarScreens(route = "home", R.string.bottom_home)
        object Class : BottomBarScreens(route = "class", R.string.bottom_class)
    }
}

sealed class BottomBarScreens(val route: String, @StringRes val resId: Int)
