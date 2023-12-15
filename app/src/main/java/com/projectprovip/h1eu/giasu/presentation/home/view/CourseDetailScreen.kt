package com.projectprovip.h1eu.giasu.presentation.home.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.CourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewScreen() {
    CourseDetailScreen(
        rememberNavController(),
        hiltViewModel(),
        hiltViewModel(),
        1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    courseDetailViewModel: CourseDetailViewModel,
    courseId: Int?
) {

    val course = if (courseId != null) homeViewModel.getClassDetailById(courseId) else null
    val courseRegisterState = courseDetailViewModel.courseRegisterState
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    var token = remember { mutableStateOf("") }


    LaunchedEffect(key1 = ""){
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[stringPreferencesKey(Constant.TOKEN_STRING)]}"
            }
        }
    }
    Scaffold(
        topBar = {
            CourseDetailAppbar(navController)
        },
    ) {
        Box(modifier = Modifier
            .padding(it)
            .padding(20.dp)) {
            CourseDetailBody(navController = navController, course = course!!)
            Button(
                onClick = {
                    courseDetailViewModel.registerCourse(courseId!!, token.value)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
            ) {
                if (courseRegisterState.value.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Tax: $${course.chargeFee} Register now", color = EDSColors.white)
                }
                LaunchedEffect(key1 = courseRegisterState.value, ){
                    if (courseRegisterState.value.error) {
                        showToast(context, courseRegisterState.value.message)
                    } else if (courseRegisterState.value.isSuccess) {
                        showToast(context, courseRegisterState.value.message)
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun CourseDetailBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    course: CourseDetail
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = course.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        item {
            Text(
                text = course.description,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = EDSColors.myBlackColor,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .background(
                        EDSColors.idClassBackgroundColor,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
            )
        }
        item {
            BottomContent(fee = course.fee, createdDate = course.creationTime)
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Info,
                "Subject: ", course.subjectName
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Info,
                "Status: ", course.status
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Phone,
                "Contact number: ", course.contactNumber
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Person,
                "Tutor gender requirement: ", course.genderRequirement
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Face,
                "Student gender: ", course.learnerGender
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Person,
                "Number of learner: ", course.numberOfLearner.toString()
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Info,
                "Learning mode: ", course.learningMode
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Info,
                "Academic Requirement: ", course.academicLevelRequirement
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Place,
                "Address: ", course.address
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Info,
                "Session: ", pluralStringResource(
                    R.plurals.session_string,
                    course.sessionPerWeek,
                    course.sessionPerWeek,
                    course.minutePerSession
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailAppbar(navController: NavController) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = EDSColors.primaryColor
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    "",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = "Course Detail",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    )
}

@Composable
fun DetailIconAndText(imageVector: ImageVector, boldedText: String, text: String) {
    Row {
        Icon(
            imageVector, null,
            tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = boldedText,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

