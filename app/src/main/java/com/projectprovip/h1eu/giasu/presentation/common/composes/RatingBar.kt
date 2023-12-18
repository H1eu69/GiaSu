package com.projectprovip.h1eu.giasu.presentation.common.composes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    rate: Int = 5,
    maxRate: Int = 5,
    iconSize: Dp = 50.dp,
    color: Color = EDSColors.yellowStar,
    onRateChange: (Int) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (index in 1..maxRate) {
            if (index <= rate)
                Icon(
                    imageVector = Icons.Filled.StarRate,
                    contentDescription = "",
                    tint = color,
                    modifier = Modifier
                        .size(iconSize)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onRateChange(index)
                        }

                )
            else Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = "",
                tint = color,
                modifier = Modifier
                    .size(iconSize)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onRateChange(index)
                    })
        }
    }
}