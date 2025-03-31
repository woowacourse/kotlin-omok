package woowacourse.omok.ui

import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import woowacourse.omok.MainActivity
import woowacourse.omok.R
import woowacourse.omok.model.Board
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.Stones

class OmokMainView(
    private val activity: MainActivity,
    private val boardTableLayout: TableLayout,
    private val onBoardPointClick: (Point) -> Unit,
    private val onResetButtonClick: () -> Unit,
) {
    private lateinit var turnTextView: TextView
    private lateinit var resetBtn: Button
    private var boardPointImageViews: Map<Point, ImageView> = mapOf()

    init {
        initBoardPointViews()
        initViews()
        setListeners()
    }

    private fun initBoardPointViews() {
        boardTableLayout
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.forEachIndexed { colIndex, imageView ->
                    imageView.tag = Point(rowIndex + 1, colIndex + 1)
                    boardPointImageViews += imageView.tag as Point to imageView as ImageView
                }
            }
    }

    private fun initViews() {
        turnTextView = activity.findViewById<TextView>(R.id.turnTextView)
        resetBtn = activity.findViewById<Button>(R.id.resetBtn)
    }

    internal fun paintStone(stone: Stone) {
        val view = boardPointImageViews[stone.point]
        when (stone.color) {
            StoneColor.BLACK -> view?.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view?.setImageResource(R.drawable.white_stone)
        }
    }

    internal fun paintEntirePoints(stones: Stones) {
        val omokStones: Set<Stone> = stones.stones
        omokStones.forEach { stone ->
            paintStone(stone)
        }
    }

    internal fun clearBoardImageViews() {
        boardPointImageViews.values.forEach {
            val point = it.tag as Point
            val imageResource: Int =
                when {
                    point == Point(1, 1) -> R.drawable.board_top_left
                    point == Point(1, 15) -> R.drawable.board_top_right
                    point == Point(15, 1) -> R.drawable.board_bottom_left
                    point == Point(15, 15) -> R.drawable.board_bottom_right

                    point.row == 1 -> R.drawable.board_top
                    point.row == 15 -> R.drawable.board_bottom
                    point.col == 1 -> R.drawable.board_left
                    point.col == 15 -> R.drawable.board_right

                    else -> R.drawable.board_center
                }
            it.setImageResource(imageResource)
        }
    }

    internal fun setBoardClickability(isClickable: Boolean) {
        boardPointImageViews.values.forEach {
            it.isClickable = isClickable
        }
    }

    internal fun setBoardPointClickListeners() {
        boardPointImageViews.values.forEach {
            it.setOnClickListener {
                onBoardPointClick(it.tag as Point)
            }
        }
    }

    internal fun setResetButtonClickListener() {
        resetBtn.setOnClickListener { onResetButtonClick() }
    }

    internal fun setTurnTextView(board: Board) {
        turnTextView.text =
            when (board.stones.currentStoneColor()) {
                StoneColor.BLACK -> activity.getString(R.string.message_show_black_turn)
                StoneColor.WHITE -> activity.getString(R.string.message_show_white_turn)
            }
    }

    private fun setListeners() {
        setBoardPointClickListeners()
        setResetButtonClickListener()
    }
}
