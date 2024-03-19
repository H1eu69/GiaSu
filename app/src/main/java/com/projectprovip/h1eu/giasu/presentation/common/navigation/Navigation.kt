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
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseInput
import com.projectprovip.h1eu.giasu.presentation.authentication.view.ForgetPasswordScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.LoginScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.SignUpScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.LoginViewModel
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.SignUpViewModel
import com.projectprovip.h1eu.giasu.presentation.common.composes.BottomBar
import com.projectprovip.h1eu.giasu.presentation.course_management.view.ClassManagementScreen
import com.projectprovip.h1eu.giasu.presentation.course_management.view.RequestedCourseDetailScreen
import com.projectprovip.h1eu.giasu.presentation.course_management.viewmodel.CourseManagementViewModel
import com.projectprovip.h1eu.giasu.presentation.course_management.viewmodel.RequestedCourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.home.model.SearchSuggestState
import com.projectprovip.h1eu.giasu.presentation.home.view.CourseDetailScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.HomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.SearchResultHomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.SearchSuggestHomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.CourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.HomeViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.view.CreateClassScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.LearningCourseScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.ProfileScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.TutorRegisterScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.TutorReviewScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.UpdateProfile
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.CreateClassViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.LearningCoursesViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.TutorRegisterViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.TutorReviewViewModel
import com.projectprovip.h1eu.giasu.presentation.splash.SplashScreen
import com.projectprovip.h1eu.giasu.presentation.tutor.view.SearchResultTutorScreen
import com.projectprovip.h1eu.giasu.presentation.tutor.view.SearchSuggestTutorScreen
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
            LoginScreen(navController, onLoginClick = { s1, s2 ->
                viewModel.loginByEmail(s1, s2)
            }, state = viewModel.loginState.value)
        }
        composable(Screens.Authentication.Signup.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            LaunchedEffect(Unit) {
                viewModel.getProvince()
            }
            SignUpScreen(
                navController,
                validate = { firstName, lastName, email, password, username, phone, birthYear, city ->
                    viewModel.validate(firstName, lastName, email, password, username, phone, birthYear, city)
                },
                onRegisterClicked = { input ->
                    viewModel.signUp(input)
                },
                signUpState = viewModel.signUpState.value,
                provinceState = viewModel.provinceState.value
            )
        }
        composable(Screens.Authentication.ForgetPassword.route) {
            ForgetPasswordScreen({ navController.popBackStack() })
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
            HomeScreen(navController, homeViewModel.courseDetailState.value)
        }
        composable(Screens.InApp.Home.SearchSuggest.route) {
            SearchSuggestHomeScreen(navController, SearchSuggestState())
        }
        composable(Screens.InApp.Home.SearchResult.route) {
            SearchResultHomeScreen(navController, homeViewModel.courseDetailState.value)
        }
        composable(
            "${Screens.InApp.Home.ClassDetail.route}/{courseId}",
            arguments = listOf(navArgument("courseId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val courseDetailViewModel = hiltViewModel<CourseDetailViewModel>()

            val courseId = backStackEntry.arguments?.getString("courseId")
            val courseDetail =
                if (courseId != null) homeViewModel.getClassDetailById(courseId) else null

            CourseDetailScreen(
                navController,
                courseDetail,
                courseDetailViewModel.courseRegisterState.value,
                onRegisterClicked = {
                    courseDetailViewModel.registerCourse(courseId!!, token.value)
                }
            )
        }
        composable(Screens.InApp.Tutor.route) {
            val vm = hiltViewModel<TutorViewModel>()
            TutorScreen(
                vm.state,
                loadMore = {
                    vm.loadMore()
                },
                navController = navController,
            )
        }

        composable(Screens.InApp.Tutor.SearchSuggest.route) {
            SearchSuggestTutorScreen(navController)
        }
        composable(Screens.InApp.Tutor.SearchResult.route) {
            SearchResultTutorScreen(navController)
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

            ClassManagementScreen(navController,
                state = vm.state.value,
                callback = {
                    vm.getRequestedCourses(token.value)
                },
                getListByFilter = {
                    vm.getListByFilter(it)
                })
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
                courseDetailViewModel.state.value,
                { id ->
                    courseDetailViewModel.getRequestedCourseDetail(id, token.value)
                },
                backStackEntry.arguments?.getInt("courseId")
            )
        }

        composable(Screens.InApp.Profile.route) { ProfileScreen(navController) }
        composable(Screens.InApp.Profile.TutorRegistration.route) {
            val vm = hiltViewModel<TutorRegisterViewModel>()
            TutorRegisterScreen(navController,
                vm.tutorRegisterState.value,
                registerTutor = { s1, s2, s3 ->
                    vm.registerTutor(token.value, s1, s2, s3)
                },
                uploadImage = { uri ->
                    vm.uploadImage(uri)
                })
        }

        composable(Screens.InApp.Profile.UpdateProfile.route) {
            UpdateProfile(navController)
        }

        composable(Screens.InApp.Profile.RequestClass.route) {
            val vm = hiltViewModel<CreateClassViewModel>()
            val createCourse: (CreateCourseInput) -> Unit = {
                vm.requestClass(token.value, it)
            }
            CreateClassScreen(navController, vm.state.value, createCourse)
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
                onNavigationIconClick = {
                    navController.popBackStack()
                },
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
