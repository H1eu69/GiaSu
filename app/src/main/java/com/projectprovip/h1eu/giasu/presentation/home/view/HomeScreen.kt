package com.projectprovip.h1eu.giasu.presentation.home.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController(), CourseDetailState())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, state: CourseDetailState) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val usernameKey = stringPreferencesKey(Constant.USERNAME_STRING)
    var userName = remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect {
                userName.value = it[usernameKey].toString()
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(text = userName.value)
        },
    ) {
        BodyContent(
            modifier = Modifier
                .padding(it)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF00FFA6),
                            Color(0xFFB2FFB2),
                        ),
                    )
                ),
            navController = navController,
            state = state
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(text: String) {
    TopAppBar(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF00FFA6),
                        Color(0xFFB2FFB2),
                    ),
                )
            ),
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Column() {
                Text(
                    text = "Hello",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Text(
                    text = text,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            }
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyContent(
    modifier: Modifier,
    navController: NavController,
    state: CourseDetailState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchTextField(onTap = {
            navController.navigate(Screens.InApp.Home.SearchSuggest.route)
        })
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White,
                    RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
//            item { RowTitle(title1 = "Category", title2 = "View all") }
            item {
                RowTitle(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        top = 8.dp,
                        bottom = 15.dp,
                        end = 10.dp
                    ),
                    title1 = "Newest Classes",
                    title2 = "View all"
                )
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchTextField(modifier: Modifier = Modifier, onTap: () -> Unit) {
    val searchTextField = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            onTap()
        },
        value = searchTextField.value,
        readOnly = true,
        enabled = false,
        leadingIcon = {
            Icon(Icons.Default.Search, "")
        },
        label = {
            Text(text = "Search")
        },
        shape = RoundedCornerShape(30),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            disabledBorderColor = Color.Gray
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
        ),
        onValueChange = {
            searchTextField.value = it
        })
}

@Composable
private fun RowTitle(
    modifier: Modifier =
        Modifier.padding
            (
            start = 10.dp,
            top = 4.dp,
            bottom = 15.dp,
            end = 10.dp
        ),
    title1: String,
    title2: String
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title1,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
        Text(
            text = title2,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                color = EDSColors.primaryColor,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
    }
}

@Composable
fun CourseItem(navController: NavController, data: CourseDetail) {
    Card(
        shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Blue
        ),
        border = BorderStroke(2.dp, Color.LightGray),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10)
            )
            .clickable {
                navController.navigate("${Screens.InApp.Home.ClassDetail.route}/${data.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            AppBarTitle(text = data.title, fontSize = 16)
            SubTitle(text = "ID: ${data.id}")
            MiddleContent(
                minutePerSession = data.minutePerSession,
                sessionPerWeek = data.sessionPerWeek,
                info = data.subjectName,
                location = data.address
            )
            BottomContent(
                fee = data.fee,
                createdDate = data.creationTime
            )
        }
    }
}

@Composable
fun SubTitle(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            color = EDSColors.myBlackColor,
            fontSize = 14.sp
        ),
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .background(
                EDSColors.idClassBackgroundColor,
                RoundedCornerShape(30)
            )
            .padding(all = 4.dp)
    )
}

@Composable
fun MiddleContent(minutePerSession: Int, sessionPerWeek: Int, info: String, location: String) {
    val session = pluralStringResource(
        id = R.plurals.session_string,
        count = sessionPerWeek,
        sessionPerWeek,
        minutePerSession
    )
    Column(
        Modifier.padding(top = 12.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconAndText(Icons.Outlined.DateRange, session)
        IconAndText(Icons.Outlined.Info, info)
        IconAndText(Icons.Outlined.LocationOn, location)
    }
}

@Composable
fun IconAndText(imageVector: ImageVector, text: String) {
    Row {
        androidx.compose.material.Icon(
            imageVector, null,
            tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
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
        IconAndText(Icons.Outlined.DateRange, DateFormat.DD_MM_YYYY(createdDate))
    }
}



