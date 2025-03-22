package omok.controller

import omok.domain.board.BoardStatus
import omok.domain.point.Point
import omok.domain.service.OmokGame
import omok.domain.stone.StoneColor
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val omokGame: OmokGame,
) {
    fun run() {
        outputView.printStartMessage()
        startGame()
    }

    private fun startGame() =
        retryWhenException(
            action = {
                omokGame.startGame(
                    onCompleteInputPoint = { stone, board -> readValidPoint(stone, board) },
                    onFinishedGame = { outputView.printPrintWinner(it) },
                )
            },
            onError = outputView::printErrorMessage,
        )

    private fun readValidPoint(
        stone: StoneColor,
        board: List<List<BoardStatus>>,
    ): Point =
        retryWhenException(
            action = { Point.of(getInputPoint(stone, board), stone) },
            onError = outputView::printErrorMessage,
        )

    private fun getInputPoint(
        stone: StoneColor,
        board: List<List<BoardStatus>>,
    ): String {
        outputView.printBoard(board, stone)
        return inputView.readStoneWithLatestStone(stone, omokGame.latestStone)
    }
}
