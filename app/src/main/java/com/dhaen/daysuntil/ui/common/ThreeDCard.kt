package com.dhaen.daysuntil.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dhaen.daysuntil.R
import com.dhaen.daysuntil.ui.theme.onCustomColor1DarkMediumContrast


@Composable
fun ThreeDCard(modifier: Modifier = Modifier, shadowColor: Brush, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .offset(y = 6.dp, x = 5.dp)
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(12.dp),
                    brush = shadowColor
                )
        )

        Box(
            modifier = modifier
                .fillMaxSize(),
        ) {
            content()
        }
    }
}