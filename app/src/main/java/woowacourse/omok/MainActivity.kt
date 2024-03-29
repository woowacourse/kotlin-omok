package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.controller.OMockGameController
import woowacourse.omok.data.OmokDAO
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.view.OutputView

class MainActivity : AppCompatActivity() {
    private val oMockGameController = OMockGameController(this@MainActivity)
    private val dao = OmokDAO(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainBoard = findViewById<TableLayout>(R.id.board)
        loadBoard(mainBoard)
        mainBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columIndex, view ->
                        view.setOnClickListener {
                            setBoard(view, columIndex to rowIndex)
                            dao.insertCoordinate(columIndex, rowIndex)
                        }
                    }
            }
    }

    private fun setBoard(
        view: ImageView,
        coordinate: Pair<Int, Int>,
    ) {
        val playerPick =
            Column.transformIndex(coordinate.first) to Row.transformIndex(coordinate.second)
        oMockGameController.setLastPickImage(view)
        OutputView.outputBoardForm()
        oMockGameController.processUserPick(playerPick)
    }

    private fun loadBoard(mainBoard : TableLayout) {
        dao.getAllCoordinates().forEach { (columnIndex, rowIndex) ->
            val row: TableRow = mainBoard.getChildAt(rowIndex) as TableRow
            val imageView: ImageView = row.getChildAt(columnIndex) as ImageView
            setBoard(imageView,columnIndex to rowIndex)
        }
        oMockGameController.startGameBoard()
    }
}
