package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import com.projectprovip.h1eu.giasu.domain.course.model.isValid
import com.projectprovip.h1eu.giasu.presentation.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.RatingBar
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.LearningCourseDetailState
import com.projectprovip.h1eu.giasu.presentation.profile.model.ReviewTutorState

@Preview
@Composable
fun PreviewTutorReviewScreen() {
    TutorReviewScreen(reviewState = ReviewTutorState(),
        learningCourseDetailState = LearningCourseDetailState(),
        {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorReviewScreen(
    reviewState: ReviewTutorState,
    learningCourseDetailState: LearningCourseDetailState,
    onReviewButtonClick: (ReviewTutorInput) -> Unit,
    onReviewSuccess: () -> Unit = {},
    onReviewError: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Review Tutor", color = EDSColors.white)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = EDSColors.white
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.primaryColor
                )
            )
        }
    ) {
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }
        val context = LocalContext.current
        val rate = remember { mutableIntStateOf(1) }
        val reviewDescription = remember { mutableStateOf("") }
        val verticalScrollState = rememberScrollState()

        learningCourseDetailState.apply {
            when {
                this.isLoading -> {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            color = EDSColors.primaryColor,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                this.data.isValid() -> {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .verticalScroll(verticalScrollState)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                focusManager.clearFocus()
                            }
                    ) {
                        LaunchedEffect(key1 = reviewState) {
                            reviewState.apply {
                                when {
                                    this.success -> {
                                        Toast.makeText(context, this.message, Toast.LENGTH_SHORT)
                                            .show()
                                        onReviewSuccess()
                                    }

                                    this.message.isNotEmpty() -> {
                                        Toast.makeText(context, this.message, Toast.LENGTH_SHORT)
                                            .show()
                                        onReviewError()
                                    }
                                }
                            }
                        }
                        var statusBackgroundColor = EDSColors.notScheduleBackgroundColor
                        var statusTextColor = EDSColors.notScheduleTextColor

                        if (learningCourseDetailState.data.status == "Available") {
                            statusBackgroundColor = EDSColors.teachingBackgroundColor
                            statusTextColor = EDSColors.teachingTextColor
                        }

                        androidx.compose.material.Text(
                            text = learningCourseDetailState.data.title,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        androidx.compose.material.Text(
                            text = learningCourseDetailState.data.status,
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = statusTextColor,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier
                                .background(
                                    statusBackgroundColor,
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                        )

                        androidx.compose.material.Text(
                            text = learningCourseDetailState.data.description,
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
                        BottomContent(
                            fee = learningCourseDetailState.data.fee,
                            createdDate = learningCourseDetailState.data.creationTime
                        )

                        DetailIconAndText(
                            Icons.Outlined.Info,
                            "Subject: ", learningCourseDetailState.data.subjectName
                        )


                        DetailIconAndText(
                            Icons.Outlined.Phone,
                            "Contact number: ", learningCourseDetailState.data.contactNumber
                        )

                        DetailIconAndText(
                            Icons.Outlined.Person,
                            "Tutor gender requirement: ",
                            learningCourseDetailState.data.genderRequirement
                        )

                        DetailIconAndText(
                            Icons.Outlined.Face,
                            "Student gender: ", learningCourseDetailState.data.learnerGender
                        )

                        DetailIconAndText(
                            Icons.Outlined.Person,
                            "Number of learner: ",
                            learningCourseDetailState.data.numberOfLearner.toString()
                        )

                        DetailIconAndText(
                            Icons.Outlined.Info,
                            "Learning mode: ", learningCourseDetailState.data.learningMode
                        )

                        DetailIconAndText(
                            Icons.Outlined.Info,
                            "Academic Requirement: ",
                            learningCourseDetailState.data.academicLevelRequirement
                        )

                        DetailIconAndText(
                            Icons.Outlined.Place,
                            "Address: ", learningCourseDetailState.data.address
                        )

                        DetailIconAndText(
                            Icons.Outlined.Info,
                            "Session: ", pluralStringResource(
                                R.plurals.session_string,
                                learningCourseDetailState.data.sessionPerWeek,
                                learningCourseDetailState.data.sessionPerWeek,
                                learningCourseDetailState.data.minutePerSession
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row {
                            Icon(
                                Icons.Outlined.Reviews, null,
                                tint = EDSColors.primaryColor
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            androidx.compose.material.Text(
                                text = "How do you feel about this course?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        RatingBar(
                            modifier = Modifier
                                .fillMaxWidth(),
                            rate = rate.intValue,
                            onRateChange = { rates ->
                                rate.intValue = rates
                            })
                        OutlinedTextField(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            label = {
                                Text(text = "Your review")
                            },
                            keyboardActions = KeyboardActions(),
                            maxLines = 4,
                            shape = RoundedCornerShape(12.dp),
                            onValueChange = { value ->
                                reviewDescription.value = value
                            },
                            value = reviewDescription.value,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = EDSColors.primaryColor,
                                focusedLabelColor = EDSColors.primaryColor,
                                cursorColor = EDSColors.primaryColor,
                            ),
                        )
                        Spacer(modifier = Modifier.weight(1.0f)) // fill height with spacer
                        EduSmartButton(
                            text = "Review", onClick = {
                                onReviewButtonClick(
                                    ReviewTutorInput(
                                        rate = rate.intValue.toString(),
                                        detail = reviewDescription.value
                                    )
                                )
                            },
                            isLoading = reviewState.isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun TutorProfile(image: String, name: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .requiredSize(150.dp)
        ) {
            AsyncImage(
                image,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable {

                    })
        }
        androidx.compose.material.Text(
            text = name, style = TextStyle(
                fontSize = 16.sp,
                color = EDSColors.primaryColor
            )
        )
    }
}

@Composable
fun DetailIconAndText(
    imageVector: ImageVector,
    boldedText: String,
    text: String
) {
    Row {
        Icon(
            imageVector, null,
            tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        androidx.compose.material.Text(
            text = boldedText,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        androidx.compose.material.Text(
            text = text,
            textAlign = TextAlign.End,
            color = EDSColors.costTextColor,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}

@Composable
fun BottomContent(fee: Double, createdDate: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$fee $",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EDSColors.costTextColor
            )
        )
        com.projectprovip.h1eu.giasu.presentation.home.view.IconAndText(
            Icons.Outlined.DateRange,
            DateFormat.DD_MM_YYYY(createdDate)
        )
    }
}