package woowacourse.omok.model

import omok.model.rule.OmokRuleManager
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.database.SavedStone
import woowacourse.omok.model.StoneColor.Companion.next
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PlaceStoneResult
import woowacourse.omok.model.board.Point

class OmokGame {
    private var isGameOver = false
    var currentStoneColor: StoneColor = StoneColor.BLACK
    private var previousPoint: Point? = null
    private var board: Board

    init {
        val size = BoardSize.OMOK_BOARD_SIZE
        board = Board(BoardSize(size), getRules())
    }

    private fun getRules(): OmokRuleManager {
        val rules = OmokRuleManager
        rules.forbiddenMoveRule.add(OverlineRule())
        rules.forbiddenMoveRule.add(DoubleThreeMoveRule())
        rules.forbiddenMoveRule.add(DoubleFourMoveRule())
        return rules
    }

    fun placeStone(
        x: Int,
        y: Int,
    ): PlaceStoneResult {
        if (isGameOver) return PlaceStoneResult.AlreadyPlaced

        val point = Point(x, y)

        return when (val result = board.placeStone(point, currentStoneColor)) {
            is PlaceStoneResult.Success -> {
                previousPoint = result.point
                currentStoneColor = currentStoneColor.next()
                result
            }

            is PlaceStoneResult.Omok -> {
                isGameOver = true
                result
            }

            else -> result
        }
    }

    fun resetGame() {
        isGameOver = false
        currentStoneColor = StoneColor.BLACK
        previousPoint = null
        val size = BoardSize.OMOK_BOARD_SIZE
        board = Board(BoardSize(size), getRules())
    }

    fun restoreGameState(savedStones: List<SavedStone>) {
        resetGame()

        savedStones.forEach { (x, y, color) ->
            val point = Point(x, y)
            board.placeStone(point, color)
        }

        currentStoneColor =
            if (savedStones.size % 2 == 0) {
                StoneColor.WHITE
            } else {
                StoneColor.BLACK
            }
    }
}
