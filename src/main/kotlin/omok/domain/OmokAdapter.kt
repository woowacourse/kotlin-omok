package omok.domain

import omok.domain.lib.RenjuRule

class OmokAdapter(private val rule: RenjuRule = RenjuRule(OmokBoard.DEFAULT_SIZE)) {
    fun isViolate(
        board: OmokBoard,
        stone: Stone,
    ): Boolean {
        val convertedBoard = convertBoard(board)
        return rule.isViolate(convertedBoard, stone.position.x, stone.position.y)
    }

    private fun convertBoard(board: OmokBoard): List<List<Int>> {
        val convertedBoard = MutableList(board.height) { MutableList(board.width) { 0 } }
        repeat(board.height) { x ->
            repeat(board.width) { y ->
                val state = board.getStoneState(Position(x, y))
                val convertedState = when (state) {
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
