package jp.ac.it_college.std.s23024.myanimation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.s23024.myanimation.ui.theme.MyAnimationTheme

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unselectedColor: Color,
    ) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(totalDots) { index ->
            val color by animateColorAsState(
                targetValue = if (index == selectedIndex) selectedColor else unselectedColor,
                animationSpec = tween(durationMillis = 1000),
                label = "dot color animation"
            )

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(color = color)
            )
        }
    }
}

@Preview
@Composable
private fun DotsIndicatorPreview() {
    MyAnimationTheme {
        DotsIndicator(
            totalDots = 10,
            selectedIndex = 2,
            selectedColor = Color.Blue,
            unselectedColor = Color.LightGray
        )
    }
}