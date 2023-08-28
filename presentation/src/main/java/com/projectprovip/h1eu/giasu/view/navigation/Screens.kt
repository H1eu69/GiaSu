package com.projectprovip.h1eu.giasu.view.navigation

import androidx.annotation.StringRes
import com.projectprovip.h1eu.giasu.R

sealed class Screens(val route: String){
    data object Splash : Screens(route = "splash")
    data object Authentication : Screens(route = "authentication"){
        data object Login : Screens(route = "login")
        object Signup : Screens(route = "signup")
        object ForgetPassword : Screens(route = "forget_password")
    }
    data object InApp : Screens(route = "in_app"){
        object HomeBottomBar : BottomBarScreens(route = "home", R.string.bottom_home)
        object ClassDetail : Screens(route = "new_class_detail")
        object ClassBottomBar : BottomBarScreens(route = "class", R.string.bottom_class)
    }
}

sealed class BottomBarScreens(val route: String, @StringRes val resId: Int)
