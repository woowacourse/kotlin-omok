package omok.view

import omok.model.stone.GoStone
import omok.model.stone.StoneType

class ResultView {
    fun printWinner(stone: GoStone) {
        lineBreak()
        if (stone.stoneType == StoneType.BLACK_STONE) {
            println(BLACK_STONE_WIN_MESSAGE)
        } else {
            println(WHITE_STONE_WIN_MESSAGE)
        }
    }

    private fun lineBreak() = println()

    companion object {
        private const val BLACK_STONE_WIN_MESSAGE = "흑의 승리입니다."
        private const val WHITE_STONE_WIN_MESSAGE = "백의 승리입니다."
    }
}
