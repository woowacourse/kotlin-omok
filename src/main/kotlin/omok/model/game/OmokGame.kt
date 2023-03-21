package omok.model.game

import omok.model.state.Judgement
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class OmokGame(val board: Board) {
    fun start(coordinate: () -> Coordinate, showBoard: (Board) -> Unit, showTurn: (GoStoneColor, String?) -> Unit) {
        while (true) {
            val newStone = turn(coordinate, showBoard, showTurn)
            val state = Judgement.judge(board, newStone)

            if (state != State.Stay) break
        }
    }

    private fun turn(coordinate: () -> Coordinate, showBoard: (Board) -> Unit, showTurn: (GoStoneColor, String?) -> Unit): GoStone {
        showTurn(board.getNextColor(), "ㅇㅇ")  //board.lastPlacedStone?.coordinate?.mark
        board.addStone(board.getNextColor(), getValidCoordinate(coordinate))
        showBoard(board)
        return board.lastPlacedStone ?: throw IllegalArgumentException("바둑판 위에 놓인 돌이 없습니다")
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