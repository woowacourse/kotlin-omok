package omok.controller

import omok.model.game.Board
import omok.model.game.Judgement
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import omok.view.InputView
import omok.view.OutputView

class OmokController {

    val board = Board()
    var isGameRunning = true
        private set

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
        isGameRunning = state.isRunning
        putStone(state, newStone)
        OutputView.printEachTurn(board, newStone, state)
        return state
    }

    fun getLastTurnColor(): GoStoneColor = board.lastPlacedStone.color

    fun getLastPlacedStone(): GoStone = board.lastPlacedStone

    fun addAll(stones: List<GoStone>) {
        board.addAllStones(stones)
        updateGameStatus()
    }

    private fun updateGameStatus() {
        val stone = getLastPlacedStone()
        if (stone != GoStone.EMPTY && !Judgement.judge(board, getLastPlacedStone()).isRunning) {
            isGameRunning = false
        }
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
}
