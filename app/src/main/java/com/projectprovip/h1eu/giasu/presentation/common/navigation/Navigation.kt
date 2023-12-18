@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.presentation.common.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
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
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.authentication.view.ForgetPasswordScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.LoginScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.SignUpScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.LoginViewModel
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.SignUpViewModel
import com.projectprovip.h1eu.giasu.presentation.class_management.view.ClassManagementScreen
import com.projectprovip.h1eu.giasu.presentation.class_management.view.RequestedCourseDetailScreen
import com.projectprovip.h1eu.giasu.presentation.class_management.viewmodel.CourseManagementViewModel
import com.projectprovip.h1eu.giasu.presentation.class_management.viewmodel.RequestedCourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.common.composes.BottomBar
import com.projectprovip.h1eu.giasu.presentation.home.view.CourseDetailScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.HomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.CourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.HomeViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.view.LearningCourseScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.ProfileScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.TutorRegisterScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.TutorReviewScreen
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.LearningCoursesViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.TutorRegisterViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.TutorReviewViewModel
import com.projectprovip.h1eu.giasu.presentation.splash.SplashScreen
import com.projectprovip.h1eu.giasu.presentation.tutor.view.TutorDetailScreen
import com.projectprovip.h1eu.giasu.presentation.tutor.view.TutorScreen
import com.projectprovip.h1eu.giasu.presentation.tutor.viewmodel.TutorDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.tutor.viewmodel.TutorViewModel
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val token = remember { mutableStateOf("") }

    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[stringPreferencesKey(Constant.TOKEN_STRING)]}"
            }
        }
    }

    NavHost(
        navController,
        startDestination = Screens.InApp.Home.route,
        route = Screens.InApp.route,
        modifier = modifier,
    ) {
        composable(Screens.InApp.Home.route) {
            HomeScreen(navController, homeViewModel)
        }
        composable(
            "${Screens.InApp.Home.ClassDetail.route}/{courseId}",
            arguments = listOf(navArgument("courseId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val courseDetailViewModel = hiltViewModel<CourseDetailViewModel>()
            CourseDetailScreen(
                navController,
                homeViewModel,
                courseDetailViewModel,
                backStackEntry.arguments?.getInt("courseId")
            )
        }
        composable(Screens.InApp.Tutor.route) {
            val vm = hiltViewModel<TutorViewModel>()
            TutorScreen(vm.state,
                loadMore = {
                    vm.loadMore()
                },
                onItemClick = {
                    navController.navigate("${Screens.InApp.Tutor.TutorDetail.route}/$it")
                })
        }

        composable("${Screens.InApp.Tutor.TutorDetail.route}/{tutorId}",
            arguments = listOf(
                navArgument("tutorId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getInt("tutorId")
            val vm = hiltViewModel<TutorDetailViewModel>()
            LaunchedEffect(key1 = vm.state) {
                vm.getTutorDetail(tutorId!!)
            }
            TutorDetailScreen(state = vm.state.value,
                onNavigateIconClick = {
                    navController.popBackStack()
                })
        }

        composable(Screens.InApp.Courses.route) {
            val vm = hiltViewModel<CourseManagementViewModel>()
            ClassManagementScreen(navController, vm)
        }

        composable(
            "${Screens.InApp.Courses.CourseDetail.route}/{courseId}",
            arguments = listOf(navArgument("courseId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val courseDetailViewModel = hiltViewModel<RequestedCourseDetailViewModel>()
            RequestedCourseDetailScreen(
                navController,
                courseDetailViewModel,
                backStackEntry.arguments?.getInt("courseId")
            )
        }

        composable(Screens.InApp.Profile.route) { ProfileScreen(navController) }
        composable(Screens.InApp.Profile.TutorRegistration.route) {
            val vm = hiltViewModel<TutorRegisterViewModel>()
            TutorRegisterScreen(navController, vm)
        }
        composable(Screens.InApp.Profile.LearningCourses.route) {
            val vm = hiltViewModel<LearningCoursesViewModel>()
            LearningCourseScreen(vm.state.value,
                getLearningCourseCallback = {
                    vm.getLearningCourse(it)
                }, onNavigationIconClick = {
                    navController.popBackStack()
                },
                onCourseItemClick = { bundle ->
                    navController.navigate("${Screens.InApp.Profile.LearningCourses.TutorReview.route}/${bundle.courseId}")
                })
        }
        composable(
            "${Screens.InApp.Profile.LearningCourses.TutorReview.route}/{courseId}",
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.IntType
                },
            )
        ) {
            val courseId = it.arguments?.getInt("courseId")
            val vm = hiltViewModel<TutorReviewViewModel>()

            LaunchedEffect(key1 = vm.learningCourseDetailState) {
                vm.getLearningCourseData(token.value, courseId!!)
            }

            TutorReviewScreen(
                reviewState = vm.reviewTutorState.value,
                learningCourseDetailState = vm.learningCourseDetailState.value,
                onReviewButtonClick = { input ->
                    vm.sendReviewRequest(token.value, courseId!!, input)
                },
                onReviewSuccess = {
                    navController.popBackStack()
                },
                onReviewError = {
                    navController.popBackStack()
                })
        }
        authenticationGraph(navController)
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
