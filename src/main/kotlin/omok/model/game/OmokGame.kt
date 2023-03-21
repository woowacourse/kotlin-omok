package omok.model.game

import omok.model.PlacementState
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class OmokGame(private val board: Board) {
    fun start(coordinate: () -> Coordinate, showBoard: (Board) -> Unit, showTurn: (GoStoneColor, Coordinate?) -> Unit) {
        while (true) {
            val newStone = turn(coordinate, showBoard, showTurn)
            val state = judge(board, newStone)

            if (state != PlacementState.STAY) break
        }
    }

    private fun turn(
        coordinate: () -> Coordinate,
        showBoard: (Board) -> Unit,
        showTurn: (GoStoneColor, Coordinate?) -> Unit
    ): GoStone {
        showTurn(board.getNextColor(), board.lastPlacedStone?.coordinate)
        board.addStone(GoStone(board.getNextColor(), getValidCoordinate(coordinate)))
        showBoard(board)
        return board.lastPlacedStone ?: throw IllegalArgumentException("바둑판 위에 놓인 돌이 없습니다")
    }

    private fun judge(board: Board, goStone: GoStone): PlacementState {
        val rule = OmokRuleAdapter(board)
        val coordinate = goStone.coordinate
        val color = goStone.color

        if (color == GoStoneColor.WHITE) return rule.checkWin(coordinate, GoStoneColor.WHITE)

        if (rule.checkWin(coordinate, GoStoneColor.BLACK) == PlacementState.WIN) return PlacementState.WIN

        return rule.checkBlackAnyViolation(coordinate)
    }

    private fun getValidCoordinate(getCoordinate: () -> Coordinate): Coordinate {
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
