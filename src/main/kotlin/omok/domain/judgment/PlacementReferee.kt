package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Black
import omok.domain.player.Stone
import omok.domain.player.White

class PlacementReferee : Referee() {
    fun isForbiddenPlacement(board: Map<Position, Stone?>, position: Position): Boolean {
        return countThree(board, position) >= 2
    }

    private fun countThree(board: Map<Position, Stone?>, position: Position): Int {
        val virtualBoard = board.toMutableMap()
        virtualBoard[position] = Black
        return listOf(
            isVerticalThree(virtualBoard, position),
            isUpwardDiagonalThree(virtualBoard, position),
            isHorizontalThree(virtualBoard, position),
            isDownwardDiagonalThree(virtualBoard, position)
        ).count { it }
    }

    private fun isVerticalThree(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northEmptyPosition = findNorthEmptyPosition(board, position)
        if (northEmptyPosition != null && isVerticalOpenFour(board.toMutableMap(), northEmptyPosition)) return true
        val southEmptyPosition = findSouthEmptyPosition(board, position)
        if (southEmptyPosition != null && isVerticalOpenFour(board.toMutableMap(), southEmptyPosition)) return true
        return false
    }

    private tailrec fun findNorthEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val northPosition = position.getNorth()
        val stone = board[northPosition]
        if (northPosition == null || stone == White) return null
        if (stone == null) return northPosition
        return findNorthEmptyPosition(board, northPosition)
    }

    private tailrec fun findSouthEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val southPosition = position.getSouth()
        val stone = board[southPosition]
        if (southPosition == null || stone == White) return null
        if (stone == null) return southPosition
        return findSouthEmptyPosition(board, southPosition)
    }

    private fun isVerticalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = Black
        val countContinuity = countNorthContinuity(board, position, 0) + countSouthContinuity(board, position, 0) + 1
        if (countContinuity == 4) return isNorthOpen(board, position) && isSouthOpen(board, position)
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

    private fun isUpwardDiagonalThree(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val northEastEmptyPosition = findNorthEastEmptyPosition(board, position)
        if (northEastEmptyPosition != null && isUpwardDiagonalOpenFour(board.toMutableMap(), northEastEmptyPosition)) return true
        val southWestEmptyPosition = findSouthWestEmptyPosition(board, position)
        if (southWestEmptyPosition != null && isUpwardDiagonalOpenFour(board.toMutableMap(), southWestEmptyPosition)) return true
        return false
    }

    private tailrec fun findNorthEastEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val northEastPosition = position.getNorthEast()
        val stone = board[northEastPosition]
        if (northEastPosition == null || stone == White) return null
        if (stone == null) return northEastPosition
        return findNorthEastEmptyPosition(board, northEastPosition)
    }

    private tailrec fun findSouthWestEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val southWestPosition = position.getSouthWest()
        val stone = board[southWestPosition]
        if (southWestPosition == null || stone == White) return null
        if (stone == null) return southWestPosition
        return findSouthWestEmptyPosition(board, southWestPosition)
    }

    private fun isUpwardDiagonalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = Black
        val countContinuity =
            countNorthEastContinuity(board, position, 0) + countSouthWestContinuity(board, position, 0) + 1
        if (countContinuity == 4) return isNorthEastOpen(board, position) && isSouthWestOpen(board, position)
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

    private fun isHorizontalThree(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val eastEmptyPosition = findEastEmptyPosition(board, position)
        if (eastEmptyPosition != null && isHorizontalOpenFour(board.toMutableMap(), eastEmptyPosition)) return true
        val westEmptyPosition = findWestEmptyPosition(board, position)
        if (westEmptyPosition != null && isHorizontalOpenFour(board.toMutableMap(), westEmptyPosition)) return true
        return false
    }

    private tailrec fun findEastEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val eastPosition = position.getEast()
        val stone = board[eastPosition]
        if (eastPosition == null || stone == White) return null
        if (stone == null) return eastPosition
        return findEastEmptyPosition(board, eastPosition)
    }

    private tailrec fun findWestEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val westPosition = position.getWest()
        val stone = board[westPosition]
        if (westPosition == null || stone == White) return null
        if (stone == null) return westPosition
        return findWestEmptyPosition(board, westPosition)
    }

    private fun isHorizontalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = Black
        val countContinuity = countEastContinuity(board, position, 0) + countWestContinuity(board, position, 0) + 1
        if (countContinuity == 4) return isEastOpen(board, position) && isWestOpen(board, position)
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

    private fun isDownwardDiagonalThree(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        val southEastEmptyPosition = findSouthEastEmptyPosition(board, position)
        if (southEastEmptyPosition != null && isDownwardDiagonalOpenFour(board.toMutableMap(), southEastEmptyPosition)) return true
        val northWestEmptyPosition = findNorthWestEmptyPosition(board, position)
        if (northWestEmptyPosition != null && isDownwardDiagonalOpenFour(board.toMutableMap(), northWestEmptyPosition)) return true
        return false
    }

    private tailrec fun findSouthEastEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val southEastPosition = position.getSouthEast()
        val stone = board[southEastPosition]
        if (southEastPosition == null || stone == White) return null
        if (stone == null) return southEastPosition
        return findSouthEastEmptyPosition(board, southEastPosition)
    }

    private tailrec fun findNorthWestEmptyPosition(board: MutableMap<Position, Stone?>, position: Position): Position? {
        val northWestPosition = position.getNorthWest()
        val stone = board[northWestPosition]
        if (northWestPosition == null || stone == White) return null
        if (stone == null) return northWestPosition
        return findNorthWestEmptyPosition(board, northWestPosition)
    }

    private fun isDownwardDiagonalOpenFour(board: MutableMap<Position, Stone?>, position: Position): Boolean {
        board[position] = Black
        val countContinuity =
            countSouthEastContinuity(board, position, 0) + countNorthWestContinuity(board, position, 0) + 1
        if (countContinuity == 4) return isSouthEastOpen(board, position) && isNorthWestOpen(board, position)
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
