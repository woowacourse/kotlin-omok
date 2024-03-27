package omok.controller

import omok.model.Board
import omok.model.OmokGame
import omok.model.Player
import omok.model.Players
import omok.model.Stone
import omok.model.rule.GamePlayingRules
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    blackStoneGamePlayingRules: GamePlayingRules,
    whiteStoneGamePlayingRules: GamePlayingRules,
) {
    private val players =
        Players(
            blackStonePlayer = Player(Stone.BLACK, blackStoneGamePlayingRules),
            whiteStonePlayer = Player(Stone.WHITE, whiteStoneGamePlayingRules),
        )

    fun startGame() {
        val board = initializedBoard()
        val winner =
            OmokGame(board, players).gameWinner(
                nextStonePosition = { player, position -> inputView.readStonePosition(player.stone, position) },
                nextStonePositionResult = { outputView.printBoard(board) },
            )
        outputView.printWinner(winner.stone)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
