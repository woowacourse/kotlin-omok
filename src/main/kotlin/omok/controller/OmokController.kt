package omok.controller

import omok.model.Board
import omok.model.Color
import omok.model.OmokGame
import omok.model.Player
import omok.model.Stones
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    fun run() {
        OutputView.printStart()
        val players = Player(Color.BLACK) to Player(Color.WHITE)
        val board = Board(Stones())
        startGame(players, board)
        displayWinner(players)
    }

    private fun startGame(
        players: Pair<Player, Player>,
        board: Board,
    ) {
        val omokGame = OmokGame(board)
        while (omokGame.isRunning()) {
            for (player in players.toList()) {
                OutputView.printTurnName(player.color)
                OutputView.printLastStone(board.stones.getLastStoneCoordinate())
                retryPlayTurnUntilSuccess(omokGame, player)
                OutputView.printBoard(board.stones)
            }
        }
    }

    private fun retryPlayTurnUntilSuccess(
        omokGame: OmokGame,
        player: Player,
    ) {
        val coordinate = InputView.inputStoneCoordinate()
        val isFinishTurn = omokGame.playTurn(player, coordinate)

        if (!isFinishTurn) {
            retryPlayTurnUntilSuccess(omokGame, player)
        }
    }

    private fun displayWinner(players: Pair<Player, Player>) {
        when (players.first.isWin) {
            true -> OutputView.printWinner(players.first.color)
            false -> OutputView.printWinner(players.second.color)
        }
    }
}
