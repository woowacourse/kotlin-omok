package omok.model

import omok.library.RenjuRule
import omok.model.RuleAdapter.Companion.WINNING_COUNT

interface RuleAdapter {
    fun checkCanPutStone(
        board: Board,
        stone: Stone,
    ): Boolean

    fun checkCount(count: Int): Boolean

    fun checkWin(
        board: Board,
        stone: Stone,
    ): Boolean {
        val renjuRule = RenjuRule(convertBoard(board))
        return renjuRule.isWinCondition(
            stone.point.x,
            stone.point.y,
            convertStoneType(stone.type),
            ::checkCount,
        )
    }

    fun convertBoard(board: Board): List<List<Int>> {
        val table =
            List(board.size) {
                MutableList(board.size) { STONE_TYPE_EMPTY }
            }
        repeat(board.size) { y ->
            repeat(board.size) { x ->
                table[y][x] = (convertStoneType(board.getBoardPoint(y, x)))
            }
        }
        return table
    }

    fun convertStoneType(stoneType: StoneType): Int {
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

class BlackRule : RuleAdapter {
    override fun checkCanPutStone(
        board: Board,
        stone: Stone,
    ): Boolean {
        val renjuRule = RenjuRule(convertBoard(board))
        return renjuRule.isForbidden(stone.point.x, stone.point.y, convertStoneType(stone.type))
    }

    override fun checkCount(count: Int): Boolean = count == WINNING_COUNT
}

class WhiteRule : RuleAdapter {
    override fun checkCanPutStone(
        board: Board,
        stone: Stone,
    ): Boolean {
        return true
    }

    override fun checkCount(count: Int): Boolean = count >= WINNING_COUNT
}
