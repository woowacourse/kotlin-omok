package woowacourse.omok.model

import omok.model.rule.OmokRuleManager
import woowacourse.omok.model.StoneColor.Companion.next
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PlaceStoneResult
import woowacourse.omok.model.board.Point
import woowacourse.omok.view.NextPointListener
import woowacourse.omok.view.OmokOutputView

class OmokGameForConsole(
    private val nextPointListener: NextPointListener,
    private val outputView: OmokOutputView,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: StoneColor = StoneColor.BLACK

    fun play(
        boardSize: BoardSize,
        rules: OmokRuleManager,
    ) {
        val board = Board(boardSize, rules)

        outputView.printStartMessage()
        outputView.printBoardStatus(board)

        playTurn(board)
    }

    private fun playTurn(board: Board) {
        while (true) {
            val result = placeStone(board)

            if (result is PlaceStoneResult.Omok) {
                outputView.printBoardStatus(board)
                break
            }
        }
    }

    private fun placeStone(board: Board): PlaceStoneResult =
        retryOnException {
            val pos = getNextPoint()
            board.placeStone(pos, currentStoneColor).also { result ->
                when (result) {
                    is PlaceStoneResult.Success -> handlePlaceStoneSuccess(result, board)
                    is PlaceStoneResult.Omok -> handleGameWin(result)
                    is PlaceStoneResult.AlreadyPlaced -> throw IllegalArgumentException(
                        ALREADY_PLACED_ERROR_MESSAGE,
                    )

                    is PlaceStoneResult.ForbiddenMove -> throw IllegalArgumentException(
                        CLOSED_ERROR_MESSAGE,
                    )
                }
            }
        }

    private fun handlePlaceStoneSuccess(
        result: PlaceStoneResult.Success,
        board: Board,
    ) {
        previousPoint = result.point
        currentStoneColor = currentStoneColor.next()

        outputView.printBoardStatus(board)
    }

    private fun handleGameWin(result: PlaceStoneResult.Omok) {
        outputView.printWinColor(result.point)
    }

    private fun getNextPoint(): Point =
        retryOnException {
            outputView.printCurrentTurn(previousPoint)
            val (x, y) = nextPointListener.onNextPoint()

            Point(x, y)
        }

    private fun <T> retryOnException(action: () -> T) =
        woowacourse.omok.utils.retryOnException(
            action = action,
            onFailure = { outputView.printErrorMessage(it.message.toString()) },
        )

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
    }
}
