package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Black
import omok.domain.player.Stone
import omok.domain.player.White

class ThreeThreeReferee : PlacementReferee() {
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
        board[position] = Black
        if (countVerticalContinuity(board, position, 0) == 4) return isNorthOpen(board, position) && isSouthOpen(board, position)
        return false
    }

    private tailrec fun isNorthOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northPosition = position.getNorth()
        val stone = board[northPosition]
        if (northPosition == null || stone == White) return false
        if (stone == null) return true
        return isNorthOpen(board, northPosition)
    }

    private fun isSouthOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val southPosition = position.getSouth()
        val stone = board[southPosition]
        if (southPosition == null || stone == White) return false
        if (stone == null) return true
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
        board[position] = Black
        if (countUpwardDiagonalContinuity(board, position, 0) == 4) return isNorthEastOpen(board, position) && isSouthWestOpen(board, position)
        return false
    }

    private tailrec fun isNorthEastOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northEastPosition = position.getNorthEast()
        val stone = board[northEastPosition]
        if (northEastPosition == null || stone == White) return false
        if (stone == null) return true
        return isNorthEastOpen(board, northEastPosition)
    }

    private fun isSouthWestOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val southWestPosition = position.getSouthWest()
        val stone = board[southWestPosition]
        if (southWestPosition == null || stone == White) return false
        if (stone == null) return true
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
        board[position] = Black
        if (countHorizontalContinuity(board, position, 0) == 4) return isEastOpen(board, position) && isWestOpen(board, position)
        return false
    }

    private tailrec fun isEastOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val eastPosition = position.getEast()
        val stone = board[eastPosition]
        if (eastPosition == null || stone == White) return false
        if (stone == null) return true
        return isEastOpen(board, eastPosition)
    }

    private fun isWestOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val westPosition = position.getWest()
        val stone = board[westPosition]
        if (westPosition == null || stone == White) return false
        if (stone == null) return true
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
        board[position] = Black
        if (countDownwardDiagonalContinuity(board, position, 0) == 4) return isSouthEastOpen(board, position) && isNorthWestOpen(board, position)
        return false
    }

    private tailrec fun isSouthEastOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val southEastPosition = position.getSouthEast()
        val stone = board[southEastPosition]
        if (southEastPosition == null || stone == White) return false
        if (stone == null) return true
        return isSouthEastOpen(board, southEastPosition)
    }

    private fun isNorthWestOpen(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northWestPosition = position.getNorthWest()
        val stone = board[northWestPosition]
        if (northWestPosition == null || stone == White) return false
        if (stone == null) return true
        return isNorthWestOpen(board, northWestPosition)
    }
}
