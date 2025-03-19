package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

interface PlayerState {
    fun putCount(): Int

    fun stoneState(): StoneState

    fun isPlaceTurn(omokBoard: OmokBoard, position: Position, stoneState: StoneState): PlayerState // 내턴인지

    fun stop(): PlayerState
}
