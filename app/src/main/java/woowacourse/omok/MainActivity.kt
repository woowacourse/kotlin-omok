package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import model.domain.OmokGame
import model.domain.OmokGame.Companion.BOARD_SIZE
import model.domain.state.BlackTurn
import model.domain.state.End
import model.domain.state.State
import model.domain.state.WhiteTurn
import model.domain.tools.Board
import model.domain.tools.Dot
import model.domain.tools.Location
import model.domain.tools.Stone
import woowacourse.omok.OmokViewUtil.getDot
import woowacourse.omok.OmokViewUtil.getStoneMessage
import woowacourse.omok.model.data.OmokDbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var state: State
    private lateinit var omokDbHelper: OmokDbHelper
    private val omokDbAdapter: OmokDbAdapter by lazy {
        OmokDbAdapter(OmokDbHelper(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runOmok()
    }

    private fun runOmok() {
        val omokGame = OmokGame()
        val board = Board.from(BOARD_SIZE)

        omokDbHelper = OmokDbHelper(this)
        setOmokTurn(board)
        omokDbAdapter.load(state.board)
        loadBoardView(omokGame)
    }

    private fun setOmokTurn(board: Board) {
        if (omokDbAdapter.nextStone() == Stone.BLACK) {
            state = BlackTurn(board)
            return
        }
        state = WhiteTurn(board)
    }

    private fun loadBoardView(omokGame: OmokGame): TableLayout? {

        return findViewById<TableLayout>(R.id.board).apply {
            children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEachIndexed { index, view ->
                    setClickListener(omokGame, index, view)
                    setStoneWithBoard(index, view)
                }
        }
    }

    private fun setClickListener(omokGame: OmokGame, index: Int, view: ImageView) {
        view.setOnClickListener {
            if (state is End) {
                endOmokGame()
                return@setOnClickListener
            }
            clickEvent(omokGame, index, view)
        }
    }

    private fun setStoneWithBoard(index: Int, view: ImageView) {
        val dot = Dot.from(index, BOARD_SIZE)
        val stoneColor = state.board.getStone(Location(dot.row, dot.col))
        setStoneImage(stoneColor, view)
    }

    private fun clickEvent(omokGame: OmokGame, index: Int, view: ImageView) {
        val nextState = omokGame.playNextTurn(state, getDot(index))

        putStone(nextState, index, view)
        state = nextState
    }

    private fun putStone(nextState: State, index: Int, view: ImageView) {
        if (state == nextState) {
            Toast.makeText(this, FORBIDDEN_MESSAGE, Toast.LENGTH_LONG)
                .show()
            return
        }
        setStoneImage(state.stone, view)
        omokDbAdapter.saveStone(index, state.stone)
    }

    private fun endOmokGame() {
        Toast.makeText(
            this,
            WINNER_MESSAGE.format(getStoneMessage(state.stone)),
            Toast.LENGTH_SHORT
        ).show()
        omokDbAdapter.deleteStones()
    }

    private fun setStoneImage(stone: Stone, view: ImageView) {
        when (stone) {
            Stone.BLACK -> view.setImageResource(R.drawable.black_stone)
            Stone.WHITE -> view.setImageResource(R.drawable.white_stone)
            Stone.EMPTY -> {}
        }
    }

    companion object {
        private const val WINNER_MESSAGE = "%s 이 승리했습니다!"
        private const val FORBIDDEN_MESSAGE = "돌을 놓을 수 없습니다."
    }
}
