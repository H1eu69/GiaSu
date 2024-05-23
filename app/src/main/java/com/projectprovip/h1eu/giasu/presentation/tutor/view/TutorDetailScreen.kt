package com.projectprovip.h1eu.giasu.presentation.tutor.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.domain.tutor.model.TutorDetail
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.RequestTutorParams
import com.projectprovip.h1eu.giasu.presentation.common.composes.MultiColorText
import com.projectprovip.h1eu.giasu.presentation.common.composes.OtpInputField
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.view.CircularLoading
import com.projectprovip.h1eu.giasu.presentation.tutor.model.RequestTutorState
import com.projectprovip.h1eu.giasu.presentation.tutor.model.TutorDetailState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
fun PreviewTutorDetailScreen() {
    val dummyData = TutorDetailState(
        data = TutorDetail()
    )
    TutorDetailScreen(
        state = dummyData,
        requestState = RequestTutorState(),
        onSendRequestBtnClick = {}
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TutorDetailScreen(
    navController: NavController = rememberNavController(),
    state: TutorDetailState,
    requestState: RequestTutorState,
    onSendRequestBtnClick: (RequestTutorParams) -> Unit,
) {

    val showBottomSheet = remember { mutableStateOf(false) }
    val dialogText = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = requestState) {
        requestState.apply {
            when {
                this.isSuccess -> {
                    Toast.makeText(context, "Request sent, we will contact you shortly", Toast.LENGTH_SHORT).show()
                    showBottomSheet.value = false
                }
                this.error.isNotEmpty() -> {
                    Toast.makeText(context, "Unexpected error, please try again later", Toast.LENGTH_SHORT).show()
                    showBottomSheet.value = false
                    Log.e("RequestTutor", this.error)
                }
            }
        }
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showBottomSheet.value = true
                Log.d("ExampleScreen", "PERMISSION GRANTED")

            } else {
                // Permission Denied: Do something
                Log.d("ExampleScreen", "PERMISSION DENIED")
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.white
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null,
                            tint = EDSColors.primaryColor
                        )
                    }
                })
        },
        containerColor = EDSColors.white

    ) {
        if (showBottomSheet.value)
            RequestTutorBottomSheet(
                onDismissRequest = { showBottomSheet.value = false },
                text = dialogText.value,
                onTextChange = {
                    dialogText.value = it
                },
                onButtonClick = {
                    onSendRequestBtnClick(
                        RequestTutorParams(
                            state.data.id,
                            dialogText.value
                        )
                    )
                }
            )
        state.apply {
            when {
                this.isLoading -> {
                    CircularLoading(color = EDSColors.primaryColor)
                }

                this.error.isNotEmpty() -> {
                    Toast.makeText(context, this.error, Toast.LENGTH_SHORT).show()

                }

                this.data != TutorDetail() -> {
                    Box(
                        Modifier
                            .padding(it)
                            .padding(horizontal = 20.dp),
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 50.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                Profile(
                                    image = state.data.avatar,
                                    name = state.data.fullName,
                                    id = state.data.id,
                                    rate = state.data.rate
                                )
                            }

                            item {
                                Text(
                                    text = state.data.description,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Medium,
                                        color = EDSColors.myBlackColor,
                                        fontSize = 14.sp
                                    ),
                                    modifier = Modifier
                                        .padding(top = 8.dp, bottom = 8.dp)
                                        .background(
                                            EDSColors.idClassBackgroundColor,
                                            RoundedCornerShape(20.dp)
                                        )
                                        .padding(
                                            start = 16.dp,
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            end = 8.dp
                                        )
                                )
                            }
                            item {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    state.data.tutorMajors.forEach { subject ->

                                        FilterChip(
                                            selected = true,
                                            onClick = { /*TODO*/ },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = EDSColors.primaryColor,
                                                selectedLabelColor = EDSColors.white,
                                                selectedTrailingIconColor = EDSColors.white
                                            ),
                                            label = { Text(subject, fontWeight = FontWeight.W300) }
                                        )
                                    }

                                }
                            }
                            item {
                                DetailIconAndText(
                                    Icons.Filled.LocationCity,
                                    "Location"
                                ) {
                                    Text(
                                        text = state.data.address,
                                        textAlign = TextAlign.End,
                                        color = EDSColors.costTextColor,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }

                            }
                            item {
                                DetailIconAndText(
                                    Icons.Filled.Cake,
                                    "Birth year",
                                ) {
                                    Text(
                                        text = state.data.birthYear.toString(),
                                        textAlign = TextAlign.End,
                                        color = EDSColors.costTextColor,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            item {
                                val academic = if( state.data.academicLevel != "UnderGraduated")  state.data.academicLevel else "Student"
                                DetailIconAndText(
                                    Icons.Filled.AutoStories,
                                    "Graduation",
                                ) {
                                    Text(
                                        text = academic,
                                        textAlign = TextAlign.End,
                                        color = EDSColors.costTextColor,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }

                            }

                            item {
                                Spacer(modifier = Modifier.height(20.dp))

                            }
                            item {
                                Text(text = "Ratings and reviews", fontSize = 20.sp)

                            }
                            item {
                                Spacer(modifier = Modifier.height(6.dp))

                            }
                            item {
                                Text(
                                    text = "Ratings and reviews are verified and are from people who learn the same course as you do",
                                    fontSize = 12.sp
                                )

                            }
                            item {
                                Spacer(modifier = Modifier.height(20.dp))

                            }
                            state.data.reviews.forEach {
                                item {
                                    ReviewItem(
                                        rate = it.rate,
                                        reviewer = it.learnerName,
                                        review = it.detail
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(10.dp))

                                }
                            }
                        }
                        ElevatedButton(
                            onClick = {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) -> {
                                        // Some works that require permission
                                        showBottomSheet.value = true
                                        Log.d("ExampleScreen", "Code requires permission")
                                    }

                                    else -> {
                                        // Asking for permission
                                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.BottomCenter),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = EDSColors.primaryColor,
                            )
                        ) {
                            androidx.compose.material.Text(
                                "Send request",
                                style = EDSTextStyle.H2Reg(EDSColors.white)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Profile(image: String, name: String, id: String, rate: Double) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .requiredSize(150.dp)
        ) {
            AsyncImage(
                image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = name, style = TextStyle(
                fontSize = 16.sp,
                color = EDSColors.primaryColor
            )
        )
        MultiColorText(
            text1 = "ID: ",
            color1 = EDSColors.primaryColor,
            text2 = id.toString(),
            color2 = Color.Black
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rate.toString(), style = TextStyle(
                    fontSize = 16.sp,
                )
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = EDSColors.yellowStar
            )
        }
    }
}

@Composable
fun DetailIconAndText(imageVector: ImageVector, boldedText: String, text: @Composable () -> Unit) {
    Row {
        Icon(
            imageVector, null,
            tint = EDSColors.primaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        androidx.compose.material.Text(
            text = boldedText,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        text()
    }
}

@Composable
@Preview
fun PreviewReviewItem() {
    ReviewItem(
        3,
        "Hieu",
        "Thang nay day ngu vai lz"
    )
}

@Composable
fun ReviewItem(rate: Int, reviewer: String, review: String) {
    Row() {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //Text(text = reviewer)
            Row {
                for (i in 1..5) {
                    if (i <= rate)
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "",
                            tint = EDSColors.yellowStar,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    else androidx.compose.material.Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = "",
                        tint = EDSColors.yellowStar,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
            Text(text = review)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RequestTutorBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    text: String,
    onTextChange: (String) -> Unit,
    onButtonClick: () -> Unit = {},

    ) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    ModalBottomSheet(
        sheetState = sheetState, onDismissRequest = { onDismissRequest() },
        containerColor = EDSColors.white
    ) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "Enter your message",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = EDSTextStyle.H2Bold()
                )
            }
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            onTextChange(it)
                        },
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .padding(horizontal = 20.dp),
                        label = {
                            Text(
                                text = "Message",
                            )
                        },
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = EDSColors.primaryColor,
                            focusedBorderColor = EDSColors.primaryColor,
                            focusedLabelColor = EDSColors.primaryColor,
                        ),
                    )
                }
            }
            item {
                ElevatedButton(
                    onClick = { onButtonClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = EDSColors.primaryColor,
                    )
                ) {
                    androidx.compose.material.Text(
                        "Send request",
                        style = EDSTextStyle.H2Reg(EDSColors.white)
                    )
                }
            }
        }
    }

}