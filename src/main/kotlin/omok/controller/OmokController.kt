package omok.controller

import omok.model.Board
import omok.model.OmokGame
import omok.model.Player2
import omok.model.Players
import omok.model.Stone
import omok.model.rule.RuleAdapter2
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    blackStoneGamePlayingRules: RuleAdapter2,
    whiteStoneGamePlayingRules: RuleAdapter2,
) {
    private val players =
        Players(
            blackStonePlayer = Player2(Stone.BLACK, blackStoneGamePlayingRules),
            whiteStonePlayer = Player2(Stone.WHITE, whiteStoneGamePlayingRules),
        )

    fun startGame() {
        val board = initializedBoard()
        val winner =
            OmokGame(board, players).gameWinner(
                nextStonePosition = { player, position -> inputView.readStonePosition(player.stone, position) },
                nextStonePositionResult = { outputView.printBoard(board) },
                handleException = { exception -> outputView.printException(exception) },
            )
        outputView.printWinner(winner.stone)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
