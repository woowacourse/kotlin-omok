package woowacourse.omok.view

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.MainActivity
import woowacourse.omok.R
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row

class OmokView(
    private val mainActivity: MainActivity,
) {
    private val boardLayout: TableLayout = mainActivity.findViewById(R.id.board)
    private val views: Sequence<ImageView> =
        boardLayout.children
            .filterIsInstance<TableRow>()
            .flatMap { tableRow -> tableRow.children }
            .filterIsInstance<ImageView>()

    init {
        mainActivity.enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(mainActivity.findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun printOmokStart() {
        Snackbar.make(boardLayout, MESSAGE_OMOK_START, Snackbar.LENGTH_SHORT).show()
    }

    fun printMoveResult(moveResult: MoveResult) {
        when (moveResult) {
            is MoveResult.Success -> printSuccessResult(moveResult)
            is MoveResult.Failure -> printFailureResult(moveResult)
        }
    }

    private fun printSuccessResult(moveResult: MoveResult.Success) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(mainActivity)
        builder.setMessage(
            when (moveResult) {
                is MoveResult.Success.Playing -> MESSAGE_OMOK_IN_PROGRESS
                is MoveResult.Success.Finished -> MESSAGE_OMOK_WINNER.format(moveResult.winner.toPlayerName())
            },
        ).show()
    }

    private fun Color.toPlayerName(): String {
        return when (this) {
            Color.BLACK -> BLACK_PLAYER
            Color.WHITE -> WHITE_PLAYER
        }
    }

    private fun printFailureResult(moveResult: MoveResult.Failure) {
        val message: String =
            when (moveResult) {
                is MoveResult.Failure.PositionAlreadyOccupied -> MESSAGE_FAILURE_POSITION_ALREADY_OCCUPIED
                is MoveResult.Failure.DoubleThreeViolation -> MESSAGE_FAILURE_DOUBLE_THREE_VIOLATION
                is MoveResult.Failure.DoubleFourViolation -> MESSAGE_FAILURE_DOUBLE_FOUR_VIOLATION
                is MoveResult.Failure.OverlineViolation -> MESSAGE_FAILURE_OVERLINE_VIOLATION
                else -> return
            }
        Snackbar.make(boardLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    fun renderStone(
        board: Board,
        stone: Stone,
    ) {
        val index =
            (stone.position.y.value - 1) * board.row.value + (stone.position.x.value - 1)
        views.toList()[index].setImageResource(stone.color.toImage())
    }

    private fun Color.toImage(): Int =
        when (this) {
            Color.BLACK -> R.drawable.black_stone
            Color.WHITE -> R.drawable.white_stone
        }

    fun setListeners(
        board: Board,
        onClick: (position: Position) -> Unit,
    ) {
        views.forEachIndexed { index, view ->
            val x = Col(index % board.col.value + 1)
            val y = Row(index / board.row.value + 1)
            view.setOnClickListener { onClick(Position(x, y)) }
        }
    }

    fun clearListeners() {
        views.forEach { view -> view.setOnClickListener(null) }
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
