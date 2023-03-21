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
        showTurn: (GoStoneColor, GoStone?) -> Unit
    ): GoStoneColor {
        var newStone: GoStone
        var state: State

        while (true) {
            newStone = turn(coordinate, showBoard, showTurn)
            state = Judgement.judge(board, newStone)

            if (state != State.Stay) break
        }

        return getWinner(state, newStone.color)
    }

    private fun turn(
        getCoordinate: () -> Coordinate,
        showBoard: (Board) -> Unit,
        showTurn: (GoStoneColor, GoStone?) -> Unit
    ): GoStone {
        showTurn(board.getNextColor(), board.lastPlacedStone)
        board.addStone(board.getNextColor(), getValidateValue(getCoordinate, board::canAdd))
        showBoard(board)
        return board.lastPlacedStone ?: throw IllegalArgumentException("바둑판 위에 놓인 돌이 없습니다")
    }

    private fun getWinner(state: State, color: GoStoneColor): GoStoneColor {
        return when (state) {
            State.Win, State.Stay -> color
            State.DoubleThree, State.DoubleFour -> return GoStoneColor.getNextColor(color)
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
