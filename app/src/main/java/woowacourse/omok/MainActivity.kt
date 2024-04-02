package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.result.ResultHandler
import omok.model.result.ResultHandler.isAvailableResult
import omok.model.result.ResultHandler.isOmok
import omok.model.stone.BlackStone
import omok.model.stone.BlackStone.value
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone
import omok.model.stone.WhiteStone.changeStone
import woowacourse.omok.game.OmokDataInitializer.resetGameData
import woowacourse.omok.game.OmokRestoreData.restoreGameData
import woowacourse.omok.omokdb.OmokDao
import woowacourse.omok.omokdb.OmokEntry

class MainActivity : AppCompatActivity() {
    private var stone: GoStone = BlackStone
    private val dao = OmokDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        restoreGameData(dao, board) { imageView(it) }
        startOmokGame(board)
    }

    override fun onDestroy() {
        super.onDestroy()
        val board = findViewById<TableLayout>(R.id.board)
        resetGameData(dao, board)
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
        val putResult = stone.putStone(position)

        if (isAvailableResult(putResult)) {
            dao.insert(
                OmokEntry(
                    position.getRowValue().toString(),
                    position.getColumnValue(),
                    stone.value(),
                ),
            )
            view.setImageResource(imageView(stone))
            checkOmok(putResult, view, board)
            return
        }
        val message = ResultHandler.handleResult(putResult, stone)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun checkOmok(
        putResult: PutResult,
        view: ImageView,
        board: TableLayout,
    ) {
        stone =
            when {
                isOmok(putResult) -> {
                    val message = ResultHandler.handleResult(putResult, stone)
                    showWinnerMessage(view, board, message)
                    BlackStone
                }

                else -> stone.changeStone()
            }
    }

    private fun imageView(stone: GoStone) =
        when (stone) {
            BlackStone -> R.drawable.black_stone
            WhiteStone -> R.drawable.white_stone
        }

    private fun showWinnerMessage(
        view: ImageView,
        board: TableLayout,
        message: String,
    ) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(CONFIRM_BUTTON_MESSAGE) {
            resetGameData(dao, board)
        }
        snackBar.show()
    }

    companion object {
        private const val CONFIRM_BUTTON_MESSAGE = "확인"
    }
}
