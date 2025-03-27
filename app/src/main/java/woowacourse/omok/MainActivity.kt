package woowacourse.omok

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import woowacourse.omok.controller.OmokAppController
import woowacourse.omok.database.DbHelper
import woowacourse.omok.database.OmokContract
import woowacourse.omok.model.Board
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.InvalidMoveResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.Stones

class MainActivity : AppCompatActivity() {
    private lateinit var omokAppController: OmokAppController
    private val dbHelper = DbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boardLayout = findViewById<TableLayout>(R.id.board)
        setBoardPoints(boardLayout)
        val initialStones = queryStones()
        omokAppController = OmokAppController(Board(initialStones))
        paintEntirePoints(boardLayout, queryStones())
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun setBoardPoints(boardLayout: TableLayout) =
        boardLayout
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.forEachIndexed { colIndex, imageView ->
                    imageView.tag = Point(rowIndex + 1, colIndex + 1)
                    imageView.setOnClickListener {
                        placeWithEndCheck(it.tag as Point, it as ImageView, boardLayout)
                    }
                }
            }

    private fun place(
        point: Point,
        view: ImageView,
    ) {
        val stone = omokAppController.stone(point)

        if (isFoulToRetry(stone) || isInvalidMoveToRetry(stone)) {
            return
        }
        omokAppController.place(stone)
        paintStone(stone, view)
        insertStone(stone)
    }

    private fun placeWithEndCheck(
        point: Point,
        view: ImageView,
        boardLayout: TableLayout,
    ) {
        if (isEnd(point)) {
            place(point, view)
            boardLayout
                .children
                .filterIsInstance<TableRow>()
                .forEach { tableRow ->
                    tableRow.forEach { imageView ->
                        imageView.isClickable = false
                    }
                }
            deleteStones()
            return
        }
        place(point, view)
    }

    private fun isEnd(point: Point): Boolean {
        val stone = omokAppController.stone(point)
        if (isInvalidMoveToStop(point)) {
            showToastMessage(InvalidMoveResult.FullBoard().message)
            return true
        }
        if (omokGameState(stone) != GameState.PLAYING) {
            showToastMessage(omokGameState(stone).toWinnerMessage())
            return true
        }
        return false
    }

    private fun paintStone(
        stone: Stone,
        view: ImageView,
    ) {
        when (stone.color) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun paintEntirePoints(
        boardLayout: TableLayout,
        stones: Stones,
    ) {
        val omokStones: Set<Stone> = stones.stones
        omokStones.forEach { stone ->
            val imageView = boardLayout.findViewWithTag<ImageView>(stone.point)
            val imageResourceId =
                when (stone.color) {
                    StoneColor.BLACK -> R.drawable.black_stone
                    StoneColor.WHITE -> R.drawable.white_stone
                }
            imageView.setImageResource(imageResourceId)
        }
    }

    private fun isFoulToRetry(stone: Stone): Boolean {
        val result = omokAppController.foulConditionResult(stone) ?: return false
        showToastMessage(result.message)
        return true
    }

    private fun isInvalidMoveToRetry(stone: Stone): Boolean {
        val result = omokAppController.invalidMoveResult(stone) ?: return false
        showToastMessage(result.message)
        return result != InvalidMoveResult.FullBoard()
    }

    private fun isInvalidMoveToStop(point: Point): Boolean {
        val stone = omokAppController.stone(point)
        val result = omokAppController.invalidMoveResult(stone) ?: return false
        return result == InvalidMoveResult.FullBoard()
    }

    private fun omokGameState(stone: Stone): GameState = omokAppController.gameState(stone)

    private fun insertStone(stone: Stone) {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        values.put("color", stone.color.name)
        values.put("row", stone.point.row)
        values.put("column", stone.point.col)

        db.insert(OmokContract.OmokStone.TABLE_NAME, null, values)
    }

    private fun queryStones(): Stones {
        val dbReader = dbHelper.readableDatabase

        val result = mutableListOf<Stone>()

        val cursor: Cursor =
            dbReader.rawQuery("SELECT * FROM ${OmokContract.OmokStone.TABLE_NAME}", arrayOf())
        with(cursor) {
            while (moveToNext()) {
                val color = StoneColor.valueOf(getString(getColumnIndexOrThrow(OmokContract.OmokStone.COLUMN_NAME_COLOR)))
                val row = getInt(getColumnIndexOrThrow(OmokContract.OmokStone.COLUMN_NAME_ROW))
                val col = getInt(getColumnIndexOrThrow(OmokContract.OmokStone.COLUMN_NAME_COLUMN))
                result.add(Stone(row, col, color))
            }
        }
        cursor.close()
        return Stones(result.toSet(), result.lastOrNull())
    }

    private fun deleteStones() {
        dbHelper.writableDatabase.delete(OmokContract.OmokStone.TABLE_NAME, null, null)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun GameState.toWinnerMessage(): String =
        when (this) {
            GameState.WHITE_OMOK -> "백돌의 승리입니다!"
            GameState.BLACK_OMOK -> "흑돌의 승리입니다!"
            GameState.PLAYING -> ""
        }
}
