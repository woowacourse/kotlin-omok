package omok.model.game.state

import omok.model.Position
import omok.model.board.Board

class Finish(board: Board) : GameState(board) {
    override fun placeStone(position: Position): GameState {
        error("게임이 이미 종료됐습니다.")
    }

    override fun canPlaceStone(position: Position): Boolean = false
}
