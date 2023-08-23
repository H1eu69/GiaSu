package com.projectprovip.h1eu.giasu.view.screens.in_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectprovip.h1eu.giasu.ui.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.ui.composes.MainTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ClassManagement() {
    val tabSelectedIndex = remember {
        mutableIntStateOf(0)
    }
    val list = listOf("All", "title 1", "title 2", "title 3", "title 4")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                AppBarTitle(text = "Classes")
            })
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top) {
            ScrollableTabRow(
                selectedTabIndex = tabSelectedIndex.value,
                edgePadding = 4.dp,
                modifier = Modifier.fillMaxWidth()) {
                list.forEachIndexed {index, item ->
                    Tab(text = { Text(item) },
                        selected = tabSelectedIndex.value == index,
                        onClick = {
                            tabSelectedIndex.value = index
                        }
                    )
                }
            }
        }
    }
}