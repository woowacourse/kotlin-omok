package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class OmokGame(private val board: Board) {
    private val blackRenjuRuleAdapter: BlackOmokRuleAdapter = BlackOmokRuleAdapter(board)
    private val whiteRenjuRuleAdapter: WhiteOmokRuleAdapter = WhiteOmokRuleAdapter(board)

    fun start(coordinate: () -> Coordinate, showBoard: (Board) -> Unit, showTurn: (GoStoneColor, Coordinate?) -> Unit) {
        while (true) {
            val newStone = turn(coordinate, showBoard, showTurn)
            val state = judge(newStone)

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

    private fun judge(goStone: GoStone): PlacementState {
        val coordinate = goStone.coordinate
        val color = goStone.color

        if (color == GoStoneColor.WHITE) return whiteRenjuRuleAdapter.checkWin(coordinate)

        if (blackRenjuRuleAdapter.checkWin(coordinate) == PlacementState.WIN) return PlacementState.WIN

        return blackRenjuRuleAdapter.checkViolation(coordinate)
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
