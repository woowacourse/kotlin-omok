package woowacourse.omok.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omock.model.Position
import omock.model.board.Block
import omock.model.board.OmokBoard
import woowacourse.omok.R
import woowacourse.omok.common_ui.showToast
import woowacourse.omok.data.OmokRepository
import woowacourse.omok.db.GameRecordDao
import woowacourse.omok.db.OmokSQLiteHelper
import woowacourse.omok.model.android.BlockAndroidModel
import woowacourse.omok.presenter.OmokGamePresenter
import woowacourse.omok.presenter.toAndroid

class MainActivity : AppCompatActivity(), OmokGameAndroidView {
    private lateinit var boardView: List<List<ImageView>>
    private val helper: OmokSQLiteHelper = OmokSQLiteHelper(this)
    private lateinit var presenter: OmokGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBoardView()
        presenter =
            OmokGamePresenter(
                view = this,
                consoleView = OmokGameConsoleView(),
                repository = OmokRepository(GameRecordDao(helper)),
            )
        initResetButton()
    }

    override fun showGameStart(initialBoard: OmokBoard) {
        initBoardClickListener()
        initialBoard.blockRecords
            .blocks
            .map(Block::toAndroid)
            .forEach { blockUiModel ->
                val (x, y, state) = blockUiModel
                boardView[x][y].setImageResource(state.res)
            }
    }

    override fun showCurrentGameState(
        board: OmokBoard,
        block: BlockAndroidModel,
    ) {
        val (x, y, state) = block
        val imageView = boardView[x][y]
        imageView.setImageResource(state.res)
    }

    override fun showGameResult(
        board: OmokBoard,
        block: BlockAndroidModel,
    ) {
        val boardState = block.blockState
        findViewById<TextView>(R.id.tv_winner).text = "${boardState.format()} 승리!!"
        boardView.forEach { row ->
            row.forEach { imageView ->
                imageView.setOnClickListener(null)
            }
        }
    }

    override fun showPlaceError(errorMessage: String) = showToast(errorMessage)

    private fun initBoardView() {
        boardView =
            findViewById<TableLayout>(R.id.board)
                .children
                .filterIsInstance<TableRow>()
                .map { it.children.filterIsInstance<ImageView>().toList() }
                .toList()
    }

    private fun initBoardClickListener() {
        boardView.forEachIndexed { x, row ->
            row.forEachIndexed { y, imageView ->
                val position = Position(x + 1, y + 1)
                imageView.setOnClickListener {
                    presenter.placeStone(position)
                }
            }
        }
    }

    override fun resetView() {
        findViewById<TextView>(R.id.tv_winner).text = ""
        boardView.forEach { row ->
            row.forEach { imageView ->
                imageView.setImageResource(INITIAL_RESOURCE)
            }
        }
    }

    private fun initResetButton() {
        findViewById<Button>(R.id.button_reset).setOnClickListener { presenter.resetGame() }
    }

    companion object {
        private const val INITIAL_RESOURCE = 0
    }
}
