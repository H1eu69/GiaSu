package com.projectprovip.h1eu.giasu.presentation.common.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.projectprovip.h1eu.giasu.R

sealed class Screens(val route: String) {
    data object Splash : Screens(route = "splash")
    data object Authentication : Screens(route = "authentication") {
        data object Login : Screens(route = "login")
        object Signup : Screens(route = "signup")
        object ForgetPassword : Screens(route = "forget_password")
    }

    data object InApp : Screens(route = "in_app") {
        object Home : BottomBarScreens(
            route = "home", R.string.bottom_home,

            icon = Icons.Outlined.Home, title = "Home"
        ) {
            object ClassDetail : Screens(route = "new_class_detail")
            object SearchSuggest : Screens(route = "search_suggest_home")
            object SearchResult : Screens(route = "search_result_home")
        }

        object Courses : BottomBarScreens(
            route = "course", R.string.bottom_course,
            icon = Icons.Outlined.Class, title = "Course"
        ) {
            object CourseDetail : Screens(route = "course_detail")
        }

        object Tutor : BottomBarScreens(
            route = "tutor", R.string.bottom_tutor,
            icon = Icons.Outlined.Person, title = "Tutor"
        ) {
            object TutorDetail : Screens(route = "tutor_detail")
            object SearchSuggest : Screens(route = "search_suggest_tutor")
            object SearchResult : Screens(route = "search_result_tutor")
        }

        object Profile : BottomBarScreens(
            route = "profile", R.string.bottom_profile,
            icon = Icons.Outlined.AccountCircle, title = "Profile"
        ) {
            object RequestClass : Screens(route = "request_class") {
                object LocationPick : Screens(route = "location_pick_profile")
            }

            object TutorRegistration : Screens(route = "tutor_registration")
            object CoursePayment : Screens(route = "course_payment") {
                object CoursePaymentDetail : Screens(route = "course_payment_detail")
            }
            object LearningCourses : Screens(route = "learning_courses") {
                object TutorReview : Screens(route = "tutor_review")
            }

            object UpdateProfile : Screens(route = "update_profile")
        }
    }
}

sealed class BottomBarScreens(
    val route: String,
    @StringRes val resId: Int,
    val icon: ImageVector,
    val title: String
)
