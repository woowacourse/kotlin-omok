package woowacourse.omok.view

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.StoneColor

object ConsoleOmokOutputView : OmokOutputView {
    override fun showStartMessage() {
        println("오목 게임을 시작합니다.")
    }

    override fun showProgress(
        board: Board,
        stone: OmokStone?,
    ) {
        showOmokBoard(board)
        printWhoIsNext(stone)
    }

    override fun showGameResult(
        board: Board,
        stone: OmokStone,
    ) {
        showOmokBoard(board)
        showWinner(stone)
    }

    private fun printWhoIsNext(stone: OmokStone?) {
        val stoneColor =
            if (stone != null) {
                getNextColor(stone)
            } else {
                "흑"
            } + "의 차례입니다"

        val lastPosition =
            if (stone != null) {
                "(마지막 돌의 위치: %s)".format(
                    stone.position.run {
                        x.formatToPositionX() + y.formatToPositionY()
                    },
                )
            } else {
                ""
            }
        println(stoneColor + lastPosition)
    }

    private fun getNextColor(stone: OmokStone) =
        when (stone.color) {
            StoneColor.BLACK -> "백"
            StoneColor.WHITE -> "흑"
        }

    private fun showWinner(stone: OmokStone?) {
        if (stone == null) {
            return println("승자가 나오지 않았습니다!")
        }
        val color = getNextColor(stone)
        println("축하합니다! %s이(가) 이겼습니다.".format(color))
    }

    private fun Int.formatToPositionX(): String = (this + 'A'.code - 1).toChar().toString()

    private fun Int.formatToPositionY(): String = (this + '0'.code).toChar().toString()

    private fun showOmokBoard(board: Board) {
        println()
        val strMap =
            """
            15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
            14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            9  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            8  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            7  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            6  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            5  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            4  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
               A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
            """.trimIndent()
        val sb = StringBuilder(strMap)
        board.stones.values.forEach {
            val stoneChar = if (it.color == StoneColor.WHITE) '○' else '●'
            val x = (it.position.x) * 3
            val y = 15 - it.position.y
            val idx = 47 * y + x
            sb.setCharAt(idx, stoneChar)
        }
        println(sb.toString())
    }
}
