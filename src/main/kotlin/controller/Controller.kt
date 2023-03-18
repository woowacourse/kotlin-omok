package controller

import domain.Listener
import domain.OmokBoard
import domain.OmokGame
import domain.State
import domain.Stone
import view.ConsoleInputView
import view.ConsoleOutputView
import view.InputView
import view.OutputView

class Controller(
    private val inputView: InputView = ConsoleInputView(),
    private val outputView: OutputView = ConsoleOutputView()
) {

    private val omokGameListener = object : Listener {
        override fun onStoneRequest(): Stone {
            return inputView.readPosition()
        }

        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
            outputView.printOmokState(omokBoard, state, stone)
        }

        override fun onMoveFail() {
            outputView.printDuplicate()
        }

        override fun onForbidden() {
            outputView.printForbidden()
        }

        override fun onFinish(state: State) {
            outputView.printWinner(state)
        }
    }

    fun run() {
        val omokGame = OmokGame(omokGameListener = omokGameListener)
        outputView.printStart()
        omokGame.runGame()
    }
}
