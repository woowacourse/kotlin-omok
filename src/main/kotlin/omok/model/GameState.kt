package omok.model

sealed class GameState(var lastPosition: Position? = null) {
    open var currentStone = StoneType.EMPTY

    fun updateState(lastPosition: Position): GameState {
        return when (this) {
            is BlackTurn -> WhiteTurn(lastPosition)
            is WhiteTurn -> BlackTurn(lastPosition)
        }
    }

    class BlackTurn(lastPosition: Position? = null) : GameState(lastPosition) {
        override var currentStone = StoneType.BLACK_STONE
    }

    class WhiteTurn(lastPosition: Position? = null) : GameState(lastPosition) {
        override var currentStone = StoneType.WHITE_STONE
    }
}
