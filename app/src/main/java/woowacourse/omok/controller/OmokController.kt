package woowacourse.omok.controller

import omok.model.rule.OmokRuleManager
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PlaceStoneResult
import woowacourse.omok.model.board.Point
import woowacourse.omok.view.OmokInputView
import woowacourse.omok.view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    private lateinit var omokGame: OmokGame

    fun play() {
        val size = BoardSize.OMOK_BOARD_SIZE
        val rules = getRules()
        omokGame = OmokGame(rules, BoardSize(size))

        outputView.printStartMessage()
        outputView.printBoardStatus(omokGame.board)

        playTurn(omokGame.board)
    }

    private fun playTurn(board: Board) {
        while (true) {
            val point = getNextPoint()
            val result = omokGame.placeStone(point.x, point.y)

            outputView.printBoardStatus(board)

            when (result) {
                is PlaceStoneResult.AlreadyPlaced -> {
                    outputView.printErrorMessage(ALREADY_PLACED_ERROR_MESSAGE)
                }

                is PlaceStoneResult.ForbiddenMove -> {
                    outputView.printErrorMessage(CLOSED_ERROR_MESSAGE)
                }

                is PlaceStoneResult.Success -> {
                    outputView.printBoardStatus(board)
                }

                is PlaceStoneResult.Omok -> {
                    outputView.printBoardStatus(board)
                    outputView.printWinColor(result.point)
                    break
                }
            }
        }
    }

    private fun getNextPoint(): Point =
        retryOnException {
            outputView.printCurrentTurn(omokGame.previousPoint)
            val (x, y) = inputView.readPosition()

            Point(x, y)
        }

    private fun getRules(): OmokRuleManager {
        val rules = OmokRuleManager

        rules.forbiddenMoveRule.add(OverlineRule())
        rules.forbiddenMoveRule.add(DoubleThreeMoveRule())
        rules.forbiddenMoveRule.add(DoubleFourMoveRule())

        return rules
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
