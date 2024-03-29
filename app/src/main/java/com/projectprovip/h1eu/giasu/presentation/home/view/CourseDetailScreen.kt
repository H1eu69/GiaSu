package com.projectprovip.h1eu.giasu.presentation.home.view

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.CodeGenerator
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.authentication.model.ProvinceItem
import com.projectprovip.h1eu.giasu.presentation.common.composes.OtpInputField
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseRegisterState
import com.projectprovip.h1eu.giasu.presentation.profile.view.CircularLoading

@Preview
@Composable
fun PreviewScreen() {
    CourseDetailScreen(
        rememberNavController(),
        courseDetailState = CourseDetailState(),
        courseRegisterState = CourseRegisterState(),
        onRegisterClicked = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    navController: NavController,
    courseDetailState: CourseDetailState,
    courseRegisterState: CourseRegisterState,
    onRegisterClicked: () -> Unit,
) {

    val context = LocalContext.current
    val showBottomSheet = remember { mutableStateOf(false) }
    val course = courseDetailState.data

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
                        text = "Course Detail",
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
                    if (showBottomSheet.value) {
                        CourseRegisterPaymentBottomSheet(
                            modifier = Modifier.padding(16.dp),
                            onDismissRequest = {
                                showBottomSheet.value = false
                            },
                            onButtonClick = {
                                onRegisterClicked()
                            },
                            fee = course?.fee ?: -1,
                            tax = course?.chargeFee?.toInt() ?: -1
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .padding(20.dp)
                            .background(
                                EDSColors.white
                            )
                    ) {
                        CourseDetailBody(navController = navController, course = course!!)
                        Button(
                            onClick = {
                                showBottomSheet.value = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
                        ) {
                            if (courseRegisterState.isLoading) {
                                CircularProgressIndicator()
                            } else {
                                Text(
                                    text = "Tax: $${course.chargeFee} Register now",
                                    color = EDSColors.white
                                )
                            }
                            LaunchedEffect(key1 = courseRegisterState) {

                                if (courseRegisterState.error) {
                                    Toast.makeText(context, courseRegisterState.message, Toast.LENGTH_SHORT)
                                        .show()
                                } else if (courseRegisterState.isSuccess) {
                                    Toast.makeText(context, courseRegisterState.message, Toast.LENGTH_SHORT)
                                        .show()
                                    navController.popBackStack()
                                }

                            }
                        }
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

    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxHeight()
            .background(EDSColors.white),
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

@Composable
fun DetailIconAndText(
    imageVector: ImageVector,
    boldedText: String,
    text: String,
    textColor: Color = EDSColors.blackColor,
    modifier: Modifier = Modifier
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

@Preview
@Composable
fun SessionSectionPreview() {
    SessionSection(
        dayAWeek = 1,
        minPerDay = 45
    )
}

@Composable
fun SessionSection(dayAWeek: Int, minPerDay: Int) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CourseRegisterPaymentDialogPreview() {
    Surface {
        CourseRegisterPaymentBottomSheet(
            modifier = Modifier.padding(16.dp), onDismissRequest = {

            })
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
    fee: Int = 100000,
    tax: Int = 20000
) {
    val context = LocalContext.current
    val otpValue = remember { mutableStateOf("") }
    val isOtpFilled = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val code = CodeGenerator.generate()

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "To complete registration, please transfer money according to content:",
                style = EDSTextStyle.H2Reg()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Vietinbank (VTB): ", style = EDSTextStyle.H2Thin(
                        EDSColors.gray
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString(("107867236970")))
                        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()

                    }
                ) {
                    Image(
                        imageVector = Icons.Default.ContentCopy, contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(
                            EDSColors.gray
                        )
                    )
                    Text(
                        text = "107867236970",
                        style = EDSTextStyle.H2Reg(
                            EDSColors.primaryColor
                        )
                    )

                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Account Owner: ", style = EDSTextStyle.H2Thin(
                        EDSColors.gray
                    )
                )
                Text(
                    text = "Huỳnh Trung Hiếu",
                    style = EDSTextStyle.H2Reg(
                        EDSColors.primaryColor
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Content: ", style = EDSTextStyle.H2Thin(
                        EDSColors.gray
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.ContentCopy, contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(
                            EDSColors.gray
                        )
                    )
                    Text(
                        text = "Register ES $code",
                        style = EDSTextStyle.H2Reg(
                            EDSColors.primaryColor
                        )
                    )

                }
            }
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
                    text = "$fee đ",
                    style = EDSTextStyle.H1MedBold(
                    )
                )
            }
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
                    text = "$tax đ",
                    style = EDSTextStyle.H1MedBold(
                    )
                )
            }
            Divider(modifier = Modifier.padding(horizontal = 8.dp), color = EDSColors.grayX3)
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
                    text = "${fee + tax} đ",
                    style = EDSTextStyle.H1MedBold(
                    )
                )
            }
            Text(
                text = "After transferring money, please fill in the Registration Code in a box below. Registration Code is:",
                style = EDSTextStyle.H2Reg()
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    code.forEach { digit ->
                        Text(
                            text = digit.toString(),
                            style = EDSTextStyle.H1Large(
                                EDSColors.primaryColor
                            )
                        )
                    }
                }

            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                OtpInputField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .focusRequester(focusRequester),
                    otpText = otpValue.value,
                    shouldCursorBlink = false,
                    onOtpModified = { value, otpFilled ->
                        otpValue.value = value
                        isOtpFilled.value = otpFilled
                        if (otpFilled) {
                            keyboardController?.hide()
                        }
                    }
                )
            }
            ElevatedButton(
                onClick = { onButtonClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = EDSColors.primaryColor,
                )
            ) {
                Text("Send request", style = EDSTextStyle.H2Reg(EDSColors.white))
            }
        }
    }

}

@Composable
private fun CourseRegisterDialogText(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subTitle: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        title()
        subTitle()
    }
}