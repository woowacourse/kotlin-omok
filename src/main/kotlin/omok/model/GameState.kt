package omok.model

sealed class GameState {
    open var currentStone = StoneType.EMPTY

    fun updateState() {
        currentStone =
            when (this) {
                is BlackTurn -> StoneType.WHITE_STONE
                is WhiteTurn -> StoneType.BLACK_STONE
            }
    }

    data object BlackTurn : GameState() {
        override var currentStone = StoneType.BLACK_STONE
    }

    data object WhiteTurn : GameState() {
        override var currentStone = StoneType.WHITE_STONE
    }
}
