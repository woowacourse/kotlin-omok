package omok.domain

import omok.domain.state.State
import omok.domain.state.Turn
import omok.domain.state.Win

class OmokGame {
    val board = Board(Player(), Player())
    var lastPosition: String? = null

    fun blackTurn(position: Position, printBoard: (Turn, Position) -> Unit): State {
        board.putStone(Turn.Black, position)
        printBoard(Turn.Black, position)
        return if (board.lineJudge(Turn.Black, position)) Win.Black else Turn.White
    }

    fun whiteTurn(position: Position, printBoard: (Turn, Position) -> Unit): State {
        board.putStone(Turn.White, position)
        printBoard(Turn.White, position)
        return if (board.lineJudge(Turn.White, position)) Win.White else Turn.Black
    }
}
