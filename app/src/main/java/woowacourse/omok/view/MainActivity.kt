package woowacourse.omok.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import omock.model.board.OmokBoard
import woowacourse.omok.R
import woowacourse.omok.common_ui.showToast
import woowacourse.omok.data.OmokRepository
import woowacourse.omok.db.GameRecordDao
import woowacourse.omok.db.OmokSQLiteHelper
import woowacourse.omok.model.android.BlockAndroidModel
import woowacourse.omok.presenter.OmokGamePresenter

class MainActivity : AppCompatActivity(), OmokGameAndroidView {
    private lateinit var boardView: BoardView
    private val helper: OmokSQLiteHelper = OmokSQLiteHelper(this)
    private lateinit var presenter: OmokGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boardView = BoardView(findViewById(R.id.board))
        presenter =
            OmokGamePresenter(
                androidView = this,
                consoleView = OmokGameConsoleView(),
                repository = OmokRepository(GameRecordDao(helper)),
            )
        initResetButton()
    }

    override fun showGameStart(blocks: List<BlockAndroidModel>) {
        boardView.setBoardClickListener { x, y ->
            presenter.placeStone(x, y)
        }
        blocks.forEach(boardView::updateBlock)
    }

    override fun showCurrentGameState(block: BlockAndroidModel) {
        boardView.updateBlock(block)
    }

    override fun showGameResult(
        board: OmokBoard,
        block: BlockAndroidModel,
    ) {
        val boardState = block.blockState
        findViewById<TextView>(R.id.tv_winner).text = "${boardState.format()} 승리!!"
        boardView.disableClickListener()
    }

    override fun showPlaceError(errorMessage: String) = showToast(errorMessage)

    override fun resetView() {
        findViewById<TextView>(R.id.tv_winner).text = ""
        boardView.reset()
    }

    private fun initResetButton() {
        findViewById<Button>(R.id.button_reset).setOnClickListener { presenter.resetGame() }
    }
}
