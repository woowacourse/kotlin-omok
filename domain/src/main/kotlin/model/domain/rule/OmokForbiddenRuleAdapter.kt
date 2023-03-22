package model.domain.rule

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokForbiddenRuleAdapter(board: Board, currentStone: Stone) : OmokForbiddenRule {

    private val convertBoard: List<List<Int>> = List(OMOK_SIZE) { row ->
        getLocationRow(row, board)
    }

    private val stoneState: Int = when (currentStone) {
        Stone.BLACK -> BLACK_STONE
        Stone.WHITE -> WHITE_STONE
        Stone.EMPTY -> EMPTY_STONE
    }

    private fun getLocationRow(row: Int, board: Board): List<Int> =
        List(OMOK_SIZE) { col ->
            getStoneNumber(requireNotNull(board.system[Location(row, col)]))
        }

    override fun isForbidden(location: Location): Boolean {
        val x = location.coordinationY.value
        val y = location.coordinationX.value
        val otherRule = OtherForbiddenRule(convertBoard, stoneState)
        return otherRule.countOpenFours(x, y) >= 2 || otherRule.countOpenThrees(x, y) >= 2
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
