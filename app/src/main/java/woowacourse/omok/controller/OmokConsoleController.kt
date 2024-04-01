package woowacourse.omok.controller

import RenjuRule
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.Player
import woowacourse.omok.model.StoneState
import woowacourse.omok.model.Stones
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

class OmokConsoleController {
    fun run() {
        OutputView.printStart()
        val players = Player(Color.BLACK) to Player(Color.WHITE)
        val stones = Stones()
        val board =
            Board(
                stones,
                rule = RenjuRule(stones),
            )
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
                playTurn(omokGame, player)
                OutputView.printBoard(board.stones)
            }
        }
    }

    private fun playTurn(
        omokGame: OmokGame,
        player: Player,
    ) {
        val coordinate = InputView.inputStoneCoordinate()
        val stonePlaced = omokGame.playTurn(player, coordinate, {})
        when (stonePlaced) {
            is StoneState.FailedPlaced -> {
                OutputView.printErrorMessage(stonePlaced.message)
                playTurn(omokGame, player)
            }

            is StoneState.SuccessfulPlaced -> return
        }
    }

    private fun displayWinner(players: Pair<Player, Player>) {
        when (players.first.isWin) {
            true -> OutputView.printWinner(players.first.color)
            false -> OutputView.printWinner(players.second.color)
        }
    }
}
