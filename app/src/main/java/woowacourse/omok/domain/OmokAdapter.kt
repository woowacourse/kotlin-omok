package woowacourse.omok.domain

import woowacourse.omok.domain.lib.RenjuRule

class OmokAdapter(
    private val rule: RenjuRule = RenjuRule(OmokBoard.DEFAULT_SIZE),
) : Rule {
    override fun isViolate(
        board: OmokBoard,
        stone: Stone,
    ): Boolean {
        val convertedBoard = convertBoard(board)
        return rule.isViolate(convertedBoard, stone.position.x, stone.position.y)
    }

    private fun convertBoard(board: OmokBoard): List<List<Int>> {
        val convertedBoard = MutableList(board.size) { MutableList(board.size) { 0 } }
        repeat(board.size) { x ->
            repeat(board.size) { y ->
                val state = board.getStoneState(Position(x, y))
                val convertedState =
                    when (state) {
                        StoneState.BLACK -> 1
                        StoneState.WHITE -> 2
                        StoneState.BLANK -> 0
                    }
                convertedBoard[y][x] = convertedState
            }
        }
        return convertedBoard
    }
}
