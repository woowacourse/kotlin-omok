package woowacourse.omok.presentation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import model.domain.tools.Stone.BLACK
import model.domain.tools.Stone.EMPTY
import model.domain.tools.Stone.WHITE
import view.GuideView
import woowacourse.omok.R
import woowacourse.omok.model.domain.StoneWithStateInBoard
import woowacourse.omok.model.domain.StoneWithStateInBoard.PlaceState.ABLE
import woowacourse.omok.model.domain.StoneWithStateInBoard.PlaceState.DISABLE
import woowacourse.omok.model.domain.StoneWithStateInBoard.PlaceState.OMOK
import woowacourse.omok.util.shortToastWithInt
import woowacourse.omok.util.shortToastWithString

class MainActivity : AppCompatActivity() {
    private val stoneWithStateInBoard: StoneWithStateInBoard by lazy { StoneWithStateInBoard() }
    private val board: Sequence<ImageView> by lazy { createBoard() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GuideView.printStart()
        setClickEventOnBoard()
    }

    private fun setClickEventOnBoard() {
        board.forEachIndexed { number, view ->
            view.setOnClickListener {
                stoneWithStateInBoard.placeStone(number)
                observePlaceState(view)
            }
        }
    }

    private fun observePlaceState(view: ImageView) {
        when (stoneWithStateInBoard.placeState) {
            OMOK -> setOmokState(view)
            DISABLE -> setDisabledState()
            ABLE -> setStoneImageByColor(view)
        }
    }

    private fun setDisabledState() = shortToastWithInt(R.string.tv_main_warning)

    private fun setOmokState(view: ImageView) {
        setStoneImageByColor(view)
        shortToastWithString(
            stoneWithStateInBoard.stoneColor.name +
                getString(R.string.tv_main_omok),
        )
    }

    private fun setStoneImageByColor(view: ImageView) =
        when (stoneWithStateInBoard.stoneColor) {
            BLACK -> view.setImageResource(R.drawable.blackstone)
            WHITE -> view.setImageResource(R.drawable.whitestone)
            EMPTY -> throw IllegalStateException(IDLE)
        }

    private fun createBoard() = findViewById<TableLayout>(R.id.board).children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>()

    companion object {
        private const val IDLE = "IDLE"
    }
}
