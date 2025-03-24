package omok.model

import omok.controller.OmokGameHandler
import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.board.Point
import omok.model.board.StoneColor
import omok.model.board.result.Finished
import omok.model.board.result.OnGoing
import omok.model.board.result.PlaceStoneResult
import omok.model.rule.RuleValidator

class OmokGame(
    private val omokGameHandler: OmokGameHandler,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: StoneColor = StoneColor.BLACK

    fun play(
        boardSize: BoardSize,
        judge: RuleValidator,
    ) {
        val board = Board(boardSize, judge)
        omokGameHandler.onStartGame()
        omokGameHandler.onBoardUpdated(board)

        playTurns(board)
    }

    private fun playTurns(board: Board) =
        retryOnException(
            action = {
                val point = getNextPoint()
                val placeResult = board.placeStone(point, currentStoneColor)
                handlePlaceResult(placeResult, board)
                placeResult
            },
            isFinish = { placeResult ->
                placeResult is Finished
            },
        )

    private fun getNextPoint(): Point =
        retryOnException(
            action = { omokGameHandler.onRequestPosition(previousPoint to currentStoneColor) },
        )

    private fun handlePlaceResult(
        result: PlaceStoneResult,
        board: Board,
    ) {
        if (result is OnGoing) handleOnGoingResult(result, board)
        handleFinishedResult(result as Finished, board)
    }

    private fun handleFinishedResult(
        result: Finished,
        board: Board,
    ) {
        when (result) {
            is Finished.GameFinished -> {
                updateGameState(result.point, board)
                showWinColor(board)
            }

            is Finished.BoardFull -> {
                updateGameState(result.point, board)
                omokGameHandler.onError(BOARD_FULL_ERROR_MESSAGE)
            }
        }
    }

    private fun handleOnGoingResult(
        result: OnGoing,
        board: Board,
    ) {
        when (result) {
            is OnGoing.StonePlaced -> updateGameState(result.point, board)
            is OnGoing.AlreadyPlaced -> omokGameHandler.onError(ALREADY_PLACED_ERROR_MESSAGE)
            is OnGoing.RuleViolation -> omokGameHandler.onError(CLOSED_ERROR_MESSAGE)
            is OnGoing.InvalidMove -> omokGameHandler.onError(INVALID_POINT_ERROR_MESSAGE)
        }
    }

    private fun updateGameState(
        point: Point,
        board: Board,
    ) {
        previousPoint = point
        currentStoneColor = currentStoneColor.reverseStoneColor()
        omokGameHandler.onBoardUpdated(board)
    }

    private fun showWinColor(board: Board) {
        previousPoint?.let { point ->
            val color = board.findStoneColor(point)
            omokGameHandler.onGameWon(color)
        }
    }

    private fun <T> retryOnException(
        action: () -> T,
        isFinish: (T) -> Boolean = { false },
    ): T =
        omok.utils.retry(
            action = action,
            isFinish = { isFinish(it) },
            onFailure = { omokGameHandler.onError(it.message.toString()) },
        )

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
        private const val INVALID_POINT_ERROR_MESSAGE = "바둑판 크기를 벗어난 위치입니다."
        private const val BOARD_FULL_ERROR_MESSAGE = "무승부! - 바둑판에 더 이상 둘 수 있는 공간이 없습니다."
    }
}
