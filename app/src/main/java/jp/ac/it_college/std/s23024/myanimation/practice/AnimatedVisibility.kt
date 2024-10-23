package jp.ac.it_college.std.s23024.myanimation.practice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.s23024.myanimation.R
import jp.ac.it_college.std.s23024.myanimation.ui.theme.MyAnimationTheme

@Preview(device = "spec:width=100dp,height=150dp,dpi=440", showBackground = true)
@Composable
private fun AnimatedVisibilityView() {
    MyAnimationTheme {
        var show by remember { mutableStateOf(true) }
        Box {
            Column {
                AnimatedVisibility(visible = show) {
                    Text(
                        modifier = Modifier
                            .size(size = 100.dp)
                            .background(color = Color.Magenta),
                        text = "アニメーションします"
                    )
                }
                Button(onClick = {
                    show = !show
                }) {
                    Text(text = if (show) "表示" else "非表示")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MultipleAnimatedVisibility() {
    MyAnimationTheme {
        var show by remember { mutableStateOf(false) }
        Column {
            Row(modifier = Modifier.size(100.dp)) {
                AnimatedVisibility(
                    visible = show,
                    enter = slideInHorizontally() + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.slide04),
                        contentDescription = null
                    )
                }
            }
            Button(onClick = {
                show = !show
            }) {
                Text(text = "ANIMATION")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimatedVisibilityAutomatically() {
    MyAnimationTheme {
        var state = remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }

        Column {
            Row(modifier = Modifier.size(width = 300.dp, height = 300.dp)) {
                AnimatedVisibility(
                    visibleState = state,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.slide04),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                }
            }
            Text(
                text = when {
                    state.isIdle && state.currentState -> "Visible"
                    !state.isIdle && state.currentState -> "Disappearing"
                    state.isIdle && !state.currentState -> "Invisible"
                    else -> "Appearing"
                }
            )
            Button(
                onClick = {
                    state.targetState = !state.targetState
                }
            ) {
                Text(text = "ANIMATION")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeTwoWindows() {
    MyAnimationTheme {
        var  state by remember { mutableStateOf(false) }
        val backgroundColor by animateColorAsState(
            targetValue = if (state) Color.Blue else Color.Yellow,
            label = "color animation"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Button(onClick = {
                state = !state
            }) {
                Text(text = "背景切り替え")
            }
        }
    }
}

@Composable
private fun AnimationBoxSize(
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float>,
    text: String
) {
    var size by remember { mutableFloatStateOf(0.5f) }
    val animationScale by animateFloatAsState(
        targetValue = size,
        animationSpec = animationSpec
    )
    Box(
        modifier = Modifier.size(size = 300.dp),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .scale(scale = animationScale)
                .size(size = 100.dp)
                .background(color = Color.Magenta)
                .clickable {
                    size = if (size == 2f) 0.5f else 2f
                }) {
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimationBoxSizePreview(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        AnimationBoxSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            text = "ばねアニメーション"
        )
        AnimationBoxSize(
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 40,
                easing = FastOutLinearInEasing
            ),
            text = "トゥイーンアニメーション"
        )

        AnimationBoxSize(
            animationSpec = repeatable(
                iterations = 3,
                animation = tween(durationMillis = 500),
                repeatMode = RepeatMode.Reverse
            ),
            text = "繰り返しアニメーション"
        )

        AnimationBoxSize(
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 500),
                repeatMode = RepeatMode.Reverse
            ),
            text = "無限繰り返しアニメーション"
        )
        AnimationBoxSize(
            animationSpec = keyframes {
                durationMillis = 1000
                0.0f at 0 using LinearOutSlowInEasing
                1.5f at 750 using  FastOutLinearInEasing
                2.0f at 1000

            },
            text = "キーフレームアニメーション"
        )
        AnimationBoxSize(
            animationSpec = snap(
                delayMillis = 1000
            ),
            text = "スナップアニメーション"
        )
    }
}

@Preview
@Composable
private fun AnimateContentSizeView() {
    var flag by remember { mutableStateOf(true) }

    Column(modifier = Modifier.width(300.dp)) {
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .animateContentSize()
        ){
            Text(text = if (flag) "Hello" else "Hello Compose Happy Kotlin coding")
        }
        Box(
            modifier = Modifier
                .background(Color.LightGray)
        ) {
            Text(text = if (flag) "Hello" else "Hello Compose Happy Kotlin coding")
        }
        Button(onClick = { flag = !flag }) {
            Text(text = "add message Compose")
        }
    }
}

@Preview
@Composable
private fun CrossfadeView() {
    var currentPage by remember { mutableStateOf("A") }

    Column {
        Box {
            Crossfade(targetState = currentPage) { screen ->
                when (screen) {
                    "A" -> Image(
                        painter = painterResource(id = R.drawable.slide04),
                        contentDescription = null
                    )

                    "B" -> Image(
                        painter = painterResource(id = R.drawable.slide09),
                        contentDescription = null
                    )
                }
            }
        }
        Button(onClick = {
            currentPage = if (currentPage == "A") "B" else "A"
        }) {
            Text(text = "currentPage: $currentPage")
        }
    }
}

enum class BoxState {
    Small, Large
}

@Preview
@Composable
private fun UpdateTransitionView() {
    var currentState by remember { mutableStateOf(BoxState.Small) }
    val transition = updateTransition(targetState = currentState)

    val size by transition.animateFloat { state ->
        when (state) {
            BoxState.Small -> 0.5f
            BoxState.Large -> 1f
        }
    }

    val  borderWidth by transition.animateDp { state ->
        when (state) {
            BoxState.Small -> 1.dp
            BoxState.Large -> 4.dp
        }
    }

    val color by transition.animateColor { state ->
        when (state) {
            BoxState.Small -> Color.Green
            BoxState.Large -> Color.Magenta
        }
    }

    Column(
        modifier = Modifier.size(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            currentState = if (currentState == BoxState.Small) {
                BoxState.Large
            } else {
                BoxState.Small
            }
        }) {
            Text(text = "updateTransition")
        }
        Image(
            modifier = Modifier
                .scale(scale = size)
                .clip(shape = CircleShape)
                .border(
                    width = borderWidth,
                    shape = CircleShape,
                    color = color
                ),
            painter = painterResource(id = R.drawable.slide04),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun RememberInfiniteTransitionView() {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    )
}

