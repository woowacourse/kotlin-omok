package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.Stone
import omok.domain.StoneState

class BlackTurn(override val beforeTurn: StoneState? = null) : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): PutStoneResult {
        val stone = Stone(position, StoneState.BLACK)

        if (board.invalidPlace(stone)) {
            return PutStoneResult.Failure(Turn.ERROR_INVALID_POSITION)
        }
        if (board.isStonePlaced(position)) {
            return PutStoneResult.Failure(Turn.ERROR_STONE_ALREADY_PUT)
        }

        board.putStone(stone)
        if (board.checkOmok(position)) {
            return PutStoneResult.Success(Finished(StoneState.BLACK))
        }
        return PutStoneResult.Success(WhiteTurn(StoneState.BLACK))
    }
}