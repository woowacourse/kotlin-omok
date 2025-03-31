package woowacourse.omok.controller

import woowacourse.omok.domain.GameState
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.BoardSize
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.PlaceStoneResult
import woowacourse.omok.domain.rule.RuleValidator
import woowacourse.omok.view.OmokInputView
import woowacourse.omok.view.OmokOutputView
import woowacourse.omok.view.omok.GameEventListener

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) : GameEventListener {
    private lateinit var game: OmokGame
    private lateinit var board: Board

    fun play() {
        outputView.printStartMessage()

        game = OmokGame(GameState(), this)
        board = Board(BoardSize(), RuleValidator())

        playTurns()
    }

    private fun playTurns() {
        outputView.printBoardStatus(board)
        while (!game.isFinish()) {
            outputView.printCurrentTurn(game.previousMovePoint() to game.currentStoneColor(board))
            val point = inputView.readPosition().run { Point(first, second) }
            game.placeStone(board, point)
        }
    }

    override fun onBoardUpdated(
        point: Point,
        state: CellState,
    ) {
        outputView.printBoardStatus(board)
    }

    override fun onGameWon(winnerState: CellState?) {
        outputView.printWinColor(winnerState)
    }

    override fun onShowMessage(result: PlaceStoneResult) {
        outputView.printMessage(result)
    }
}
