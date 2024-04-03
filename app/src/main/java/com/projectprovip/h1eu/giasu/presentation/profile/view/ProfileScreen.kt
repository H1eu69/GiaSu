package com.projectprovip.h1eu.giasu.presentation.profile.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Class
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.HowToReg
import androidx.compose.material.icons.rounded.Note
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.projectprovip.h1eu.giasu.presentation.course_management.view.TutorRegisterAlertDialog
import kotlinx.coroutines.launch

@Preview
@Composable
fun Preview() {
    ProfileScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    val usernameKey = stringPreferencesKey(Constant.USERNAME_STRING)
    val useridKey = stringPreferencesKey(Constant.USERID_STRING)
    val userImageKey = stringPreferencesKey(Constant.USER_IMAGE_STRING)
    val userEmailKey = stringPreferencesKey(Constant.USER_EMAIL_STRING)
    val userRoleKey = stringPreferencesKey(Constant.USER_ROLE_STRING)

    val userName = remember {
        mutableStateOf("")
    }

    val userImage = remember {
        mutableStateOf("")
    }
    val userEmail = remember {
        mutableStateOf("")
    }
    val onShowDialog: (Boolean) -> Unit = {
        showDialog.value = it
    }
    val role = remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect {
                userName.value = it[usernameKey].toString()
                userImage.value = it[userImageKey].toString()
                userEmail.value = it[userEmailKey].toString()
                role.value = it[userRoleKey].toString()
            }
        }
    }
    val isTutor = role.value == "Tutor"
    Scaffold(
        containerColor = EDSColors.mainBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AppBarTitle(text = "Profile")
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.white,
                    titleContentColor = EDSColors.primaryColor
                )
            )
        }
    ) {
        if (showDialog.value) {
            TutorRegisterAlertDialog(showDialog.value, {
                showDialog.value = !showDialog.value
            }, {
                showDialog.value = !showDialog.value
            })
        }
        LazyColumn(modifier = Modifier.padding(it)) {
            item { Profile(navController, userImage.value, userName.value, email = userEmail.value) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    ButtonColumnItem(
                        Icons.Rounded.AccountCircle, EDSColors.primaryColor,
                        "Profile", true,
                        onClick = {
                            navController.navigate(Screens.InApp.Profile.UpdateProfile.route)
                        }
                    )
                    ButtonColumnItem(
                        Icons.Rounded.Class, EDSColors.primaryColor,
                        "Create a new course", true,
                        onClick = {
                            navController.navigate(Screens.InApp.Profile.RequestClass.route)
                        })
                    if (!isTutor) {
                        ButtonColumnItem(Icons.Rounded.Note, EDSColors.primaryColor,
                            "Your courses", true,
                            onClick = {
                                navController.navigate(Screens.InApp.Profile.LearningCourses.route)
                            })
                        ButtonColumnItem(Icons.Rounded.HowToReg, EDSColors.primaryColor,
                            "Enroll as a tutor", true,
                            onClick = {
                                navController.navigate(Screens.InApp.Profile.TutorRegistration.route)
                            })
                    } else {
                        ButtonColumnItem(Icons.Rounded.Note, EDSColors.primaryColor,
                            "Your courses", true,
                            onClick = {
                                navController.navigate(Screens.InApp.Profile.LearningCourses.route)
                            })
                    }

                }
            }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                ButtonColumnItem(
                    Icons.AutoMirrored.Rounded.ExitToApp, EDSColors.primaryColor,
                    "Change password", true
                )
            }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                ButtonColumnItem(Icons.Rounded.ExitToApp, Color.Red,
                    "Logout", false, onClick = {
                        navController.navigate(Screens.Authentication.route) {
                            popUpTo(Screens.InApp.route) {
                                inclusive = true
                            }
                        }
                    })
            }
            item { Spacer(modifier = Modifier.height(50.dp)) }

        }
    }
}

@Composable
fun Profile(
    navController: NavController, image: String,
    name: String = "Dummy name",
    email: String = "DummyEmfdsfsdsail@gmail.com",
    phone: String = "097845612",
    role: String = "Dummy Role",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(EDSColors.mainBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
        ) {
            AsyncImage(
                image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable {

                    })
        }
        Text(
            text = name, style = EDSTextStyle.H1Med()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 20.dp, top = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = null,
                tint = Color(79, 79, 79),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = email, style = EDSTextStyle.H3Reg(
                    Color(79, 79, 79)
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TutorRegisterAlertDialog(
    open: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    when {
        open -> {
            AlertDialog(
                title = {
                    androidx.compose.material3.Text(text = "You don't have permission to access")
                },
                text = {
                    androidx.compose.material3.Text(text = "Please register as tutor to access this")
                },
                onDismissRequest = {
                    onDismissRequest()
                },
                confirmButton = {
                    TextButton(onClick = { onConfirmation() }) {
                        androidx.compose.material3.Text("Accept")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onDismissRequest()
                        }
                    ) {
                        androidx.compose.material3.Text("Dismiss")
                    }
                }
            )
        }
    }
}


@Composable
fun ButtonColumnItem(
    imageVector: ImageVector, iconColor: Color, title: String, hasNavigateIcon: Boolean,
    onClick: (() -> Unit) = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .background(Color.White)
            .padding(12.dp)

    ) {
        Icon(
            imageVector = imageVector, contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title, style = TextStyle(
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        if (hasNavigateIcon)
            Icon(
                imageVector = Icons.Default.ArrowForward, contentDescription = null,
                tint = iconColor
            )
    }
}