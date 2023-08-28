package com.projectprovip.h1eu.giasu.view.screens.in_app.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.ui.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.ui.composes.SubjectCategoryItem
import com.projectprovip.h1eu.giasu.ui.theme.costTextColor
import com.projectprovip.h1eu.giasu.ui.theme.idClassBackgroundColor
import com.projectprovip.h1eu.giasu.ui.theme.myBlackColor
import com.projectprovip.h1eu.giasu.ui.theme.primaryColor
import com.projectprovip.h1eu.giasu.view.navigation.Screens

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar()
        },
    ) {
        BodyContent(
            modifier = Modifier.padding(it)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF00FFA6),
                            Color(0xFFB2FFB2),
                        ),
                    )
                ),
            navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
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
                    text = "{Username}",
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
fun BodyContent(modifier: Modifier,
                navController: NavController) {
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SearchTextField()
        LazyColumn(
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White,
                    RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {
            item { RowTitle("Category", "View all") }
            item { CategoryItemList() }
            item { RowTitle("Newest Classes", "View all") }
            items(8) {
                NewClassItem(navController)
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchTextField(modifier: Modifier = Modifier) {
    val searchTextField = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = searchTextField.value,
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Search, "")
        },
        label = {
            Text(text = "Search")
        },
        shape = RoundedCornerShape(30),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,

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
private fun CategoryItemList() {
    val list = listOf<Int>(1, 2, 3, 4, 5, 5, 5, 5, 5, 55, 5, 5, 1, 2, 3, 4, 5, 5, 5, 5, 5, 55, 5, 5, 1, 2, 3, 4, 5, 5, 5, 5, 5, 55, 5, 5)
    LazyRow(
        Modifier.fillMaxWidth(),
        userScrollEnabled = true,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
    ) {
        items(list.size) {
            SubjectCategoryItem()
        }
    }
}
@Composable
private fun RowTitle(title1: String, title2: String) {
    Row(
        Modifier
            .padding( start = 10.dp, top = 15.dp, bottom = 15.dp, end = 10.dp)
            .fillMaxWidth(),
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
                color = primaryColor,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
    }
}
@Composable
fun NewClassItem(navController: NavController) {
    Card(
        shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Blue),
        border = BorderStroke(2.dp, Color.LightGray),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10)
            )
            .clickable {
                navController.navigate(Screens.InApp.ClassDetail.route)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            AppBarTitle(text = "IT Beginner Entry")
            SubTitle()
            MiddleContent()
            BottomContent()
        }
    }
}

@Composable
fun SubTitle() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "ID: 1222",
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                color = myBlackColor,
                fontSize = 14.sp
            ),
            modifier = Modifier
                .background(
                    idClassBackgroundColor,
                    RoundedCornerShape(30)
                )
                .padding(all = 4.dp)
        )
    }
}

@Composable
fun BottomContent() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "3.000.000 / day",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = costTextColor
            )
        )
        IconAndText(Icons.Outlined.DateRange, "31/05/2023")
    }
}

@Composable
fun IconAndText(imageVector : ImageVector, text : String){
    Row{
        androidx.compose.material.Icon(
            imageVector, null,
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Composable
fun MiddleContent() {
    Column(
        Modifier.padding(top = 12.dp, bottom = 20.dp)
    ) {
        IconAndText(Icons.Outlined.DateRange, "3 days / week (90 min / day)")
        IconAndText(Icons.Outlined.Info, "At home")
        IconAndText(Icons.Outlined.LocationOn, "3 Dinh Ha noi")
    }
}



