package com.projectprovip.h1eu.giasu.presentation.tutor.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.tutor.viewmodel.TutorViewModel

@Preview
@Composable
fun TutorPreview() {
    TutorScreen(rememberNavController(), hiltViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorScreen(navController: NavController, vm: TutorViewModel) {

    val state = vm.state
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Tutors") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.primaryColor, titleContentColor = Color.White
                )
            )
        },
    ) {
        when {
            state.value.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            }

            state.value.data.isNotEmpty() -> {
                InfiniteList(state.value.data,
                    modifier = Modifier.padding(it),
                    onLoadMore = {
                        vm.loadMore()
                    })
            }
        }
    }
}

@Composable
fun InfiniteList(
    data: List<Tutor>,
    modifier: Modifier,
    onLoadMore: (() -> Unit)
) {
    val listState = rememberLazyGridState()
    val listCount = data.count()
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        items(count = listCount) { index ->
            TutorItem(data[index])
            if ( index == listCount - 2) {
                onLoadMore()
            }
        }
    }
}

@Composable
fun GridBodyList(data: List<Tutor>, modifier: Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        data.forEach {
            item {
                TutorItem(it)
            }
        }

    }
}

@Composable
fun TutorItem(tutor: Tutor) {
    Card(shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = EDSColors.white
        ),
        border = BorderStroke(1.dp, EDSColors.grayText),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10)
            )
            .clickable {
                //navController.navigate("${Screens.InApp.Home.ClassDetail.route}/${data.id}")
            }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))

        ) {
            AsyncImage(
                model = tutor.image,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                )

            )
            IconAndText(
                Icons.Outlined.Person,
                "${tutor.firstName} ${tutor.lastName}",
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            IconAndText(
                Icons.Outlined.Info,
                tutor.academicLevel,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            IconAndText(
                Icons.Outlined.Cake,
                tutor.birthYear.toString(),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            IconAndText(
                Icons.Outlined.School, tutor.university, modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
            )
        }
    }

}

@Composable
fun IconAndText(imageVector: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector, null, tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text, maxLines = 1, overflow = TextOverflow.Ellipsis, color = EDSColors.grayText
        )
    }
}
