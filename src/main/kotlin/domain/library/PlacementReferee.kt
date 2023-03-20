package domain.library

import domain.stone.Color
import domain.stone.Position

abstract class PlacementReferee : Referee() {
    abstract fun isForbiddenPlacement(board: Map<Position, Color?>, position: Position): Boolean

    tailrec fun findNorthEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val northPosition = position.getNorth()
        val stone = board[northPosition]
        if (northPosition == null || stone == Color.WHITE) return null
        if (stone == null) return northPosition
        return findNorthEmptyPosition(board, northPosition)
    }

    tailrec fun findSouthEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val southPosition = position.getSouth()
        val stone = board[southPosition]
        if (southPosition == null || stone == Color.WHITE) return null
        if (stone == null) return southPosition
        return findSouthEmptyPosition(board, southPosition)
    }

    tailrec fun findNorthEastEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val northEastPosition = position.getNorthEast()
        val stone = board[northEastPosition]
        if (northEastPosition == null || stone == Color.WHITE) return null
        if (stone == null) return northEastPosition
        return findNorthEastEmptyPosition(board, northEastPosition)
    }

    tailrec fun findSouthWestEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val southWestPosition = position.getSouthWest()
        val stone = board[southWestPosition]
        if (southWestPosition == null || stone == Color.WHITE) return null
        if (stone == null) return southWestPosition
        return findSouthWestEmptyPosition(board, southWestPosition)
    }

    tailrec fun findEastEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val eastPosition = position.getEast()
        val stone = board[eastPosition]
        if (eastPosition == null || stone == Color.WHITE) return null
        if (stone == null) return eastPosition
        return findEastEmptyPosition(board, eastPosition)
    }

    tailrec fun findWestEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val westPosition = position.getWest()
        val stone = board[westPosition]
        if (westPosition == null || stone == Color.WHITE) return null
        if (stone == null) return westPosition
        return findWestEmptyPosition(board, westPosition)
    }

    tailrec fun findSouthEastEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val southEastPosition = position.getSouthEast()
        val stone = board[southEastPosition]
        if (southEastPosition == null || stone == Color.WHITE) return null
        if (stone == null) return southEastPosition
        return findSouthEastEmptyPosition(board, southEastPosition)
    }

    tailrec fun findNorthWestEmptyPosition(board: Map<Position, Color?>, position: Position): Position? {
        val northWestPosition = position.getNorthWest()
        val stone = board[northWestPosition]
        if (northWestPosition == null || stone == Color.WHITE) return null
        if (stone == null) return northWestPosition
        return findNorthWestEmptyPosition(board, northWestPosition)
    }
}
