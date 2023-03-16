package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Stone

class ThreeThreeReferee(target: Stone) : PlacementReferee(target) {
    override fun isForbiddenPlacement(board: Map<Position, Stone?>, position: Position): Boolean {
        return countThree(board, position) >= 2
    }

    private fun countThree(board: Map<Position, Stone?>, position: Position): Int {
        return listOf(
            isVerticalThree(board, position),
            isUpwardDiagonalThree(board, position),
            isHorizontalThree(board, position),
            isDownwardDiagonalThree(board, position)
        ).count { it }
    }

    private fun isVerticalThree(board: Map<Position, Stone?>, position: Position): Boolean {
        val northEmptyPosition = findNorthEmptyPosition(board, position)
        if (northEmptyPosition != null && isVerticalOpenFour(board.toMutableMap(), northEmptyPosition)) return true
        val southEmptyPosition = findSouthEmptyPosition(board, position)
        if (southEmptyPosition != null && isVerticalOpenFour(board.toMutableMap(), southEmptyPosition)) return true
        return false
    }

    private fun isVerticalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = target
        if (countVerticalContinuity(board, position) == FOUR_NUMBER) return isNorthOpen(board, position) && isSouthOpen(board, position)
        return false
    }

    private tailrec fun isNorthOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northPosition = position.getNorth()
        val stone = board[northPosition] ?: return true
        if (northPosition == null || stone != target) return false
        return isNorthOpen(board, northPosition)
    }

    private fun isSouthOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val southPosition = position.getSouth()
        val stone = board[southPosition] ?: return true
        if (southPosition == null || stone != target) return false
        return isSouthOpen(board, southPosition)
    }

    private fun isUpwardDiagonalThree(board: Map<Position, Stone?>, position: Position): Boolean {
        val northEastEmptyPosition = findNorthEastEmptyPosition(board, position)
        if (northEastEmptyPosition != null && isUpwardDiagonalOpenFour(board.toMutableMap(), northEastEmptyPosition)) return true
        val southWestEmptyPosition = findSouthWestEmptyPosition(board, position)
        if (southWestEmptyPosition != null && isUpwardDiagonalOpenFour(board.toMutableMap(), southWestEmptyPosition)) return true
        return false
    }

    private fun isUpwardDiagonalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = target
        if (countUpwardDiagonalContinuity(board, position) == FOUR_NUMBER) return isNorthEastOpen(board, position) && isSouthWestOpen(board, position)
        return false
    }

    private tailrec fun isNorthEastOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northEastPosition = position.getNorthEast()
        val stone = board[northEastPosition] ?: return true
        if (northEastPosition == null || stone != target) return false
        return isNorthEastOpen(board, northEastPosition)
    }

    private fun isSouthWestOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val southWestPosition = position.getSouthWest()
        val stone = board[southWestPosition] ?: return true
        if (southWestPosition == null || stone != target) return false
        return isSouthWestOpen(board, southWestPosition)
    }

    private fun isHorizontalThree(board: Map<Position, Stone?>, position: Position): Boolean {
        val eastEmptyPosition = findEastEmptyPosition(board, position)
        if (eastEmptyPosition != null && isHorizontalOpenFour(board.toMutableMap(), eastEmptyPosition)) return true
        val westEmptyPosition = findWestEmptyPosition(board, position)
        if (westEmptyPosition != null && isHorizontalOpenFour(board.toMutableMap(), westEmptyPosition)) return true
        return false
    }

    private fun isHorizontalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = target
        if (countHorizontalContinuity(board, position) == FOUR_NUMBER) return isEastOpen(board, position) && isWestOpen(board, position)
        return false
    }

    private tailrec fun isEastOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val eastPosition = position.getEast()
        val stone = board[eastPosition] ?: return true
        if (eastPosition == null || stone != target) return false
        return isEastOpen(board, eastPosition)
    }

    private fun isWestOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val westPosition = position.getWest()
        val stone = board[westPosition] ?: return true
        if (westPosition == null || stone != target) return false
        return isWestOpen(board, westPosition)
    }

    private fun isDownwardDiagonalThree(board: Map<Position, Stone?>, position: Position): Boolean {
        val southEastEmptyPosition = findSouthEastEmptyPosition(board, position)
        if (southEastEmptyPosition != null && isDownwardDiagonalOpenFour(board.toMutableMap(), southEastEmptyPosition)) return true
        val northWestEmptyPosition = findNorthWestEmptyPosition(board, position)
        if (northWestEmptyPosition != null && isDownwardDiagonalOpenFour(board.toMutableMap(), northWestEmptyPosition)) return true
        return false
    }

    private fun isDownwardDiagonalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = target
        if (countDownwardDiagonalContinuity(board, position) == FOUR_NUMBER) return isSouthEastOpen(board, position) && isNorthWestOpen(board, position)
        return false
    }

    private tailrec fun isSouthEastOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val southEastPosition = position.getSouthEast()
        val stone = board[southEastPosition] ?: return true
        if (southEastPosition == null || stone != target) return false
        return isSouthEastOpen(board, southEastPosition)
    }

    private fun isNorthWestOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northWestPosition = position.getNorthWest()
        val stone = board[northWestPosition] ?: return true
        if (northWestPosition == null || stone != target) return false
        return isNorthWestOpen(board, northWestPosition)
    }
}
