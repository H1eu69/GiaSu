package com.projectprovip.h1eu.giasu.presentation.common.composes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectprovip.h1eu.giasu.presentation.common.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(value: String,
                  label: String,
                  supportText: String? = null,
                  isError: Boolean = false,
                  modifier : Modifier = Modifier,
                  onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        isError = isError,
        supportingText = {
            if(supportText != null && isError){
                Text(
                    supportText,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        label = {
            Text(label)
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = primaryColor,
            focusedLabelColor = primaryColor
        ),
        modifier = modifier
    )
}

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
fun MultiColorText(text1: String, color1: Color, text2: String, color2 : Color) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = color1, fontSize = 16.sp)) {
                append(text1)
            }
            withStyle(style = SpanStyle(color = color2, fontSize = 16.sp)) {
                append(text2)
            }
        }
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
            focusedIndicatorColor = primaryColor,
            unfocusedIndicatorColor = Color.LightGray,
            cursorColor = primaryColor
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.border( width = 1.dp ,
            color = Color.LightGray,
            shape = RectangleShape)
    ) {
        Text(text = title,
            modifier = Modifier.width(IntrinsicSize.Min))
        radioOptions.forEach { text ->
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Start,
                modifier = Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        })
            )
            {
                RadioButton(
                    selected = (text == selectedOption),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = primaryColor,
                        unselectedColor = Color.LightGray
                    ),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    color = if (text == selectedOption) primaryColor
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
    buttonColor: Color = primaryColor,
 )  {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(text = text)
    }
}