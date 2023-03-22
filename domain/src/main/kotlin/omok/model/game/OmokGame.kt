package omok.model.game

import omok.model.state.Judgement
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class OmokGame(val board: Board) {
    fun start(
        coordinate: () -> Coordinate,
        showBoard: (Board) -> Unit,
        showTurn: (GoStoneColor, GoStone?) -> Unit,
        showMessage: (State, GoStoneColor) -> Unit
    ) {
        var newStone: GoStone
        var state: State

        while (true) {
            newStone = turn(coordinate, showTurn)
            state = Judgement.judge(board, newStone)
            putStone(state, newStone)
            showMessage(state, newStone.color)
            showBoard(board)

            if (state == State.Win) break
        }
    }

    private fun turn(
        getCoordinate: () -> Coordinate,
        showTurn: (GoStoneColor, GoStone?) -> Unit
    ): GoStone {
        showTurn(board.getNextColor(), board.lastPlacedStone)
        return GoStone(board.getNextColor(), getValidateValue(getCoordinate, board::canAdd))
    }

    private fun putStone(
        state: State,
        stone: GoStone
    ) {
        if (state == State.Win || state == State.Stay) {
            board.addStone(stone)
        }
    }

    private fun <T> getValidateValue(getValue: () -> (T), condition: (T) -> Boolean): T {
        while (true) {
            runCatching {
                val value = getValue()
                if (condition(value)) return value
            }.onFailure {
                println(it.message)
            }
        }
    }
}
