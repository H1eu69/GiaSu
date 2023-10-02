package com.projectprovip.h1eu.giasu.common.composes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectprovip.h1eu.giasu.common.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(value: String, label: String, modifier : Modifier = Modifier, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label) },
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
fun InformationTextField(text: MutableState<String>,
                         tintIcon: Color = primaryColor,
                         singleLine: Boolean = true,
                         modifier: Modifier = Modifier) {

    TextField(value = text.value,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = primaryColor,
            unfocusedIndicatorColor = Color.LightGray,
            cursorColor = primaryColor
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = tintIcon,)
        },
        onValueChange = { name ->
            text.value = name
        },
        singleLine = singleLine,
        modifier = modifier
    )
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