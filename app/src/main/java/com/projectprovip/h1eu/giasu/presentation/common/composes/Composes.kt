package com.projectprovip.h1eu.giasu.presentation.common.composes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors


@Composable
fun AppBarTitle(text: String, fontSize: Int = 20) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp
        )
    )
}

@Composable
fun MultiColorText(text1: String, color1: Color, text2: String, color2 : Color, modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = color1, fontSize = 16.sp)) {
                append(text1)
            }
            withStyle(style = SpanStyle(color = color2, fontSize = 16.sp)) {
                append(text2)
            }
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(text: MutableState<String>,
                    hint: String? = null,
                    singleLine: Boolean = true,
                    keyboardOptions:KeyboardOptions = KeyboardOptions.Default,
                    icon: @Composable (() -> Unit)? = null,
                    modifier: Modifier = Modifier) {

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
                Text(text = hint,
                    color = Color.LightGray)
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
@Composable
fun CommonRadioButton(title: String,
                      radioOptions: List<String>,
                      selectedOption: String,
                      onOptionSelected: (String) -> Unit,
                      modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(

        modifier = modifier
    ) {
        Text(text = title,)
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
 )  {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        if(!isLoading)
            Text(text = text)
        else
            CircularProgressIndicator()
    }
}