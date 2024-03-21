package omok.controller

import omok.model.Board
import omok.model.Color
import omok.model.Player
import omok.model.Stones
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    fun run() {
        OutputView.printStart()
        val players = Player(Color.BLACK) to Player(Color.WHITE)
        val board = Board(Stones())

        playGame(players, board)
        displayWinner(players)
    }

    private fun playGame(
        players: Pair<Player, Player>,
        board: Board,
    ) {
        while (true) {
            if (playOmok(players.first, board)) break
            if (playOmok(players.second, board)) break
        }
    }

    private fun playOmok(
        player: Player,
        board: Board,
    ): Boolean {
        OutputView.printTurnName(player.color)
        OutputView.printLastStone(board.stones.getLastStoneCoordinate())
        retryPlayTurnUntilSuccess(player, board)
        OutputView.printBoard(board.stones)
        return player.isWin
    }

    private fun retryPlayTurnUntilSuccess(
        player: Player,
        board: Board,
    ) {
        while (true) {
            val result = playTurn(player, board)
            if (result.isSuccess) {
                break
            } else {
                result.onFailure { e -> OutputView.printErrorMessage(e.message!!) }
            }
        }
    }

    private fun playTurn(
        player: Player,
        board: Board,
    ) = runCatching {
        val coordinate = InputView.inputStoneCoordinate()
        player.playTurn(board, coordinate)
    }

    private fun displayWinner(players: Pair<Player, Player>) {
        when (players.first.isWin) {
            true -> OutputView.printWinner(players.first.color)
            false -> OutputView.printWinner(players.second.color)
        }
    }
}
