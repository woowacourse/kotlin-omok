package omok.controller

import omok.model.Board
import omok.model.Color
import omok.model.OmokGame
import omok.model.Player
import omok.model.Stones
import omok.view.OutputView

class OmokController {
    fun run() {
        OutputView.printStart()
        val players = Player(Color.BLACK) to Player(Color.WHITE)
        val board = Board(Stones())
        val omokGame = OmokGame(players, board)
        omokGame.playGame()
        displayWinner(players)
    }

    private fun displayWinner(players: Pair<Player, Player>) {
        when (players.first.isWin) {
            true -> OutputView.printWinner(players.first.color)
            false -> OutputView.printWinner(players.second.color)
        }
    }
}
