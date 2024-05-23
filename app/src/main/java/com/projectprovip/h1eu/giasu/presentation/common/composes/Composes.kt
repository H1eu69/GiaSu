package com.projectprovip.h1eu.giasu.presentation.common.composes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projectprovip.h1eu.giasu.common.EDSTextStyle
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay


@Composable
fun AppBarTitle(text: String, fontSize: Int = 20) {
    Text(
        text = text,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,

        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp
        )
    )
}

@Composable
fun MultiColorText(
    text1: String,
    color1: Color,
    text2: String,
    color2: Color,
    modifier: Modifier = Modifier
) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = color1, fontSize = 16.sp)) {
                append(text1)
            }
            withStyle(style = SpanStyle(color = color2, fontSize = 16.sp)) {
                append(text2)
            }
        },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    text: MutableState<String>,
    hint: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    icon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    TextField(
        value = text.value,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = EDSColors.primaryColor,
            unfocusedIndicatorColor = Color.LightGray,
            cursorColor = EDSColors.primaryColor
        ),
        leadingIcon = icon,
        placeholder = {
            if (hint != null) {
                Text(
                    text = hint,
                    color = Color.LightGray
                )
            }
        },
        onValueChange = { name ->
            text.value = name
        },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Preview
@Composable
fun CommonRadioButtonPreview() {
    CommonRadioButton("test", listOf("1", "2", "3"), "1", { s1 -> })
}

@Composable
fun CommonRadioButton(
    title: String,
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
    ) {
        Text(text = title)
        radioOptions.forEach { text ->
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Start,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            onOptionSelected(text)
                        })
            )
            {
                RadioButton(
                    selected = (text == selectedOption),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = EDSColors.primaryColor,
                        unselectedColor = Color.LightGray
                    ),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    color = if (text == selectedOption) EDSColors.primaryColor
                    else Color.LightGray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EduSmartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    isLoading: Boolean = false,
    buttonColor: Color = EDSColors.primaryColor,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        if (!isLoading)
            Text(text = text)
        else
            CircularProgressIndicator()
    }
}

@Composable
fun RightModalDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    scrimColor: Color = DrawerDefaults.scrimColor,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = modifier,
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled,
            scrimColor = scrimColor,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    // under the hood, drawerContent is wrapped in a Column, but it would be under the Rtl layout
                    // so we create new column filling max width under the Ltr layout
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        content = drawerContent
                    )
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            },
        )

    }
}

@Preview
@Composable
fun OtpTextFieldPreview() {
    val context = LocalContext.current
    val otpValue = remember { mutableStateOf("") }
    val isOtpFilled = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    OtpInputField(
        modifier = Modifier
            .padding(top = 48.dp)
            .focusRequester(focusRequester),
        otpText = otpValue.value,
        shouldCursorBlink = false,
        onOtpModified = { value, otpFilled ->
            otpValue.value = value
            isOtpFilled.value = otpFilled
            if (otpFilled) {
                keyboardController?.hide()
            }
        }
    )
}

@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpLength: Int = 6,
    shouldShowCursor: Boolean = false,
    shouldCursorBlink: Boolean = false,
    onOtpModified: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpLength) {
            throw IllegalArgumentException("OTP should be $otpLength digits")
        }
    }
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpLength) {
                onOtpModified.invoke(it.text, it.text.length == otpLength)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),

        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpLength) { index ->
                    CharacterContainer(
                        index = index,
                        text = otpText,
                        shouldShowCursor = shouldShowCursor,
                        shouldCursorBlink = shouldCursorBlink,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

/**
 * An internal composable function used within [OtpInputField] to render individual character containers.
 *
 * Each character container displays a single character of the OTP and manages cursor visibility and blinking.
 *
 * @param index The position of this character in the OTP.
 * @param text The current text of the OTP input field.
 * @param shouldShowCursor Boolean flag to indicate if the cursor should be shown for this container.
 * @param shouldCursorBlink Boolean flag to indicate if the cursor should blink when shown.
 *
 * Note: This function cannot be used outside the context of [OtpInputField] as it is tailored to its specific use-case.
 */
@Composable
fun CharacterContainer(
    index: Int,
    text: String,
    shouldShowCursor: Boolean,
    shouldCursorBlink: Boolean,
) {
    val isFocused = text.length == index
    val character = when {
        index < text.length -> text[index].toString()
        else -> ""
    }

    // Cursor visibility state
    val cursorVisible = remember { mutableStateOf(shouldShowCursor) }

    // Blinking effect for the cursor
    LaunchedEffect(key1 = isFocused) {
        if (isFocused && shouldShowCursor && shouldCursorBlink) {
            while (true) {
                delay(800) // Adjust the blinking speed here
                cursorVisible.value = !cursorVisible.value
            }
        }
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .border(
                width = when {
                    isFocused -> 2.dp
                    else -> 1.dp
                },
                color = when {
                    isFocused -> EDSColors.primaryColor
                    else -> EDSColors.gray
                },
                shape = RoundedCornerShape(6.dp)
            )
            .padding(2.dp), contentAlignment = Alignment.Center
    ) {
        Text(

            text = character,
            style = EDSTextStyle.H1Med(),
            color = if (isFocused) EDSColors.primaryColor else EDSColors.blackColor,
            textAlign = TextAlign.Center
        )

        // Display cursor when focused
        AnimatedVisibility(visible = isFocused && cursorVisible.value) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(2.dp)
                    .height(24.dp) // Adjust height according to your design
                    .background(EDSColors.grayX2)
            )
        }
    }
}

@Preview
@Composable
private fun ShimmerCoursePreview() {
    Surface {
        ShimmerCourse()
    }
}

@Composable
fun ShimmerCourse(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.LightGray
        ),
        border = BorderStroke(2.dp, Color.LightGray),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10)
            )
            .shimmer()

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)

                    .background(EDSColors.grayX2)
            )
            //SubTitle(text = "ID: ${data.id}")
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(EDSColors.grayX2)
            )
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(20.dp)
                        .background(EDSColors.grayX2)
                )
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(20.dp)
                        .background(EDSColors.grayX2)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ShimmerTutorPreview() {

    Surface {
        ShimmerTutorList()
    }
}

@Composable
fun ShimmerTutorList() {

    val listState = rememberLazyGridState()
    val listCount = 10
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(count = listCount) { index ->
            TutorShimmer()
        }
    }
}

@Composable
fun TutorShimmer() {
    Card(
        shape = RoundedCornerShape(10),
        colors = CardDefaults.elevatedCardColors(
            containerColor = EDSColors.white
        ),
        border = BorderStroke(1.dp, EDSColors.gray),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10)
            )
            .shimmer()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))

        ) {
//            AsyncImage(
//                model = tutor.image,
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.clip(
//                    RoundedCornerShape(
//                        topStart = 20.dp,
//                        topEnd = 20.dp
//                    )
//                )
//            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)

                    .background(EDSColors.grayX2)

            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(20.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                        .background(EDSColors.grayX2)
                )
            }
        }
    }

}

@Preview
@Composable
private fun test() {
    Surface {
        test2()
    }
}

@Composable
private fun test2() {
    AsyncImage(
        model = "https://img.vietqr.io/image/vietinbank-107867236970-compact2.jpg?amount=790000&addInfo=Register%20ES%201000&accountName=HieuPro",
        contentDescription =null
    )
}