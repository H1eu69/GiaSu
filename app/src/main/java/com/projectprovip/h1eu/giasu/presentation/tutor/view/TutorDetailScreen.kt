package com.projectprovip.h1eu.giasu.presentation.tutor.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.domain.tutor.model.TutorDetail
import com.projectprovip.h1eu.giasu.presentation.common.composes.MultiColorText
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.tutor.model.TutorDetailState


@Composable
@Preview
fun PreviewTutorDetailScreen() {
    val dummyData = TutorDetailState(
        data = TutorDetail()
    )
    TutorDetailScreen(dummyData)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorDetailScreen(
    state: TutorDetailState,
    onNavigateIconClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.primaryColor
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateIconClick()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            null,
                            tint = EDSColors.white
                        )
                    }
                })
        }
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(bottom = 50.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Profile(
                        image = state.data.image,
                        name = "${state.data.firstName} ${state.data.lastName}",
                        id = state.data.id,
                        rate = state.data.rate
                    )
                }

                item {
                    Text(
                        text = state.data.description,
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
                        Icons.Filled.LocationCity,
                        "Location"
                    ) {
                        Text(
                            text = state.data.address,
                            textAlign = TextAlign.End,
                            color = EDSColors.costTextColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }

                }
                item {
                    DetailIconAndText(
                        Icons.Filled.Cake,
                        "Birth year",
                    ) {
                        Text(
                            text = state.data.birthYear.toString(),
                            textAlign = TextAlign.End,
                            color = EDSColors.costTextColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                }
                item {
                    DetailIconAndText(
                        Icons.Filled.AutoStories,
                        "Graduation",
                    ) {
                        Text(
                            text = state.data.academicLevel,
                            textAlign = TextAlign.End,
                            color = EDSColors.costTextColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }

                }
                item {
                    DetailIconAndText(Icons.Filled.Subject, "Subjects") {
                        Column(horizontalAlignment = Alignment.End) {
                            state.data.subjects.forEach { subject ->
                                Text(
                                    text = subject,
                                    textAlign = TextAlign.End,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))

                }
                item {
                    Text(text = "Ratings and reviews", fontSize = 20.sp)

                }
                item {
                    Spacer(modifier = Modifier.height(6.dp))

                }
                item {
                    Text(text = "Ratings and reviews are verified and are from people who learn the same course as you do",
                        fontSize = 12.sp)

                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))

                }
                state.data.reviewDetailDtos.forEach {
                    item {
                        ReviewItem(
                            rate = it.rate,
                            reviewer = it.learnerName,
                            review = it.detail
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }
            }
        }
    }
}

@Composable
fun Profile(image: String, name: String, id: Int, rate: Int) {
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
            )
        }
        Text(
            text = name, style = TextStyle(
                fontSize = 16.sp,
                color = EDSColors.primaryColor
            )
        )
        MultiColorText(
            text1 = "ID: ",
            color1 = EDSColors.primaryColor,
            text2 = id.toString(),
            color2 = Color.Black
        )
        Row {
            Text(
                text = rate.toString(), style = TextStyle(
                    fontSize = 16.sp,
                )
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = EDSColors.yellowStar
            )
        }
    }
}

@Composable
fun DetailIconAndText(imageVector: ImageVector, boldedText: String, text: @Composable () -> Unit) {
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
        text()
    }
}

@Composable
@Preview
fun PreviewReviewItem() {
    ReviewItem(
        3,
        "Hieu",
        "Thang nay day ngu vai lz"
    )
}

@Composable
fun ReviewItem(rate: Int, reviewer: String, review: String) {
    Row() {
        //AsyncImage(model = "", contentDescription = null)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //Text(text = reviewer)
            Row {
                for (i in 1..5) {
                    if (i <= rate)
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "",
                            tint = EDSColors.yellowStar,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    else androidx.compose.material.Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = "",
                        tint = EDSColors.yellowStar,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
            Text(text = review)
        }
    }
}