package domain.player

import domain.position.Position
import domain.state.PlayerState
import domain.state.WinState
import domain.stone.Stone
import domain.stone.Stones

abstract class Player(protected val state: PlayerState) : Cloneable {
    val isWin: Boolean = state is WinState

    abstract fun putStone(stone: Stone, otherStones: Stones): Player

    fun canPlace(): Boolean = state !is WinState

    fun isPlaced(stone: Stone): Boolean = state.hasStone(stone)

    fun getPositions(): List<Position> = state.getPlaced()

    fun getLastStone(): Stone = state.getLastStone()

    fun getAllStones(): Stones = state.getAllStones()

    public override fun clone(): Player = super.clone() as Player
}
