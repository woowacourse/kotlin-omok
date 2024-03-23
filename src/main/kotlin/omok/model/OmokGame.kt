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

}