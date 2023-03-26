package omok.controller

import omok.model.game.Board
import omok.model.game.Judgement
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.view.InputView
import omok.view.OutputView

class OmokController {

    val board = Board()

    fun init() {
        OutputView.printInitGame()
    }

    fun play() {
        while (true) {
            val coordinate = getValidateCoordinate(InputView::getCoordinate)
            if (!playTurn(coordinate).isRunning) {
                break
            }
        }
    }

    fun playTurn(coordinate: Coordinate): State {
        val newStone = GoStone(board.getNextColor(), coordinate)
        val state = Judgement.judge(board, newStone)
        putStone(state, newStone)
        OutputView.printEachTurn(board, newStone, state)
        return state
    }

    private fun putStone(
        state: State,
        stone: GoStone
    ) {
        if (!state.isForbidden) {
            board.addStone(stone)
        }
    }

    private fun getValidateCoordinate(getCoordinate: () -> (Coordinate)): Coordinate {
        while (true) {
            runCatching {
                val value = getCoordinate()
                if (board.canAdd(value)) return value
            }.onFailure {
                println(it.message)
            }
        }
    }

    fun addAll(stones: List<GoStone>) {
        board.addAllStones(stones)
    }
}
