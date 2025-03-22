package omok.model

import omok.controller.OmokGameListener
import omok.model.board.Board
import omok.model.board.PlaceStoneResult
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.count.FiveInRowRule
import omok.model.rule.count.OmokCountRule

class OmokGame(
    private val omokGameView: OmokGameListener,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: PointState = PointState.BLACK

    fun play() {
        val board = Board()
        omokGameView.onStartGame()
        omokGameView.onBoardUpdated(board)

        playTurn(board)
    }

    private fun playTurn(board: Board) {
        while (judgeRule(FiveInRowRule, board)) {
            placeStone(board)
        }

        showWinColor(board)
    }

    private fun placeStone(board: Board) =
        retryOnException {
            val point = getNextPoint()
            when (val result = board.placeStone(point, currentStoneColor)) {
                is PlaceStoneResult.Success -> {
                    handlePlaceSuccess(result, board)
                    return@retryOnException
                }
                is PlaceStoneResult.AlreadyPlaced -> throw IllegalArgumentException(ALREADY_PLACED_ERROR_MESSAGE)
                is PlaceStoneResult.Closed -> throw IllegalArgumentException(CLOSED_ERROR_MESSAGE)
                is PlaceStoneResult.InvalidPoint -> throw IllegalArgumentException(INVALID_POINT_ERROR_MESSAGE)
            }
        }

    private fun handlePlaceSuccess(
        result: PlaceStoneResult.Success,
        board: Board,
    ) {
        previousPoint = result.point
        currentStoneColor = currentStoneColor.reverseStoneColor() ?: throw IllegalArgumentException(INVALID_CURRENT_STONE_COLOR)
        omokGameView.onBoardUpdated(board)
    }

    private fun judgeRule(
        omokCountRule: OmokCountRule,
        board: Board,
    ): Boolean {
        if (previousPoint == null) return true
        return !omokCountRule.calculate(board, previousPoint!!)
    }

    private fun showWinColor(board: Board) {
        val color = board.findPoint(previousPoint!!)?.second
        omokGameView.onGameWon(color)
    }

    private fun getNextPoint(): Point =
        retryOnException {
            omokGameView.onRequestPosition(previousPoint to currentStoneColor)
        }

    private fun <T> retryOnException(action: () -> T) =
        omok.utils.retryOnException(
            action = action,
            onFailure = {
                omokGameView.onError(it.message.toString())
            },
        )

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
        private const val INVALID_POINT_ERROR_MESSAGE = "바둑판 크기를 벗어난 위치입니다."
        private const val INVALID_CURRENT_STONE_COLOR = "현재 턴의 돌 색상을 찾을 수 없습니다."
    }
}
