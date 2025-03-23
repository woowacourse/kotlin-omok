package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.Stone
import omok.domain.StoneState

class WhiteTurn(override val beforeTurn: StoneState) : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): PutStoneResult {
        val stone = Stone(position, StoneState.WHITE)
        if (board.isStonePlaced(position)) return PutStoneResult.Failure(Turn.ERROR_STONE_ALREADY_PUT)

        board.putStone(stone)
        if (board.checkOmok(position)) {
            return PutStoneResult.Success(Finished(StoneState.WHITE))
        }
        return PutStoneResult.Success(WhiteTurn(StoneState.WHITE))
    }
}
