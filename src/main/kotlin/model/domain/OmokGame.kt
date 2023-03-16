package model.domain

import model.domain.state.* // ktlint-disable no-wildcard-imports

class OmokGame(private val board: Board) {
    private var player: State = BlackTurn()

    fun gameStart(getCoordination: (Stone) -> Pair<Int, Int>, printBoard: (Board) -> Unit) {
        while (player !is Omok) {
            printBoard(board)
            play(getCoordination)
            changeState()
        }
        findWinner()
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

    private fun findWinner() = when (player) {
        is BlackOmok -> 10
        is WhiteOmok -> 20
        else -> throw IllegalStateException()
    }

    private fun getStoneColor() = when (player) {
        is BlackTurn -> Stone.BLACK
        is WhiteTurn -> Stone.WHITE
        else -> throw IllegalStateException()
    }
}
