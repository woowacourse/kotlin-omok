package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokBoard
import domain.OmokGame
import domain.OmokGameListener
import domain.State
import domain.Stone

class MainActivity : AppCompatActivity() {

    private val omokDatabase by lazy { OmokDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        val omokUiBoard = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

        val omokGameListener = object : OmokGameListener {

            override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {}

            override fun onMoveFail() {
                Toast.makeText(this@MainActivity, "중복된 위치입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onForbidden() {
                Toast.makeText(this@MainActivity, "금수 입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(state: State) {
                moveToGameOver(state)
            }
        }

        val stones: List<StoneEntity> = omokDatabase.fetchStones()

        val blackCount: Int = stones.filter { stone ->
            stone.color == State.BLACK.name
        }.size

        var isBlackTurn = (blackCount == stones.size - blackCount)

        val omokBoard = setUpBoard(
            omokUiBoard = omokUiBoard,
            stones = stones
        )

        val omokGame = OmokGame(
            omokBoard = omokBoard,
            omokGameListener = omokGameListener
        )

        omokUiBoard.forEachIndexed { index, view ->

            view.setOnClickListener {
                val row = index / BOARD_SIZE
                val col = index % BOARD_SIZE

                val state = if (isBlackTurn) {
                    State.BLACK
                } else {
                    State.WHITE
                }
                val stoneImage = when (state) {
                    State.BLACK -> R.drawable.black_stone
                    State.WHITE -> R.drawable.white_stone
                    State.EMPTY -> null
                }

                if (omokGame.successTurn(Stone(row, col), state)) {
                    stoneImage?.let { view.setImageResource(it) }
                    omokDatabase.saveStone(index, state)
                    isBlackTurn = !isBlackTurn
                    omokGame.isVictory(state)
                }
            }
        }
    }

    private fun setUpBoard(omokUiBoard: List<ImageView>, stones: List<StoneEntity>): OmokBoard {

        val omokBoard = MutableList(OmokBoard.BOARD_SIZE) {
            MutableList(OmokBoard.BOARD_SIZE) {
                State.EMPTY
            }
        }

        stones.forEach { stone ->
            val index = stone.index
            val row = index / BOARD_SIZE
            val column = index % BOARD_SIZE

            if (stone.color == State.BLACK.name) {
                omokUiBoard[index].setImageResource(R.drawable.black_stone)
                omokBoard[row][column] = State.BLACK
            }
            if (stone.color == State.WHITE.name) {
                omokUiBoard[index].setImageResource(R.drawable.white_stone)
                omokBoard[row][column] = State.WHITE
            }
        }
        return OmokBoard(omokBoard)
    }

    private fun moveToGameOver(state: State) {
        val intent = Intent(this, GameOverActivity::class.java).apply {
            putExtra(OMOK_WINNER, state.name)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        omokDatabase.close()
    }

    companion object {
        private const val BOARD_SIZE = 15
        const val OMOK_WINNER = "OMOK_WINNER"
    }
}
