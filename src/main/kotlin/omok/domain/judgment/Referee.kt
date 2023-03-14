package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Stone

class Referee {
    fun hasFiveOrMoreStoneInRow(board: Map<Position, Stone?>, position: Position): Boolean {
        val verticalContinuityCount =
            countNorthContinuity(board, position, 0) + countSouthContinuity(board, position, 0) + 1
        val horizontalContinuityCount =
            countEastContinuity(board, position, 0) + countWestContinuity(board, position, 0) + 1
        val upwardDiagonalContinuityCount =
            countSouthWestContinuity(board, position, 0) + countNorthEastContinuity(board, position, 0) + 1
        val downwardDiagonalContinuityCount =
            countNorthWestContinuity(board, position, 0) + countSouthEastContinuity(board, position, 0) + 1

        return listOf(verticalContinuityCount, horizontalContinuityCount, upwardDiagonalContinuityCount, downwardDiagonalContinuityCount).any { it >= 5 }
    }

    private tailrec fun countNorthContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val northPosition = position.getNorth()
        if (northPosition != null && hasSameStone(board, position, northPosition)) return countNorthContinuity(board, northPosition, count + 1)
        return count
    }

    private tailrec fun countNorthEastContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val northEastPosition = position.getNorthEast()
        if (northEastPosition != null && hasSameStone(board, position, northEastPosition)) return countNorthEastContinuity(board, northEastPosition, count + 1)
        return count
    }

    private tailrec fun countEastContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val eastPosition = position.getEast()
        if (eastPosition != null && hasSameStone(board, position, eastPosition)) return countEastContinuity(board, eastPosition, count + 1)
        return count
    }

    private tailrec fun countSouthEastContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val southEastPosition = position.getSouthEast()
        if (southEastPosition != null && hasSameStone(board, position, southEastPosition)) return countSouthEastContinuity(board, southEastPosition, count + 1)
        return count
    }

    private tailrec fun countSouthContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val southPosition = position.getSouth()
        if (southPosition != null && hasSameStone(board, position, southPosition)) return countSouthContinuity(board, southPosition, count + 1)
        return count
    }

    private tailrec fun countSouthWestContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val southWestPosition = position.getSouthWest()
        if (southWestPosition != null && hasSameStone(board, position, southWestPosition)) return countSouthWestContinuity(board, southWestPosition, count + 1)
        return count
    }

    private tailrec fun countWestContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val westPosition = position.getWest()
        if (westPosition != null && hasSameStone(board, position, westPosition)) return countWestContinuity(board, westPosition, count + 1)
        return count
    }

    private tailrec fun countNorthWestContinuity(board: Map<Position, Stone?>, position: Position, count: Int): Int {
        val northWestPosition = position.getNorthWest()
        if (northWestPosition != null && hasSameStone(board, position, northWestPosition)) return countNorthWestContinuity(board, northWestPosition, count + 1)
        return count
    }

    private fun hasSameStone(board: Map<Position, Stone?>, position1: Position, position2: Position): Boolean {
        return board[position1] == board[position2]
    }
}
