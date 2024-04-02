package woowacourse.omok.domain.model

import woowacourse.omok.domain.library.RenjuRule

class RuleAdapter(val board: Board) {
    fun checkForbidden(
        point: Point,
        stoneType: StoneType,
    ): Boolean {
        val renjuRule = RenjuRule(convertBoard())
        return renjuRule.isForbidden(
            point.x,
            point.y,
            convertStoneType(stoneType),
        ) || point in board
    }

    fun checkWin(
        point: Point,
        stoneType: StoneType,
    ): Boolean {
        val renjuRule = RenjuRule(convertBoard())
        return renjuRule.isWinCondition(
            point.x,
            point.y,
            convertStoneType(stoneType),
        )
    }

    private fun convertBoard(): List<List<Int>> {
        val table =
            List(board.table.size) {
                MutableList(board.table.size) { STONE_TYPE_EMPTY }
            }
        repeat(board.table.size) { y ->
            repeat(board.table.size) { x ->
                table[y][x] = (convertStoneType(board.getBoardPoint(Point(x, y))))
            }
        }
        return table
    }

    private fun convertStoneType(stoneType: StoneType): Int {
        return when (stoneType) {
            StoneType.EMPTY -> STONE_TYPE_EMPTY
            StoneType.BLACK -> STONE_TYPE_BLACK
            StoneType.WHITE -> STONE_TYPE_WHITE
        }
    }

    companion object {
        const val WINNING_COUNT = 5
        private const val STONE_TYPE_EMPTY = 0
        private const val STONE_TYPE_BLACK = 1
        private const val STONE_TYPE_WHITE = 2
    }
}
