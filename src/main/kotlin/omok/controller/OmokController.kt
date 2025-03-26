package omok.controller

import omok.domain.Game
import omok.domain.model.Board
import omok.domain.model.Board.Companion.DEFAULT_BOARD_SIZE
import omok.domain.model.position.Position
import omok.domain.model.rule.OmokRuleAdapter
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {
    fun run() {
        val game = Game(rule = OmokRuleAdapter())
        outputView.printStart()

        game.play(
            onBeforePlace = ::showBoardStatus,
            onPlace = ::getPosition,
            onFailure = outputView::printErrorMessage,
        )
        showResult(game)
    }

    private fun showBoardStatus(
        board: Board,
        stoneType: StoneType,
        omokStone: OmokStone?,
    ) {
        outputView.printBoardState(board)
        outputView.printTurn(stoneType)
        if (omokStone != null) {
            outputView.printLastStone(omokStone)
            return
        }
    }

    private fun getPosition(): Position {
        return retryEvent {
            val (column, row) = inputView.getPosition()
            Position.of(column, row, DEFAULT_BOARD_SIZE)
        }
    }

    private fun showResult(game: Game) {
        val board = game.board
        outputView.printBoardState(board)
        if (board.isFull()) {
            outputView.printDraw()
            return
        }
        val lastStone = board.getLastStone() ?: error("게임이 비정상적으로 종료되었습니다.")
        outputView.printResult(lastStone)
    }

    private fun <T> retryEvent(event: () -> T): T {
        while (true) {
            runCatching { event() }
                .onSuccess { return it }
                .onFailure { outputView.printErrorMessage(it.message ?: it.stackTraceToString()) }
        }
    }
}
