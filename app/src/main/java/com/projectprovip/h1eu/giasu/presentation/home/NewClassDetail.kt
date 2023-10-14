package com.projectprovip.h1eu.giasu.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectprovip.h1eu.giasu.presentation.common.theme.primaryColor

@Preview
@Composable
fun PreviewScreen() {
    ClassDetailScreen(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ClassDetailAppBar(navController)
        },
    ) {
        Column(modifier = Modifier.padding(it)
            .fillMaxSize()
            .padding(20.dp)) {
            ClassDetailBody(navController = navController)
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                androidx.compose.material3.Text(text = "Tax: (750,000d) Register now")
            }
        }
    }
}

@Composable
fun ClassDetailBody(modifier: Modifier = Modifier, navController: NavController) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Can tim gia su pro vip 123",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        item {
            SubTitle()
        }
        item {
            BottomContent()
        }
        items(7) {
            DetailIconAndText(
                Icons.Outlined.Info,
                "Subject: ", "idiot baka oni chan"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailAppBar(navController: NavController) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = primaryColor
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Rounded.ArrowBack,
                    "",
                    tint = Color.White)
            }
        },
        title = {
            Text(
                text = "Class Detail",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    )
}

@Composable
fun DetailIconAndText(imageVector : ImageVector, boldedText : String, text : String){
    Row{
        Icon(
            imageVector, null,
            tint = primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = boldedText,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text,
            fontSize = 16.sp
        )
    }
}


