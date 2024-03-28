package omok.controller

import omok.model.Board
import omok.model.OmokGame2
import omok.model.Player2
import omok.model.Players2
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
        Players2(
            blackStonePlayer = Player2(Stone.BLACK, blackStoneGamePlayingRules),
            whiteStonePlayer = Player2(Stone.WHITE, whiteStoneGamePlayingRules),
        )

    fun startGame() {
        val board = initializedBoard()
        val winner =
            OmokGame2(board, players).gameWinner(
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
