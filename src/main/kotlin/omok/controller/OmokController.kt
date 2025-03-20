package omok.controller

import omok.domain.Game
import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.state.BlackStoneTurn
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {
    fun run() {
        val board = Board()
        val game = Game(BlackStoneTurn(board))

        outputView.printStart()

        val winner =
            game.play(
                onBeforePlace = ::showBoardStatus,
                onPlace = ::getPosition,
            )

        outputView.printResult(winner)
    }

    private fun showBoardStatus(
        board: Board,
        stoneType: StoneType,
        omokStone: OmokStone?,
    ) {
        outputView.printBoardState(board)
        outputView.printTurn(stoneType, omokStone)
    }

    private fun getPosition(): Position {
        return retryEvent {
            val input = inputView.getPosition()
            val columnInput = input[0]
            val rowInput = input.substring(1).toIntOrNull() ?: throw IllegalArgumentException("잘못된 위치입니다.")

            Position(Column.from(columnInput), Row(rowInput))
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
