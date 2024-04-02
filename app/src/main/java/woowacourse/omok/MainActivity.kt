package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResultUtils.handleHintMessage
import omok.model.result.PutResultUtils.isRunning
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.GoStone.Companion.change
import woowacourse.omok.adapter.OmokBoardAdapter
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao

class MainActivity : AppCompatActivity() {
    private lateinit var stone: GoStone
    private val dao = OmokEntryDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val positions = getPositions(board)

        val lastStone = GameResume.restoreProgressGameData(positions, dao)
        stone = lastStone.change()
        positions.forEachIndexed { index, view ->
            clickToPlaceStone(view, index, positions)
        }
    }

    private fun getPositions(board: TableLayout) =
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

    private fun clickToPlaceStone(
        view: ImageView,
        index: Int,
        positions: List<ImageView>,
    ) {
        view.setOnClickListener {
            val position = getPosition(index)
            val putResult = stone.putStone(position)
            if (isRunning(putResult).not()) {
                makeHintSnackBar(view, handleHintMessage(putResult))
                return@setOnClickListener
            }
            saveData(position)
            showStone(view)
            if (stone.findOmok(position)) {
                endGame(positions, view)
            }
            stone = stone.change()
        }
    }

    private fun getPosition(index: Int) = OmokBoardAdapter.convertIndexToPosition(index)

    private fun saveData(position: Position) {
        val entry = OmokEntry(stone.stoneType.type, position.row.value, position.column.value)
        dao.save(entry)
    }

    private fun showStone(view: ImageView) = view.setImageResource(OmokBoardAdapter.convertStoneTypeToDrawable(stone.stoneType))

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
        val snackBar =
            Snackbar.make(
                view,
                this.getString(R.string.winnier_message).format(stone.stoneType.type),
                Snackbar.LENGTH_INDEFINITE,
            )
        snackBar.setAction(R.string.game_restart) {
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

    private fun makeHintSnackBar(
        view: ImageView,
        message: String,
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
