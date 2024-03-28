package omok.controller

import omok.model.Board
import omok.model.OmokGame
import omok.model.Player
import omok.model.Players
import omok.model.Stone
import omok.model.rule.RuleAdapter
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    blackStoneGamePlayingRules: RuleAdapter,
    whiteStoneGamePlayingRules: RuleAdapter,
) {
    private val players =
        Players(
            blackStonePlayer = Player(Stone.BLACK, blackStoneGamePlayingRules),
            whiteStonePlayer = Player(Stone.WHITE, whiteStoneGamePlayingRules),
        )

    fun startGame2() {
        val board = initializedBoard()
        val winner =
            OmokGame(board, players,
                listOf(
                    EmptyPosition { player, position, message -> outputView.printInvalidPosition(player, position, message) },
                    AbideForbiddenRules { player, position, message -> outputView.printInvalidPosition(player, position, message) },),
                ).gameWinner(
                nextStonePosition = { player, position -> inputView.readStonePosition(player.stone, position) },
                nextStonePositionResult = { outputView.printBoard(board) },
                    )

        outputView.printWinner(winner.stone)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
