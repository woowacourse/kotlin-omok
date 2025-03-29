package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.controller.OmokAppControl
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.database.OmokDBHelper
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row
import woowacourse.omok.view.OutputAppView
import kotlin.concurrent.thread
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: OmokDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        dbHelper = OmokDBHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boardSize = BoardSize(BOARD_SIZE)
        val outputAppView = OutputAppView(this)
        val omokAppControl = OmokAppControl(boardSize, outputAppView, dbHelper)

        val board = findViewById<TableLayout>(R.id.board)
        val positionViews: MutableMap<Position, ImageView> = mutableMapOf()
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, positionView ->
                val rowIndex = abs(MAX_BOARD_INDEX - (index / BOARD_SIZE))
                val colIndex = index % BOARD_SIZE
                positionViews[Position(Row(rowIndex), Col(colIndex))] = positionView
                positionView.setOnClickListener {
                    thread {
                        val coordinate = Pair(rowIndex, colIndex)
                        omokAppControl.turn(positionView, coordinate)
                    }
                }
            }
        omokAppControl.boardUiRestore(positionViews)

        val gameEndButton = findViewById<Button>(R.id.end_game_button)
        gameEndButton.setOnClickListener {
            outputAppView.gameEndDialogAlert { dbHelper.resetDatabase() }
        }
    }

    override fun onDestroy() {
        dbHelper.close()

        super.onDestroy()
    }

    companion object {
        private const val BOARD_SIZE = 15
        private const val INDEX_OFFSET = 1
        private const val MAX_BOARD_INDEX = BOARD_SIZE - INDEX_OFFSET
    }
}
