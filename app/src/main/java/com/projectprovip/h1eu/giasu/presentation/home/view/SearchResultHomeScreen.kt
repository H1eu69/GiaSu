package com.projectprovip.h1eu.giasu.presentation.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import kotlinx.coroutines.launch

@Preview
@Composable
fun SearchResultHomeScreenPreview() {
    SearchResultHomeScreen(
        rememberNavController(),
        state = CourseDetailState(
            data = listOf(
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
            ),
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultHomeScreen(
    navController: NavController,
    state: CourseDetailState = CourseDetailState()
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    CompositionLocalProvider(
        LocalLayoutDirection provides
                LayoutDirection.Rtl
    ) {
        ModalNavigationDrawer(
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(
                    LocalLayoutDirection provides
                            LayoutDirection.Ltr
                ) {
                    ModalDrawerSheet(
                        drawerShape = RectangleShape,
                        drawerContainerColor = EDSColors.white,
                        modifier = Modifier.fillMaxWidth(.8f)

                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(EDSColors.grayX3)
                                .padding(12.dp)
                        ) {
                            Text("Search Filters", style = EDSTextStyle.H1Med())
                        }

                        FilterItem(
                            modifier = Modifier.padding(8.dp),
                            title = "location",
                            filterItems = listOf(
                                "Tay ninh",
                                "TP HCm",
                                "Ha long",
                                "Vinh phuc",
                                "Ha noi",
                                "TP HCm",
                                "Ha long",
                                "Vinh phuc",
                                "Ha noi",
                                "TP HCm",
                                "Ha long",
                                "Vinh phuc",
                                "Ha noi",
                                "TP HCm",
                                "Ha long",
                                "Vinh phuc",
                                "Ha noi"
                            )
                        )
                        BudgetRangeSection(
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            },
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides
                        LayoutDirection.Ltr
            ) {
                Scaffold(
                    topBar = {
                        SearchResultAppBar(
                            navController,
                            onFilterClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        )
                    },
                    containerColor = EDSColors.white
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 30.dp),
                        modifier = Modifier
                            .padding(it)
                    ) {
                        item {
                            ResultChip(navController, "Note 13 pro")
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
                                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                                CourseItem(
                                                    navController = navController,
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
        }
    }
}

@Composable
fun ResultChip(navController: NavController, result: String) {
    AssistChip(
        modifier = Modifier.padding(start = 16.dp),
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
                    append(result)
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
}

@Composable
fun SearchResultAppBar(
    navController: NavController,
    onFilterClick: () -> Unit
) {
    val searchTextField = remember {
        mutableStateOf("Note 13 Pro")
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
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
                .fillMaxWidth(.9f)
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
                            text = "Find courses, tutor and so on",
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
        FilterButton(
            onClick = onFilterClick
        )

    }
}

@Composable
fun FilterButton(onClick: () -> Unit) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box {
        Image(
            imageVector = ImageVector.vectorResource(
                R.drawable.icon_filter
            ),
            contentDescription = null,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
        )
    }
}

@Preview
@Composable
fun FilterItemPreview() {
    val list1 = listOf(
        "Tay ninh",
        "TP HCm",
        "Ha long",
        "Vinh phuc",
        "Ha noi",
        "Tay ninh",
        "TP HCm",
        "Ha long",
        "Vinh phuc",
        "Ha noi",
        "Tay ninh",
        "TP HCm",
        "Ha long",
        "Vinh phuc",
        "Ha noi"
    )
    val list2 = listOf("Ha long", "Vinh phuc", "Ha noi")
    val list3 = listOf("Tay ninh", "Ha long")
    val list4 = listOf("Tay ninh")

    Column {
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItems = list1,
            hasViewMore = list1.count() > 4
        )
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItems = list2,
            hasViewMore = list2.count() > 4
        )
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItems = list3,
            hasViewMore = list3.count() > 4
        )
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItems = list4,
            hasViewMore = list4.count() > 4
        )
    }

}

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    title: String, filterItems: List<String>,
    hasViewMore: Boolean = true,
    onSelected: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val wantViewMore = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        Text(
            title, style = EDSTextStyle.H2Reg(),
            modifier = Modifier.padding(vertical = 12.dp)
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            filterItems.forEachIndexed { index, filterItem ->
                if (!wantViewMore.value) {
                    if (index <= 3)
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp, vertical = 2.dp)
                                    .fillMaxWidth()
                                    .background(EDSColors.grayX3)
                                    .clickable {
                                        onSelected()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = filterItem, style = EDSTextStyle.H3Reg(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                } else {
                    if (index <= 9)
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp, vertical = 2.dp)
                                    .fillMaxWidth()
                                    .background(EDSColors.grayX3)
                                    .clickable {
                                        onSelected()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = filterItem, style = EDSTextStyle.H3Reg(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                }
            }
        }
        if (hasViewMore) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    if (!wantViewMore.value) "View more" else "Collapse",
                    style = EDSTextStyle.H3Reg(
                        EDSColors.gray
                    ),
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            wantViewMore.value = !wantViewMore.value
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    imageVector = ImageVector.vectorResource(
                        id = if (!wantViewMore.value)
                            R.drawable.icon_arrow_down
                        else R.drawable.icon_arrow_up
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(EDSColors.gray),
                )
            }
        } else Spacer(modifier = Modifier.height(12.dp))
        Divider(color = EDSColors.grayX3, thickness = 1.dp)
    }
}

@Preview
@Composable
fun BudgetSectionPreview() {
    BudgetRangeSection()
}

@Composable
fun BudgetRangeSection(modifier: Modifier = Modifier, hasViewMore: Boolean = true) {
    val minimum = remember {
        mutableStateOf("")
    }
    val maximum = remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Budget range (vnđ)", style = EDSTextStyle.H2Reg(),
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = minimum.value, onValueChange = {
                    minimum.value = it
                }, singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                shape = RectangleShape,
                placeholder = {
                    Text(text = "Minimum", color = EDSColors.gray)
                },
                modifier = Modifier.weight(.45f),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = EDSColors.white,
                    focusedContainerColor = EDSColors.white,
                    unfocusedIndicatorColor = EDSColors.gray,
                    focusedIndicatorColor = EDSColors.primaryColor,
                    cursorColor = EDSColors.primaryColor
                )
            )
            Divider(
                color = EDSColors.gray,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(.05f)
                    .padding(horizontal = 2.dp)
            )
            OutlinedTextField(
                value = maximum.value, onValueChange = {
                    maximum.value = it
                },
                singleLine = true,
                shape = RectangleShape, placeholder = {
                    Text(
                        text = "Maximum" +
                                "", color = EDSColors.gray
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.weight(.45f),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = EDSColors.white,
                    focusedContainerColor = EDSColors.white,
                    unfocusedIndicatorColor = EDSColors.gray,
                    focusedIndicatorColor = EDSColors.primaryColor,
                    cursorColor = EDSColors.primaryColor
                )
            )
        }

        Row {
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .background(EDSColors.grayX3)
                    .weight(1f)

                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "0-500k", style = EDSTextStyle.H3Reg(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .background(EDSColors.grayX3)
                    .weight(1f)
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "500k-1000k", style = EDSTextStyle.H3Reg(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 8.dp)

                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .background(EDSColors.grayX3)
                    .weight(1f)

                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "1000k-2000k", style = EDSTextStyle.H3Reg(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = EDSColors.grayX3, thickness = 1.dp)
    }
}