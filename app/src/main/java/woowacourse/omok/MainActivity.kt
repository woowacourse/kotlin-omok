package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.controller.OMockGameController
import woowacourse.omok.data.OmokDAO

class MainActivity : AppCompatActivity() {
    private lateinit var mainBoard: TableLayout
    private lateinit var resetButton: TextView
    private val oMockGameController = OMockGameController(this@MainActivity)
    private val dao = OmokDAO(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView(){
        mainBoard = findViewById(R.id.board)
        resetButton = findViewById(R.id.reset_button)
        loadBoard(mainBoard)
        mainBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columIndex, view ->
                        view.setOnClickListener {
                            oMockGameController.setBoard(view, columIndex to rowIndex)
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
    }

    private fun loadBoard(mainBoard : TableLayout) {
        dao.getAllCoordinates().forEach { (columnIndex, rowIndex) ->
            val row: TableRow = mainBoard.getChildAt(rowIndex) as TableRow
            val imageView: ImageView = row.getChildAt(columnIndex) as ImageView
            oMockGameController.setBoard(imageView,columnIndex to rowIndex)
        }
        oMockGameController.startGameBoard()
    }
}
