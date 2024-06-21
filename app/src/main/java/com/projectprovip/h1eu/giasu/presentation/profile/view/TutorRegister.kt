package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonRadioButton
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectItem
import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectState
import com.projectprovip.h1eu.giasu.presentation.profile.model.TutorRegisterState
import com.projectprovip.h1eu.giasu.presentation.profile.model.toMajor
import kotlinx.coroutines.launch
import kotlin.math.ceil


@Preview
@Composable
fun PreviewTutorRegister() {
    TutorRegisterScreen(
        rememberNavController(),
        TutorRegisterState(),
        SubjectState(),
        { s1, s2, s3, s4 -> },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TutorRegisterScreen(
    navController: NavController,
    registerState: TutorRegisterState,
    subjectState: SubjectState,
    registerTutor: (String, String, List<Int>, List<Uri>) -> Unit,
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val tokenKey = stringPreferencesKey(Constant.TOKEN_STRING)
    val token = remember {
        mutableStateOf("")
    }
    val academicLevels = listOf("Ungraduated", "Graduated", "Lecturer")

    var (academicLevelSelectedOption, academicOnOptionSelected) = remember {
        mutableStateOf(academicLevels[0])
    }

    LaunchedEffect(key1 = token) {
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[tokenKey].toString()}"
                Log.d("Token in tutor", token.value)
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            null,
                            tint = EDSColors.primaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.white
                ),
                title = {
                    Text(
                        text = "Enroll",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = EDSColors.primaryColor,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    ) {
        val images = remember {
            mutableStateOf(
                listOf(
                    "".toUri(),
                )
            )
        }

        val academicText = remember { mutableStateOf("") }
        val universityText = remember { mutableStateOf("") }

        val academicInteractionSource = remember { MutableInteractionSource() }
        val academicIsFocused = academicInteractionSource.collectIsFocusedAsState()

        val universityInteractionSource = remember { MutableInteractionSource() }
        val universityIsFocused = universityInteractionSource.collectIsFocusedAsState()

        val subject = remember {
            mutableStateOf(subjectState.data)
        }
        val selectedSubjects = remember {
            mutableStateOf(emptyList<SubjectItem>())
        }

        LaunchedEffect(subjectState) {
            subject.value = subjectState.data
        }

        val openEditSubjectDialog = remember { mutableStateOf(false) }

        if (openEditSubjectDialog.value) {
            Log.d("subject", subject.value.toString())

            SubjectDialog(subject, onDisMiss = {
                openEditSubjectDialog.value = false
            },
                onConfirm = {
                    val newList = selectedSubjects.value.toMutableList()
                    newList.clear()
                    subject.value.forEach {
                        if (it.isSelected) {
                            newList.add(it)
                        }
                    }
                    selectedSubjects.value = newList
                    Log.d("selectedSubjects in enroll", selectedSubjects.value.toString())
                })
        }

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item {
                    CommonRadioButton(
                        title = "Academic Requirement",
                        radioOptions = academicLevels,
                        selectedOption = academicLevelSelectedOption,
                        onOptionSelected = academicOnOptionSelected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(Color.White)
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .padding(horizontal = 20.dp),
                        interactionSource = universityInteractionSource,
                        label = {
                            if (!universityIsFocused.value)
                                Text(
                                    text = "University", color = EDSColors.grayX2
                                ) else Text(
                                text = "University",
                            )
                        },
                        keyboardActions = KeyboardActions(),
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = { value ->
                            universityText.value = value
                        },
                        value = universityText.value,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = EDSColors.primaryColor,
                            focusedLabelColor = EDSColors.primaryColor,
                            cursorColor = EDSColors.primaryColor,
                        ),
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .padding(horizontal = 20.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                androidx.compose.material3.Text(
                                    text = "Subjects",
                                )
                                Text(
                                    text = "(*)", fontSize = 16.sp,
                                    color = EDSColors.notScheduleTextColor
                                )
                            }

                            androidx.compose.material3.Text(
                                text = "Edit",
                                color = EDSColors.primaryColor,
                                modifier = Modifier.clickable {
                                    openEditSubjectDialog.value = true
                                })
                        }
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            selectedSubjects.value.forEach {
                                FilterChip(
                                    selected = true,
                                    onClick = { /*TODO*/ },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = EDSColors.greenCheck,
                                        selectedLabelColor = EDSColors.white,
                                        selectedTrailingIconColor = EDSColors.white
                                    ),
                                    label = {
                                        androidx.compose.material3.Text(
                                            it.name,
                                            fontWeight = FontWeight.W300
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(

                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = "",
                                tint = EDSColors.primaryColor
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Verification", fontSize = 16.sp)
                            Text(
                                text = "(*)", fontSize = 16.sp,
                                color = EDSColors.notScheduleTextColor
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Add more", fontSize = 16.sp,
                                color = EDSColors.primaryColor,
                                modifier = Modifier.clickable {
                                    val newList = images.value.toMutableList()
                                    newList.add(Uri.EMPTY)
                                    images.value = newList
                                }
                            )

                        }
                        ImagePicker(
                            images.value,
                            onImageUriChanged = { uri, index ->
                                val newList = images.value.toMutableList()
                                newList.removeAt(index)
                                newList.add(index, uri)
                                images.value = newList
                            },
                            onDeleteUri = { index ->
                                val newList = images.value.toMutableList()
                                val emptyUri = Uri.EMPTY

                                newList.removeAt(index)
                                newList.add(index, emptyUri)
                                images.value = newList
                            },
                        )
                    }
                }
            }
            Button(
                onClick = {
                    registerTutor(
                        academicLevelSelectedOption,
                        universityText.value,
                        selectedSubjects.value.map {subject ->
                            subject.id
                        },
                        images.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
            ) {

                if (registerState.isLoading)
                    CircularProgressIndicator()
                else
                    Text(text = "Register", color = EDSColors.white)
                LaunchedEffect(key1 = registerState) {
                    if (registerState.error.isNotEmpty() || registerState.success) {
                        showToast(context, registerState.error)
                        navController.popBackStack()
                    }

                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImagePicker(
    images: List<Uri>, onImageUriChanged: ((uri: Uri, index: Int) -> Unit),
    onDeleteUri: ((index: Int) -> Unit)
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val imageWidth = (screenWidth - 40) / 3
    val rowCount = ceil(images.count().toDouble() / 3).toInt()
    val listHeight = rowCount * imageWidth + ((rowCount - 1) * 8)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(top = 20.dp)
            .height(listHeight.dp)
    ) {
        images.forEachIndexed { index, u ->
            item {
                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
                        if (it != null) {
                            onImageUriChanged(it, index)
                            Log.d("Test uri ", u.lastPathSegment.toString())
                        }
                    }

                if (u.toString().isNotEmpty()) {
                    Box {
                        GlideImage(model = u, contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(imageWidth.dp)
                                .clip(
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.close_with_bg),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 2.dp, end = 2.dp)
                                .background(
                                    EDSColors.imageBackground,
                                    CircleShape
                                )
                                .clickable {
                                    onDeleteUri(index)
                                    Log.d("Test uri on delete", u.lastPathSegment.toString())
                                }
                        )
                    }


                } else {
                    Box(modifier = Modifier
                        .size(imageWidth.dp)
                        .background(
                            EDSColors.imageBackground,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "",
                            tint = EDSColors.plusBackground,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectDialog(
    subjects: MutableState<List<SubjectItem>>,
    onDisMiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    val searchText = remember { mutableStateOf("") }
    AlertDialog(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            androidx.compose.material3.Text(text = "Subjects")
        },
        containerColor = EDSColors.white,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                androidx.compose.material3.OutlinedTextField(
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(30),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = EDSColors.primaryColor,
                        focusedBorderColor = EDSColors.primaryColor,
                        focusedLeadingIconColor = EDSColors.primaryColor,
                    ),
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    subjects.value.forEachIndexed { index, subject ->
                        item {
                            FilterChip(
                                modifier = Modifier.fillMaxWidth(),
                                selected = subject.isSelected,
                                onClick = {
                                    val newList = subjects.value.toMutableList()
                                    newList[index] = subject.copy(isSelected = !subject.isSelected)
                                    subjects.value = newList
                                },
                                label = {
                                    androidx.compose.material3.Text(
                                        subject.name, fontWeight = FontWeight.W300,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .fillMaxWidth(.85f)
                                    )
                                },
                                trailingIcon = {
                                    if (subject.isSelected)
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null
                                        )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EDSColors.greenCheck,
                                    selectedLabelColor = EDSColors.white,
                                    selectedTrailingIconColor = EDSColors.white
                                ),
                            )
                        }
                    }
                }
            }
        },

        onDismissRequest = {
            onDisMiss()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDisMiss()
            }) {
                androidx.compose.material3.Text("Accept", color = EDSColors.primaryColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDisMiss()
                }
            ) {
                androidx.compose.material3.Text("Dismiss", color = EDSColors.notScheduleTextColor)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun FilterItemPreview() {
    Surface {
        FilterChip(
            modifier = Modifier.fillMaxWidth(),
            selected = true,
            onClick = {

            },
            label = {
                androidx.compose.material3.Text(
                    "subject.name", fontWeight = FontWeight.W300,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                )
            },

            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = EDSColors.greenBackground,
                selectedLabelColor = EDSColors.greenText,
                selectedTrailingIconColor = EDSColors.greenText
            ),
        )
    }

}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
