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
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Position

class MainActivity : AppCompatActivity() {
    private val boardView: TableLayout by lazy { findViewById(R.id.board) }
    private lateinit var backingBoard: Board
    private val restartButton: Button by lazy { findViewById(R.id.restart_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playUntilFinish()
        restart()
    }

    private fun playUntilFinish() {
        backingBoard = Board()
        resetBoardView()
        val explainMessage = findViewById<TextView>(R.id.expalin_message)
        explainMessage.text = "흑의 차례입니다"
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
            imageView.isClickable = false
            runCatching {
                val eachPlacedPosition = Position.of(rowIndex + 1, colIndex.toChar() + 'A'.code)
                backingBoard.place(eachPlacedPosition)
                explainMessage.text = backingBoard.currentTurn.label + "의 차례입니다"
                displayOnAndroidBoard(backingBoard, imageView)
                finishGame(eachPlacedPosition, explainMessage)
            }.onFailure {
                explainMessage.text = it.message
            }
        }
    }

    private fun finishGame(
        eachPlacedPosition: Position,
        explainMessage: TextView,
    ) {
        if (backingBoard.getGameResult(eachPlacedPosition) != GameResult.PROCEEDING) {
            explainMessage.text = "${backingBoard.getGameResult(eachPlacedPosition).label}의 승리"
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

    private fun restart() {
        restartButton.setOnClickListener {
            restartButton.visibility = View.INVISIBLE
            playUntilFinish()
        }
    }

    private fun resetBoardView() {
        boardView.children.filterIsInstance<TableRow>().forEach { tableRow ->
            resetImageView(tableRow)
        }
    }

    private fun resetImageView(tableRow: TableRow) {
        tableRow.children.filterIsInstance<ImageView>().forEach { imageView ->
            imageView.setImageDrawable(null)
        }
    }
}
