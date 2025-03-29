package woowacourse.omok.domain.game

import android.widget.TableLayout
import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Place
import omok.domain.place.White
import woowacourse.omok.entity.OmokBoardEntity
import woowacourse.omok.global.retryOnFailedToAddStone
import woowacourse.omok.ioc.Container
import woowacourse.omok.view.ext.serialize
import woowacourse.omok.view.ext.setOnClickListener

class OmokGame(
    private val omokBoard: OmokBoard,
    private val layout: TableLayout,
    private val nickname: String,
) {
    private val container = Container(layout)
    private val omokRules = container.omokRules
    private val omokDao = container.omokDao
    private val event = container.event

    fun startGame(target: Place) {
        event.onBoardView(omokBoard)
        layout.setOnClickListener { x, y, view ->
            val stone = if (target is Black) Black(x, y) else White(x, y)
            onClickAction(stone)
            omokDao.updateBoard(
                OmokBoardEntity(
                    nickname,
                    omokBoard.serialize(),
                ),
            )
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
