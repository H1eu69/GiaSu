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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Subject
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.LearningCourseBundle
import com.projectprovip.h1eu.giasu.presentation.profile.model.LearningCoursesState
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewLearningCourseScreen() {
    val dummyData = listOf(
        LearningCourse(
            "",
            "",
            500.3,
            "0967075340",
            "sddas",
            "heheehehehehe",
            200.3,
            "Male",
            50, "",
            "Female",
            "Hybrid",
            180,
            2,
            5,
            "Available",
            20,
            "Khoa hoc java cap toc 20p",
            "Vua java",
            50,
            "Huynh trung hieu",
            "0967075340"
        ),
        LearningCourse(
            "",
            "",
            500.3,
            "0967075340",
            "sddas",
            "heheehehehehe",
            200.3,
            "Male",
            50, "",
            "Female",
            "Online",
            180,
            2,
            5,
            "Available",
            20,
            "Khoa hoc java cap toc 20p",
            "Vua java",
            50,
            "Huynh trung hieu",
            "0967075340"
        ),
        LearningCourse(
            "",
            "",
            500.3,
            "0967075340",
            "sddas",
            "heheehehehehe",
            200.3,
            "Male",
            50, "",
            "Female",
            "Offline",
            180,
            2,
            5,
            "Available",
            20,
            "Khoa hoc java cap toc 20p",
            "Vua java",
            50,
            "Huynh trung hieu",
            "0967075340"
        ),
        LearningCourse(
            "",
            "",
            500.3,
            "0967075340",
            "sddas",
            "heheehehehehe",
            200.3,
            "Male",
            50, "",
            "Female",
            "Hybrid",
            180,
            2,
            5,
            "Available",
            20,
            "Khoa hoc java cap toc 20p",
            "Vua java",
            50,
            "Huynh trung hieu",
            "0967075340"
        ),
    )
    LearningCourseScreen(
        state = LearningCoursesState(data = dummyData),
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
            "",
            "",
            500.3,
            "0967075340",
            "sddas",
            "heheehehehehe",
            200.3,
            "Male",
            50, "",
            "Female",
            "Hybrid",
            180,
            2,
            5,
            "Available",
            20,
            "Khoa hoc java cap toc 20p",
            "Vua java",
            50,
            "Huynh trung hieu",
            "0967075340"
        ),
        onClick = {

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningCourseScreen(state: LearningCoursesState,
                         getLearningCourseCallback: (String) -> Unit,
                         onNavigationIconClick: () -> Unit,
                         onCourseItemClick: (LearningCourseBundle) -> Unit){
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
                    ), color = EDSColors.white
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = EDSColors.primaryColor
            ),
            navigationIcon = {
                IconButton(onClick = {
                    onNavigationIconClick()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "",
                        tint = EDSColors.white
                    )
                }
            }
        )
    }) {
        if (state.isLoading)
            CircularLoading(modifier = Modifier.padding(it))
        else if (state.data.isNotEmpty()) {
            LazyColumn() {
                state.data.forEach { learningCourse ->
                    item {
                        CourseItem(data = learningCourse,
                            onClick = {
                                val bundle = LearningCourseBundle(
                                    learningCourse.id,
                                    learningCourse.tutorId,
                                )
                                onCourseItemClick(bundle)
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun CircularLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun CourseItem(data: LearningCourse, onClick: () -> Unit) {
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
            MiddleContent(data.subjectName, data.subjectId, data.creationTime)
        }
    }
}

@Composable
fun SubTitle(subTitle: Int, status: String, learningMode: String) {
    var statusBackgroundColor = EDSColors.notScheduleBackgroundColor
    var statusTextColor = EDSColors.notScheduleTextColor

    var learningModeBackgroundColor = EDSColors.learningModeOnlineBackgroundColor
    var learningModeTextColor = EDSColors.learningModeOnlineTextColor

    if (status == "Available") {
        statusBackgroundColor = EDSColors.teachingBackgroundColor
        statusTextColor = EDSColors.teachingTextColor
    }

    if (learningMode == "Offline") {
        learningModeBackgroundColor = EDSColors.learningModeOfflineBackgroundColor
        learningModeTextColor = EDSColors.learningModeOfflineTextColor
    } else if (learningMode == "Hybrid") {
        learningModeBackgroundColor = EDSColors.learningModeHybridBackgroundColor
        learningModeTextColor = EDSColors.learningModeHybridTextColor
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "ID: $subTitle", style = TextStyle(
                fontWeight = FontWeight.Medium, color = EDSColors.myBlackColor, fontSize = 14.sp
            ), modifier = Modifier
                .background(
                    EDSColors.idClassBackgroundColor, RoundedCornerShape(30)
                )
                .padding(all = 4.dp)
        )
        Text(
            text = status, style = TextStyle(
                fontWeight = FontWeight.Medium, color = statusTextColor
            ), modifier = Modifier
                .background(
                    statusBackgroundColor, RoundedCornerShape(30)
                )
                .padding(all = 4.dp)
        )
        Text(
            text = learningMode, style = TextStyle(
                fontWeight = FontWeight.Medium, color = learningModeTextColor
            ), modifier = Modifier
                .background(
                    learningModeBackgroundColor, RoundedCornerShape(30)
                )
                .padding(all = 4.dp)
        )
    }
}

@Composable
fun IconAndText(imageVector: ImageVector, text: String) {
    Row {
        Icon(
            imageVector, null, tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Composable
fun MiddleContent(subjectName: String, subjectId: Int, creationTime: String) {
    Column(
        Modifier.padding(top = 12.dp, bottom = 20.dp)
    ) {
        IconAndText(Icons.Outlined.Info, "Subject ID: $subjectId")
        IconAndText(Icons.Outlined.Subject, subjectName)
        IconAndText(
            Icons.Outlined.DateRange, "Created at ${DateFormat.DD_MM_YYYY(creationTime)}"
        )
    }
}
