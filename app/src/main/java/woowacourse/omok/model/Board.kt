package woowacourse.omok.model

import omok.library.OmokRule
import woowacourse.omok.model.state.CoordinateState
import woowacourse.omok.model.state.PlaceResult
import woowacourse.omok.model.state.Turn
import woowacourse.omok.utils.OmokRuleMapper

class Board(
    private val boardSize: Int = BOARD_SIZE,
    private var _boardLayout: MutableList<MutableList<CoordinateState>> =
        MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { CoordinateState.Empty } },
) {
    val boardLayout: List<List<CoordinateState>>
        get() = _boardLayout

    private var lastCoordinate: Coordinate? = null
        private set
    private var currentTurn: Turn = Turn.Black
        private set
    private val currentStone get() = getStoneFromTurn()

    var omokRule: OmokRule = OmokRuleMapper.map(currentTurn, boardLayout, boardSize)

    private fun getStoneFromTurn(): CoordinateState {
        return when (currentTurn) {
            is Turn.Black -> CoordinateState.BlackStone
            is Turn.White -> CoordinateState.WhiteStone
        }
    }

    fun setUpBoard(currentTurn: Turn) {
        omokRule = OmokRuleMapper.map(currentTurn, boardLayout, boardSize)
        this.currentTurn = currentTurn

        if (omokRule.isForbiddenUtilizable()) setBlock() else removeBlock()
    }

    private fun setBlock() {
        val forbiddenCoordinates: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in MIN_INDEX until BOARD_SIZE) {
            for (j in MIN_INDEX until BOARD_SIZE) {
                if (isForbiddenCoordinate(i, j)) {
                    forbiddenCoordinates.add(Pair(i, j))
                }
            }
        }
        forbiddenCoordinates.forEach {
            _boardLayout[it.first][it.second] = CoordinateState.Forbidden
        }
    }

    private fun isForbiddenCoordinate(
        i: Int,
        j: Int,
    ) = omokRule.isForbidden(i, j) &&
        _boardLayout[i][j] == CoordinateState.Empty

    private fun removeBlock() {
        for (x in 0 until boardSize) {
            for (y in 0 until boardSize) {
                if (_boardLayout[x][y] == CoordinateState.Forbidden) {
                    _boardLayout[x][y] = CoordinateState.Empty
                }
            }
        }
    }

    private fun isWin(coordinate: Coordinate): Boolean {
        return omokRule.isWin(coordinate.x, coordinate.y)
    }

    fun placeStone(coordinate: Coordinate): PlaceResult {
        return when (_boardLayout[coordinate.x][coordinate.y]) {
            CoordinateState.Forbidden -> {
                PlaceResult.Block
            }
            CoordinateState.Empty -> {
                _boardLayout[coordinate.x][coordinate.y] = currentStone
                lastCoordinate = coordinate
                if (isWin(coordinate)) PlaceResult.Omok else PlaceResult.Done
            }
            else -> {
                PlaceResult.Duplicate
            }
        }
    }

    companion object {
        const val BOARD_SIZE = 15
        private const val MIN_INDEX = 0
    }
}
