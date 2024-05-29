package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.CodeGenerator
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.ShimmerCourse
import com.projectprovip.h1eu.giasu.presentation.common.Instants
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.common.toVndFormat
import com.projectprovip.h1eu.giasu.presentation.common.usdToVnd
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseRegisterState
import com.projectprovip.h1eu.giasu.presentation.home.model.RecommendCoursesState
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentDetailModel
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentDetailState
import com.projectprovip.h1eu.giasu.presentation.profile.view.CircularLoading

@Preview
@Composable
private fun PreviewScreen() {
    CoursePaymentDetailScreen(
        rememberNavController(),
        courseDetailState = CoursePaymentDetailState(
            courses = CoursePaymentDetailModel()
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursePaymentDetailScreen(
    navController: NavController,
    courseDetailState: CoursePaymentDetailState,
    status: String = "Completed"
) {

    val context = LocalContext.current
    val course = courseDetailState.courses

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = EDSColors.white
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            "",
                            tint = EDSColors.primaryColor
                        )
                    }
                },
                title = {
                    Text(
                        text = "Course Information",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = EDSColors.primaryColor,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
        containerColor = EDSColors.white
    ) {
        courseDetailState.apply {
            when {
                this.isLoading -> {
                    CircularLoading(
                        color = EDSColors.primaryColor
                    )
                }

                !this.error.isNullOrEmpty() -> {

                }

                else -> {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .background(
                                EDSColors.white
                            )
                    ) {
                        CourseDetailBody(
                            navController = navController,
                            course = course,
                            paymentStatus = status
                        )


                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CourseDetailBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    course: CoursePaymentDetailModel,
    paymentStatus: String
) {

    var statusTextColor = EDSColors.notScheduleTextColor
    var backgroundTextColor = EDSColors.notScheduleBackgroundColor

    if (paymentStatus == "Completed") {
        statusTextColor = EDSColors.teachingTextColor
        backgroundTextColor = EDSColors.teachingBackgroundColor
    } else if (paymentStatus == "Pending") {
        statusTextColor = EDSColors.waitingTextColor
        backgroundTextColor = EDSColors.waitingBackgroundColor
    }

//    if (learningMode == "Offline") {
//        learningModeBackgroundColor = EDSColors.learningModeOfflineBackgroundColor
//        learningModeTextColor = EDSColors.learningModeOfflineTextColor
//    }

    val context = LocalContext.current
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .background(EDSColors.white),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            androidx.compose.material3.Text(
                text = paymentStatus,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = statusTextColor
                ),
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundTextColor)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
        item {
            Text(
                text = course.title, modifier = modifier
                    .padding(horizontal = 20.dp),
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .padding(horizontal = 20.dp),
            ) {

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
                course.sessionPerWeek, course.sessionDuration,
                modifier = modifier
                    .padding(horizontal = 20.dp),
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
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .background(
                        EDSColors.idClassBackgroundColor,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
            )
        }
        item {
            com.projectprovip.h1eu.giasu.presentation.home.view.BottomContent(
                fee = course.fee, createdDate = course.creationTime,
                modifier = modifier
                    .padding(horizontal = 20.dp),
            )
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun DetailIconAndText(
    imageVector: ImageVector,
    boldedText: String,
    text: String,
    textColor: Color = EDSColors.blackColor,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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
        }
        Text(
            text = text,
            textAlign = TextAlign.End,
            color = textColor,
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
private fun SessionSectionPreview() {
    SessionSection(
        dayAWeek = 1,
        minPerDay = 45
    )
}

@Composable
private fun SessionSection(dayAWeek: Int, minPerDay: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
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

private fun concat(s1: String, s2: String) = "$s1/$s2"