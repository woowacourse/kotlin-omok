package omok.domain

import omok.domain.lib.RenjuRule

class OmokAdapter(private val rule: RenjuRule = RenjuRule(boardSize = 15)) {
    fun validatePosition(
        board: OmokBoard,
        stone: Stone,
    ): Boolean {
        val convertedBoard = convertBoard(board)
        return rule.isViolate(convertedBoard, stone.position.x, stone.position.y)
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
            convertedBoard[position.x - 1][position.y - 1] = state
        }

        return convertedBoard
    }

    private fun convertPosition(position: Position): Pair<Int, Int> {
        return (position.x - 1) to (position.y - 1)
    }
}
