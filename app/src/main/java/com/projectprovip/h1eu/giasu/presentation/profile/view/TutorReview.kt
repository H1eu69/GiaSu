package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import com.projectprovip.h1eu.giasu.presentation.common.composes.EduSmartButton
import com.projectprovip.h1eu.giasu.presentation.common.composes.RatingBar
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.ReviewTutorState

@Preview
@Composable
fun PreviewTutorReviewScreen() {
    TutorReviewScreen(state = ReviewTutorState(),{})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorReviewScreen(state: ReviewTutorState,
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
        val rate = remember { mutableIntStateOf(0) }
        val reviewDescription = remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    focusManager.clearFocus()
                }
        ) {
            LaunchedEffect(key1 = state, ){
                state.apply {
                    when {
                        this.success -> {
                            Toast.makeText(context, this.message, Toast.LENGTH_SHORT).show()
                            onReviewSuccess()
                        }
                        this.message.isNotEmpty() -> {
                            Toast.makeText(context, this.message, Toast.LENGTH_SHORT).show()
                            onReviewError()
                        }
                    }
                }
            }

            TutorProfile("", "")
            RatingBar(
                Modifier.size(50.dp),
                rate = rate.intValue.toDouble(),
                onRateChange = { rates ->
                    rate.intValue = rates.toInt()
                })
            OutlinedTextField(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                label = {
                    Text(text = "Your review",)
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
            EduSmartButton(text = "Review", onClick = {
                onReviewButtonClick(ReviewTutorInput( rate = rate.intValue.toString(), detail = reviewDescription.value))
            },
                isLoading = state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
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