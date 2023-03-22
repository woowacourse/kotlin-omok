package domain.game

import domain.stone.StoneColor

sealed class PutResult
class PutSuccess(val stoneColor: StoneColor) : PutResult()
object PutFailed : PutResult()
class GameFinish(val lastStoneColor: StoneColor, val winnerStoneColor: StoneColor) : PutResult()

