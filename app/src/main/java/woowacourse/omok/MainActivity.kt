package woowacourse.omok

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.omok.controller.OmokController
import woowacourse.omok.db.StoneEntity
import woowacourse.omok.db.StoneMapper
import woowacourse.omok.db.StonesDao
import woowacourse.omok.model.Color
import woowacourse.omok.model.GameEventListener

class MainActivity : AppCompatActivity(), GameEventListener {
    private lateinit var stonesDao: StonesDao
    private lateinit var boardLayout: TableLayout
    private lateinit var omokController: OmokController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stonesDao = StonesDao(this)
        boardLayout = findViewById(R.id.board)
        omokController = OmokController(this)
        loadStonesFromDb()

        setOnBoardTouch()
        omokController.printStart()

        val restartButton: Button = findViewById(R.id.restartButton)
        restartButton.setOnClickListener {
            omokController.restartGame()
            resetBoardView()
            clearDb()
        }

        val exitButton: Button = findViewById(R.id.exitButton)
        exitButton.setOnClickListener {
            moveTaskToBack(true)
        }
    }

    override fun onForbiddenStone() {
        runOnUiThread {
            viewToastMessage(FORBIDDEN_STONE_MESSAGE, SHORT_DURATION)
        }
    }

    override fun onGameEnd(winner: Color) {
        runOnUiThread {
            viewToastMessage(WINNER_MESSAGE.format(getPlayerColor(winner)), LONG_DURATION)
        }
        clearDb()
    }

    private fun loadStonesFromDb() = CoroutineScope(Dispatchers.IO).launch {
        val stoneEntities = stonesDao.getAllStones()
        val stones = stoneEntities.map { StoneMapper.toStone(it) }
        omokController.setStonesOnBoard(stones)
        withContext(Dispatchers.Main) {
            updateUI(stoneEntities)
        }
    }

    private fun setOnBoardTouch() {
        boardLayout
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    if (!omokController.gameEnded) {
                        handleStonePlacement(view)
                    }
                }
            }
    }

    private fun insertStoneToDb(
        color: Color,
        row: Int,
        col: Int,
        order: Int,
    ) {
        val stoneEntity =
            StoneEntity(
                color = color.name.lowercase(),
                row = row,
                column = col,
                order = order,
            )
        stonesDao.insertStone(stoneEntity)
    }

    private fun clearDb() {
        stonesDao.deleteAllStones()
    }

    private fun updateUI(stoneEntities: List<StoneEntity>) {
        stoneEntities.forEach { stoneEntity ->
            val rowIndex = stoneEntity.row - INDEX_ADJUSTMENT
            val columnIndex = stoneEntity.column - INDEX_ADJUSTMENT

            val row = boardLayout.getChildAt(rowIndex) as? TableRow
            val cell = row?.getChildAt(columnIndex) as? ImageView

            cell?.let {
                val color = Color.valueOf(stoneEntity.color.uppercase())
                viewStone(color, it)
            }
        }
    }

    private fun handleStonePlacement(clickedView: ImageView) {
        val tableRow = clickedView.parent as? ViewGroup
        val col = tableRow?.indexOfChild(clickedView)
        val row = tableRow?.let { tblRow -> boardLayout.indexOfChild(tblRow) }

        if (col != null && row != null) {
            tryPlaceStone(row + INDEX_ADJUSTMENT, col + INDEX_ADJUSTMENT, clickedView)
        }
    }

    private fun tryPlaceStone(
        row: Int,
        col: Int,
        clickedView: ImageView,
    ) {
        val color = omokController.getNextTurn()
        if (omokController.placeStoneAtPosition(row, col)) {
            placeStone(color, row, col, clickedView)
        }
    }

    private fun placeStone(
        color: Color,
        row: Int,
        col: Int,
        clickedView: ImageView,
    ) {
        val order = omokController.getStoneOrder()
        if (!omokController.gameEnded) {
            insertStoneToDb(color, row, col, order)
        }
        viewStone(color, clickedView)
    }

    private fun viewStone(
        color: Color,
        view: ImageView,
    ) {
        when (color) {
            Color.BLACK -> view.setImageResource(R.drawable.black_stone)
            Color.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun resetBoardView() {
        for (r in MIN_INDEX until boardLayout.childCount) {
            val row = boardLayout.getChildAt(r) as TableRow
            for (c in MIN_INDEX until row.childCount) {
                val cell = row.getChildAt(c) as ImageView
                cell.setImageDrawable(null)
            }
        }
    }

    private fun viewToastMessage(
        message: String?,
        duration: Int,
    ) {
        Toast.makeText(this, message, duration).show()
    }

    private fun getPlayerColor(color: Color): String {
        return when (color) {
            Color.BLACK -> PLAYER_COLOR_BLACK
            Color.WHITE -> PLAYER_COLOR_WHITE
        }
    }

    companion object {
        const val INDEX_ADJUSTMENT = 1
        const val MIN_INDEX = 0
        const val FORBIDDEN_STONE_MESSAGE = "이 위치는 금수입니다!"
        const val WINNER_MESSAGE = "게임 종료! 우승자는 %s 입니다!"
        const val PLAYER_COLOR_BLACK = "흑"
        const val PLAYER_COLOR_WHITE = "백"
        const val SHORT_DURATION = 3
        const val LONG_DURATION = 8
    }
}
