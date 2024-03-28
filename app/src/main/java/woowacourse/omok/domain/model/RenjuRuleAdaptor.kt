package omok.model

import woowacourse.omok.domain.model.OmokRule

class RenjuRuleAdaptor(private val rule: List<OmokRule>) {
    fun isForbidden(
        board: Board,
        stone: Stone,
    ): Boolean {
        val boardConverted = board.convert()
        val point = stone.point
        return rule.all { it.validPosition(boardConverted, point.x, point.y) }
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
