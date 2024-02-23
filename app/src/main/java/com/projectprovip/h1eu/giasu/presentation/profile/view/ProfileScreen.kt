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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Class
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Star
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
import com.projectprovip.h1eu.giasu.common.dataStore
import com.projectprovip.h1eu.giasu.presentation.class_management.view.TutorRegisterAlertDialog
import com.projectprovip.h1eu.giasu.presentation.common.composes.AppBarTitle
import com.projectprovip.h1eu.giasu.presentation.common.composes.MultiColorText
import com.projectprovip.h1eu.giasu.presentation.common.navigation.Screens
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
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
    val userName = remember {
        mutableStateOf("")
    }
    val userId = remember {
        mutableStateOf("")
    }
    val userImage = remember {
        mutableStateOf("")
    }
    val onShowDialog: (Boolean) -> Unit = {
        showDialog.value = it
    }
    LaunchedEffect(key1 = "") {
        coroutine.launch {
            context.dataStore.data.collect {
                userName.value = it[usernameKey].toString()
                userId.value = it[useridKey].toString()
                userImage.value = it[userImageKey].toString()
            }
        }
    }
    Scaffold(
        containerColor = Color.LightGray,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AppBarTitle(text = "Profile")
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EDSColors.primaryColor,
                    titleContentColor = Color.White
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
            item { Profile(userImage.value, userName.value, userId.value) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item { ColumnOfButton(navController, onShowDialog) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                ButtonColumnItem(
                    Icons.Rounded.ExitToApp, EDSColors.primaryColor,
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
fun Profile(image: String, name: String, id: String) {
    Column(
        verticalArrangement = Arrangement.Center,
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
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable {

                    })
            Icon(
                imageVector = Icons.Default.Create, contentDescription = null,
                tint = EDSColors.primaryColor,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(2.dp)
                    .align(Alignment.BottomCenter)
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
            text2 = id,
            color2 = Color.Black
        )
    }
}

@Composable
fun ColumnOfButton(navController: NavController, onShowDialog: (Boolean) -> Unit) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        ButtonColumnItem(
            Icons.Rounded.AccountCircle, EDSColors.primaryColor,
            "Personal information", true
        )
        ButtonColumnItem(
            Icons.Rounded.Class, EDSColors.primaryColor,
            "Request a new class", true,
            onClick = {
                navController.navigate(Screens.InApp.Profile.RequestClass.route)
            })
        ButtonColumnItem(Icons.Rounded.AddCircle, EDSColors.primaryColor,
            "Register lecturer", true,
            onClick = {
                navController.navigate(Screens.InApp.Profile.TutorRegistration.route)
            }
        )
        ButtonColumnItem(Icons.Rounded.Star, EDSColors.primaryColor,
            "Learning Courses", true,
            onClick = {
                navController.navigate(Screens.InApp.Profile.LearningCourses.route)
            })
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
            .border(BorderStroke(0.3.dp, Color.LightGray))
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