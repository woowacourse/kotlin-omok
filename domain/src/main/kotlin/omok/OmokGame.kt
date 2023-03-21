package omok

import omok.state.State
import omok.state.Turn
import omok.state.Win

class OmokGame {
    val board = Board(Player(), Player())
    var lastPosition: String? = null

    fun blackTurn(position: Position): State {
        board.putStone(Turn.Black, position)
        return if (board.lineJudge(Turn.Black, position)) Win.Black else Turn.White
    }

    fun whiteTurn(position: Position): State {
        board.putStone(Turn.White, position)
        return if (board.lineJudge(Turn.White, position)) Win.White else Turn.Black
    }
}
