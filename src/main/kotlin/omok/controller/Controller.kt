package omok.controller

import omok.domain.ColorState
import omok.domain.HorizontalAxis
import omok.domain.Position
import omok.view.GameView

class Controller(private val gameView: GameView) {
    var lastPosition: String? = null

    fun gameStart() {
        printStart()
        blackTurn()
    }

    private fun printStart() = gameView.printStartMessage()

    private fun blackTurn() {
        val input = gameView.readPosition(ColorState.Black, lastPosition)
        lastPosition = input
        val position = Position(HorizontalAxis.valueOf(input[0].toString()), input.slice(1 until input.length).toInt())
        gameView.printBoard(ColorState.Black, position)
    }
}
