package com.projectprovip.h1eu.giasu.ui.composes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectprovip.h1eu.giasu.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(value: String, label: String, onValueChange: (String) -> Unit) {
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
        )
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