package domain.game

import domain.point.Point
import domain.stone.StoneColor

sealed class PutResult
class PutSuccess(val stoneColor: StoneColor, val point: Point) : PutResult()
object PutFailed : PutResult()
class GameFinish(val lastStoneColor: StoneColor, val winnerStoneColor: StoneColor) : PutResult()
