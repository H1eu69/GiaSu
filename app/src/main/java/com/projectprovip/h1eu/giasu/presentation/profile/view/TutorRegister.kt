package com.projectprovip.h1eu.giasu.presentation.profile.view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.common.composes.CommonTextField
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.profile.viewmodel.TutorRegisterViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@Preview
@Composable
fun PreviewTutorRegister() {
    TutorRegisterScreen(rememberNavController(), hiltViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorRegisterScreen(navController: NavController, vm: TutorRegisterViewModel) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val tokenKey = stringPreferencesKey(Constant.TOKEN_STRING)
    val token = remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = token) {
        coroutine.launch {
            context.dataStore.data.collect { preference ->
                token.value = "Bearer ${preference[tokenKey].toString()}"
                Log.d("Token in tutor", token.value)
            }
        }
    }

    val registerState = vm.tutorRegisterState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.primaryColor
                ),
                title = {
                    Text(
                        text = "Tutor Registration",
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
    ) {
        val academicText = remember { mutableStateOf("") }
        val universityText = remember { mutableStateOf("") }
        val majorText = remember { mutableStateOf("") }

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
                    CommonTextField(
                        academicText,
                        hint = "Academic level",
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Subtitles,
                                contentDescription = "",
                                tint = EDSColors.primaryColor
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    CommonTextField(
                        universityText,
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.School,
                                contentDescription = "",
                                tint = EDSColors.primaryColor
                            )
                        },
                        hint = "University",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    CommonTextField(
                        majorText,
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Subtitles,
                                contentDescription = "",
                                tint = EDSColors.primaryColor
                            )
                        },
                        hint = "Major",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row {
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
                        }
                        ImagePicker(
                            onImageUriChanged = { uri ->
                                vm.uploadImage(uri)
                            }
                        )
                    }
                }
//                item() {
//                    CommonTextField(remember { mutableStateOf("") },
//                        hint = "last field",
//                        singleLine = false,
//                        modifier = Modifier.fillMaxWidth().
//                        height(150.dp))
//                }
            }
            Button(
                onClick = {
                    Log.d("Token in tutor onclick" , token.value)

                    vm.registerTutor(
                        token.value,
                        academicText.value,
                        universityText.value,
                        majorText.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(containerColor = EDSColors.primaryColor)
            ) {

                if (registerState.value.isLoading)
                    CircularProgressIndicator()
                else
                    Text(text = "Register", color = EDSColors.white)
                LaunchedEffect(key1 = registerState.value ) {
                    if (registerState.value.error.isNotEmpty() || registerState.value.success) {
                        showToast(context, registerState.value.error)
                        navController.popBackStack()
                    }

                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImagePicker(onImageUriChanged: ((uri: Uri) -> Unit)) {
//    val launcher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
//            hasImage.value = it
//        }
    val uri = remember { mutableStateOf(Uri.parse("")) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                uri.value = it
                onImageUriChanged(it)
                Log.d("Test uri ", uri.value.lastPathSegment.toString())
            }
        }


    if (uri.value.toString().isNotEmpty()) {
        GlideImage(model = uri.value, contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 20.dp)
                .clip(
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
        )

    } else {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 20.dp)
            .background(
                EDSColors.imageBackground,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "",
                tint = EDSColors.plusBackground,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
