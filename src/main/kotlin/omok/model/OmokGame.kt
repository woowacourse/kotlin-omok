package omok.model

import omok.view.InputView
import omok.view.OutputView

class OmokGame(
    val players: Pair<Player, Player>,
    val board: Board,
) {
    fun playGame() {
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
        var finishTurn = false
        while (!finishTurn) {
            finishTurn = playTurn(player, board)
        }
    }

    private fun playTurn(
        player: Player,
        board: Board,
    ): Boolean {
        val coordinate = InputView.inputStoneCoordinate()
        return player.playTurn(board, coordinate)
    }
}
