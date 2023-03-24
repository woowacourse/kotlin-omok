package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokGame
import domain.State
import domain.Stone
import woowacourse.omok.listener.OmokGameListener

class MainActivity : AppCompatActivity() {
    private val TAG_STONE_POSITION = "착수 위치"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = OmokDbHelper(this)
        val board = findViewById<TableLayout>(R.id.board)
        val boardView = board.children.filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }.toList()
        val omokGame = makeOmokGame(db)
        setPreBoardImage(omokGame, db, boardView)
        addImageViewListener(omokGame, db, boardView)
    }

    private fun addImageViewListener(
        omokGame: OmokGame,
        db: OmokDbHelper,
        boardView: List<List<ImageView>>
    ) {
        boardView.forEachIndexed { row, imageViews ->
            imageViews.forEachIndexed { column, imageView ->
                imageView.setOnClickListener {
                    Log.d(TAG_STONE_POSITION, "${row}행 ${column}열")
                    moveTurn(omokGame, Stone(row, column), boardView, db)
                }
            }
        }
    }

    private fun moveTurn(
        omokGame: OmokGame,
        stone: Stone,
        boardView: List<List<ImageView>>,
        db: OmokDbHelper
    ) {
        if (!omokGame.isGameOver()) {
            moveStone(omokGame, stone, boardView, db)
        } else {
            startActivity(Intent(this, GameOverActivity::class.java))
        }
    }

    private fun moveStone(
        omokGame: OmokGame,
        stone: Stone,
        boardView: List<List<ImageView>>,
        db: OmokDbHelper
    ) {
        if (omokGame.successTurn(stone)) {
            changeImage(omokGame, boardView[stone.row][stone.column])
            db.insertData(stone.row, stone.column, omokGame.turn)
            omokGame.goNext()
        }
    }

    private fun setPreBoardImage(
        omokGame: OmokGame,
        db: OmokDbHelper,
        boardView: List<List<ImageView>>
    ) {
        db.getStones(State.BLACK).forEach { stone ->
            omokGame.initBoard(State.BLACK, stone)
            setImageViewResource(State.BLACK, boardView[stone.row][stone.column])
        }
        db.getStones(State.WHITE).forEach { stone ->
            omokGame.initBoard(State.WHITE, stone)
            setImageViewResource(State.WHITE, boardView[stone.row][stone.column])
        }
    }

    private fun makeOmokGame(db: OmokDbHelper): OmokGame {
        val blackStones = db.getStones(State.BLACK)
        Log.d(TAG_STONE_POSITION, "흑돌의 위치 : $blackStones")

        val whiteStones = db.getStones(State.WHITE)
        Log.d(TAG_STONE_POSITION, "백돌의 위치 : $whiteStones")

        val turn = if (blackStones.size > whiteStones.size) State.WHITE else State.BLACK
        return OmokGame(omokGameListener = OmokGameListener(this), _turn = turn)
    }

    private fun setImageViewResource(state: State, boardView: ImageView) {
        when (state) {
            State.BLACK -> boardView.setImageResource(R.drawable.black_stone)
            State.WHITE -> boardView.setImageResource(R.drawable.white_stone)
            State.EMPTY -> null
        }
    }

    private fun changeImage(omokGame: OmokGame, boardView: ImageView) {
        setImageViewResource(omokGame.turn, boardView)
    }
}
