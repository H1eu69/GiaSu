package com.projectprovip.h1eu.giasu.presentation.tutor.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor
import com.projectprovip.h1eu.giasu.presentation.common.composes.ShimmerTutorList
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.home.view.SearchTextField
import com.projectprovip.h1eu.giasu.presentation.tutor.model.TutorListState

@Preview
@Composable
fun TutorPreview() {
    val dummyData = remember {
        mutableStateOf(
            TutorListState(
                data = listOf<Tutor>(
                    Tutor(
                        "he",
                        2022,
                        "desdasdasas",
                        "hehe",
                        "4",
                        "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
                        "last",
                        3.0,
                        "UIT"
                    ),
                    Tutor(
                        "he",
                        2022,
                        "desdasdasas",
                        "hehe",
                        "4",
                        "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
                        "last",
                        3.0,
                        "UIT"
                    ),
                    Tutor(
                        "he",
                        2022,
                        "desdasdasas",
                        "hehe",
                        "4",
                        "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
                        "last",
                        3.0,
                        "UIT"
                    )
                )
            )
        )
    }
    TutorScreen(
        state = dummyData,
        navController = rememberNavController(),
        loadMore = {

        }
    )
}

@Preview
@Composable
fun TutorItemPreview() {
    Surface {
        TutorItem(
            tutor = Tutor(
                "he",
                2022,
                "desdasdasas",
                "hehe",
                "4",
                "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
                "last",
                3.0,
                "UIT"
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorScreen(
    state: State<TutorListState>,
    navController: NavController,
    loadMore: (() -> Unit),
    lazyListState: LazyGridState = rememberLazyGridState()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tutors",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = EDSColors.primaryColor
                )
                SearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    onTap = {
                        navController.navigate(Screens.InApp.Tutor.SearchSuggest.route)
                    })
            }
//            CenterAlignedTopAppBar(
//                title = {
//
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = EDSColors.white,
//                    titleContentColor = EDSColors.primaryColor
//                )
//            )
        },
    ) {

        when {
            state.value.isLoading -> {
                ShimmerTutorList()
            }

            state.value.error.isNotEmpty() -> {
                LaunchedEffect(key1 = state.value.error) {
                    Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
                }
            }

            state.value.data.isNotEmpty() -> {
                InfiniteStaggeredList(state.value.data,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    onItemClick = { tutorId ->
                        navController.navigate("${Screens.InApp.Tutor.TutorDetail.route}/$tutorId")
                    },
                    listState = lazyListState,
                    onLoadMore = {
                        loadMore()
                    })
            }
        }

    }
}

@Composable
fun InfiniteStaggeredList(
    data: List<Tutor>,
    modifier: Modifier,
    onItemClick: (String) -> Unit,
    listState: LazyGridState,
    onLoadMore: (() -> Unit)
) {
    val listCount = data.count()
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        items(count = listCount) { index ->
            TutorItem(data[index], onItemClick = onItemClick)
            if (index == listCount - 2) {
                onLoadMore()
            }
        }
    }
}

@Composable
fun TutorItem(tutor: Tutor, onItemClick: (String) -> Unit = {}) {
    val academicLevel =
        if (tutor.academicLevel != "UnderGraduated") tutor.academicLevel else "Student"
    Card(shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = EDSColors.white
        ),
        border = BorderStroke(1.dp, EDSColors.gray),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = Modifier
            .padding(8.dp)
            .clip(
                RoundedCornerShape(10)
            )
            .clickable {
                onItemClick(tutor.id)
                Log.d("Test tutor id clicked", tutor.id)
                //navController.navigate("${Screens.InApp.Home.ClassDetail.route}/${data.id}")
            }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier

        ) {
            AsyncImage(
                model = tutor.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )
            IconAndText(
                Icons.Outlined.Person,
                "${tutor.firstName} ${tutor.lastName}",
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            IconAndText(
                Icons.Outlined.Info,
                academicLevel,
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
            )
            IconAndText(
                Icons.Outlined.Star, "${tutor.rate.toDouble()} /5.0",
                tint = EDSColors.yellowStar,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
            )
        }
    }

}

@Composable
fun IconAndText(
    imageVector: ImageVector, text: String, modifier: Modifier = Modifier,
    tint: Color = EDSColors.primaryColor
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector, null, tint = tint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text, maxLines = 1, overflow = TextOverflow.Ellipsis, color = EDSColors.gray
        )
    }
}
