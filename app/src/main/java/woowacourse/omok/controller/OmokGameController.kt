package woowacourse.omok.controller

import android.widget.ImageView
import domain.OmokBoard
import domain.OmokGame
import domain.State
import domain.Stone
import domain.listener.OmokListener
import woowacourse.omok.OmokDbHelper
import woowacourse.omok.R

class OmokGameController(
    private val omokGameListener: OmokListener,
    private val db: OmokDbHelper,
    private val boardView: List<List<ImageView>>
) {
    private val omokGame: OmokGame

    init {
        omokGame = makeOmokGame()
        setPreBoardImage()
    }

    fun move(row: Int, column: Int) {
        if(omokGame.successTurn(Stone(row, column), omokGame.turn)) {
            db.insertData(row, column, omokGame.turn)
            setImageViewResource(omokGame.turn, boardView[row][column])
            omokGame.isVictory(omokGame.turn)
        }
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
        return OmokGame(OmokBoard(omokBoard), omokGameListener, turn)
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

    fun setImageViewResource(state: State, imageView: ImageView) {
        when (state) {
            State.BLACK -> imageView.setImageResource(R.drawable.black_stone)
            State.WHITE -> imageView.setImageResource(R.drawable.white_stone)
            State.EMPTY -> null
        }
    }
}
