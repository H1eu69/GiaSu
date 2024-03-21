package com.projectprovip.h1eu.giasu.presentation.home.view

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
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
    HomeScreen(
        rememberNavController(), CourseDetailState(
            data = listOf(
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
                CourseDetail(),
            )
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, state: CourseDetailState,
               onLoadMore: () -> Unit = {}) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val usernameKey = stringPreferencesKey(Constant.USERNAME_STRING)
    val userName = remember {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        EDSColors.white
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "EDUSMART",
                        style = EDSTextStyle.Logo(
                            EDSColors.primaryColor
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Hello",
                            style = EDSTextStyle.H2Reg(
                                EDSColors.primaryColor
                            )
                        )
                        Text(
                            text = userName.value,
                            style = EDSTextStyle.H2Bold(
                                EDSColors.primaryColor
                            )
                        )
                    }
                }
                Row {
                    IconButton(onClick = {
                        navController.navigate(Screens.InApp.Profile.RequestClass.route)
                    }) {

                        Icon(
                            Icons.Filled.Add, null,
                            tint = EDSColors.blackColor,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(EDSColors.grayX4, CircleShape)
                                .padding(4.dp)
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Screens.InApp.Home.SearchSuggest.route)
                    }) {

                        Icon(
                            Icons.Filled.Search, null,
                            tint = EDSColors.blackColor,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(EDSColors.grayX4, CircleShape)
                                .padding(4.dp)
                        )
                    }
                }

            }
        }
    ) {
        state.apply {
            when {
                this.isLoading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            color = EDSColors.primaryColor
                        )
                    }
                }

                this.data.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(it)
                            .background(
                                EDSColors.grayX3
                            )
                            .padding(top = 8.dp)
                            .fillMaxSize()
                            .background(
                                EDSColors.white,
                            )
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.data.count()) { index ->
                            CourseItem(
                                navController = navController,
                                data = state.data[index],
                                modifier = Modifier.padding(top = if (index == 0) 16.dp else 0.dp)
                            )
                            if (index == state.data.count() - 2) {
                                onLoadMore()
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
fun BodyContent(
    modifier: Modifier,
    navController: NavController,
    state: CourseDetailState,
) {
    state.apply {
        when {
            this.isLoading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        color = EDSColors.primaryColor
                    )
                }
            }

            this.data.isNotEmpty() -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            EDSColors.white,
                        )
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.data.count()) { index ->
                        CourseItem(
                            navController = navController,
                            data = state.data[index],
                            modifier = Modifier.padding(top = if (index == 0) 16.dp else 0.dp)
                        )
                    }
//        item {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(top = 8.dp, start = 8.dp),
//                    horizontalAlignment = Alignment.Start,
//                ) {
//                    Text(
//                        text = "Hello",
//                        style = TextStyle(
//                            fontSize = 18.sp,
//                            color = Color.White,
//                            fontFamily = FontFamily.SansSerif
//                        )
//                    )
//                    Text(
//                        text = name,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White,
//                            fontSize = 20.sp,
//                            fontFamily = FontFamily.SansSerif
//                        )
//                    )
//                }
//
//                IconButton(onClick = { /*TODO*/ }) {
//                    Icon(Icons.Default.Search, null,
//                        tint = EDSColors.white)
//                }
//            }
//
//        }
//        item {
//            SearchTextField(onTap = {
//                navController.navigate(Screens.InApp.Home.SearchSuggest.route)
//            })
//        }

//        item {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        Color.White,
//                        RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
//                    )
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//
//            ) {
////                RowTitle(
////                    modifier = Modifier.padding(
////                        start = 10.dp,
////                        top = 8.dp,
////                        bottom = 15.dp,
////                        end = 10.dp
////                    ),
////                    title1 = "Newest Courses",
////                    title2 = "View all"
////                )
//
//            }
//        }

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
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            disabledBorderColor = Color.Gray,
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

@Preview
@Composable
fun PreviewCourseItem() {
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CourseItem(
                rememberNavController(),
                CourseDetail()
            )
            CourseItem(
                rememberNavController(),
                CourseDetail()
            )
            CourseItem(
                rememberNavController(),
                CourseDetail()
            )
            CourseItem(
                rememberNavController(),
                CourseDetail()
            )
        }
    }
}

@Composable
fun CourseItem(navController: NavController, data: CourseDetail, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Blue
        ),
        border = BorderStroke(2.dp, Color.LightGray),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = modifier
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
            //SubTitle(text = "ID: ${data.id}")
            MiddleContent(
                sessionDuration = data.sessionDuration,
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
fun MiddleContent(sessionDuration: Int, sessionPerWeek: Int, info: String, location: String) {
    val session = pluralStringResource(
        id = R.plurals.session_string,
        count = sessionPerWeek,
        sessionPerWeek,
        sessionDuration
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
fun BottomContent(fee: Int, createdDate: String) {
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



