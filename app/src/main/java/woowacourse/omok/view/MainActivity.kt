package woowacourse.omok.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omock.model.Failure
import omock.model.InvalidDuplicatedPlaced
import omock.model.InvalidFourFourRule
import omock.model.InvalidGameOver
import omock.model.InvalidOutOfBound
import omock.model.InvalidOverLineRule
import omock.model.InvalidThreeThreeRule
import omock.model.Position
import omock.model.board.Block
import omock.model.board.OmokBoard
import woowacourse.omok.R
import woowacourse.omok.common_ui.showToast
import woowacourse.omok.data.OmokRepository
import woowacourse.omok.db.GameRecordDao
import woowacourse.omok.db.OmokSQLiteHelper
import woowacourse.omok.model.BlockStateUiModel
import woowacourse.omok.presenter.OmokGamePresenter
import woowacourse.omok.presenter.toUiModel

class MainActivity : AppCompatActivity(), OmokGameView, PlaceErrorHandler {
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
                consoleView = ConsoleOmokGameView(),
                repository = OmokRepository(GameRecordDao(helper)),
                errorHandler = this,
            )
        initBoardClickListener()
        initResetButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }

    override fun showGameStart(initialBoard: OmokBoard) {
        initBoardClickListener()
        initialBoard.blockRecords
            .blocks
            .map(Block::toUiModel)
            .forEach { blockUiModel ->
                val (x, y, stoneColor) = blockUiModel
                boardView[x][y].setImageResource(stoneColor.resource())
            }
    }

    override fun showCurrentGameState(
        board: OmokBoard,
        stone: Block,
    ) {
        val block = stone.toUiModel()
        val state = block.blockState
        val (x, y) = stone.position
        val imageView = boardView[x - 1][y - 1]
        imageView.setImageResource(state.resource())
    }

    override fun showGameResult(
        board: OmokBoard,
        stone: Block,
    ) {
        val boardState = stone.toUiModel().blockState
        findViewById<TextView>(R.id.tv_winner).text = "$boardState 승리!!"
        boardView.forEach { row ->
            row.forEach { imageView ->
                imageView.setOnClickListener(null)
            }
        }
    }

    override fun showPlaceError(errorMessage: String) = showToast(errorMessage)

    override fun onError(placeFail: Failure): String =
        when (placeFail) {
            InvalidGameOver -> "게임이 이미 종료되었습니다."
            InvalidOverLineRule -> "장목 규칙을 위반하였습니다."
            InvalidDuplicatedPlaced -> "이미 돌이 놓여진 자리입니다."
            InvalidThreeThreeRule -> "33 규칙을 위반하였습니다."
            InvalidFourFourRule -> "44 규칙을 위반하였습니다."
            InvalidOutOfBound -> "바둑판을 벗어난 자리입니다."
        }

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

    private fun BlockStateUiModel.resource(): Int {
        return when (this) {
            BlockStateUiModel.BLACK -> R.drawable.black_stone
            BlockStateUiModel.WHITE -> R.drawable.white_stone
            BlockStateUiModel.EMPTY -> INITIAL_RESOURCE
        }
    }

    companion object {
        private const val INITIAL_RESOURCE = 0
    }
}
