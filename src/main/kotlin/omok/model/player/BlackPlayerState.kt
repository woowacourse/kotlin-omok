package omok.model.player

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

class BlackPlayerState(
    private val count: Int = 0,
) : PlayerState {
    private val state: StoneState = StoneState.BLACK

    override fun putCount(): Int = count

    override fun stoneState(): StoneState = state

    override fun isPlaceTurn(
        omokBoard: OmokBoard,
        position: Position,
        stoneState: StoneState,
    ): PlayerState {
        omokBoard.placeStone(position, stoneState)
        if (count >= 5 && omokBoard.isOmok(position)) return Win()
        return WhitePlayerState(count + 1)
    }

    override fun stop(): Lose = Lose()
}
