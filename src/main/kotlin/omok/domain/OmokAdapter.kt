package omok.domain

import omok.domain.lib.RenjuRule

class OmokAdapter(private val rule: RenjuRule = RenjuRule(boardSize = 15)) {
    fun isForbidden(
        board: OmokBoard,
        position: Position,
    ): Boolean {
        val convertedBoard = convertBoard(board)
        val convertedPosition = convertPosition(position)
        return rule.validPosition(convertedBoard, convertedPosition.first, convertedPosition.second)
    }

    private fun convertBoard(board: OmokBoard): List<List<Int>> {
        val convertedBoard = MutableList(15) { MutableList(15) { 0 } }

        board.positions.forEach { position ->
            val state =
                when (position.stoneState) {
                    StoneState.BLACK -> 1
                    StoneState.WHITE -> 2
                    else -> 0
                }
            convertedBoard[position.x][position.y] = state
        }

        return convertedBoard
    }

    private fun convertPosition(position: Position): Pair<Int, Int> {
        return position.x to position.y
    }
}
