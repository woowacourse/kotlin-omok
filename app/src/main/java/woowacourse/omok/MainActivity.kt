package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.PutResult
import omok.model.OmokGame
import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone
import woowacourse.omok.adapter.OmokBoardAdapter
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao
import woowacourse.omok.utils.HandlingPutResultUtils

class MainActivity : AppCompatActivity() {
    private var stone: GoStone = BlackStone()
    private val omokGame = OmokGame(BlackStone(), WhiteStone())
    private val dao = OmokEntryDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val positions = getPositions(board)

        GameResume.restoreProgressGameData(positions, dao)
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
            if (checkForOccupiedOrForbidden(view, position)) return@setOnClickListener

            val stoneType = stone.putStone(position)
            saveData(index)
            showStone(view)
            if (stone.findOmok(position)) {
                endGame(positions, view)
            }
            stone = omokGame.changeStone(stoneType)
        }
    }

    private fun checkForOccupiedOrForbidden(
        view: ImageView,
        position: Position,
    ): Boolean {
        if (validatePosition(
                view,
                { position.validatePosition() },
                ::isFailure,
            )
        ) {
            return true
        }
        if (validatePosition(
                view,
                { position.checkForbidden() },
                ::isNotRunning,
            )
        ) {
            return true
        }
        return false
    }

    private fun validatePosition(
        view: ImageView,
        putResult: () -> PutResult,
        result: (PutResult) -> Boolean,
    ): Boolean {
        if (result(putResult())) {
            HandlingPutResultUtils.displayPutStatus(view, putResult(), this)
            return true
        }
        return false
    }

    private fun isFailure(putResult: PutResult) = putResult == PutResult.Failure

    private fun isNotRunning(putResult: PutResult) = putResult != PutResult.Running

    private fun getPosition(index: Int) = OmokBoardAdapter.convertIndexToPosition(index)

    private fun saveData(index: Int) {
        val entry = OmokEntry(stone.stoneType.type, index)
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
}
