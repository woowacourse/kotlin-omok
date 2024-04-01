package woowacourse.omok.controller

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.game.FinishAction
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.game.OmokGame
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.utils.retryUntilNotException
import woowacourse.omok.view.ProgressView
import woowacourse.omok.view.ResultView
import woowacourse.omok.view.StartView
import woowacourse.omok.view.StonePositionView

class OmokController(
    private val stonePositionView: StonePositionView,
    private val startView: StartView,
    private val progressView: ProgressView,
    private val resultView: ResultView,
    private val boardSize: Int,
) : FinishAction {
    private var isFinish = false

    fun startGame() {
        val board = initializedBoard()
        val omokGame = OmokGame(board, OmokPlayers(), this)
        while (!isFinish) {
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
        isFinish = true
        resultView.print(finishType)
    }
}
