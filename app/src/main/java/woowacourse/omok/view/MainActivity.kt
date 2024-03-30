package woowacourse.omok.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.controller.OMockViewController
import woowacourse.omok.data.OmokDAO

class MainActivity : AppCompatActivity() {
    private lateinit var mainBoard: TableLayout
    private val oMockViewController = OMockViewController(this@MainActivity)
    private val dao = OmokDAO(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBoard = findViewById(R.id.board)
        val resetButton = findViewById<Button>(R.id.reset_button)
        loadBoard()
        mainBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columIndex, view ->
                        view.setOnClickListener {
                            oMockViewController.setBoard(view, columIndex to rowIndex)
                            dao.insertCoordinate(columIndex, rowIndex)
                        }
                    }
            }
        resetButton.setOnClickListener {
            resetBoard()
        }
    }

    private fun resetBoard(){
        dao.resetAllCoordinates()
        oMockViewController.resetBoard(mainBoard)
    }

    private fun loadBoard() {
        dao.getAllCoordinates().forEach { (columnIndex, rowIndex) ->
            val row: TableRow = mainBoard.getChildAt(rowIndex) as TableRow
            val imageView: ImageView = row.getChildAt(columnIndex) as ImageView
            oMockViewController.setBoard(imageView,columnIndex to rowIndex)
        }
        oMockViewController.startGameBoard()
    }
}
