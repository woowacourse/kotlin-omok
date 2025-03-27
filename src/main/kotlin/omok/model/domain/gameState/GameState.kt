package omok.model.domain.gameState

import omok.model.entity.Stone
import omok.model.entity.board.Board
import omok.model.entity.position.Position

interface GameState {
    val stone: Stone

    interface Playing : GameState {
        fun play(
            board: Board,
            position: Position,
        ): GameState
    }

    enum class Finish(
        override val stone: Stone,
    ) : GameState {
        BLACK_WIN(Stone.BLACK),
        WHITE_WIN(Stone.WHITE),
    }
}
