package omok.model

import omok.model.Board.Companion.BOARD_SIZE
import omok.rule.RenjuRule

class RenjuRuleAdaptor(private val rule: RenjuRule = RenjuRule(boardSize = BOARD_SIZE)) {
    fun isForbidden(
        board: Board,
        stone: Stone,
    ): Boolean {
        val boardConverted = board.convert()
        val point = stone.point
        return rule.validPosition(boardConverted, point.x, point.y)
    }

    private fun Board.convert(): List<List<Int>> {
        return board.map { row ->
            row.map { stoneType ->
                when (stoneType) {
                    StoneType.BLACK -> 1
                    StoneType.WHITE -> 2
                    StoneType.EMPTY -> 0
                }
            }
        }
    }
}
