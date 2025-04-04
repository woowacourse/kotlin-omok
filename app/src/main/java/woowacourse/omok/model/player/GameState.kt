package woowacourse.omok.model.player

sealed class GameState {
    data object Playing : GameState()

    data object Win : GameState()

    sealed class ForbiddenMove : GameState() {
        data object DoubleThree : ForbiddenMove()

        data object DoubleFour : ForbiddenMove()
    }

    protected var gameState: GameState = Playing

    fun win(): Boolean = gameState is Win

    fun playing(): Boolean = gameState is Playing

    fun forbidden(): Boolean = gameState is ForbiddenMove

    fun doubleThree(): Boolean = gameState is ForbiddenMove.DoubleThree

    fun doubleFour(): Boolean = gameState is ForbiddenMove.DoubleFour
}
