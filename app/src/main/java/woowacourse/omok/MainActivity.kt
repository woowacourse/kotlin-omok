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
import omok.model.stone.StoneType
import omok.model.stone.WhiteStone

class MainActivity : AppCompatActivity() {
    private var stone: GoStone = BlackStone()
    private val omokGame = OmokGame(BlackStone(), WhiteStone())

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

        positions.forEachIndexed { index, view ->
            view.setOnClickListener {
                val position = putStone(index, view)
                val isOmok = stone.findOmok(position)
                if (isOmok) {
                    endGame(positions, view)
                }
                stone = omokGame.changeStone(stone.stoneType)
            }
        }
    }

    private fun putStone(
        index: Int,
        view: ImageView,
    ): Position {
        val position = PositionAdapter.convertIndexToPosition(index)
        retryUntilSuccess(view) {
            stone.putStone(position)
            view.setImageResource(getStoneImage(stone))
        }
        return position
    }

    private fun getStoneImage(stone: GoStone): Int {
        return when (stone.stoneType) {
            StoneType.BLACK_STONE -> R.drawable.black_stone
            StoneType.WHITE_STONE -> R.drawable.white_stone
            StoneType.NONE -> 0
        }
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
        Board.reset()
        positions.forEach { stone ->
            stone.setImageResource(0)
        }
        stone = BlackStone()
        setBoardClickable(positions, isClickable = true)
    }

    private fun <T> retryUntilSuccess(
        view: ImageView,
        action: () -> T,
    ): T? =
        runCatching {
            action()
        }.getOrElse {
            Snackbar.make(view, it.localizedMessage, Snackbar.LENGTH_LONG).show()
            return null
        }
}
