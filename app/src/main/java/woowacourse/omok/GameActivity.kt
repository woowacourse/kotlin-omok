package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.util.setOnSingleClickListener

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        setBoardOnclickListener(board)
    }

    private fun setBoardOnclickListener(board: TableLayout) {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                setCoordinateOnclickListener(
                    tableRow,
                    rowIndex
                )
            }
    }

    private fun setCoordinateOnclickListener(
        tableRow: TableRow,
        rowIndex: Int
    ) = tableRow.children
        .filterIsInstance<ImageView>()
        .forEachIndexed() { columIndex, imageView ->
            imageView.setOnSingleClickListener {
                coordinateClickListener(
                    imageView = imageView,
                    rowIndex = rowIndex,
                    columIndex = columIndex
                )
            }
        }

    private fun coordinateClickListener(imageView: ImageView, rowIndex: Int, columIndex: Int) {
    }
}
