package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.stone.StoneType

class Finish(
    board: Board,
    override val stoneType: StoneType,
) : OmokState(board) {
    override fun placeStone(onPlace: () -> Position): OmokState {
        error("이미 게임이 종료되었습니다.")
    }
}
