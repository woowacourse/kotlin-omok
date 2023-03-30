package omok

import omok.judgement.RenjuJudgement
import omok.state.Fail
import omok.state.State
import omok.state.Turn
import omok.state.Win

class OmokGame(val board: Board = Board(RenjuJudgement(), Player(), Player())) {
    var lastPosition: String? = null
        private set

    fun takeTurn(turn: Turn, position: Position): State {
        if (!board.isPlaceable(turn, position)) {
            return Fail
        }
        lastPosition = position.toString()
        return when (turn) {
            Turn.Black -> blackTurn(position)
            Turn.White -> whiteTurn(position)
        }
    }

    private fun blackTurn(position: Position): State {
        board.putStone(Turn.Black, position)
        return if (board.lineJudge(Turn.Black, position)) Win.Black else Turn.White
    }

    private fun whiteTurn(position: Position): State {
        board.putStone(Turn.White, position)
        return if (board.lineJudge(Turn.White, position)) Win.White else Turn.Black
    }
}
