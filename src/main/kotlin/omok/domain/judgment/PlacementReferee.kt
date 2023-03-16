package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Stone

abstract class PlacementReferee(val target: Stone) : Referee() {
    abstract fun isForbiddenPlacement(board: Map<Position, Stone?>, position: Position): Boolean

    tailrec fun findNorthEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val northPosition = position.getNorth()
        val stone = board[northPosition] ?: return northPosition
        if (northPosition == null || stone != target) return null
        return findNorthEmptyPosition(board, northPosition)
    }

    tailrec fun findSouthEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val southPosition = position.getSouth()
        val stone = board[southPosition] ?: return southPosition
        if (southPosition == null || stone != target) return null
        return findSouthEmptyPosition(board, southPosition)
    }

    tailrec fun findNorthEastEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val northEastPosition = position.getNorthEast()
        val stone = board[northEastPosition] ?: return northEastPosition
        if (northEastPosition == null || stone != target) return null
        return findNorthEastEmptyPosition(board, northEastPosition)
    }

    tailrec fun findSouthWestEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val southWestPosition = position.getSouthWest()
        val stone = board[southWestPosition] ?: return southWestPosition
        if (southWestPosition == null || stone != target) return null
        return findSouthWestEmptyPosition(board, southWestPosition)
    }

    tailrec fun findEastEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val eastPosition = position.getEast()
        val stone = board[eastPosition] ?: return eastPosition
        if (eastPosition == null || stone != target) return null
        return findEastEmptyPosition(board, eastPosition)
    }

    tailrec fun findWestEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val westPosition = position.getWest()
        val stone = board[westPosition] ?: return westPosition
        if (westPosition == null || stone != target) return null
        return findWestEmptyPosition(board, westPosition)
    }

    tailrec fun findSouthEastEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val southEastPosition = position.getSouthEast()
        val stone = board[southEastPosition] ?: return southEastPosition
        if (southEastPosition == null || stone != target) return null
        return findSouthEastEmptyPosition(board, southEastPosition)
    }

    tailrec fun findNorthWestEmptyPosition(board: Map<Position, Stone?>, position: Position): Position? {
        val northWestPosition = position.getNorthWest()
        val stone = board[northWestPosition] ?: return northWestPosition
        if (northWestPosition == null || stone != target) return null
        return findNorthWestEmptyPosition(board, northWestPosition)
    }
}
