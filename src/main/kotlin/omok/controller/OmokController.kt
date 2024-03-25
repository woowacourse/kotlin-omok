package omok.controller

import omok.model.Board
import omok.model.OmokGame
import omok.model.OmokPlayers
import omok.model.OmokTurnAction
import omok.model.Player
import omok.model.Position
import omok.model.Stone
import omok.model.rule.ban.ForbiddenPlace
import omok.model.rule.finish.FinishCondition
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val boardSize: Int,
    private val finishConditions: List<FinishCondition>,
    blackStoneForbiddenPlaces: List<ForbiddenPlace>,
    whiteStoneForbiddenPlaces: List<ForbiddenPlace>,
) {
    private val omokPlayers =
        OmokPlayers(
            blackStonePlayer = Player(Stone.BLACK, blackStoneForbiddenPlaces),
            whiteStonePlayer = Player(Stone.WHITE, whiteStoneForbiddenPlaces),
        )

    fun startGame() {
        val board = initializedBoard()
        val omokGame = OmokGame(board, omokPlayers, finishConditions)
        val finishType = omokGame.gameResult(omokTurnAction())
        outputView.printResult(finishType)
    }

    private fun initializedBoard(): Board {
        return Board(boardSize).apply { outputView.printInitialGuide(this) }
    }

    private fun omokTurnAction(): OmokTurnAction {
        return object : OmokTurnAction {
            override fun nextStonePosition(
                nowOrderStone: Stone,
                recentPosition: Position?,
            ): Position {
                return inputView.readStonePosition(boardSize, nowOrderStone, recentPosition)
            }

            override fun onStonePlace(board: Board) {
                outputView.printBoard(board)
            }
        }
    }
}

fun <T> retryUntilNotException(block: () -> (T)): T {
    return try {
        block()
    } catch (e: IllegalArgumentException) {
        println(e.localizedMessage)
        retryUntilNotException(block)
    }
}
