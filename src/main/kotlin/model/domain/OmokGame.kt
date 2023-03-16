package model.domain

import model.domain.state.Omok
import model.domain.state.RetryTurn
import model.domain.state.State
import model.domain.state.black.BlackOmok
import model.domain.state.black.BlackTurn
import model.domain.state.white.WhiteOmok
import model.domain.state.white.WhiteTurn
import model.domain.tools.Board
import model.domain.tools.Coordination
import model.domain.tools.Location
import model.domain.tools.Stone
import model.domain.tools.Stone.BLACK
import model.domain.tools.Stone.WHITE

class OmokGame(private val board: Board) {
    private var player: State = BlackTurn()

    fun gameStart(getCoordination: (Stone) -> Pair<Int, Int>, printBoard: (Board) -> Unit) {
        while (player !is Omok) {
            printBoard(board)
            play(getCoordination)
            changeState()
        }
    }

    private fun play(getCoordination: (Stone) -> Pair<Int, Int>) {
        val value = getCoordination(getStoneColor())

        val location = Location(Coordination.from(value.first), Coordination.from(value.second))
        player = player.place(location, board)
    }

    private fun changeState() {
        when (player) {
            is RetryTurn -> player = player.retry()
            is BlackTurn -> player = WhiteTurn()
            is WhiteTurn -> player = BlackTurn()
        }
    }

    fun getWinner(printBoard: (Board) -> Unit, printWinner: (Stone) -> Unit) {
        printWinner(getStoneColor())
        printBoard(board)
    }

    private fun getStoneColor() = when (player) {
        is BlackTurn -> BLACK
        is BlackOmok -> BLACK
        is WhiteTurn -> WHITE
        is WhiteOmok -> WHITE
        else -> throw IllegalStateException()
    }
}
