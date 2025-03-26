package woowacourse.omok

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.RenjuRule

class MainActivity : AppCompatActivity() {
    private val game = Game(Board(), RenjuRule())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val color: Color = game.chooseTurn()
                    val stoneImage =
                        when (color) {
                            Color.BLACK -> R.drawable.black_stone
                            Color.WHITE -> R.drawable.white_stone
                        }

                    val x = Col(index % game.board.col.value + 1)
                    val y = Row(index / game.board.row.value + 1)
                    val moveResult: MoveResult = game.processTurn(Position(x, y), color)

                    when (moveResult) {
                        is MoveResult.Success.Playing -> view.setImageResource(stoneImage)
                        is MoveResult.Success.Finished -> {
                            view.setImageResource(stoneImage)
                            printMoveResult(moveResult, board)
                            board
                                .children
                                .filterIsInstance<TableRow>()
                                .flatMap { it.children }
                                .filterIsInstance<ImageView>()
                                .forEach { it.setOnClickListener(null) }
                            return@setOnClickListener
                        }
                        is MoveResult.Failure -> {
                            printMoveResult(moveResult, board)
                        }
                    }
                }
            }
    }

    private fun printMoveResult(
        moveResult: MoveResult,
        view: View,
    ) {
        when (moveResult) {
            is MoveResult.Success -> printSuccessResut(moveResult)
            is MoveResult.Failure -> printFailureResult(moveResult, view)
        }
    }

    private fun printSuccessResut(moveResult: MoveResult.Success) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
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
