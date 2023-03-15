package model.domain

import model.domain.state.*

class OmokGame(private val board: Board) {
    private var player: State = BlackTurn()

    fun abc(getCoordination: () -> Pair<Int, Int>) {
        while (true) {
            play(getCoordination)
        }
    }

    fun play(getCoordination: () -> Pair<Int, Int>) {
        val value = getCoordination()
        val location = Location(Coordination.from(value.first), Coordination.from(value.second))
        player = player.place(location, board)
        findTurn(getCoordination)
    }

    fun findTurn(getCoordination: () -> Pair<Int, Int>) {
        when (player) {
            is Omok -> findWinner()
            is RetryTurn -> {
                player.retry()
                play(getCoordination)
            }

            is BlackTurn -> player = WhiteTurn()
            is WhiteTurn -> player = BlackTurn()
        }
    }

    fun findWinner() {}
}
