package woowacourse.omok

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.domain.OmokDbHelper
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Position
import woowacourse.omok.domain.omok.model.Stone

class MainActivity : AppCompatActivity() {
    private val boardView: TableLayout by lazy { findViewById(R.id.board) }
    private lateinit var boardData: Board
    private val restartButton: Button by lazy { findViewById(R.id.restart_button) }
    private val dbHelper = OmokDbHelper(this)
    private val db by lazy { dbHelper.writableDatabase }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playUntilFinish()
        restartIfRestartButtonClicked()
    }

    private fun playUntilFinish() {
        val cursor = db.rawQuery("select * from notation", null)
        val initialBoardStatus = Array(16) { Array(16) { Color.NONE } }
        val initialNotation: MutableList<Stone> = mutableListOf()
        while (cursor.moveToNext()) {
            initialBoardStatus[cursor.getInt(1)][cursor.getInt(2)] =
                Color.of(cursor.getString(0))
            when (cursor.getString(0)) {
                "흑" -> initialNotation.add(Stone.Black(Position.of(cursor.getInt(1), cursor.getInt(2).toChar() + 'A'.code)))
                "백" -> initialNotation.add(Stone.White(Position.of(cursor.getInt(1), cursor.getInt(2).toChar() + 'A'.code)))
            }
        }

        boardData = Board(initialNotation, initialBoardStatus)

        boardView.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            tableRow.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, imageView ->
                when (boardData.status[15 - rowIndex][colIndex + 1]) {
                    Color.BLACK -> imageView.setImageResource(R.drawable.black_stone)
                    Color.WHITE -> imageView.setImageResource(R.drawable.white_stone)
                    Color.NONE -> imageView.setImageDrawable(null)
                }
            }
        }

        val explainMessage = findViewById<TextView>(R.id.expalin_message)
        explainMessage.text = boardData.currentTurn.label + "의 차례입니다"
        boardView.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            setupRowImageViews(tableRow, rowIndex, explainMessage)
        }
    }

    private fun setupRowImageViews(
        tableRow: TableRow,
        rowIndex: Int,
        explainMessage: TextView,
    ) {
        tableRow.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, imageView ->
            setupImageViewClickListener(imageView, rowIndex, colIndex, explainMessage)
        }
    }

    private fun setupImageViewClickListener(
        imageView: ImageView,
        rowIndex: Int,
        colIndex: Int,
        explainMessage: TextView,
    ) {
        imageView.setOnClickListener {
            runCatching {
                val eachPlacedPosition = Position.of(rowIndex + 1, colIndex.toChar() + 'A'.code)
                boardData.place(eachPlacedPosition)
                recordInDb(rowIndex, colIndex)
                explainMessage.text = boardData.currentTurn.label + "의 차례입니다"
                displayOnAndroidBoard(boardData, imageView)
                imageView.isClickable = false
                finishIfGameOver(eachPlacedPosition, explainMessage)
            }.onFailure {
                explainMessage.text = it.message
            }
        }
    }

    private fun recordInDb(
        rowIndex: Int,
        colIndex: Int,
    ) {
        db.execSQL(
            """
                        insert into notation (color, rowCoordinate, colCoordinate)
                            values ('${boardData.lastTurn.label}', ${15 - rowIndex}, ${colIndex + 1})
                        """,
        )
    }

    private fun finishIfGameOver(
        eachPlacedPosition: Position,
        explainMessage: TextView,
    ) {
        if (boardData.getGameResult(eachPlacedPosition) != GameResult.PROCEEDING) {
            explainMessage.text = "${boardData.getGameResult(eachPlacedPosition).label}의 승리"
            disableBoardClickListener()
            restartButton.visibility = View.VISIBLE
        }
    }

    private fun disableBoardClickListener() {
        boardView.children.filterIsInstance<TableRow>().forEach { tableRow ->
            disableImageViewClickListener(tableRow)
        }
    }

    private fun disableImageViewClickListener(tableRow: TableRow) {
        tableRow.children.filterIsInstance<ImageView>().forEach { imageView ->
            imageView.isClickable = false
        }
    }

    private fun displayOnAndroidBoard(
        backingBoard: Board,
        imageView: ImageView,
    ) {
        when (backingBoard.lastTurn) {
            Color.BLACK -> imageView.setImageResource(R.drawable.black_stone)
            Color.WHITE -> imageView.setImageResource(R.drawable.white_stone)
            Color.NONE -> return
        }
    }

    private fun restartIfRestartButtonClicked() {
        restartButton.setOnClickListener {
            restartButton.visibility = View.INVISIBLE
            db.execSQL("delete from notation")
            playUntilFinish()
        }
    }
}
