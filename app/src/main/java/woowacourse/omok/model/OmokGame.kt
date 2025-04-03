package woowacourse.omok.model

import android.util.Log
import omok.model.rule.OmokRuleManager
import woowacourse.omok.database.SavedStone
import woowacourse.omok.model.StoneColor.Companion.next
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PlaceStoneResult
import woowacourse.omok.model.board.Point

class OmokGame(
    private val rules: OmokRuleManager,
    size: BoardSize,
) {
    private var isGameOver = false
    var currentStoneColor: StoneColor = StoneColor.BLACK
        private set
    var previousPoint: Point? = null
        private set
    var board = Board(size, rules)
        private set

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

            is PlaceStoneResult.ForbiddenMove -> {
                Log.i(TAG_PLACEMENT_ERROR, "금수")
                result
            }

            is PlaceStoneResult.AlreadyPlaced -> {
                Log.i(TAG_PLACEMENT_ERROR, "중복위치")
                result
            }
        }
    }

    fun resetGame() {
        isGameOver = false
        currentStoneColor = StoneColor.BLACK
        previousPoint = null
        val size = BoardSize.OMOK_BOARD_SIZE
        board = Board(BoardSize(size), rules)
    }

    fun restoreGameState(savedStones: List<SavedStone>) {
        resetGame()

        savedStones.forEach { (x, y, color) ->
            val point = Point(x, y)
            board.placeStone(point, color)
        }

        currentStoneColor =
            if (savedStones.size % 2 == 0) {
                StoneColor.BLACK
            } else {
                StoneColor.WHITE
            }
    }

    companion object {
        const val TAG_PLACEMENT_ERROR = "omokPlacementError"
    }
}
