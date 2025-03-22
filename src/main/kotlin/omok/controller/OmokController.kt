package omok.controller

import omok.domain.board.BoardStatus
import omok.domain.point.Point
import omok.domain.service.OmokGame
import omok.domain.stone.StoneColor
import omok.exception.ResultState
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

    private fun startGame() {
        omokGame.startGame(
            onCompleteInputPoint = { stone, board -> readValidPoint(stone, board) },
            onFinishedGame = { outputView.printPrintWinner(it) },
            onFailToAddStone = { outputView.printErrorMessage(it) },
        )
    }

    private fun readValidPoint(
        stone: StoneColor,
        board: List<List<BoardStatus>>,
    ): Point {
        return when (val result = Point.of(getInputPoint(stone, board), stone)) {
            is ResultState.Success -> result.data
            is ResultState.Error -> {
                outputView.printErrorMessage(result.message)
                readValidPoint(stone, board)
            }
        }
    }

    private fun getInputPoint(
        stone: StoneColor,
        board: List<List<BoardStatus>>,
    ): String {
        outputView.printBoard(board, stone)
        return inputView.readStoneWithLatestStone(stone, omokGame.latestStone)
    }
}
