package woowacourse.omok

import omok.library.BlackOmokRule
import omok.library.WhiteOmokRule

class Board(private val boardSize: Int = BOARD_SIZE) {
    private var boardLayout = BoardLayout(boardSize)
    var lastCoordinate: Coordinate? = null
        private set
    var currentTurn: Turn = Turn.Black
        private set
    val currentStone get() = getStoneFromTurn()

    fun getStoneFromTurn(): CoordinateState {
        return when(currentTurn) {
            is Turn.Black -> CoordinateState.BlackStone
            is Turn.White -> CoordinateState.WhiteStone
        }
    }

    fun getBoardLayout(): Array<Array<CoordinateState>> {
        return boardLayout.deepCopy()
    }

    fun setUpBoard(currentTurn: Turn) {
        when (currentTurn) {
            Turn.Black -> {
                setBlock()
            }
            Turn.White -> {
                removeBlock()
            }
        }
        this.currentTurn = currentTurn
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
            boardLayout[it.first, it.second] = CoordinateState.Forbidden
        }
    }

    private fun isBlockPosition(
        i: Int,
        j: Int,
    ) = (
        BlackOmokRule.isThreeThree(i, j, boardLayout.deepCopy()) ||
            BlackOmokRule.isFourFour(i, j, boardLayout.deepCopy()) ||
            BlackOmokRule.isMoreThanFive(i, j, boardLayout.deepCopy())
    ) &&
        boardLayout[i, j] == CoordinateState.Empty

    private fun removeBlock() {
        for (x in 0 until boardSize) {
            for (y in 0 until boardSize) {
                if (boardLayout[x, y] == CoordinateState.Forbidden) {
                    boardLayout[x, y] = CoordinateState.Empty
                }
            }
        }
    }

    private fun isWin(
        coordinate: Coordinate,
        coordinateState: CoordinateState,
    ): Boolean {
        return when (coordinateState) {
            CoordinateState.BlackStone -> {
                BlackOmokRule.isWin(coordinate.x, coordinate.y, boardLayout.deepCopy())
            }
            CoordinateState.WhiteStone -> {
                WhiteOmokRule.isWin(coordinate.x, coordinate.y, boardLayout.deepCopy())
            }
            else -> false
        }
    }

    // 겹치게 놓을 수 없다, 이미 놓은 곳이다, 놓은 곳이다, 오목이다,
    fun placeStone(
        coordinate: Coordinate,
    ): PlaceResult {
        return when (boardLayout[coordinate.x, coordinate.y]) {
            CoordinateState.Forbidden -> {
                PlaceResult.Block
            }
            CoordinateState.Empty -> {
                boardLayout[coordinate.x, coordinate.y] = currentStone
                lastCoordinate = coordinate
                if (isWin(coordinate, currentStone)) PlaceResult.Omok else PlaceResult.Done
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
