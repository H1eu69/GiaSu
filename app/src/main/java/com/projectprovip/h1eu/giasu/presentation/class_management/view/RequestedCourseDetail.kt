package com.projectprovip.h1eu.giasu.presentation.class_management.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Face5
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Subject
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail
import com.projectprovip.h1eu.giasu.presentation.class_management.viewmodel.RequestedCourseDetailViewModel
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import kotlinx.coroutines.launch

@Composable
@Preview
fun PreviewDetailScreen() {
    RequestedCourseDetailScreen(
        rememberNavController(),
        hiltViewModel(),
        1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestedCourseDetailScreen(
    navController: NavController,
    vm: RequestedCourseDetailViewModel,
    id: Int?
) {

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    var token = remember { mutableStateOf("") }
    val state = vm.state

    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[stringPreferencesKey(Constant.TOKEN_STRING)]}"
            }
        }
    }
    if (state.value.data == null) {
        vm.getRequestedCourseDetail(id = id!!, token.value)
    }

    Scaffold(
        topBar = {
            CourseDetailAppbar(navController)
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            state.apply {
                when {
                    state.value.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    state.value.data != null -> {
                        Log.d("z`", state.value.data.toString())
                        RequestedCourseDetailBody(
                            navController = navController,
                            course = state.value.data!!
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RequestedCourseDetailBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    course: RequestedCourseDetail
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Title: ${course.title}",
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
            DetailIconAndText(
                imageVector = Icons.Outlined.Subject,
                boldedText = "Subject: ",
                contentText = {
                    Text(
                        text = course.subjectName,
                        fontSize = 16.sp
                    )
                }
            )
        }
        item {
            DetailIconAndText(
                imageVector = Icons.Outlined.Info,
                boldedText = "Status: ", contentText = {
                    Text(
                        text = course.requestStatus,
                        fontSize = 16.sp
                    )
                }
            )
        }
        item {
            DetailIconAndText(
                imageVector = Icons.Outlined.Phone,
                boldedText = "Contact number: ", contentText = {
                    Text(
                        text = course.learnerContact,
                        fontSize = 16.sp,
                        color = EDSColors.costTextColor,
                        modifier = Modifier.clickable {
                            val uri = Uri.parse("tel: ${course.learnerContact}")
                            val dialIntent = Intent(Intent.ACTION_DIAL, uri)
                            context.startActivity(dialIntent)
                        }
                    )
                }
            )
        }
        item {
            DetailIconAndText(
                imageVector = Icons.Outlined.Person,
                boldedText = "Learner name: ", contentText = {
                    Text(
                        text = course.learnerName,
                        fontSize = 16.sp
                    )
                }
            )
        }
        item {
            DetailIconAndText(
                imageVector = Icons.Outlined.Face5,
                boldedText = "Tutor name: ", contentText = {
                    Text(
                        text = course.tutorName,
                        fontSize = 16.sp
                    )
                }
            )
        }
        item {
            DetailIconAndText(
                imageVector = Icons.Outlined.Phone,
                boldedText = "Tutor phone: ", contentText = {
                    Text(
                        text = course.tutorPhone,
                        fontSize = 16.sp,
                        color = EDSColors.costTextColor,
                        modifier = Modifier.clickable {
                            val uri = Uri.parse("tel: ${course.tutorPhone}")
                            val dialIntent = Intent(Intent.ACTION_DIAL, uri)
                            context.startActivity(dialIntent)
                        }
                    )
                }
            )
        }
    //        item {
    //            DetailIconAndText(
    //                Icons.Outlined.Person,
    //                "Tutor email: ", course.tu
    //            )
    //        }
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
fun DetailIconAndText(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    boldedText: String,
    contentText: @Composable (() -> Unit),
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
        Spacer(modifier = Modifier.width(8.dp))
        contentText()
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

