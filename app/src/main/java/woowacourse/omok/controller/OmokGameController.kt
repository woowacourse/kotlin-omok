package woowacourse.omok.controller

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import domain.OmokBoard
import domain.OmokGame
import domain.State
import domain.Stone
import domain.listener.OmokListener
import woowacourse.omok.GameOverActivity
import woowacourse.omok.OmokDbHelper
import woowacourse.omok.R

class OmokGameController(
    private val context: Context,
    private val db: OmokDbHelper,
    private val boardView: List<List<ImageView>>
) {
    private val omokGame: OmokGame

    init {
        omokGame = makeOmokGame()
        setPreBoardImage()
    }

    private fun makeOmokGame(): OmokGame {
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
        return OmokGame(OmokBoard(omokBoard), OmokGameListener(), turn)
    }

    private fun setPreBoardImage() {
        omokGame.omokBoard.value.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, state ->
                setImageViewResource(state, boardView[rowIndex][columnIndex])
            }
        }
    }

    private fun setImageViewResource(state: State, imageView: ImageView) {
        when (state) {
            State.BLACK -> imageView.setImageResource(R.drawable.black_stone)
            State.WHITE -> imageView.setImageResource(R.drawable.white_stone)
            State.EMPTY -> null
        }
    }

    fun move(row: Int, column: Int) {
        if (omokGame.successTurn(Stone(row, column), omokGame.turn)) {
            db.insertData(row, column, omokGame.turn)
            setImageViewResource(omokGame.turn, boardView[row][column])
            omokGame.isVictory(omokGame.turn)
            omokGame.changeTurn()
        }
    }

    inner class OmokGameListener : OmokListener {
        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
            when (state) {
                State.BLACK -> setImageViewResource(State.BLACK, boardView[stone.row][stone.column])
                State.WHITE -> setImageViewResource(State.WHITE, boardView[stone.row][stone.column])
                State.EMPTY -> null
            }
        }

        override fun onMoveFail() {
            Toast.makeText(context, "이미 돌이 존재합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onForbidden() {
            Toast.makeText(context, "이미 돌이 존재합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onFinish(state: State): State {
            Toast.makeText(context, "${state.name}승!", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, GameOverActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
            return state
        }
    }
}
