package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.OmokGame
import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone
import woowacourse.omok.adapter.OmokBoardAdapter
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao
import woowacourse.omok.utils.HandlingExceptionUtils

class MainActivity : AppCompatActivity() {
    private var stone: GoStone = BlackStone()
    private val omokGame = OmokGame(BlackStone(), WhiteStone())
    private val dao = OmokEntryDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val positions =
            board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

        GameResume.restoreProgressGameData(positions, dao)

        positions.forEachIndexed { index, view ->
            clickToPlaceStone(view, index, positions)
        }
    }

    private fun clickToPlaceStone(
        view: ImageView,
        index: Int,
        positions: List<ImageView>,
    ) {
        view.setOnClickListener {
            val position = putStone(index, view)
            val isOmok = stone.findOmok(position)
            if (isOmok) {
                endGame(positions, view)
            }
            stone = omokGame.changeStone(stone.stoneType)
        }
    }

    private fun putStone(
        index: Int,
        view: ImageView,
    ): Position {
        val position = OmokBoardAdapter.convertIndexToPosition(index)
        HandlingExceptionUtils.retryUntilSuccess(view) {
            stone.putStone(position)
            val entry = OmokEntry(stone.stoneType.type, index)
            dao.save(entry)
            view.setImageResource(OmokBoardAdapter.convertStoneTypeToDrawable(stone.stoneType))
        }
        return position
    }

    private fun endGame(
        positions: List<ImageView>,
        view: ImageView,
    ) {
        setBoardClickable(positions, isClickable = false)
        showWinner(view, positions)
    }

    private fun setBoardClickable(
        positions: List<ImageView>,
        isClickable: Boolean,
    ) {
        positions.forEach { position ->
            position.isClickable = isClickable
        }
    }

    private fun showWinner(
        view: ImageView,
        positions: List<ImageView>,
    ) {
        val snackBar = Snackbar.make(view, "${stone.stoneType.type}의 승리입니다 🏆", Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("다시 시작") {
            snackBar.dismiss()
            restartGame(positions)
        }
        snackBar.show()
    }

    private fun restartGame(positions: List<ImageView>) {
        dao.drop()
        Board.reset()
        positions.forEach { stone ->
            stone.setImageResource(0)
        }
        stone = BlackStone()
        setBoardClickable(positions, isClickable = true)
    }
}
