package model.domain.rule.omokForbiddenRule

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

abstract class OmokForbiddenUseOtherRule(board: Board, currentStone: Stone) {

    protected val convertBoard: List<List<Int>> = List(OMOK_SIZE) { row ->
        getLocationRow(row, board)
    }

    protected val stoneState: Int = when (currentStone) {
        Stone.BLACK -> BLACK_STONE
        Stone.WHITE -> WHITE_STONE
        Stone.EMPTY -> EMPTY_STONE
    }

    private fun getLocationRow(row: Int, board: Board): List<Int> =
        List(OMOK_SIZE) { col ->
            getStoneNumber(requireNotNull(board.system[Location(row, col)]))
        }

    private fun getStoneNumber(stone: Stone): Int = when (stone) {
        Stone.BLACK -> BLACK_STONE
        Stone.WHITE -> WHITE_STONE
        Stone.EMPTY -> EMPTY_STONE
    }

    companion object {
        private const val EMPTY_STONE = 0
        private const val BLACK_STONE = 1
        private const val WHITE_STONE = 2
        private const val OMOK_SIZE = 15
    }
}
