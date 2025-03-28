package woowacourse.omok.domain.game

import android.widget.TableLayout
import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Place
import omok.domain.place.White
import omok.domain.rule.OmokRules
import omok.event.GameEventListener
import woowacourse.omok.global.retryOnFailedToAddStone
import woowacourse.omok.view.ext.setOnClickListener

class OmokGame(
    val omokBoard: OmokBoard,
    val layout: TableLayout,
    val omokRules: OmokRules,
    val event: GameEventListener,
) {
    fun startGame(target: Place) {
        layout.setOnClickListener { x, y, view ->
            val stone = if (target is Black) Black(x, y) else White(x, y)
            onClickAction(stone)
        }
    }

    private val onClickAction = { stone: Place ->
        retryOnFailedToAddStone(event) {
            omokBoard.addStone(stone)
            event.onBoardView(omokBoard)
            when {
                isFinished(stone) -> event.onFinished(stone)
                !omokBoard.isNotFull() -> event.onFinished(null)
                else -> startGame(stone.opponent())
            }
        }
    }

    private fun isFinished(target: Place): Boolean {
        return omokRules.isOmok(target, omokBoard)
    }
}
