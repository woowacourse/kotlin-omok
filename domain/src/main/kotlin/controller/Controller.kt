package controller

import domain.OmokBoard
import domain.OmokGame
import domain.OmokGameListener
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

    private val omokGameListener = object : OmokGameListener {

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

        var turn = State.BLACK
        while (true) {
            omokGame.nextTurn(turn)
            if (omokGame.isVictory(turn)) break

            turn = turn.nextState()
        }
    }

    private fun OmokGame.nextTurn(turn: State) {
        val stone = inputView.readPosition()
        if (!this.successTurn(stone, turn)) return nextTurn(turn)
    }

    private fun State.nextState(): State =
        if (this == State.BLACK) State.WHITE
        else State.BLACK
}
