package woowacourse.omok.domain.game

import omok.domain.board.OmokBoard
import omok.domain.place.Place
import omok.domain.rule.OmokRules
import omok.event.GameEventListener
import woowacourse.omok.dao.Dao
import woowacourse.omok.entity.OmokBoardEntity
import woowacourse.omok.global.retryOnFailedToAddStone
import woowacourse.omok.view.ext.serialize

class OmokGame(
    private val omokBoard: OmokBoard,
    private val nickname: String,
    private val omokDao: Dao,
    private val event: GameEventListener,
    private val omokRules: OmokRules = omokBoard.omokRules,
) {
    fun startGame(target: Place) {
        event.onBoardView(omokBoard)
        event.onStoneChange(target, onClickAction)
        omokDao.updateBoard(
            OmokBoardEntity(
                nickname,
                omokBoard.serialize(),
            ),
        )
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
