package omok.model

import omok.rule.RenjuRule

object RenjuRuleAdaptor {
    fun isForbidden(
        board: Board,
        stone: Stone,
    ): Boolean {
        val boardConverted = board.convert()
        val point = stone.point
        val rule = RenjuRule(boardConverted, boardSize = 15)
        return rule.run {
            checkThreeThree(point.x, point.y) || checkFourFour(point.x, point.y) || checkMoreThanFive(point.x, point.y)
        }
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
