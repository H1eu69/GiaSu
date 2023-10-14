package com.projectprovip.h1eu.giasu.view.screens.in_app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.common.theme.costTextColor
import com.projectprovip.h1eu.giasu.common.theme.idClassBackgroundColor
import com.projectprovip.h1eu.giasu.common.theme.myBlackColor
import com.projectprovip.h1eu.giasu.common.theme.notScheduleBackgroundColor
import com.projectprovip.h1eu.giasu.common.theme.notScheduleTextColor

@Preview
@Composable
fun PreviewScreen() {
    ClassManagementScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassManagementScreen(navController: NavController) {
    val tabSelectedIndex = remember {
        mutableIntStateOf(0)
    }
    val list = listOf("All", "title 1", "title 2dsadsadasdas ", "title 3", "title 4")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                AppBarTitle(text = "Classes")
            })
        }
    ) {
        LazyColumn (
            Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top) {
            item {
                ScrollableTabRow(
                    selectedTabIndex = tabSelectedIndex.value,
                    edgePadding = 8.dp,
                    modifier = Modifier.fillMaxWidth()) {
                    list.forEachIndexed {index, item ->
                        Tab(text = { Text(item) },
                            selected = tabSelectedIndex.value == index,
                            unselectedContentColor = Color.LightGray,
                            onClick = {
                                tabSelectedIndex.value = index
                            }
                        )
                    }
                }
            }

            items(4) {
                ClassItem()
            }
        }
    }
}

@Preview
@Composable
fun ClassItem() {
    Card(
        shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Blue),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.outlinedCardElevation(2.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10)
            )
            .clickable {

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
        Text(text = "Not schedule",
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                color = notScheduleTextColor
            ),
            modifier = Modifier
                .background(
                    notScheduleBackgroundColor,
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
        Icon(imageVector, null,
            tint = Color.Gray)
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
