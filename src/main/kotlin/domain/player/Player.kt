package domain.player

import domain.position.Position
import domain.rule.OmokRule
import domain.state.LoseState
import domain.state.PlayerState
import domain.state.WinState
import domain.stone.Stone
import domain.stone.Stones

abstract class Player(protected val state: PlayerState) : Cloneable {
    fun canPlace(): Boolean = state !is WinState && state !is LoseState

    fun isPlaced(stone: Stone): Boolean = state.hasStone(stone)

    fun getPositions(): List<Position> = state.getPlaced()

    fun getLastStone(): Stone = state.getLastStone()

    fun getAllStones(): Stones = state.getAllStones()

    abstract fun putStone(stone: Stone, otherStones: Stones, rule: OmokRule): Player

    public override fun clone(): Player = super.clone() as Player
}
