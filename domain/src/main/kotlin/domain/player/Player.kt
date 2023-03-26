package domain.player

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule
import domain.state.PlayerState
import domain.stone.StoneColor

abstract class Player(protected val state: PlayerState, val rule: OmokRule) {
    val isPlaying: Boolean
        get() = state.isPlaying
    val isFoul: Boolean
        get() = state.isFoul

    fun isPlaced(stone: Point): Boolean = state.hasStone(stone)

    fun getLastPoint(): Point? = state.getLastStone()

    fun getPointSize(): Int = state.getAllPoints().getAll().size

    fun getAllPoints(): Points = state.getAllPoints()

    abstract fun getLatestPlayer(player: Player): Player

    abstract fun getColor(): StoneColor

    abstract fun putPoint(point: Point, otherPoints: Points): Player
}
