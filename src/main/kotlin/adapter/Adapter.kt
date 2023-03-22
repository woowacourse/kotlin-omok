package adapter

import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType
import rule.wrapper.point.Point

abstract class Adapter {

    abstract fun checkWin(board: Board, stone: Stone): Boolean

    fun getBlackStonesPoint(board: Board): List<Point> =
        board.stones.getStonesPosition(StoneType.BLACK).map { Point(it.x, it.y) }

    fun getWhiteStonesPoint(board: Board): List<Point> =
        board.stones.getStonesPosition(StoneType.WHITE).map { Point(it.x, it.y) }

    fun StonePosition.getPoint(): Point = Point(x, y)
}
