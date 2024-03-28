package woowacourse.omok

import android.os.Bundle
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
    private val backingBoard: Board by lazy { Board() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playUntilFinish()
    }

    private fun playUntilFinish() {
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
            explainMessage.text = backingBoard.currentTurn.label + "의 차례입니다"
            imageView.isClickable = false
            runCatching {
                val eachPlacedPosition = Position.of(rowIndex + 1, colIndex.toChar() + 'A'.code)
                backingBoard.place(eachPlacedPosition)
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
        }
    }

    private fun disableBoardClickListener() {
        boardView.children.filterIsInstance<TableRow>().forEach { tableRow ->
            tableRow.children.filterIsInstance<ImageView>().forEach { imageView ->
                imageView.isClickable = false
            }
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
}
