package com.projectprovip.h1eu.giasu.presentation.common.composes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors

@Composable
@Preview
fun PreviewRatingBar() {
    RatingBar() {

    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rate: Double = 0.0,
    maxRate: Int = 5,
    color: Color = EDSColors.yellowStar,
    onRateChange: (Double) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row {
        for (index in 1..maxRate) {
            if (index <= rate)
                Icon(
                    imageVector = Icons.Filled.StarRate,
                    contentDescription = "",
                    tint = color,
                    modifier = modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onRateChange(index.toDouble())
                    }

                )
            else Icon(
                imageVector = Icons.Outlined.StarRate,
                contentDescription = "",
                tint = color,
                modifier = modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onRateChange(index.toDouble())
                })
        }
    }
}