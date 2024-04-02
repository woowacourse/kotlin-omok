package woowacourse.omok

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.controller.OMockViewController
import woowacourse.omok.data.OmokDAO
import woowacourse.omok.model.GameState

class MainActivity : AppCompatActivity() {
    private lateinit var mainBoard: TableLayout
    private lateinit var resetButton: Button
    private val oMockViewController = OMockViewController()
    private val dao = OmokDAO(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView(this@MainActivity)
        initListener(this@MainActivity)
    }

    private fun initView(context: Context) {
        mainBoard = findViewById(R.id.board)
        resetButton = findViewById(R.id.reset_button)
        loadBoard(context = context)
    }

    private fun initListener(context: Context) {
        mainBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columIndex, view ->
                        view.setOnClickListener {
                            val boardResultState =
                                oMockViewController.setBoard(view, columIndex to rowIndex)
                            setBoardFlow(boardResultState, context)
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

    private fun loadBoard(context: Context) {
        dao.getAllCoordinates().forEach { (columnIndex, rowIndex) ->
            val row: TableRow = mainBoard.getChildAt(rowIndex) as TableRow
            val imageView: ImageView = row.getChildAt(columnIndex) as ImageView
            val boardResultState = oMockViewController.setBoard(imageView, columnIndex to rowIndex)
            setBoardFlow(boardResultState, context)
        }
        oMockViewController.startGameBoard()
    }

    private fun setBoardFlow(
        boardResultState: GameState,
        context: Context,
    ) {
        println(boardResultState)
        when (boardResultState) {
            is GameState.Finish -> oMockViewController.finishedGameFlow(context = context)
            is GameState.LoadStone.Failure -> oMockViewController.showToastMessage(
                context,
                boardResultState.throwable
            )

            is GameState.LoadStoneState.Failure -> oMockViewController.showToastMessage(
                context,
                boardResultState.throwable
            )

            is GameState.CheckRuleTypeState.Failure -> oMockViewController.showToastMessage(
                context,
                boardResultState.throwable
            )
            else -> {}
        }
    }
}
