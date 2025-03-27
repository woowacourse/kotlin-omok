package woowacourse.omok.controller

import android.app.Activity
import android.util.Log
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
    private val mainActivity: Activity,
    private val boardSize: BoardSize,
    private val outputAppView: OutputAppView,
) {
    private val omokReferee = OmokReferee(BudoolRenjuRuleAdapter(boardSize))
    private var board = Board(boardSize)

    init {
        outputAppView.updateTurnStoneColor(board.nextStoneColor)
    }

    fun turn(
        positionView: ImageView,
        coordinate: Pair<Int, Int>,
    ) {
        val row = Row(coordinate.first)
        val col = Col(coordinate.second)
        val nextPosition = Position(row, col)
        if (!isValidPosition(nextPosition)) return

        stoneAdd(nextPosition, positionView)
        outputAppView.updateTurnStoneColor(board.nextStoneColor)
    }

    private fun isValidPosition(position: Position): Boolean {
        val positionState = board.positionStatus(position)

        if (positionState == EMPTY) {
            return true
        }
        outputAppView.printPositionStatus(positionState)
        return false
    }

    private fun stoneAdd(
        nextPosition: Position,
        positionView: ImageView,
    ) {
        val newBoard = board.nextStonePlacedBoard(nextPosition)
        val foul = omokReferee.lastStoneFoul(newBoard)

        if (foul == SAFE) {
            outputAppView.showStone(board.nextStoneColor, positionView)
            board = newBoard
            if (omokReferee.isOmok(board)) {
                board.lastStone?.let { outputAppView.omokAlert(it.stoneColor, ::restartGame) }
            }
            return
        }
        outputAppView.printFoul(foul)
    }

    private fun restartGame() {
        Log.d("재시작", "재시작호출됨")
    }
}
