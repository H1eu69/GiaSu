package com.projectprovip.h1eu.giasu.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.model.SearchSuggestState

@Preview
@Composable
fun SearchSuggestHomeScreenPreview() {
    SearchSuggestHomeScreen(
        rememberNavController(),
        state = SearchSuggestState(
            data = listOf(
                "Note 13 Pro",
                "Lop hoc python",
                "Hoc guitar",
                "Nguyen Bao Chau",
                "Goi om sieu mem",
                "Note 13 Pro",
                "Lop hoc python",
                "Hoc guitar",
                "Nguyen Bao Chau",
                "Goi om sieu mem",
                "Note 13 Pro",
                "Lop hoc python",
                "Hoc guitar",
                "Nguyen Bao Chau",
                "Goi om sieu mem",
                "Note 13 Pro",
                "Lop hoc python",
                "Hoc guitar",
                "Nguyen Bao Chau",
                "Goi om sieu mem"
            )
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSuggestHomeScreen(
    navController: NavController,
    state: SearchSuggestState
) {
    val focusManager = LocalFocusManager.current
    val searchTextField = remember {
        mutableStateOf("")
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
                                    text = "Enter course name, subjects and so on",
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 30.dp),
            modifier = Modifier
                .padding(it)
        ) {
            item {
                SuggestChipRow(navController)
            }
            state.apply {
                when {
                    this.isLoading -> {
                        item {
                            CircularProgressIndicator(
                                color = EDSColors.primaryColor
                            )
                        }
                    }

                    this.data.isNotEmpty() -> {
                        data.forEach {
                            item {
                                Box(modifier = Modifier) {
                                    SearchSuggestItem(
                                        data = it
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestChipRow(navController: NavController) {
    val listChip = listOf("Math", "Physics", "Geography", "advanced math", "java", "python")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        listChip.forEachIndexed { index, title ->
            when (index) {
                0 -> item {
                    AssistChip(
                        modifier = Modifier.padding(start = 16.dp),
                        label = {
                            Text(title, fontWeight = FontWeight.W300)
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
                            navController.navigate(Screens.InApp.Home.SearchResult.route)
                        }
                    )
                }

                listChip.count() - 1 -> item {
                    AssistChip(
                        modifier = Modifier.padding(end = 16.dp),
                        label = {
                            Text(title, fontWeight = FontWeight.W300)
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

                        }
                    )
                }

                else -> item {
                    AssistChip(
                        label = {
                            Text(title, fontWeight = FontWeight.W300)
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

                        }
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchSuggestItemPreview() {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            SearchSuggestItem(data = "Note 13 Pro")
        }
    }
}

@Composable
fun SearchSuggestItem(data: String) {
    Column(
    ) {
        Text(
            text = data, fontSize = 12.sp, fontWeight = FontWeight.W300,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
        )
        Divider()
    }
}