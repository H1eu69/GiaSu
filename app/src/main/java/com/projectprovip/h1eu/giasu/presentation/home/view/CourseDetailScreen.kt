package com.projectprovip.h1eu.giasu.presentation.home.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseRegisterState
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.CourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewScreen() {
    CourseDetailScreen(
        rememberNavController(),
        course = CourseDetail(),
        courseRegisterState = CourseRegisterState(),
        onRegisterClicked = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    navController: NavController,
    course: CourseDetail?,
    courseRegisterState: CourseRegisterState,
    onRegisterClicked: () -> Unit,
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CourseDetailAppbar(navController)
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
        ) {
            CourseDetailBody(navController = navController, course = course!!)
            Button(
                onClick = {
                    onRegisterClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
            ) {
                if (courseRegisterState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Tax: $${course.chargeFee} Register now", color = EDSColors.white)
                }
                LaunchedEffect(key1 = courseRegisterState) {
                    if (courseRegisterState.error) {
                        showToast(context, courseRegisterState.message)
                    } else if (courseRegisterState.isSuccess) {
                        showToast(context, courseRegisterState.message)
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
    var statusTextColor = EDSColors.notScheduleTextColor

    if (course.status == "Available") {
        statusTextColor = EDSColors.teachingTextColor
    } else if (course.status == "Verifying") {
        statusTextColor = EDSColors.waitingBackgroundColor
    }

    val  context = LocalContext.current
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                androidx.compose.material3.Text(
                    text = course.status,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = statusTextColor
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(EDSColors.greenBackground)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )

                androidx.compose.material3.Text(
                    text = course.learningMode,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = EDSColors.purpleText
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(EDSColors.purpleBackground)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }

        }

        item {
            SessionSection(
                course.sessionPerWeek, course.sessionDuration
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
//        item {
//            DetailIconAndText(
//                Icons.Outlined.Info,
//                "Status: ", course.status
//            )
//        }
        item {
            DetailIconAndText(
                Icons.Outlined.Phone,
                "Contact number: ", course.contactNumber,
                textColor = EDSColors.costTextColor,
                modifier = Modifier.clickable {
                    val uri = Uri.parse("tel: ${course.contactNumber}")
                    val dialIntent = Intent(Intent.ACTION_DIAL, uri)
                    context.startActivity(dialIntent)
                }
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Person,
                "Tutor gender: ", course.genderRequirement
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
                "Academic: ", course.academicLevelRequirement
            )
        }
        item {
            DetailIconAndText(
                Icons.Outlined.Place,
                "Address: ", course.address
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
fun DetailIconAndText(imageVector: ImageVector,
                      boldedText: String,
                      text: String,
textColor: Color = EDSColors.blackColor,
                      modifier: Modifier = Modifier) {
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
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = text,
            textAlign = TextAlign.End,
            color = textColor,
             fontSize = 16.sp,
            modifier = modifier
        )
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun SessionSectionPreview() {
    SessionSection(
        dayAWeek = 1,
        minPerDay = 45
    )
}

@Composable
fun SessionSection( dayAWeek: Int, minPerDay: Int) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(EDSColors.greenBackground)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayAWeek.toString(), color = EDSColors.greenText,
                fontSize = 24.sp
            )
            Text(text = pluralStringResource(id = R.plurals.day_per_week_string, count = dayAWeek))
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(EDSColors.orangeBackground)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = minPerDay.toString(),
                color = EDSColors.orangeText,
                fontSize = 24.sp
            )
            Text(text = "minutes / day")
        }
    }
}