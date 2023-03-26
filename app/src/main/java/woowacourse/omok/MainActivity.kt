package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokBoard
import domain.OmokGame
import domain.State
import domain.Stone
import woowacourse.omok.listener.OmokGameListener

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var boardView: List<List<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = OmokDbHelper(this)
        val board = findViewById<TableLayout>(R.id.board)
        boardView = board.children.filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }.toList()
        omokGame = makeOmokGame(db)
        setPreBoardImage()
        addImageViewListener(db)
    }

    private fun addImageViewListener(db: OmokDbHelper) {
        boardView.forEachIndexed { row, imageViews ->
            imageViews.forEachIndexed { column, imageView ->
                imageView.setOnClickListener {
                    moveTurn(Stone(row, column), db)
                }
            }
        }
    }

    private fun moveTurn(stone: Stone, db: OmokDbHelper) {
        if (!omokGame.isGameOver()) {
            moveStone(stone, db)
        } else {
            startActivity(Intent(this, GameOverActivity::class.java))
        }
    }

    private fun moveStone(stone: Stone, db: OmokDbHelper) {
        if (omokGame.successTurn(stone)) {
            setImageViewResource(omokGame.turn, boardView[stone.row][stone.column])
            db.insertData(stone.row, stone.column, omokGame.turn)
            omokGame.goNext()
        }
    }

    private fun setPreBoardImage() {
        omokGame.omokBoard.value.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, state ->
                when (state) {
                    State.BLACK -> setImageViewResource(State.BLACK, boardView[rowIndex][columnIndex])
                    State.WHITE -> setImageViewResource(State.WHITE, boardView[rowIndex][columnIndex])
                    State.EMPTY -> null
                }
            }
        }
    }

    private fun makeOmokGame(db: OmokDbHelper): OmokGame {
        val omokBoard =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }

        val blackStones = db.getStones(State.BLACK)
        blackStones.forEach { stone ->
            omokBoard[stone.row][stone.column] = State.BLACK
        }

        val whiteStones = db.getStones(State.WHITE)
        whiteStones.forEach { stone ->
            omokBoard[stone.row][stone.column] = State.WHITE
        }

        val turn = if (blackStones.size > whiteStones.size) State.WHITE else State.BLACK
        return OmokGame(OmokBoard(omokBoard), OmokGameListener(this), turn)
    }

    private fun setImageViewResource(state: State, imageView: ImageView) {
        when (state) {
            State.BLACK -> imageView.setImageResource(R.drawable.black_stone)
            State.WHITE -> imageView.setImageResource(R.drawable.white_stone)
            State.EMPTY -> null
        }
    }
}
