package woowacourse.omok

import omok.library.OmokRule

class Board(private val boardSize: Int = BOARD_SIZE) {
    private var _boardLayout: MutableList<MutableList<CoordinateState>> =
        MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { CoordinateState.Empty } }
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

        if (omokRule.isBlockable()) setBlock() else removeBlock()
    }

    private fun setBlock() {
        val blockPositions: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in MIN_INDEX until BOARD_SIZE) {
            for (j in MIN_INDEX until BOARD_SIZE) {
                if (isBlockPosition(i, j)) {
                    blockPositions.add(Pair(i, j))
                }
            }
        }
        blockPositions.forEach {
            _boardLayout[it.first][it.second] = CoordinateState.Forbidden
        }
    }

    private fun isBlockPosition(
        i: Int,
        j: Int,
    ) = (
        omokRule.isThreeThree(i, j) ||
            omokRule.isFourFour(i, j) ||
            omokRule.isMoreThanFive(i, j)
    ) &&
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
