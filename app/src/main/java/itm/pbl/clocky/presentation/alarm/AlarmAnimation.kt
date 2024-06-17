package itm.pbl.clocky.presentation.alarm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import itm.pbl.clocky.R

@Composable
fun AlarmAnimation() {
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.alarm_animation)
    )
    LottieAnimation(
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever
    )
}