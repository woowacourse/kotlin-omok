package omok.model.game

import omok.model.state.State
import omok.model.state.Stay
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class OmokGame(val board: Board) {
    fun start(coordinate: () -> Coordinate, showBoard: (Board) -> Unit, showTurn: (GoStoneColor, String?) -> Unit) {
        while (true) {
            val newStone = turn(coordinate, showBoard, showTurn)
            val state = checkBoard(newStone)

            if (state !is Stay) break
        }
    }

    fun turn(coordinate: () -> Coordinate, showBoard: (Board) -> Unit, showTurn: (GoStoneColor, String?) -> Unit): GoStone {
        showTurn(board.getNextColor(), board.lastPlacedStone?.coordinate?.mark)
        board.addStone(board.getNextColor(), getValidCoordinate(coordinate))
        showBoard(board)
        return board.lastPlacedStone ?: throw IllegalArgumentException("바둑판 위에 놓인 돌이 없습니다")
    }

    fun checkBoard(stone: GoStone): State {
        val rule = OmokRule(board, stone)

        return rule.checkWin()
    }

    private fun getValidCoordinate(getCoordinate: () -> Coordinate): Coordinate {
        return getValidateValue(getCoordinate, board::canAdd)
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
