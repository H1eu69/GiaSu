package com.projectprovip.h1eu.giasu.presentation.tutor.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.tutor.model.TutorListState

@Preview
@Composable
fun SearchResultTutorScreenPreview() {
    SearchResultTutorScreen(
        rememberNavController(),
        state = TutorListState(
            data = listOf(
                Tutor(), Tutor(), Tutor(), Tutor(), Tutor(), Tutor(), Tutor()
            ),
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultTutorScreen(
    navController: NavController,
    state: TutorListState = TutorListState(
        data = listOf(
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor(),
            Tutor()
        )
    )
) {
    val focusManager = LocalFocusManager.current
    val searchTextField = remember {
        mutableStateOf("Lap trinh java Ho Chi Minh")
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        "",
                        tint = EDSColors.primaryColor
                    )
                }
                BasicTextField(
                    value = searchTextField.value,
                    onValueChange = {
                        searchTextField.value = it
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    cursorBrush = SolidColor(EDSColors.darkCyan)
                ) { innerTextField ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                EDSColors.white,
                                RoundedCornerShape(50)
                            )
                            .border(
                                width = 1.dp, EDSColors.primaryColor,
                                RoundedCornerShape(50)
                            )
                            .height(IntrinsicSize.Max)
                            .fillMaxWidth(.85f),
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(.85f)
                                .padding(start = 16.dp, end = 8.dp)
                        ) {
                            innerTextField()
                            if (searchTextField.value.isEmpty()) {
                                Text(
                                    text = "Lap Trinh Java",
                                    style = TextStyle.Default,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400,
                                    color = EDSColors.gray,
                                    modifier = Modifier.padding(start = 2.dp)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                                .background(
                                    EDSColors.primaryColor,
                                    RoundedCornerShape(
                                        topEndPercent = 50,
                                        bottomEndPercent = 50
                                    )
                                )
                        ) {
                            Icon(
                                Icons.Default.Search, contentDescription = null,
                                tint = EDSColors.white,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(it)
        ) {
            when {
                state.isLoading -> item {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                }

                state.data.isNotEmpty() -> {
                    item(
                        span = {
                            GridItemSpan(maxCurrentLineSpan)
                        }
                    ) {
                        Row {
                            AssistChip(
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                label = {
                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.W300
                                                )
                                            ) {
                                                append("Result for")
                                            }
                                            append(" ")
                                            append("Lap trinh Java Ho Chi Mnh")
                                        }
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Sharp.Close,
                                        contentDescription = null,
                                        tint = EDSColors.darkCyan,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                shape = RoundedCornerShape(50),
                                border = AssistChipDefaults.assistChipBorder(
                                    borderColor = EDSColors.transparent
                                ),
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = EDSColors.grayX2.copy(0.3f),
                                    labelColor = EDSColors.chipTextColor,
                                ),
                                onClick = {
                                    navController.popBackStack()
                                }
                            )
                            Box {}
                        }
                    }
                    items(count = state.data.count()) { index ->
                        TutorItem(state.data[index], onItemClick = { tutorId ->
                            navController.navigate("${Screens.InApp.Tutor.TutorDetail.route}/$tutorId")
                        })
                        if (index == state.data.count() - 2) {
                            //onLoadMore()
                        }
                    }
                }
            }
        }
    }
}