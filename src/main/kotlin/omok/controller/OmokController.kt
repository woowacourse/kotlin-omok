package omok.controller

import omok.adapter.RenjuRuleAdapter
import omok.domain.Game
import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.rule.OmokRule
import omok.domain.model.stone.StoneType
import omok.view.InputView
import omok.view.OutputView
import rule.BlackRenjuRule

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {
    fun run() {
        val board = Board()
        val renjuRule = RenjuRuleAdapter(BlackRenjuRule(board.size, board.size))
        val omokGame = Game(board, OmokRule(renjuRule))
        outputView.printStart()

        val winner =
            omokGame.play(
                onBoardState = outputView::printBoardState,
                onBoardTurn = outputView::printTurn,
                onPlace = outputView::printErrorMessage,
                onPosition = ::position,
                stoneType = StoneType.BLACK,
            )

        outputView.printResult(winner)
    }

    private fun position(board: Board): Position {
        return retryEvent {
            val (column, row) = inputView.position()
            Position(Column.from(column, board.inRange(column)), Row.from(row, board.inRange(row)))
        }
    }

    private fun <T> retryEvent(event: () -> T): T {
        while (true) {
            runCatching { event() }
                .onSuccess { return it }
                .onFailure { outputView.printErrorMessage(it.message ?: it.stackTraceToString()) }
        }
    }
}
