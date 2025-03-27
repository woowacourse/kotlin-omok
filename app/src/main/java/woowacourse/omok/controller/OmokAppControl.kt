package woowacourse.omok.controller

import android.widget.ImageView
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PositionStatus.EMPTY
import woowacourse.omok.model.rule.BudoolRenjuRuleAdapter
import woowacourse.omok.model.rule.OmokReferee
import woowacourse.omok.model.rule.RenjuFoul.SAFE
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row
import woowacourse.omok.view.OutputAppView

class OmokAppControl(
    private val boardSize: BoardSize,
    private val outputAppView: OutputAppView,
) {
    private val omokReferee = OmokReferee(BudoolRenjuRuleAdapter(boardSize))
    private var board = Board(boardSize)

    init {
        outputAppView.turnInfoUiUpdate(board.nextStoneColor)
    }

    fun turn(
        positionView: ImageView,
        coordinate: Pair<Int, Int>,
    ) {
        val row = Row(coordinate.first)
        val col = Col(coordinate.second)
        val nextPosition = Position(row, col)
        if (!isPositionValid(nextPosition)) return

        stoneAdd(nextPosition, positionView)
        outputAppView.turnInfoUiUpdate(board.nextStoneColor)
    }

    private fun isPositionValid(position: Position): Boolean {
        val positionState = board.positionStatus(position)

        if (positionState == EMPTY) {
            return true
        }
        outputAppView.positionStatusAlert(positionState)
        return false
    }

    private fun stoneAdd(
        nextPosition: Position,
        positionView: ImageView,
    ) {
        val newBoard = board.nextStonePlacedBoard(nextPosition)
        val foul = omokReferee.lastStoneFoul(newBoard)

        if (foul == SAFE) {
            outputAppView.stoneUiDraw(board.nextStoneColor, positionView)
            board = newBoard
            if (omokReferee.isOmok(board)) {
                board.lastStone?.let { outputAppView.omokDialogAlert(it.stoneColor, ::gameRestart) }
            }
            return
        }
        outputAppView.foulAlert(foul)
    }

    private fun gameRestart() {
        board = Board(boardSize)
        outputAppView.turnInfoUiUpdate(board.nextStoneColor)
        outputAppView.stoneUiClear()
    }
}
