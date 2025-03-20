package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position

class Finish(board: Board) : OmokState(board) {
    override fun placeStone(onPlace: () -> Position): OmokState {
        error("이미 게임이 종료되었습니다.")
    }
}
