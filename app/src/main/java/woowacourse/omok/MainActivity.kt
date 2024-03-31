package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.controller.OMockViewController
import woowacourse.omok.data.OmokDAO

class MainActivity : AppCompatActivity() {
    private lateinit var mainBoard: TableLayout
    private lateinit var resetButton : Button
    private val oMockViewController = OMockViewController(this@MainActivity)
    private val dao = OmokDAO(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
    }

    private fun initView(){
        mainBoard = findViewById(R.id.board)
        resetButton = findViewById(R.id.reset_button)
        loadBoard()
    }

    private fun initListener(){
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

    private fun resetBoard() {
        dao.resetAllCoordinates()
        oMockViewController.resetBoard(mainBoard)
    }

    private fun loadBoard() {
        dao.getAllCoordinates().forEach { (columnIndex, rowIndex) ->
            val row: TableRow = mainBoard.getChildAt(rowIndex) as TableRow
            val imageView: ImageView = row.getChildAt(columnIndex) as ImageView
            oMockViewController.setBoard(imageView, columnIndex to rowIndex)
        }
        oMockViewController.startGameBoard()
    }
}
