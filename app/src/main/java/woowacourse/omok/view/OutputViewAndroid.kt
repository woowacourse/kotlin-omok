package woowacourse.omok.view

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.MainActivity
import woowacourse.omok.model.Color
import woowacourse.omok.model.MoveResult

class OutputViewAndroid {
    fun printOmokStart(view: View) {
        Snackbar.make(view, MESSAGE_OMOK_START, Snackbar.LENGTH_SHORT).show()
    }

    fun printMoveResult(
        moveResult: MoveResult,
        mainActivity: MainActivity,
        view: View,
    ) {
        when (moveResult) {
            is MoveResult.Success -> printSuccessResut(moveResult, mainActivity)
            is MoveResult.Failure -> printFailureResult(moveResult, view)
        }
    }

    private fun printSuccessResut(
        moveResult: MoveResult.Success,
        mainActivity: MainActivity,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(mainActivity)
        builder.setMessage(
            when (moveResult) {
                is MoveResult.Success.Playing -> MESSAGE_OMOK_IN_PROGRESS
                is MoveResult.Success.Finished -> MESSAGE_OMOK_WINNER.format(moveResult.winner.toPlayerName())
            },
        ).show()
    }

    private fun printFailureResult(
        moveResult: MoveResult.Failure,
        view: View,
    ) {
        val message: String =
            when (moveResult) {
                is MoveResult.Failure.PositionAlreadyOccupied -> MESSAGE_FAILURE_POSITION_ALREADY_OCCUPIED
                is MoveResult.Failure.DoubleThreeViolation -> MESSAGE_FAILURE_DOUBLE_THREE_VIOLATION
                is MoveResult.Failure.DoubleFourViolation -> MESSAGE_FAILURE_DOUBLE_FOUR_VIOLATION
                is MoveResult.Failure.OverlineViolation -> MESSAGE_FAILURE_OVERLINE_VIOLATION
                else -> return
            }
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun Color.toPlayerName(): String {
        return when (this) {
            Color.BLACK -> BLACK_PLAYER
            Color.WHITE -> WHITE_PLAYER
        }
    }

    companion object {
        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
        private const val MESSAGE_OMOK_WINNER = "%s이 승리했습니다!"
        private const val MESSAGE_OMOK_IN_PROGRESS = "게임이 아직 종료되지 않았습니다."

        private const val MESSAGE_FAILURE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val MESSAGE_FAILURE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val MESSAGE_FAILURE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val MESSAGE_FAILURE_OVERLINE_VIOLATION = "장목 금수입니다."

        private const val BLACK_PLAYER = "흑"
        private const val WHITE_PLAYER = "백"
    }
}
