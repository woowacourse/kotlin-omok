package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.view.OutputViewAndroid

class MainActivity : AppCompatActivity() {
    private val game = Game(Board(), RenjuRule())
    private val outputView = OutputViewAndroid()

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
        outputView.printOmokStart(board)
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
                            outputView.printMoveResult(moveResult, this, board)
                            board
                                .children
                                .filterIsInstance<TableRow>()
                                .flatMap { it.children }
                                .filterIsInstance<ImageView>()
                                .forEach { it.setOnClickListener(null) }
                            return@setOnClickListener
                        }

                        is MoveResult.Failure -> {
                            outputView.printMoveResult(moveResult, this, board)
                        }
                    }
                }
            }
    }
}
