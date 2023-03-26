package woowacourse.omok.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.state.BlackTurn
import domain.state.End
import domain.state.State
import domain.state.WhiteTurn
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType
import woowacourse.omok.R
import woowacourse.omok.database.OmokDatabase
import woowacourse.omok.repository.OmokRepository

class MainActivity : AppCompatActivity() {

    private val omokRepository by lazy { OmokRepository(OmokDatabase.getDatabase(this)) }
    private val board by lazy { Board() }
    private var state: State = BlackTurn(board)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resetButton = findViewById<Button>(R.id.resetButton)

        if (!omokRepository.isEmpty()) {
            initState()
            setPreviousGameStone()
        }
        clickOmokBoard()

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initState() {
        omokRepository.getLast().use {
            while (it.moveToNext()) {
                val stoneColor = it.getInt(it.getColumnIndexOrThrow("stone_color"))
                when (stoneColor) {
                    0 -> state = WhiteTurn(board)
                    1 -> state = BlackTurn(board)
                }
            }
        }
    }

    private fun setPreviousGameStone() {
        omokRepository.getAll().use {
            while (it.moveToNext()) {
                val stoneColor = it.getInt(it.getColumnIndexOrThrow("stone_color"))
                val row = it.getInt(it.getColumnIndexOrThrow("x"))
                val col = it.getInt(it.getColumnIndexOrThrow("y"))
                val index = stonePositionToIndex(row, col)
                val view: ImageView = getOmokBoardView()[index]
                val stone = Stone(
                    StonePosition.from(row, col),
                    StoneType.getStoneType(stoneColor)!!,
                )
                state = state.put(stone)
                checkState(view, stone)
            }
        }
    }

    private fun stonePositionToIndex(row: Int, col: Int): Int = (col - 1) * BOARD_SIZE + row - 1

    private fun putStone(view: ImageView, color: StoneType) {
        when (color) {
            StoneType.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneType.WHITE -> view.setImageResource(R.drawable.white_stone)
            StoneType.EMPTY -> view.setImageResource(0)
        }
    }

    private fun getOmokBoardView(): List<ImageView> {
        return findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
    }

    private fun checkState(view: ImageView, stone: Stone) {
        when {
            state is End -> {
                putStone(view, stone.type)
                showWinner(stone.type)
            }
            isPutBlackStone(stone) || isPutWhiteStone(stone) -> {
                putStone(view, stone.type)
                insertStone(
                    stone.type,
                    stone.position,
                )
            }
            putBlackStoneFailed(stone) || putWhiteStoneFailed(stone) -> showAlertDialog()
        }
    }

    private fun clickOmokBoard() {
        getOmokBoardView().forEachIndexed { index, view ->
            view.setOnClickListener {
                val stone = Stone(
                    indexToStonePosition(index),
                    state.stoneColor,
                )
                state = state.put(stone)
                checkState(view, stone)
            }
        }
    }

    private fun indexToStonePosition(index: Int): StonePosition {
        return StonePosition.from((index % BOARD_SIZE) + 1, (index / BOARD_SIZE) + 1)
    }

    private fun showWinner(stoneType: StoneType) {
        val intent = Intent(this, WinnerActivity::class.java)
        intent.putExtra("winner", stoneType.number)
        startActivity(intent)
        finish()
        resetGame()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("")
            .setMessage("이 위치에 돌을 놓을 수 없습니다.")
            .setPositiveButton("ok", null)
            .create()
            .show()
    }

    private fun insertStone(type: StoneType, position: StonePosition) {
        val values = ContentValues()
        values.put("stone_color", type.number)
        values.put("x", position.x)
        values.put("y", position.y)
        omokRepository.insert(values)
    }

    private fun resetGame() {
        if (!omokRepository.isEmpty()) {
            getOmokBoardView().forEach { it.setImageResource(0) }
            omokRepository.reset()
        }
        state = BlackTurn(Board())
    }

    private fun isPutBlackStone(stone: Stone): Boolean =
        stone.type == StoneType.BLACK && state !is BlackTurn

    private fun isPutWhiteStone(stone: Stone): Boolean =
        stone.type == StoneType.WHITE && state !is WhiteTurn

    private fun putBlackStoneFailed(stone: Stone): Boolean =
        stone.type == StoneType.BLACK && state is BlackTurn

    private fun putWhiteStoneFailed(stone: Stone): Boolean =
        stone.type == StoneType.WHITE && state is WhiteTurn

    override fun onDestroy() {
        super.onDestroy()
        omokRepository.close()
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
