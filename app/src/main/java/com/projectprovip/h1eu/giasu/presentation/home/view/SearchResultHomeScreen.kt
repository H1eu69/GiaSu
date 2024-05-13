package com.projectprovip.h1eu.giasu.presentation.home.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.common.composes.ShimmerCourse
import com.projectprovip.h1eu.giasu.presentation.common.composes.VerticalGrid
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.model.FilterSelect
import com.projectprovip.h1eu.giasu.presentation.home.model.SearchResultState
import com.projectprovip.h1eu.giasu.presentation.profile.view.CircularLoading
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Preview
@Composable
fun SearchResultHomeScreenPreview() {
    SearchResultHomeScreen(
        rememberNavController(),
        state = SearchResultState(
            data = listOf(
                CourseDetail(
                    address = "Tây Ninh",
                    fee = 200.0
                ),
                CourseDetail(
                    address = "Hà Nội",
                    fee = 100.0
                ),
                CourseDetail(
                    fee = 2000.0
                ),
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
    state: SearchResultState = SearchResultState(),
    searchText: String? = null,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val locationFilter = remember {
        mutableStateOf(
            listOf(
                FilterSelect("Hà Nội"),
                FilterSelect("Hà Tĩnh"),
                FilterSelect("Hà Giang"),
                FilterSelect("Hồ Chí Minh"),
                FilterSelect("Quận 1"),
                FilterSelect("Quận 2"),
                FilterSelect("Quận 3"),
                FilterSelect("Quận 4"),
                FilterSelect("Quận 5"),
                FilterSelect("Quận 6"),
                FilterSelect("Quận 7"),
                FilterSelect("Quận 8"),
                FilterSelect("Quận 9"),
                FilterSelect("Quận 10"),
                )
        )
    }

    val learningModeFilter = remember {
        mutableStateOf(
            listOf(
                FilterSelect("Online"),
                FilterSelect("Offline"),
                FilterSelect("Hybrid"),
            )
        )
    }

    val academicFilter = remember {
        mutableStateOf(
            listOf(
                FilterSelect("Ungraduated"),
                FilterSelect("Graduated"),
                FilterSelect("Lecturer"),
            )
        )
    }

    val budgetFilter = remember {
        mutableStateOf(
            Pair(0.0, 0.0)
        )
    }
    val isLocationFilterSelected = remember {
        mutableStateOf(false)
    }

    val isLearningModeFilterSelected = remember {
        mutableStateOf(false)
    }

    val isBudgetRangeSelected = remember {
        mutableStateOf(false)
    }

    val isAcademicFilterSelected = remember {
        mutableStateOf(false)
    }

    val filteredList = remember {
        mutableStateOf(
            state.data
        )
    }

    LaunchedEffect(state) {
        filteredList.value = state.data
    }

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
                        LazyColumn(
                            modifier = Modifier.weight(.8f)
                        ) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(EDSColors.primaryColor)
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        "Search Filters", style = EDSTextStyle.H1Med(
                                            EDSColors.white
                                        )
                                    )
                                }
                            }

                            item {
                                FilterItem(
                                    modifier = Modifier.padding(8.dp),
                                    title = "location",
                                    filterItemsState = locationFilter,
                                    onSelected = {
                                        isLocationFilterSelected.value = it
                                    }
                                )
                            }

                            item {
                                BudgetRangeSection(
                                    modifier = Modifier.padding(8.dp),
                                    onSelected = { selected, minimum, maximum ->
                                        isBudgetRangeSelected.value = selected
                                        budgetFilter.value = Pair(minimum, maximum)
                                    }
                                )
                            }

                            item {
                                FilterItem(
                                    modifier = Modifier.padding(8.dp),
                                    title = "Learning mode",
                                    hasViewMore = false,
                                    filterItemsState = learningModeFilter,
                                    onSelected = {
                                        isLearningModeFilterSelected.value = it
                                    }
                                )
                            }

                            item {
                                FilterItem(
                                    modifier = Modifier.padding(8.dp),
                                    title = "Academic level requirement",
                                    hasViewMore = false,
                                    filterItemsState = academicFilter,
                                    onSelected = {
                                        isAcademicFilterSelected.value = it
                                    }
                                )
                            }
                        }
                        ApplyFilterButtons(
                            Modifier.padding(8.dp),
                            onFilterClick = {
                                filteredList.value = state.data
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }

                                if (isLocationFilterSelected.value)
                                    filteredList.value = filteredList.value.filter { courseDetail ->
                                        locationFilter.value.forEach { location ->
                                            Log.d(
                                                "Test filter 4",
                                                location.title + location.selected
                                            )

                                            if (location.selected && courseDetail.address.contains(
                                                    location.title
                                                )
                                            ) {
                                                return@filter true
                                            }
                                        }
                                        false
                                    }
                                Log.d("Test filter 1", filteredList.value.toString())
                                if (isLearningModeFilterSelected.value)
                                    filteredList.value = filteredList.value.filter { courseDetail ->
                                        learningModeFilter.value.forEach { learningMode ->
                                            Log.d(
                                                "Test filter 5",
                                                learningMode.title + learningMode.selected
                                            )

                                            if (learningMode.selected && courseDetail.learningMode.contains(
                                                    learningMode.title
                                                )
                                            ) {
                                                return@filter true
                                            }
                                        }
                                        false
                                    }
                                Log.d("Test filter 2", filteredList.value.toString())

                                if (isAcademicFilterSelected.value)
                                    filteredList.value = filteredList.value.filter { courseDetail ->
                                        academicFilter.value.forEach { academic ->
                                            Log.d(
                                                "Test filter 6",
                                                academic.title + academic.selected
                                            )

                                            if (academic.selected && courseDetail.academicLevelRequirement.contains(
                                                    academic.title
                                                )
                                            ) {
                                                return@filter true
                                            }
                                        }
                                        false
                                    }
                                Log.d("Test filter 3", filteredList.value.toString())

                                if (isBudgetRangeSelected.value)
                                    filteredList.value = filteredList.value.filter { courseDetail ->
                                        if (courseDetail.fee in budgetFilter.value.first..budgetFilter.value.second)
                                            return@filter true
                                        false
                                    }
                                Log.d("Test filter 4", filteredList.value.toString())

                                if (!isLocationFilterSelected.value &&
                                    !isLearningModeFilterSelected.value &&
                                    !isAcademicFilterSelected.value &&
                                    !isBudgetRangeSelected.value
                                ) {
                                    filteredList.value = state.data
                                }
                            })
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
                            searchText ?: "",
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
                ) { it ->
                    state.apply {
                        when {
                            this.isLoading -> {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    ShimmerCourse()
                                    ShimmerCourse()
                                    ShimmerCourse()
                                    ShimmerCourse()
                                    ShimmerCourse()

                                }
                            }

                            filteredList.value.isEmpty() -> {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    val composition by rememberLottieComposition(
                                        LottieCompositionSpec.RawRes(R.raw.empty_box),
                                        onRetry = { failCount, exception ->
                                            Log.d("LottieAnimation", failCount.toString())
                                            Log.d("LottieAnimation", exception.toString())
                                            // Continue retrying. Return false to stop trying.
                                            false
                                        })
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        LottieAnimation(
                                            composition = composition,
                                            iterations = LottieConstants.IterateForever,
                                        )
                                        Text("No courses found")
                                    }
                                }
                            }

                            else -> {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(bottom = 30.dp),
                                    modifier = Modifier
                                        .padding(it)
                                ) {
                                    item {
                                        ResultChip(navController, searchText ?: "")
                                    }
                                    filteredList.value.forEach { courseDetail ->
                                        item {
                                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                                CourseItem(
                                                    data = courseDetail,
                                                    onClick = {
                                                        navController.navigate("${Screens.InApp.Home.ClassDetail.route}/${courseDetail.id}")
                                                    },
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
    searchText: String,
    navController: NavController,
    onFilterClick: () -> Unit,
) {
    val searchTextField = remember {
        mutableStateOf(searchText)
    }
    val interactionSource = remember {
        MutableInteractionSource()
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
            enabled = false,
            textStyle = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(end = 16.dp)
                .clickable(
                    interactionSource, null
                ) {
                    navController.navigate(
                        Screens.InApp.Home.SearchSuggest.route +
                                "/${searchTextField.value}",

                        )
                },
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
    val list1 = remember {
        mutableStateOf(
            listOf(
                FilterSelect("Tay ninh 1", true),
                FilterSelect("Tay ninh2", true),
                FilterSelect("Tay ninh3"),
                FilterSelect("Tay ninh4"),
                FilterSelect("Tay ninh5"),
                FilterSelect("Tay ninh6"),
                FilterSelect("Tay ninh7"),
                FilterSelect("Tay ninh8"),
                FilterSelect("Tay ninh9"),
            )
        )
    }
    val list2 = remember {
        mutableStateOf(
            listOf(
                FilterSelect("Tay ninh 1", true),
                FilterSelect("Tay ninh2"),
                FilterSelect("Tay ninh3"),
            )
        )
    }
    val list3 = remember {
        mutableStateOf(
            listOf(
                FilterSelect("Tay ninh 1", true),
                FilterSelect("Tay ninh2"),
            )
        )
    }
    val list4 = remember {
        mutableStateOf(
            listOf(
                FilterSelect("Tay ninh 1"),
            )
        )
    }

    Column {
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItemsState = list1,
            hasViewMore = list1.value.count() > 4
        )
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItemsState = list2,
            hasViewMore = list2.value.count() > 4
        )
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItemsState = list3,
            hasViewMore = list3.value.count() > 4
        )
        FilterItem(
            modifier = Modifier.background(EDSColors.white),
            title = "location",
            filterItemsState = list4,
            hasViewMore = list4.value.count() > 4
        )
    }

}

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    title: String,
    filterItemsState: MutableState<List<FilterSelect>>,
    hasViewMore: Boolean = true,
    multipleSelection: Boolean = false,
    onSelected: (Boolean) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val wantViewMore = remember {
        mutableStateOf(false)
    }
    val filterItems = filterItemsState.value

    val totalItem = if (!wantViewMore.value) {
        if (filterItems.count() > 4) 4 else filterItems.count()
    } else filterItems.count()

    Column(
        modifier = modifier
    ) {
        Text(
            title, style = EDSTextStyle.H2Reg(),
            modifier = Modifier.padding(vertical = 12.dp)
        )
        VerticalGrid(
            gridCells = com.projectprovip.h1eu.giasu.presentation.common.composes.GridCells.Fixed(2),
            totalItems = totalItem
        ) { index ->
            if (filterItems[index].selected)
                SelectedItem(title = filterItems[index].title,
                    onClick = {
                        val newList = filterItemsState.value.toMutableList()
                        newList[index] = filterItems[index].copy(selected = false)
                        filterItemsState.value = newList
                        onSelected(false)
                    })
            else
                UnselectItem(title = filterItems[index].title,
                    onClick = {
                        val newList = filterItemsState.value.toMutableList()
                        newList.forEach {
                            it.selected = false
                        }
                        newList[index] = filterItems[index].copy(selected = true)
                        filterItemsState.value = newList
                        onSelected(true)
                    })

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

@Composable
fun SelectedItem(title: String, onClick: () -> Unit) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .fillMaxWidth()
            .border(1.dp, EDSColors.primaryColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text =
            if (LocalInspectionMode.current)
            // Show this text in a preview window:
                "Preview Item"
            else
            // Show this text in the app:
                title,
            style = EDSTextStyle.H3Reg(
                EDSColors.primaryColor
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Image(
            imageVector = Icons.Default.Check, contentDescription = null,
            colorFilter = ColorFilter.tint(EDSColors.primaryColor),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 4.dp)
        )
    }
}

@Composable
fun UnselectItem(title: String, onClick: () -> Unit) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .fillMaxWidth()
            .background(EDSColors.grayX4)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text =
            if (LocalInspectionMode.current)
            // Show this text in a preview window:
                "Preview Item"
            else
            // Show this text in the app:
                title,
            style = EDSTextStyle.H3Reg(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun BudgetSectionPreview() {
    Surface {
        BudgetRangeSection(
            Modifier.padding(8.dp),
            onSelected = { s1, s2, s3 ->
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetRangeSection(
    modifier: Modifier = Modifier,
    hasViewMore: Boolean = true,
    onSelected: (Boolean, Double, Double) -> Unit
) {
    val minimum = remember {
        mutableStateOf("")
    }
    val maximum = remember {
        mutableStateOf("")
    }
    val interactionSource = remember {
        MutableInteractionSource()
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
                value = minimum.value,
                onValueChange = {
                    minimum.value = it
                    onSelected(
                        true,
                        if(minimum.value.isNotEmpty()) minimum.value.toDouble() else 0.0,
                        if(maximum.value.isNotEmpty()) maximum.value.toDouble() else 0.0
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                shape = RectangleShape,

                placeholder = {
                    Text(
                        text = "Minimum", color = EDSColors.gray, textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .weight(.45f)
                    .height(52.dp),

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
                    .padding(horizontal = 4.dp)
            )
            OutlinedTextField(
                value = maximum.value, onValueChange = {
                    maximum.value = it
                    onSelected(
                        true,
                        if(minimum.value.isNotEmpty()) minimum.value.toDouble() else 0.0,
                        if(maximum.value.isNotEmpty()) maximum.value.toDouble() else 0.0
                    )

                }, singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),

                shape = RectangleShape,
                placeholder = {
                    Text(
                        text = "Maximum", color = EDSColors.gray, textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .weight(.45f)
                    .height(52.dp),
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
                    .background(EDSColors.grayX4)
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        minimum.value = "0"
                        maximum.value = "500000"
                        onSelected(
                            true,
                            minimum.value.toDouble(),
                            maximum.value.toDouble()
                        )
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
                    .background(EDSColors.grayX4)
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        minimum.value = "500000"
                        maximum.value = "1000000"
                        onSelected(
                            true,
                            minimum.value.toDouble(),
                            maximum.value.toDouble()
                        )
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
                    .background(EDSColors.grayX4)
                    .weight(1f)

                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        minimum.value = "1000000"
                        maximum.value = "2000000"
                        onSelected(
                            true,
                            minimum.value.toDouble(),
                            maximum.value.toDouble()
                        )
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

@Preview
@Composable
fun ApplyFilterButtonsPreview() {
    Surface {
        ApplyFilterButtons(
            Modifier.padding(8.dp),
            {}
        )
    }
}


@Composable
fun ApplyFilterButtons(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(
                1.dp,
                EDSColors.primaryColor
            ),
            modifier = Modifier.weight(.5f)
        ) {
            Text(
                text = "Reset",
                style = EDSTextStyle.H2Reg(
                    EDSColors.primaryColor
                )
            )
        }
        OutlinedButton(
            onClick = { onFilterClick() },
            shape = RoundedCornerShape(4.dp),
            border = null,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = EDSColors.primaryColor,
            ),
            modifier = Modifier.weight(.5f)
        ) {
            Text(
                text = "Apply",
                style = EDSTextStyle.H2Reg(
                    EDSColors.white
                )
            )
        }
    }
}