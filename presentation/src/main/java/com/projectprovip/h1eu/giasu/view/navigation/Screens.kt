package com.projectprovip.h1eu.giasu.view.navigation

sealed class Screens(val route: String){
    object Splash : Screens(route = "splash")
    object Authentication : Screens(route = "authentication"){
        object Login : Screens(route = "login")
        object Signup : Screens(route = "signup")
        object ForgetPassword : Screens(route = "forget_password")

    }


}
