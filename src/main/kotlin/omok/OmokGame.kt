package omok

import omok.model.board.Board
import omok.model.board.PlaceStoneResult
import omok.model.board.Point
import omok.model.rule.FiveInRow
import omok.model.rule.Rule
import omok.model.stone.Position
import omok.model.stone.StoneColor
import omok.model.stone.StoneColor.Companion.next

class OmokGame {
    private var previousPoint: Point? = null
    private var currentStoneColor: StoneColor = StoneColor.BLACK
    private val rule: Rule = FiveInRow()

    fun play() {
        val board = Board()

        playTurn(board)
    }

    private fun playTurn(board: Board) {
        while (judgeRule(board)) {
            val pos = getNextPoint()
            placeStone(board, pos)
        }
    }

    private fun placeStone(
        board: Board,
        pos: Position,
    ) {
        when (val result = board.placeStone(pos, currentStoneColor)) {
            is PlaceStoneResult.Success -> {
                previousPoint = result.point
                currentStoneColor = currentStoneColor.next()
            }

            is PlaceStoneResult.AlreadyPlaced -> {}
            is PlaceStoneResult.Closed -> {}
        }
    }

    private fun judgeRule(board: Board): Boolean {
        if (previousPoint == null) return false
        return !rule.calculate(board, previousPoint!!)
    }

    private fun getNextPoint(): Position {
        return Position(1, 1)
    }
}
