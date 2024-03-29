package com.projectprovip.h1eu.giasu.presentation.course_management.view

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Subject
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.course_management.model.RequestedCourseState

@Preview
@Composable
fun PreviewClassManagementScreen() {
    ClassManagementScreen(navController = rememberNavController(),
        state = RequestedCourseState(),
        getListByFilter = {s ->})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassManagementScreen(
    navController: NavController,
    state: RequestedCourseState,
    callback: () -> Unit = {},
    getListByFilter: (String) -> Unit,
) {
    val tabSelectedIndex = remember {
        mutableIntStateOf(0)
    }
    val list = listOf("All", "Success", "Canceled", "Verifying")
    val context = LocalContext.current

    LaunchedEffect(key1 = "",) {
        callback()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                AppBarTitle(text = "Courses")
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TabRow(
                selectedTabIndex = tabSelectedIndex.intValue,
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEachIndexed { index, item ->
                    Tab(text = { Text(item) },

                        selected = tabSelectedIndex.intValue == index,
                        unselectedContentColor = Color.LightGray,
                        onClick = {
                            tabSelectedIndex.intValue = index
                            getListByFilter(item)
                        },
                    )
                }
            }
            UIBasedOnState(state, context, navController = navController)
        }
    }
}

@Composable
fun UIBasedOnState(
    state: RequestedCourseState,
    context: Context,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val openDialog = remember { mutableStateOf(true) }

    when {
        state.message.isNotEmpty() -> {
            if (state.message == "Error403 Forbidden") {
                TutorRegisterAlertDialog(open = openDialog.value,
                    onDismissRequest = {
                        openDialog.value = false
                        navController.popBackStack()
                    }, onConfirmation = {
                        openDialog.value = false
                        navController.popBackStack()
                        navController.navigate(Screens.InApp.Profile.TutorRegistration.route)
                    })
            } else
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }

        state.isLoading -> {
            showLoading(modifier = modifier.fillMaxSize())
        }

        state.data.isNotEmpty() -> {
            if (state.filteredData.isNotEmpty()) {
                ListCourses(
                    modifier,
                    data = state.filteredData,
                    navController
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_box),
                        onRetry = {
                                failCount, exception ->
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
}

@Composable
fun TutorRegisterAlertDialog(
    open: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    when {
        open -> {
            AlertDialog(
                title = {
                    Text(text = "You don't have permission to access")
                },
                text = {
                    Text(text = "Please register as tutor to access this")
                },
                onDismissRequest = {
                    onDismissRequest()
                },
                confirmButton = {
                    TextButton(onClick = { onConfirmation() }) {
                        Text("Accept")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onDismissRequest()
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

@Composable
fun showLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ListCourses(
    modifier: Modifier = Modifier,
    data: List<RequestedCourse>,
    navController: NavController
) {
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        data.forEach {
            item {
                ClassItem(it, navController)
            }
        }
    }
}

@Composable
fun ClassItem(data: RequestedCourse, navController: NavController) {
    Card(
        shape = RoundedCornerShape(10),
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
                navController.navigate("${Screens.InApp.Courses.CourseDetail.route}/${data.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            AppBarTitle(text = data.title)
            SubTitle(data.id, data.requestStatus)
            MiddleContent(data.subjectName, data.courseId, data.creationTime)
        }
    }
}

@Composable
fun SubTitle(subTitle: Int, status: String) {
    var backgroundColor = EDSColors.notScheduleBackgroundColor
    var textColor = EDSColors.notScheduleTextColor

    if (status == "Success") {
        backgroundColor = EDSColors.teachingBackgroundColor
        textColor = EDSColors.teachingTextColor
    } else if (status == "Verifying") {
        backgroundColor = EDSColors.waitingTextColor
        textColor = EDSColors.waitingBackgroundColor
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
                fontWeight = FontWeight.Medium,
                color = EDSColors.myBlackColor,
                fontSize = 14.sp
            ),
            modifier = Modifier
                .background(
                    EDSColors.idClassBackgroundColor,
                    RoundedCornerShape(30)
                )
                .padding(all = 4.dp)
        )
        Text(
            text = status,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                color = textColor
            ),
            modifier = Modifier
                .background(
                    backgroundColor,
                    RoundedCornerShape(30)
                )
                .padding(all = 4.dp)
        )
    }
}

@Composable
fun IconAndText(imageVector: ImageVector, text: String) {
    Row {
        Icon(
            imageVector, null,
            tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Composable
fun MiddleContent(subjectName: String, courseId: Int, creationTime: String) {
    Column(
        Modifier.padding(top = 12.dp, bottom = 20.dp)
    ) {
        IconAndText(Icons.Outlined.Subject, subjectName)
        IconAndText(Icons.Outlined.Info, "Course ID: $courseId")
        IconAndText(
            Icons.Outlined.DateRange,
            "Created at ${DateFormat.DD_MM_YYYY(creationTime)}"
        )
    }
}
