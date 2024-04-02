package woowacourse.omok.presentation.ui

import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.model.Column
import omok.model.OMokBoard
import omok.model.OMokGame
import omok.model.Row
import omok.model.ValidCoordinatesListener
import woowacourse.omok.R
import woowacourse.omok.local.db.OmokDao
import woowacourse.omok.local.repository.OmokRepositoryImpl
import woowacourse.omok.presentation.base.OmokGameActivity
import woowacourse.omok.presentation.model.Omok
import woowacourse.omok.presentation.toStoneIconRes

class MainActivity : OmokGameActivity(R.layout.activity_main) {
    override lateinit var dao: OmokDao
    override var oMokGame = OMokGame()
    private val viewModel: MainViewModel by lazy { MainViewModel(OmokRepositoryImpl(dao)) }

    override fun initStartView() {
        initOmok()
        resetOmokClickListener()
        setupBoardClickListener()
    }

    override fun initOmok() {
        val board = findViewById<TableLayout>(R.id.board)
        val size = board.children.toMutableList().size

        when (val state = viewModel.selectOmok()) {
            is UiState.Success -> handleSelectedOmok(state, board, size)
            is UiState.Failure -> showSnackbar(board, state.error)
        }
    }

    private fun handleSelectedOmok(
        state: UiState.Success,
        board: TableLayout,
        size: Int,
    ) {
        when (state) {
            is UiState.Success.Loaded -> {
                state.data.forEach { omok ->
                    val row = Row(omok.rowComma)
                    val column = Column(omok.columnComma)
                    val view =
                        board.children.filterIsInstance<TableRow>().flatMap { it.children }
                            .filterIsInstance<ImageView>()
                            .toList()[((row.toIntIndex() - 1) * size) + column.getIndex()]
                    val turn = oMokGame.getTurn()

                    turn.toStoneIconRes()?.let { stoneIconRes ->
                        if (oMokGame.executeTurn(row, column)) {
                            view.setImageResource(stoneIconRes)
                        }
                    }
                }
            }

            is UiState.Success.Empty -> Unit
        }
    }

    override fun resetOmokClickListener() {
        val resetBtn = findViewById<Button>(R.id.btn_reset)
        resetBtn.setOnClickListener {
            when (val state = viewModel.deleteAllOmok()) {
                is UiState.Success -> resetOmokView()
                is UiState.Failure -> showSnackbar(resetBtn, state.error)
            }
        }
    }

    private fun resetOmokView() {
        oMokGame = OMokGame()

        val board = findViewById<TableLayout>(R.id.board)
        board.children.filterIsInstance<TableRow>().flatMap { it.children }
            .filterIsInstance<ImageView>().forEach { view ->
                view.setImageResource(0)
            }

        OMokBoard.resetBoard()
    }

    override fun setupBoardClickListener() {
        val board = findViewById<TableLayout>(R.id.board)
        val size = board.children.toMutableList().size

        board.children.filterIsInstance<TableRow>().flatMap { it.children }
            .filterIsInstance<ImageView>().forEachIndexed { idx, view ->
                view.setOnClickListener {
                    handleStonePlacement(idx, size, view)
                }
            }
    }

    private fun handleStonePlacement(
        idx: Int,
        size: Int,
        view: ImageView,
    ) {
        val turn = oMokGame.getTurn()
        oMokGame.executeValidCoordinates(
            idx,
            size,
            object : ValidCoordinatesListener {
                override fun onValidCoordinates(
                    rowComma: String,
                    columnComma: String,
                ) {
                    turn.toStoneIconRes()?.let { stoneIconRes ->
                        view.setImageResource(stoneIconRes)
                        val omok = Omok(rowComma = rowComma, columnComma = columnComma)

                        when (val state = viewModel.insertOmok(omok)) {
                            is UiState.Success -> Unit
                            is UiState.Failure -> showSnackbar(view, state.error)
                        }
                    }
                }

                override fun onInvalidCoordinates() {
                    showSnackbar(view, getString(R.string.omok_placement_invalid))
                }

                override fun onGameEnded() {
                    showSnackbar(view, getString(R.string.success_omock))
                }
            },
        )
    }
}
