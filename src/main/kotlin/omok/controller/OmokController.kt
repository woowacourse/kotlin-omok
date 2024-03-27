package omok.controller

import RuleAdaptor
import omok.model.Board
import omok.model.Color
import omok.model.OmokGame
import omok.model.Player
import omok.model.StoneState
import omok.model.Stones
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    fun run() {
        OutputView.printStart()
        val players = Player(Color.BLACK) to Player(Color.WHITE)
        val stones = Stones()
        val board =
            Board(
                stones,
                rule = RuleAdaptor(stones),
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
        val stonePlaced = omokGame.playTurn(player, coordinate)
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
