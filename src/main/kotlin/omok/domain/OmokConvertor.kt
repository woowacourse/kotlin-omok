package omok.domain

import omok.domain.lib.OmokRule

object OmokConvertor {
    fun convertBoard(board: OmokBoard): List<List<Int>> {
        val convertedBoard = MutableList(15) { MutableList(15) { 0 } }

        board.positions.forEach { position ->
            val state =
                when (position.stoneState) {
                    StoneState.BLACK -> OmokRule.BLACK_STONE
                    StoneState.WHITE -> OmokRule.WHITE_STONE
                    else -> OmokRule.EMPTY_STONE
                }
            convertedBoard[position.x][position.y] = state
        }

        return convertedBoard
    }

    fun convertPosition(position: Position): Pair<Int, Int> {
        return position.x to position.y
    }
}