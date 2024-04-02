package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Player
import woowacourse.omok.domain.model.Players
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

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

    fun startGame() {
        val board = initializedBoard()
        val winner =
            OmokGame(
                board,
                players,
                listOf(
                    EmptyPosition { player, position, message -> outputView.printInvalidPosition(player, position, message) },
                    AbideForbiddenRules { player, position, message -> outputView.printInvalidPosition(player, position, message) },
                ),
            ).gameWinner(
                nextStonePosition = { player, position -> inputView.readStonePosition(player.stone, position) },
                nextStonePositionResult = { outputView.printBoard(board) },
            )

        outputView.printWinner(winner.stone)
    }

    fun startGame2() {
        val board = initializedBoard()
        val omokGame =
            OmokGame(
                board,
                players,
                listOf(
                    EmptyPosition { player, position, message -> outputView.printInvalidPosition(player, position, message) },
                    AbideForbiddenRules { player, position, message -> outputView.printInvalidPosition(player, position, message) },
                ),
            )

        val result =
            omokGame.runGame(
                { inputView.readFirstStonePosition(Stone.BLACK) },
                { gameState ->
                    inputView.readStonePosition(
                        gameState.latestStonePosition().stone.nextOrFirst(),
                        gameState.latestStonePosition().position,
                    )
                },
                { inValidStonePosition, message -> outputView.printInvalidPosition(inValidStonePosition, message) },
                { outputView.printBoard(board) },
            )
        outputView.printWinner(result.latestStonePosition().stone)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
