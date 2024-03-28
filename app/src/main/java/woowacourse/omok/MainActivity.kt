package woowacourse.omok

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.BlackStone.value
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone
import omok.model.stone.WhiteStone.changeStone
import woowacourse.omok.omokdb.OmokDataInitializer.resetGameData
import woowacourse.omok.omokdb.OmokDbHelper
import woowacourse.omok.omokdb.OmokRestoreData.restoreGameData

class MainActivity : AppCompatActivity() {
    private var stone: GoStone = BlackStone
    private lateinit var dbHelper: OmokDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        dbHelper = OmokDbHelper(this)

        restoreGameData(dbHelper, board) { imageView(it) }
        startOmokGame(board)
    }

    override fun onDestroy() {
        super.onDestroy()
        val board = findViewById<TableLayout>(R.id.board)
        resetGameData(dbHelper, board)
    }

    private fun startOmokGame(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    handleStonePlacement(board, Position.fromIndex(index), view)
                }
            }
    }

    private fun handleStonePlacement(
        board: TableLayout,
        position: Position,
        view: ImageView,
    ) {
        val currentStone = detectRenjuRule(view) { stone.putStone(position) }
        currentStone?.let {
            dbHelper.insert(position.getRowValue(), position.getColumnValue(), stone.value())
            view.setImageResource(imageView(stone))
            if (checkOmok(board, position, view)) return
            stone = stone.changeStone()
        }
    }

    private fun <T> detectRenjuRule(
        view: View,
        action: () -> T,
    ): T? =
        runCatching {
            action()
        }.getOrElse {
            Snackbar.make(view, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
            return null
        }

    private fun imageView(stone: GoStone) =
        when (stone) {
            BlackStone -> R.drawable.black_stone
            WhiteStone -> R.drawable.white_stone
        }

    private fun checkOmok(
        board: TableLayout,
        stonePosition: Position,
        view: ImageView,
    ): Boolean {
        if (stone.findOmok(stonePosition)) {
            showWinnerMessage(view, board)
            stone = BlackStone
            return true
        }
        return false
    }

    private fun showWinnerMessage(
        view: ImageView,
        board: TableLayout,
    ) {
        val snackBar = Snackbar.make(view, "${stone.value()} 승리", Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(CONFIRM_BUTTON_MESSAGE) {
            resetGameData(dbHelper, board)
        }
        snackBar.show()
    }

    companion object {
        private const val CONFIRM_BUTTON_MESSAGE = "확인"
    }
}
