package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

interface PlayerState {
    fun isPlaceTurn(omokBoard: OmokBoard, position: Position, stoneState: StoneState): PlayerState // 내턴인지

    fun nextTurn(): PlayerState
}
