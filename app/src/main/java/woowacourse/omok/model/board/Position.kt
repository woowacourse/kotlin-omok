package omok.model.board

import omok.model.stone.StoneState

data class Position private constructor(
    val x: X,
    val y: Y,
    private val stoneState: StoneState,
) {
    constructor(x: Int, y: Int) : this(X(x), Y(y), StoneState.NONE)

    fun canPlace(): Boolean = stoneState == StoneState.NONE

    fun placeStone(stoneState: StoneState): Position {
        if (canPlace()) return Position(this.x, this.y, stoneState)
        throw IllegalArgumentException("이 자리에는 이미 돌이 위치해 있습니다. 이 자리에는 돌을 놓지 못합니다.")
    }

    fun stoneState(): StoneState = stoneState

    companion object {
        fun from(
            x: Int,
            y: Int,
        ): Position = Position(X(x), Y(y), StoneState.NONE)
    }
}
