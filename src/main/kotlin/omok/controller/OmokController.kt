package omok.controller

import omok.domain.OmokGame
import omok.domain.OmokResult
import omok.domain.StoneState
import omok.view.InputView
import omok.view.OutputView
import rule.wrapper.point.Point

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun play() {
        val omokGame = initGame()
        val result = playGame(omokGame)
        printWinner(result, omokGame)
    }

    private fun initGame(): OmokGame {
        outputView.printStartMessage()
        return OmokGame()
    }

    private fun playGame(omokGame: OmokGame): OmokResult {
        var latestPosition = ""
        var nowTurn: StoneState = StoneState.BLACK
        while (true) {
            val position = turn(nowTurn, omokGame, latestPosition)
            if (omokGame.checkOmok(position)) return OmokResult.returnWinner(nowTurn)
            if (omokGame.grid.isFull()) break
            nowTurn = StoneState.changeTurn(nowTurn)
            latestPosition = convertLetter(position.col) + (position.row + 1).toString()
        }
        return OmokResult.DRAW
    }

    private fun turn(
        state: StoneState,
        omokGame: OmokGame,
        latestPosition: String,
    ): Point {
        return retryInput {
            outputView.printBoardState(omokGame.grid.board)
            val point = inputView.getPoint(state, latestPosition).minus(1)
            omokGame.grid.putStone(point, state)
            point
        }
    }

    private fun convertLetter(number: Int): String {
        return ('A' + number).toString()
    }

    private fun printWinner(
        omokResult: OmokResult,
        omokGame: OmokGame,
    ) {
        outputView.printBoardState(omokGame.grid.board)
        outputView.printWinner(omokResult)
    }

    private fun <T> retryInput(inputFunction: () -> T): T {
        return runCatching { inputFunction() }
            .getOrElse { e ->
                println(e.message)
                retryInput(inputFunction)
            }
    }
}

fun Point.minus(value: Int): Point {
    return Point(this.row - value, this.col - value)
}
