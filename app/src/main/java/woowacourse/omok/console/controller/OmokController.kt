package woowacourse.omok.console.controller

import woowacourse.omok.console.utils.retryUntilNotException
import woowacourse.omok.console.view.ProgressView
import woowacourse.omok.console.view.ResultView
import woowacourse.omok.console.view.StartView
import woowacourse.omok.console.view.StonePositionView
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.game.FinishAction
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.game.OmokGame
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.model.game.TurnHistory

class OmokController(
    private val stonePositionView: StonePositionView,
    private val startView: StartView,
    private val progressView: ProgressView,
    private val resultView: ResultView,
    private val boardSize: Int,
) : FinishAction {
    fun startGame() {
        val board = initializedBoard()
        val omokGame = OmokGame(board, this, TurnHistory(OmokPlayers()))
        while (!omokGame.isFinish()) {
            progressView.printBoard(board)
            val position = nextStonePosition(omokGame)
            val placeType = omokGame.turn(position)
            progressView.printPlaceResult(placeType)
        }
    }

    private fun initializedBoard(): Board {
        return Board(boardSize).apply { startView.print() }
    }

    private fun nextStonePosition(omokGame: OmokGame): Position {
        return retryUntilNotException {
            stonePositionView.read(
                boardSize,
                omokGame.nowOrderStone(),
                omokGame.recentPosition(),
            )
        }
    }

    override fun onFinish(finishType: FinishType) {
        resultView.print(finishType)
    }
}
