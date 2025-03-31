package woowacourse.omok

import android.os.Build
import androidx.annotation.RequiresApi
import woowacourse.omok.controller.OmokGame
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val omokGame = OmokGame(inputView, outputView)

    omokGame.start()
}
