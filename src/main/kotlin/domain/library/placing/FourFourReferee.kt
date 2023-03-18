package domain.library.placing

import domain.library.position.Position
import domain.stone.Color

class FourFourReferee : PlacementReferee() {
    override fun isForbiddenPlacement(board: Map<Position, Color?>, position: Position): Boolean {
        return countFour(board, position) >= 2
    }

    private fun countFour(board: Map<Position, Color?>, position: Position): Int {
        return listOf(
            countVerticalFour(board, position),
            countUpwardDiagonalFour(board, position),
            countHorizontalFour(board, position),
            countDownwardDiagonalFour(board, position)
        ).sum()
    }

    private fun countVerticalFour(board: Map<Position, Color?>, position: Position): Int {
        if (countVerticalContinuity(board, position, 0) == 4) return 1

        var count = 0
        val northEmptyPosition = findNorthEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInVertical(board.toMutableMap(), northEmptyPosition, position)) count++
        val southEmptyPosition = findSouthEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInVertical(board.toMutableMap(), southEmptyPosition, position)) count++
        return count
    }

    private fun hasFiveOrMoreStoneInVertical(
        board: MutableMap<Position, Color?>,
        fillPosition: Position?,
        position: Position
    ): Boolean {
        if (fillPosition != null) board[fillPosition] = Color.BLACK
        if (countVerticalContinuity(board, position, 0) >= 5) return true
        return false
    }

    private fun countUpwardDiagonalFour(board: Map<Position, Color?>, position: Position): Int {
        if (countUpwardDiagonalContinuity(board, position, 0) == 4) return 1

        var count = 0
        val northEastEmptyPosition = findNorthEastEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInUpwardDiagonal(board.toMutableMap(), northEastEmptyPosition, position)) count++
        val southWestEmptyPosition = findSouthWestEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInUpwardDiagonal(board.toMutableMap(), southWestEmptyPosition, position)) count++
        return count
    }

    private fun hasFiveOrMoreStoneInUpwardDiagonal(
        board: MutableMap<Position, Color?>,
        fillPosition: Position?,
        position: Position
    ): Boolean {
        if (fillPosition != null) board[fillPosition] = Color.BLACK
        if (countUpwardDiagonalContinuity(board, position, 0) >= 5) return true
        return false
    }

    private fun countHorizontalFour(board: Map<Position, Color?>, position: Position): Int {
        if (countHorizontalContinuity(board, position, 0) == 4) return 1

        var count = 0
        val eastEmptyPosition = findEastEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInHorizontal(board.toMutableMap(), eastEmptyPosition, position)) count++
        val westEmptyPosition = findWestEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInHorizontal(board.toMutableMap(), westEmptyPosition, position)) count++
        return count
    }

    private fun hasFiveOrMoreStoneInHorizontal(
        board: MutableMap<Position, Color?>,
        fillPosition: Position?,
        position: Position
    ): Boolean {
        if (fillPosition != null) board[fillPosition] = Color.BLACK
        if (countHorizontalContinuity(board, position, 0) >= 5) return true
        return false
    }

    private fun countDownwardDiagonalFour(board: Map<Position, Color?>, position: Position): Int {
        if (countDownwardDiagonalContinuity(board, position, 0) == 4) return 1

        var count = 0
        val southEastEmptyPosition = findSouthEastEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInDownwardDiagonal(board.toMutableMap(), southEastEmptyPosition, position)) count++
        val northWestEmptyPosition = findNorthWestEmptyPosition(board, position)
        if (hasFiveOrMoreStoneInDownwardDiagonal(board.toMutableMap(), northWestEmptyPosition, position)) count++
        return count
    }

    private fun hasFiveOrMoreStoneInDownwardDiagonal(
        board: MutableMap<Position, Color?>,
        fillPosition: Position?,
        position: Position
    ): Boolean {
        if (fillPosition != null) board[fillPosition] = Color.BLACK
        if (countDownwardDiagonalContinuity(board, position, 0) >= 5) return true
        return false
    }
}
