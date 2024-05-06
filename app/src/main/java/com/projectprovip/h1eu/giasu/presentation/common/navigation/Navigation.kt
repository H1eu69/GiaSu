@file:OptIn(ExperimentalMaterial3Api::class)

package com.projectprovip.h1eu.giasu.presentation.common.navigation

import android.util.Log
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
import com.projectprovip.h1eu.giasu.common.toEDSIntGender
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateTutorInfoParams
import com.projectprovip.h1eu.giasu.presentation.authentication.view.ForgetPasswordScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.LoginScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.view.SignUpScreen
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.LoginViewModel
import com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel.SignUpViewModel
import com.projectprovip.h1eu.giasu.presentation.common.composes.BottomBar
import com.projectprovip.h1eu.giasu.presentation.course_management.view.ClassManagementScreen
import com.projectprovip.h1eu.giasu.presentation.course_management.view.RequestedCourseDetailScreen
import com.projectprovip.h1eu.giasu.presentation.course_management.viewmodel.CourseManagementViewModel
import com.projectprovip.h1eu.giasu.presentation.course_management.viewmodel.CourseManagementDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.home.model.SearchSuggestState
import com.projectprovip.h1eu.giasu.presentation.home.view.CourseDetailScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.HomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.SearchResultHomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.view.SearchSuggestHomeScreen
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.CourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.HomeViewModel
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.SearchResultViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.view.CreateClassScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.LearningCourseScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.LocationPickScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.ProfileScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.TutorRegisterScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.TutorReviewScreen
import com.projectprovip.h1eu.giasu.presentation.profile.view.UpdateProfile
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.CreateClassViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.LearningCoursesViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.LocationPickViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.TutorRegisterViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.TutorReviewViewModel
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.UpdateProfileViewModel
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
                    viewModel.validate(
                        firstName,
                        lastName,
                        email,
                        password,
                        username,
                        phone,
                        birthYear,
                        city
                    )
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
    val avatar = remember { mutableStateOf("") }

    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[stringPreferencesKey(Constant.TOKEN_STRING)]}"
                avatar.value = "${preference[stringPreferencesKey(Constant.USER_IMAGE_STRING)]}"
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
            HomeScreen(navController, homeViewModel.homeState.value,
                onLoadMore = {
                    homeViewModel.getCourses()
                })
        }
        composable(
            Screens.InApp.Home.SearchSuggest.route,

            ) {
            SearchSuggestHomeScreen(
                navController, SearchSuggestState(),
            )
        }
        composable(
            "${Screens.InApp.Home.SearchSuggest.route}/{searchText}",
            arguments = listOf(navArgument("searchText") { type = NavType.StringType })
        ) {
            val searchText = it.arguments?.getString("searchText")
            SearchSuggestHomeScreen(
                navController, SearchSuggestState(),
                searchText
            )
        }
        composable(
            "${Screens.InApp.Home.SearchResult.route}/{searchText}",
            arguments = listOf(navArgument("searchText") { type = NavType.StringType })
        ) {
            val vm = hiltViewModel<SearchResultViewModel>()
            val searchText = it.arguments?.getString("searchText")
            LaunchedEffect(Unit) {
                vm.getCourses(searchText!!)
            }
            SearchResultHomeScreen(
                navController, vm.searchResultState.value,
                searchText
            )
        }
        composable(
            "${Screens.InApp.Home.ClassDetail.route}/{courseId}",
            arguments = listOf(navArgument("courseId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val courseDetailViewModel = hiltViewModel<CourseDetailViewModel>()

            val courseId = backStackEntry.arguments?.getString("courseId")
            LaunchedEffect(Unit) {
                courseDetailViewModel.getCourseById(courseId!!)
            }
            LaunchedEffect(courseDetailViewModel.courseDetailState.value.data.subjectId) {
                if(courseDetailViewModel.courseDetailState.value.data.subjectId > 0)
                courseDetailViewModel.getRecommendedCoursesName(courseDetailViewModel.courseDetailState.value.data.subjectId.toString())
            }
            LaunchedEffect(courseDetailViewModel.recommendedCourseNameState.value) {
                courseDetailViewModel.getRecommendedCourses()
            }

            CourseDetailScreen(
                navController,
                courseDetailState = courseDetailViewModel.courseDetailState.value,
                courseRegisterState = courseDetailViewModel.courseRegisterState.value,
                recommendCourseState = courseDetailViewModel.recommendedCourseState.value,
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
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getString("tutorId")
            val vm = hiltViewModel<TutorDetailViewModel>()
            Log.d("Test tutorId tutor reivew", tutorId.toString())
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
                    vm.getCourses(token.value)
                },
//                getLearningCoursesCallBack = {
//                    vm.getLearningCourses(token.value)
//                },
//                getRequestedCoursesCallBack = {
//                    vm.getRequestedCourses(token.value)
//                },
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
            val courseDetailViewModel = hiltViewModel<CourseManagementDetailViewModel>()

            val id = backStackEntry.arguments?.getString("courseId")

            if (courseDetailViewModel.state.value.data == null) {
                courseDetailViewModel.getRequestedCourseDetail(id!!, token.value)

            }
            RequestedCourseDetailScreen(
                navController,
                courseDetailViewModel.state.value
            )
        }

        composable(Screens.InApp.Profile.route) { ProfileScreen(navController) }
        composable(Screens.InApp.Profile.TutorRegistration.route) {
            val vm = hiltViewModel<TutorRegisterViewModel>()
            LaunchedEffect(Unit) {
                vm.getSubject()
            }
            TutorRegisterScreen(
                navController,
                vm.tutorRegisterState.value,
                vm.subjectState.value,
                registerTutor = { s1, s2, s3, s4 ->
                    vm.registerTutor(token.value, s1, s2, s3, s4)
                },
            )
        }

        composable(Screens.InApp.Profile.UpdateProfile.route) {
            val vm = hiltViewModel<UpdateProfileViewModel>()
            Log.d("test get avatar", avatar.value)
            LaunchedEffect(Unit) {
                vm.getProfile(token.value)
                vm.getSubject()
            }
            UpdateProfile(
                navController,
                getProfileState = vm.getProfileState.value,
                getTutorInfoState = vm.getTutorInfoState.value,
                updateProfileState = vm.updateProfileState.value,
                subjectState = vm.subjectState.value,
                onUpdateBtnClick = { image, email, birthYear, address, country, description, firstName, gender, lastName, phoneNumber, academic, university, majors ->
//                    val realAvatar = if(avatar.value == image) "" else image
                    val majorIdList = majors.map {
                        it.subjectId
                    }
                    vm.updateProfile(
                        token.value,
                        UpdateProfileParams(
                            avatar = image, //will change
                            email = email,
                            birthYear = birthYear,
                            city = address,
                            country = country,
                            description = description,
                            firstName = firstName,
                            gender = gender.toEDSIntGender(),
                            lastName = lastName,
                            phoneNumber = phoneNumber,
                        )
                    )
                    vm.updateTutorInfo(
                        token.value,
                        UpdateTutorInfoParams(
                            academicLevel = academic,
                            university = university,
                            majors = majorIdList
                        )
                    )
                }
            )
        }

        composable(Screens.InApp.Profile.RequestClass.route) {
            val vm = hiltViewModel<CreateClassViewModel>()
            val createCourse: (CreateCourseParams) -> Unit = {
                vm.requestClass(token.value, it)
            }
            CreateClassScreen(navController, vm.state.value, createCourse)
        }
        composable(Screens.InApp.Profile.RequestClass.LocationPick.route) {
            val vm = hiltViewModel<LocationPickViewModel>()
            LaunchedEffect(Unit) {
                vm.getProvince()
            }
            LocationPickScreen(navController, state = vm.state.value,
                onSelectProvince = {
                    vm.selectProvince(it)
                },
                onSelectDistrict = {
                    vm.selectDistrict(it)
                },
                onSelectWard = {
                    vm.selectWard(it)
                },
                onGetDistrict = {
                    vm.getDistrict(it)
                },
                onGetWard = {
                    vm.getWard(it)
                })
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
                    type = NavType.StringType
                },
            )
        ) {
            val courseId = it.arguments?.getString("courseId")
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