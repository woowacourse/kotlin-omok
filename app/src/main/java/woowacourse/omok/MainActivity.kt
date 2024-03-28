package woowacourse.omok

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.model.board.CoordsNumber
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.view.OutputView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private val outputView = OutputView()
    val omok = OmokGame(this.outputView)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val board = findViewById<TableLayout>(R.id.board)
        dbHelper = DatabaseHelper(this)
        loadGame()

        val button = findViewById<Button>(R.id.resetButton)
        button.setOnClickListener {
            resetBoard()
        }
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rows ->
            rows.children.filterIsInstance<ImageView>().forEachIndexed { columIndex, view ->
                view.setOnClickListener {
                    val forbiddenPositions =
                        omok.renjuGameRule.findForbiddenPositions(omok.currentStone)
                    if (omok.board.isRunning()) {
                        if (requestPlayerMove(
                                omok.currentStone,
                                CoordsNumber(rowIndex),
                                CoordsNumber(columIndex),
                                forbiddenPositions
                            )
                        ) {
                            updateUI()
                        }
                    }
                    saveGame()
                }
            }
        }
    }

    fun resetBoard() {
        omok.resetGame()
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM GameBoard")
        updateUI()
    }

    private fun requestPlayerMove(
        currentStone: Stone,
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        forbiddenPositions: List<Position>,
    ): Boolean {
        if (rowCoords != null && columnCoords != null &&
            !omok.board.isMoveForbidden(
                rowCoords,
                columnCoords,
                forbiddenPositions,
            ) && !omok.board.isNotEmpty(rowCoords, columnCoords)
        ) {
            omok.placeStone(Position(rowCoords, columnCoords), currentStone)
            outputView.printBoard(
                omok.board.gameBoard,
                forbiddenPositions
            )
            return true
        } else {
            outputView.printForbiddenMoveMessage()
            return false
        }
    }

    private fun saveGame() {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            db.delete("GameBoard", null, null) // 기존 데이터 삭제
            for (i in omok.board.gameBoard.indices) {
                for (j in omok.board.gameBoard[i].indices) {
                    val stoneType = when (omok.board.gameBoard[i][j]) {
                        Stone.EMPTY -> 0
                        Stone.BLACK -> 1
                        Stone.WHITE -> 2
                    }
                    val values = ContentValues().apply {
                        put("rowIndex", i)
                        put("columnIndex", j)
                        put("stoneType", stoneType)
                    }
                    db.insert("GameBoard", null, values)
                }
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    private fun loadGame() {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "GameBoard",
            arrayOf("rowIndex", "columnIndex", "stoneType"),
            null,
            null,
            null,
            null,
            null
        )
        omok.resetGame()
        with(cursor) {
            while (moveToNext()) {
                val rowIndex = getInt(getColumnIndexOrThrow("rowIndex"))
                val columnIndex = getInt(getColumnIndexOrThrow("columnIndex"))
                val stoneType = getInt(getColumnIndexOrThrow("stoneType"))
                val stone = when (stoneType) {
                    1 -> Stone.BLACK
                    2 -> Stone.WHITE
                    else -> Stone.EMPTY
                }
                if (stone == Stone.EMPTY) continue
                omok.placeStone(
                    Position(CoordsNumber(columnIndex), CoordsNumber(rowIndex)),
                    stone
                )
            }
            close()
        }
        updateUI()
    }


    private fun updateUI() {
        val board = findViewById<TableLayout>(R.id.board)
        val forbiddenPositions =
            omok.renjuGameRule.findForbiddenPositions(omok.currentStone)
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rows ->
            rows.children.filterIsInstance<ImageView>().forEachIndexed { columnIndex, view ->
                if (Position(
                        CoordsNumber(columnIndex),
                        CoordsNumber(rowIndex)
                    ) in forbiddenPositions
                ) {
                    view.setImageResource(R.drawable.x_mark)
                } else {
                    val stone = omok.board.gameBoard[columnIndex][rowIndex]
                    val resId = when (stone) {
                        Stone.BLACK -> R.drawable.black_stone
                        Stone.WHITE -> R.drawable.white_stone
                        else -> 0
                    }
                    if (resId != 0) view.setImageResource(resId)
                    else view.setImageDrawable(null)
                }
            }
        }
    }
}
