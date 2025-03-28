package woowacourse.omok.view

import woowacourse.omok.domain.Board
import woowacourse.omok.domain.StoneType

object BoardRenderer {
    fun render(board: Board): String {
        val boardMap =

            StringBuilder(
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
                """.trimIndent(),
            )
        board.stones.forEach {
            if (it.color != StoneType.EMPTY) {
                val stone = if (it.color == StoneType.WHITE) '○' else '●'
                val pointX = (it.position.x) * BOARD_INTERVAL
                val pointY = BOARD_LENGTH - it.position.y
                val idx = (BOARD_LENGTH * BOARD_INTERVAL + BOARD_EMPTY_INTERVAL) * pointY + pointX
                boardMap.setCharAt(idx, stone)
            }
        }
        return boardMap.toString()
    }

    private const val BOARD_INTERVAL = 3
    private const val BOARD_LENGTH = 15
    private const val BOARD_EMPTY_INTERVAL = 2
}
