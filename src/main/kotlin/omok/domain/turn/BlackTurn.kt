package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.Stone
import omok.domain.StoneState

class BlackTurn(override val beforeTurn: StoneState? = null) : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn {
        val stone = Stone(position, StoneState.BLACK)
        if (board.invalidPlace(stone) || board.board[position.y][position.x] != StoneState.BLANK) return this
        board.putStone(stone)
        if (board.checkOmok(position)) return Finished(StoneState.BLACK)
        return WhiteTurn(StoneState.BLACK)
    }
}
