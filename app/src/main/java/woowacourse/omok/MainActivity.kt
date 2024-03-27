package woowacourse.omok

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone

class MainActivity : AppCompatActivity() {
    private var stone: GoStone = BlackStone
    private lateinit var dbHelper: OmokDbHelper
    lateinit var board: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById(R.id.board)
        dbHelper = OmokDbHelper(this)
        dbHelper.selectStonesInfo().forEach {
            recoverBoard(board, it.first, it.second)
        }
        startOmokGame(board)
    }

    override fun onDestroy() {
        super.onDestroy()
        restoreOriginalImage(board)
    }

    fun recoverBoard(
        board: TableLayout,
        stoneIndex: Int,
        stoneColor: String,
    ) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                if (index == stoneIndex) {
                    if (stoneColor == "흑") {
                        view.setImageResource(R.drawable.black_stone)
                    } else if (stoneColor == "백") {
                        view.setImageResource(R.drawable.white_stone)
                    }
                }
            }
        if (stoneColor == "흑") {
            BlackStone.putStone(indexAdapter(stoneIndex))
        } else if (stoneColor == "백") {
            WhiteStone.putStone(indexAdapter(stoneIndex))
        }
    }

    private fun startOmokGame(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    handleStonePlacement(board, index, view)
                }
            }
    }

    private fun handleStonePlacement(
        board: TableLayout,
        index: Int,
        view: ImageView,
    ) {
        val stonePosition = indexAdapter(index)
        val currentStone = detectRenjuRule(view) { stone.putStone(stonePosition) }
        currentStone?.let {
            dbHelper.insert(index, stone.value())
            view.setImageResource(stone.imageView())
            if (checkOmok(board, stonePosition, view)) return
            stone = currentStone
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

    private fun GoStone.imageView() =
        when (this) {
            BlackStone -> R.drawable.black_stone
            WhiteStone -> R.drawable.white_stone
        }

    private fun checkOmok(
        board: TableLayout,
        stonePosition: Position,
        view: ImageView,
    ): Boolean {
        if (stone.findOmok(stonePosition)) {
            val snackBar = Snackbar.make(view, "${stone.value()} 승리", Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(CONFIRM_BUTTON_MESSAGE) {
                restoreOriginalImage(board)
            }
            snackBar.show()
            return true
        }
        return false
    }

    private fun indexAdapter(index: Int): Position {
        val row = FIRST_COLUMN + (index % BOARD_SIZE)
        val column = BOARD_SIZE - (index / BOARD_SIZE)
        return Position.of(row, column)
    }

    private fun restoreOriginalImage(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setImageResource(RESET_IMAGE_ID)
            }
        Board.resetBoard()
        dbHelper.reset()
    }

    private fun GoStone.value() =
        when (this) {
            BlackStone -> BLACK_STONE_VALUE_MESSAGE
            WhiteStone -> WHITE_STONE_VALUE_MESSAGE
        }

    companion object {
        private const val RESET_IMAGE_ID = 0
        private const val BOARD_SIZE = 15
        private const val FIRST_COLUMN = 'A'
        private const val CONFIRM_BUTTON_MESSAGE = "확인"
        private const val BLACK_STONE_VALUE_MESSAGE = "흑"
        private const val WHITE_STONE_VALUE_MESSAGE = "백"
    }
}
