package woowacourse.omok

import android.widget.ImageView
import domain.OmokBoard
import domain.OmokGame
import domain.OmokGameListener
import domain.State
import domain.Stone

class OmokController(
    private val omokGameListener: OmokGameListener,
    private val omokDatabase: OmokDatabase,
    private val omokUiBoard: List<ImageView>
) {

    private lateinit var omokGame: OmokGame
    private var isBlackTurn: Boolean = true

    init {
        initOmokGame()
    }

    private fun initOmokGame() {
        val stones: List<StoneEntity> = omokDatabase.fetchStones()

        val blackCount: Int = stones.filter { stone ->
            stone.color == State.BLACK.name
        }.size

        isBlackTurn = (blackCount == stones.size - blackCount)

        val omokBoard = MutableList(OmokBoard.BOARD_SIZE) {
            MutableList(OmokBoard.BOARD_SIZE) {
                State.EMPTY
            }
        }

        stones.forEach { stone ->
            val index = stone.index
            val row = index / 15
            val column = index % 15

            if (stone.color == State.BLACK.name) {
                omokUiBoard[index].setImageResource(R.drawable.black_stone)
                omokBoard[row][column] = State.BLACK
            }
            if (stone.color == State.WHITE.name) {
                omokUiBoard[index].setImageResource(R.drawable.white_stone)
                omokBoard[row][column] = State.WHITE
            }
        }

        omokGame = OmokGame(
            omokBoard = OmokBoard(omokBoard),
            omokGameListener = omokGameListener
        )
    }

    fun run(index: Int) {
        val state = if (isBlackTurn) {
            State.BLACK
        } else {
            State.WHITE
        }

        val row = index / 15
        val col = index % 15
        if (omokGame.successTurn(Stone(row, col), state)) {
            omokDatabase.saveStone(index, state)
            isBlackTurn = !isBlackTurn
            omokGame.isVictory(state)
        }
    }
}
