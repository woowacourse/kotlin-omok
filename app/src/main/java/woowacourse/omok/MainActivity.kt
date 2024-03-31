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
import woowacourse.omok.FeedReaderContract.SQL_DELETE_ALL_ENTRIES
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Place
import woowacourse.omok.domain.omok.model.Position

class MainActivity : AppCompatActivity() {
    private val boardView: TableLayout by lazy { findViewById(R.id.board) }
    private lateinit var boardData: Board
    private val restartButton: Button by lazy { findViewById(R.id.restart_button) }
    private val dbHelper = FeedReaderDbHelper(this)
    private val db by lazy { dbHelper.writableDatabase }
    private val notationDao = NotationDao(this)
    private val explainMessage by lazy { findViewById<TextView>(R.id.expalin_message) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playUntilFinish()
        restartIfRestartButtonClicked()
    }

    private fun playUntilFinish() {
        val notation = notationDao.findAll()
        val initialBoardStatus = Array(Board.ARRAY_SIZE) { Array(Board.ARRAY_SIZE) { Color.NONE } }
        // val notation: MutableList<Stone> = mutableListOf()
        addPlacesToNotation(notation, initialBoardStatus)
        boardData = Board(notation, initialBoardStatus)
        setupBoardView()
        explainMessage.text = boardData.currentTurn.label + "의 차례입니다"
        boardView.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            setupRowImageViews(tableRow, rowIndex)
        }
    }

    private fun addPlacesToNotation(
        places: List<Place>,
        initialBoardStatus: Array<Array<Color>>,
    ) {
        places.forEach { place ->
            val color = place.color
            val rowCoordinate = place.rowCoordinate
            val colCoordinate = place.colCoordinate
            initialBoardStatus[rowCoordinate][colCoordinate] = Color.of(color)
        }
    }

    private fun setupBoardView() {
        boardView.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            setupRowOfBoardView(tableRow, rowIndex)
        }
    }

    private fun setupRowOfBoardView(
        tableRow: TableRow,
        rowIndex: Int,
    ) {
        tableRow.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, imageView ->
            val rowCoordinate = Board.ARRAY_SIZE - (rowIndex + 1)
            val colCoordinate = colIndex + 1
            setupImageView(rowCoordinate, colCoordinate, imageView)
        }
    }

    private fun setupImageView(
        rowCoordinate: Int,
        colCoordinate: Int,
        imageView: ImageView,
    ) {
        when (boardData.status[rowCoordinate][colCoordinate]) {
            Color.BLACK -> imageView.setImageResource(R.drawable.black_stone)
            Color.WHITE -> imageView.setImageResource(R.drawable.white_stone)
            Color.NONE -> imageView.setImageDrawable(null)
        }
    }

    private fun setupRowImageViews(
        tableRow: TableRow,
        rowIndex: Int,
    ) {
        tableRow.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, imageView ->
            setupImageViewClickListener(imageView, rowIndex, colIndex)
        }
    }

    private fun setupImageViewClickListener(
        imageView: ImageView,
        rowIndex: Int,
        colIndex: Int,
    ) {
        imageView.setOnClickListener {
            runCatching {
                val eachPlacedPosition = Position.of(rowIndex + 1, colIndex.toChar() + 'A'.code)
                boardData.place(eachPlacedPosition)
                recordInDb(rowIndex, colIndex)
                explainMessage.text = boardData.currentTurn.label + "의 차례입니다"
                displayOnBoardView(boardData, imageView)
                imageView.isClickable = false
                finishIfGameOver(eachPlacedPosition)
            }.onFailure {
                explainMessage.text = it.message
            }
        }
    }

    private fun recordInDb(
        rowIndex: Int,
        colIndex: Int,
    ) {
        val rowCoordinate = Board.ARRAY_SIZE - (rowIndex + 1)
        val colCoordinate = colIndex + 1
        val place = Place(boardData.lastTurn.label, rowCoordinate, colCoordinate)
        notationDao.save(place)
    }

    private fun finishIfGameOver(eachPlacedPosition: Position) {
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

    private fun displayOnBoardView(
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
            db.execSQL(SQL_DELETE_ALL_ENTRIES)
            playUntilFinish()
        }
    }
}
