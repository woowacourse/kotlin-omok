package woowacourse.omok.activity

import android.content.ContentValues
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.game.*
import domain.point.Point
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.stone.StoneColor
import woowacourse.omok.R
import woowacourse.omok.database.OmokDatabase
import woowacourse.omok.database.repository.OmokRepository
import woowacourse.omok.database.repository.Repository
import woowacourse.omok.mapper.toPresentation
import woowacourse.omok.model.StoneColorModel
import woowacourse.omok.utils.createAlertDialog
import woowacourse.omok.utils.message
import woowacourse.omok.utils.negativeButton
import woowacourse.omok.utils.positiveButton

class MainActivity : AppCompatActivity() {
    private lateinit var board: TableLayout
    private lateinit var omok: Omok
    private lateinit var omokRepo: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokRepo = OmokRepository(OmokDatabase.getInstance(this))
        omok = Omok(BlackRenjuRule(), WhiteRenjuRule())
        board = findViewById(R.id.board)

        if (hasPreviousGameHistory()) {
            showRestartDialog()
        }
        setOmokClickListener()
        showMessage(getString(R.string.omok_start_message))
    }

    private fun continuePreviousGame() {
        omokRepo.getAll {
            while (it.moveToNext()) {
                val col = it.getInt(it.getColumnIndexOrThrow("x"))
                val row = it.getInt(it.getColumnIndexOrThrow("y"))
                val index = row * Omok.OMOK_BOARD_SIZE + col
                val omokIv: ImageView = boardImageViews()[index]
                val putResult = omok.put { Point(row, col) }
                processPutResult(putResult, omokIv)
            }
        }
    }

    private fun setOmokClickListener() {
        boardImageViews().forEachIndexed { index, view ->
            val row: Int = index / Omok.OMOK_BOARD_SIZE
            val col: Int = index % Omok.OMOK_BOARD_SIZE

            view.setOnClickListener {
                val putResult = omok.put { Point(row, col) }
                processPutResult(putResult, view)
            }
        }
    }

    private fun processPutResult(result: PutResult, view: ImageView) {
        when (result) {
            is PutSuccess -> {
                savePoint(result.stoneColor.toPresentation(), result.point)
                drawStoneOnBoard(view, result.stoneColor)
            }
            is PutFailed -> showMessage(getString(R.string.inplace_stone))
            is GameFinish -> {
                showWinner(view, result.lastStoneColor, result.winnerStoneColor.toPresentation())
                omokRepo.clear()
                finish()
            }
        }
    }

    private fun savePoint(stoneColor: StoneColorModel, point: Point) {
        val record = ContentValues().apply {
            put("stone_color", stoneColor.value)
            put("x", point.col)
            put("y", point.row)
        }
        omokRepo.insert(record)
    }

    private fun hasPreviousGameHistory(): Boolean = !omokRepo.isEmpty()

    private fun drawStoneOnBoard(view: ImageView, stoneColor: StoneColor) {
        when (stoneColor) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun showWinner(
        view: ImageView,
        lastStoneColor: StoneColor,
        winnerColor: StoneColorModel,
    ) {
        drawStoneOnBoard(view, lastStoneColor)
        showMessage("${winnerColor.text} 승리!")
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showRestartDialog(): Unit = createAlertDialog {
        message(getString(R.string.restart_game_ask_message))
        positiveButton(getString(R.string.yes)) { continuePreviousGame() }
        negativeButton(getString(R.string.no)) { omokRepo.clear() }
    }.show()

    private fun boardImageViews(): List<ImageView> = board.children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>()
        .toList()

    override fun onDestroy() {
        super.onDestroy()
        omokRepo.close()
    }
}
