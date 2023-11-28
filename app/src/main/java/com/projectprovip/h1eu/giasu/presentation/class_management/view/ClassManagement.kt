package com.projectprovip.h1eu.giasu.presentation.class_management.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.Subject
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse
import com.projectprovip.h1eu.giasu.presentation.class_management.viewmodel.CourseManagementViewModel
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewClassManagementScreen() {
    ClassManagementScreen(navController = rememberNavController(), hiltViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassManagementScreen(navController: NavController, vm: CourseManagementViewModel) {
    val tabSelectedIndex = remember {
        mutableIntStateOf(0)
    }
    val list = listOf("All", "Success", "Canceled", "Pending")
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    var token = remember { mutableStateOf("") }
    val state = vm.state
    val listRequestedCourse = vm.filteredList

    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[stringPreferencesKey(Constant.TOKEN_STRING)]}"
            }
        }
    }
    if(state.value.data.isEmpty()){
        vm.getRequestedCourses(token.value)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                AppBarTitle(text = "Courses")
            })
        }
    ) {
        LazyColumn(
            Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ScrollableTabRow(
                    selectedTabIndex = tabSelectedIndex.value,
                    edgePadding = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    list.forEachIndexed { index, item ->
                        Tab(text = { Text(item) },
                            selected = tabSelectedIndex.value == index,
                            unselectedContentColor = Color.LightGray,
                            onClick = {
                                tabSelectedIndex.value = index
                                vm.getListByFilter(item)
                            }
                        )
                    }
                }
            }
            when {
                state.value.isLoading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }

                state.value.message.isNotEmpty() -> {
                    Toast.makeText(context, state.value.message, Toast.LENGTH_SHORT).show()
                }

                state.value.data.isNotEmpty() -> {
                    if (listRequestedCourse.value.isNotEmpty()) {
                        listRequestedCourse.value.forEach {
                            item {
                                ClassItem(it, navController)
                            }
                        }
                    } else {
                        item {
                            Text(
                                text = "No items to show",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
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
