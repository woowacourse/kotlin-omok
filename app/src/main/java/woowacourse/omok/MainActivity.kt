package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.domain.OmokAdapter
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.StoneState
import woowacourse.omok.domain.turn.PutStoneResult
import woowacourse.omok.domain.turn.PutStoneResult.Finished
import woowacourse.omok.domain.turn.PutStoneResult.NextTurn

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var board: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        omokGame = OmokGame(OmokBoard(rule = OmokAdapter()))
        board = findViewById(R.id.board)
        setBoard()
    }

    private fun setBoard() {
        val columns = ('A'..'O').toList()
        val rows = (15 downTo 1).toList()

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val x = columns[index % 15]
                val y = rows[index / 15]
                view.tag = "$x$y"

                view.setOnClickListener {
                    handlePutStoneResult(view, Position(x - 'A', y - 1))
                }
            }
    }

    private fun handlePutStoneResult(
        view: ImageView,
        position: Position,
    ) {
        when (omokGame.putStone(position)) {
            is NextTurn -> {
                if (omokGame.getNowTurn() == StoneState.BLACK) {
                    view.setImageResource(R.drawable.white_stone)
                } else {
                    view.setImageResource(R.drawable.black_stone)
                }
            }

            is Finished -> {
                board.children
                    .filterIsInstance<TableRow>()
                    .flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .forEach { it.setOnClickListener(null) }

                val winner = omokGame.getNowTurn()
                if (winner == StoneState.BLACK) {
                    view.setImageResource(R.drawable.black_stone)
                } else {
                    view.setImageResource(R.drawable.white_stone)
                }
                Toast
                    .makeText(
                        this,
                        getString(R.string.text_win_message, winner.name),
                        Toast.LENGTH_LONG,
                    ).show()
            }

            is PutStoneResult.AlreadyPlaced -> {
                Toast.makeText(this, R.string.text_already_placed, Toast.LENGTH_SHORT).show()
            }

            is PutStoneResult.Violation -> {
                Toast.makeText(this, R.string.text_violate_rule, Toast.LENGTH_SHORT).show()
            }

            is PutStoneResult.InvalidPosition -> {
                Toast.makeText(this, R.string.text_invalid_position, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
