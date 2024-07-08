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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.presentation.common.DateFormat
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.ShimmerCourse
import com.projectprovip.h1eu.giasu.presentation.common.thangNguBECourseStatus
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.LearningCourseBundle
import com.projectprovip.h1eu.giasu.presentation.profile.model.ListLearningCourseState
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewLearningCourseScreen() {
    val dummyData = listOf(
        LearningCourse(
            "2015-08-30T22:13:04",
            "2efe7384-cb24-4d85-a685-a4016e62e2f5",
            "2023-12-13T20:34:59.6344398",
            "Cancel",
            "Java programmisng",
            "heheehehehehe",
            "Online"
        ),
        LearningCourse(
            "2015-08-30T22:13:04",
            "2efe7384-cb24-4d85-a685-a4016e62e2f5",
            "2023-12-13T20:34:59.6344398",
            "Available",
            "Piano",
            "heheehehehehe",
            "Hybrid"
        ),
        LearningCourse(
            "2015-08-30T22:13:04",
            "2efe7384-cb24-4d85-a685-a4016e62e2f5",
            "2023-12-13T20:34:59.6344398",
            "Verifying",
            "Khoa hoc lap trinh 10p",
            "heheehehehehe",
            "Offline"

        ),
        LearningCourse(
            "2015-08-30T22:13:04",
            "2efe7384-cb24-4d85-a685-a4016e62e2f5",
            "2023-12-13T20:34:59.6344398",
            "0967075340",
            "Vua hai tac",
            "heheehehehehe",
            "Online"
        ),
    )
    LearningCourseScreen(
        state = ListLearningCourseState(data = dummyData),
        {},
        {},
        {}
    )
}

@Preview
@Composable
fun PreviewCourseItem() {
    CourseItem(
        data = LearningCourse(
            "2015-08-30T22:13:04",
            "2efe7384-cb24-4d85-a685-a4016e62e2f5",
            "2023-12-13T20:34:59.6344398",
            "0967075340",
            "sddas",
            "heheehehehehe",
            "Online"
            ),
        onClick = {

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningCourseScreen(
    state: ListLearningCourseState,
    getLearningCourseCallback: (String) -> Unit,
    onNavigationIconClick: () -> Unit,
    onCourseItemClick: (LearningCourseBundle) -> Unit
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val token = remember { mutableStateOf("") }

    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[stringPreferencesKey(Constant.TOKEN_STRING)]}"
                getLearningCourseCallback(token.value)
            }
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Your learning course", style = TextStyle(
                        fontWeight = FontWeight.Bold, fontSize = 18.sp
                    ), color = EDSColors.primaryColor
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = EDSColors.white
            ),
            navigationIcon = {
                IconButton(onClick = {
                    onNavigationIconClick()
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
                modifier = Modifier.padding(it).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerCourse()
                ShimmerCourse()
                ShimmerCourse()
                ShimmerCourse()
                ShimmerCourse()
            }
        else if (state.data.isNotEmpty()) {
            LazyColumn {
                state.data.forEach { learningCourse ->
                    val newLearningCourse = learningCourse.copy(
                        status = learningCourse.status.replace("On","")
                    )
                    item {
                        CourseItem(data = newLearningCourse,
                            onClick = {
                                val bundle = LearningCourseBundle(newLearningCourse.id)
                                onCourseItemClick(bundle)
                            })
                    }
                }
            }
        }
        else {
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

@Composable
fun CircularLoading(modifier: Modifier = Modifier,
                    color: Color = EDSColors.primaryColor,) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center),
            color = color)
    }
}

@Composable
private fun CourseItem(data: LearningCourse, onClick: () -> Unit) {
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
            AppBarTitle(text = data.title)
            SubTitle(data.id, data.status, data.learningMode)
            MiddleContent(data.subjectName, data.creationTime)
        }
    }
}

@Composable
private fun SubTitle(subTitle: String, status: String, learningMode: String) {
    var statusBackgroundColor = EDSColors.waitingBackgroundColor
    var statusTextColor = EDSColors.waitingTextColor

    val formattedStatus = status.thangNguBECourseStatus()

    if (formattedStatus == "Available" || formattedStatus == "Confirmed") {
        statusBackgroundColor = EDSColors.teachingBackgroundColor
        statusTextColor = EDSColors.teachingTextColor
    } else if(formattedStatus == "Canceled") {
        statusBackgroundColor = EDSColors.notScheduleBackgroundColor
        statusTextColor = EDSColors.notScheduleTextColor
    }

    var learningModeBackgroundColor = EDSColors.learningModeOnlineBackgroundColor
    var learningModeTextColor = EDSColors.learningModeOnlineTextColor

    if (learningMode == "Offline") {
        learningModeBackgroundColor = EDSColors.learningModeOfflineBackgroundColor
        learningModeTextColor = EDSColors.learningModeOfflineTextColor
    } else if (learningMode == "Hybrid") {
        learningModeBackgroundColor = EDSColors.purpleBackground
        learningModeTextColor = EDSColors.purpleText
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
            text = formattedStatus, style = TextStyle(
                fontWeight = FontWeight.Medium, color = statusTextColor
            ), modifier = Modifier
                .background(
                    statusBackgroundColor, RoundedCornerShape(30)
                )
                .padding(8.dp)
        )
        Text(
            text = learningMode, style = TextStyle(
                fontWeight = FontWeight.Medium, color = learningModeTextColor
            ), modifier = Modifier
                .background(
                    learningModeBackgroundColor, RoundedCornerShape(30)
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
