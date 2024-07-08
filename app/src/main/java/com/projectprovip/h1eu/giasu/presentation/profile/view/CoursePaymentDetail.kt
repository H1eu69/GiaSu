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
import com.projectprovip.h1eu.giasu.presentation.common.thangNguBECourseStatus
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.common.toVndFormat
import com.projectprovip.h1eu.giasu.presentation.common.usdToVnd
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseRegisterState
import com.projectprovip.h1eu.giasu.presentation.home.model.RecommendCoursesState
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentDetailModel
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentDetailState
import com.projectprovip.h1eu.giasu.presentation.profile.model.NotifyCoursePaymentState
import com.projectprovip.h1eu.giasu.presentation.profile.view.CircularLoading

@Preview
@Composable
private fun PreviewScreen() {
    CoursePaymentDetailScreen(
        rememberNavController(),
        courseDetailState = CoursePaymentDetailState(
            courses = CoursePaymentDetailModel()
        ), NotifyCoursePaymentState()
        ,onRegisterClicked = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursePaymentDetailScreen(
    navController: NavController,
    courseDetailState: CoursePaymentDetailState,    notifyState: NotifyCoursePaymentState,

    status: String = "Completed",
    onRegisterClicked: () -> Unit,

    ) {

    val context = LocalContext.current
    val course = courseDetailState.courses
    val showBottomSheet = remember { mutableStateOf(false) }
    val code = remember {
        mutableStateOf(CodeGenerator.generate())
    }
    if (showBottomSheet.value) {
        CourseRegisterPaymentBottomSheet(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = {
                showBottomSheet.value = false
            },
            onButtonClick = {
                onRegisterClicked()
            },
            code = code.value,
            fee = course.fee.usdToVnd(),
            tax = course.chargeFee.usdToVnd()
        )
    }
    LaunchedEffect(key1 = notifyState) {
        notifyState.apply {
            when {
                this.isSuccess -> {
                    Toast.makeText(context, "We will check and confirm shortly", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                this.error.isNotEmpty() -> {
                    Toast.makeText(context,  this.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
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
                            paymentStatus = status,
                        )
                        if(status == "Pending")
                        Button(
                            onClick = {
                                showBottomSheet.value = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
                        ) {
                            Text(
                                text = "Tax: $${course.chargeFee} Register now",
                                color = EDSColors.white
                            )
//                            LaunchedEffect(key1 = courseRegisterState) {
//
//                                if (courseRegisterState.error) {
//                                    Toast.makeText(
//                                        context,
//                                        courseRegisterState.message,
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                } else if (courseRegisterState.isSuccess) {
//                                    Toast.makeText(
//                                        context,
//                                        courseRegisterState.message,
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                    navController.popBackStack()
//                                }
//
//                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CourseDetailBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    course: CoursePaymentDetailModel,
    paymentStatus: String
    ) {

    var statusTextColor = EDSColors.waitingTextColor
    var statusBackgroundColor = EDSColors.waitingBackgroundColor
    val formattedStatus = paymentStatus.thangNguBECourseStatus()

    if (formattedStatus == "Completed" || formattedStatus == "Confirmed") {
        statusBackgroundColor = EDSColors.teachingBackgroundColor
        statusTextColor = EDSColors.teachingTextColor
    } else if (formattedStatus == "Canceled" || formattedStatus == "Canceled With Refund") {
        statusBackgroundColor = EDSColors.notScheduleBackgroundColor
        statusTextColor = EDSColors.notScheduleTextColor
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
                text = formattedStatus,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = statusTextColor
                ),
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(statusBackgroundColor)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseRegisterPaymentBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    onButtonClick: () -> Unit = {},
    code: String,
    fee: Double = 1000.00,
    tax: Double = 200.00
) {
    val context = LocalContext.current

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
        containerColor = EDSColors.white
    ) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "To complete registration, please transfer money according to content:",
                    style = EDSTextStyle.H2Reg()
                )
            }
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = Instants.getVietQrImage(
                            addInfo = "Register ES $code",
                            amount = (fee + tax).toBigDecimal().toPlainString()
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Course fee: ", style = EDSTextStyle.H2Reg(
                            EDSColors.gray

                        )
                    )
                    Text(
                        text = "${fee.toBigDecimal().toPlainString().toVndFormat()}",
                        style = EDSTextStyle.H1MedBold(
                        )
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tax: ", style = EDSTextStyle.H2Reg(
                            EDSColors.gray

                        )
                    )
                    Text(
                        text = "${tax.toBigDecimal().toPlainString().toVndFormat()}",
                        style = EDSTextStyle.H1MedBold(
                        )
                    )
                }
            }
            item {
                Divider(modifier = Modifier.padding(horizontal = 8.dp), color = EDSColors.grayX3)

            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total amount: ", style = EDSTextStyle.H2Reg(
                            EDSColors.gray

                        )
                    )
                    Text(
                        text = "${(fee + tax).toBigDecimal().toPlainString().toVndFormat()}",
                        style = EDSTextStyle.H1MedBold(
                        )
                    )
                }
            }
            item {
                Text(
                    text = "Done payment? Notify us by clicking button below",
                    modifier = Modifier.padding(top = 8.dp),
                    style = EDSTextStyle.H2Reg()
                )
            }
            item {
                ElevatedButton(
                    onClick = {
//                        onButtonClick()
//                        val deepLinkIntent = Intent(
//                            Intent.ACTION_VIEW,
//                            "https://www.bidvsmartbanking.vn"
//                                .toUri(),
//                        )
//                        startActivity(context, deepLinkIntent, null)
                        onButtonClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = EDSColors.primaryColor,
                    )
                ) {
                    Text("Notify payment", style = EDSTextStyle.H2Reg(EDSColors.white))
                }
            }
        }
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