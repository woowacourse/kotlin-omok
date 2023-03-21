package domain.state

import domain.stone.Board
import domain.stone.Stone
import rule.type.Violation

class BlackTurn(val board: Board) : Running() {

    override fun put(stone: Stone): State {
        if (blackRenjuRule.checkAnyFoulCondition(
                board.blackStonesPoint(),
                board.whiteStonesPoint(),
                stone.point,
            ) != Violation.NONE
        ) {
            return BlackTurn(board)
        }
        if (blackRenjuRule.checkWin(board.blackStonesPoint(), board.whiteStonesPoint(), stone.point)) {
            return Win(stone)
        }
        runCatching { board.putStone(stone) }.onFailure { return BlackTurn(board) }
        return WhiteTurn(board)
    }
}