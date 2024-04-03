package woowacourse.omok.console.view

import woowacourse.omok.model.game.FinishType

class ResultView {
    fun print(finishType: FinishType) {
        if (finishType == FinishType.DRAW) {
            println(DRAW_MESSAGE)
            return
        }
        println(WINNER_MESSAGE.format(finishType.stone.output()))
    }

    companion object {
        private const val DRAW_MESSAGE = "무승부입니다."
        private const val WINNER_MESSAGE = "우승은 %s🎉 입니다"
    }
}
