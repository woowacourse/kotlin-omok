package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

class WhitePlayerState(
    private val count: Int = 0,
) : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        stoneState: StoneState,
    ): PlayerState {
        omokBoard.placeStone(position, stoneState)
        if (count >= 5 && omokBoard.isOmok(position, stoneState, omokBoard)) return Win()

        return BlackPlayerState(count + 1)
    }

    override fun nextTurn(): PlayerState = BlackPlayerState()
}
