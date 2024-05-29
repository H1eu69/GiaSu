package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Subject
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.common.DateFormat
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.ShimmerCourse
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentModel
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
private fun PreviewCoursePaymentScreen() {
    val state = CoursePaymentState(
        courses = listOf(
            CoursePaymentModel(),
            CoursePaymentModel(
                paymentStatus = "Pending",
            ),
            CoursePaymentModel(
                paymentStatus = "Canceled",

                ),
            CoursePaymentModel(),
            CoursePaymentModel(),
            CoursePaymentModel(),
            CoursePaymentModel(),
            CoursePaymentModel(),
        )
    )
    Surface {
        CoursePaymentScreen(
            navController = rememberNavController(),
            state = state,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursePaymentScreen(
    navController: NavController,
    state: CoursePaymentState,
) {
    LaunchedEffect(key1 = Unit) {
        delay(5000)
        navController.navigate(
//                                    "${Screens.InApp.Profile.CoursePayment.CoursePaymentDetail}/${course.courseId}"
            "${Screens.InApp.Profile.CoursePayment.CoursePaymentDetail.route}/${123}/Completed"
        )
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Your Payment", style = TextStyle(
                        fontWeight = FontWeight.Bold, fontSize = 18.sp
                    ), color = EDSColors.primaryColor
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = EDSColors.white
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "",
                        tint = EDSColors.primaryColor
                    )
                }
            }
        )
    }) {
        if (state.isLoading)
            Column(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerCourse()
                ShimmerCourse()
                ShimmerCourse()
                ShimmerCourse()
                ShimmerCourse()
            }
        else if (state.courses.isNotEmpty()) {
            LazyColumn {
                state.courses.forEach { course ->
                    item {
                        CourseItem(data = course,
                            onClick = {
                                navController.navigate(
//                                    "${Screens.InApp.Profile.CoursePayment.CoursePaymentDetail}/${course.courseId}"
                                    "${Screens.InApp.Profile.CoursePayment.CoursePaymentDetail.route}/${123}/Completed"
                                )
                            })
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.empty_box),
                    onRetry = { failCount, exception ->
                        Log.d("LottieAnimation", failCount.toString())
                        Log.d("LottieAnimation", exception.toString())
                        // Continue retrying. Return false to stop trying.
                        false
                    })

                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )

            }
        }
    }
}

@Preview
@Composable
private fun PreviewPaymentItem() {
    CourseItem(
        data = CoursePaymentModel(),
        onClick = {}
    )
}

@Composable
private fun CourseItem(
    data: CoursePaymentModel,
    onClick: () -> Unit
) {
    Card(shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Blue
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.outlinedCardElevation(2.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10)
            )
            .clickable {
                onClick()
            }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            AppBarTitle(text = data.courseTitle)
            SubTitle(data.courseId, data.paymentStatus)
        }
    }
}

@Composable
private fun SubTitle(subTitle: String, status: String) {
    var statusBackgroundColor = EDSColors.waitingBackgroundColor
    var statusTextColor = EDSColors.waitingTextColor

    if (status == "Completed" || status == "Confirmed") {
        statusBackgroundColor = EDSColors.teachingBackgroundColor
        statusTextColor = EDSColors.teachingTextColor
    } else if (status == "Canceled") {
        statusBackgroundColor = EDSColors.notScheduleBackgroundColor
        statusTextColor = EDSColors.notScheduleTextColor
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "ID: $subTitle",
            style = TextStyle(
                fontWeight = FontWeight.Medium, color = EDSColors.myBlackColor, fontSize = 14.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(0.1f)
                .background(
                    EDSColors.idClassBackgroundColor, RoundedCornerShape(30)
                )
                .padding(8.dp)
        )
        Text(
            text = status, style = TextStyle(
                fontWeight = FontWeight.Medium, color = statusTextColor
            ), modifier = Modifier
                .background(
                    statusBackgroundColor, RoundedCornerShape(30)
                )
                .padding(8.dp)
        )
    }
}

@Composable
private fun IconAndText(imageVector: ImageVector, text: String) {
    Row {
        Icon(
            imageVector, null, tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Composable
private fun MiddleContent(subjectName: String, creationTime: String) {
    Column(
        Modifier.padding(top = 12.dp, bottom = 20.dp)
    ) {
        IconAndText(Icons.AutoMirrored.Outlined.Subject, subjectName)
        IconAndText(
            Icons.Outlined.DateRange, "Created at ${DateFormat.DD_MM_YYYY_ISO(creationTime)}"
        )
    }
}
