package woowacourse.omok.domain.game

import omok.domain.place.Place
import omok.domain.rule.OmokRules
import omok.event.GameEventListener
import woowacourse.omok.dao.LatestStoneDao
import woowacourse.omok.dao.OmokPlaceDao
import woowacourse.omok.dto.OmokGameDto
import woowacourse.omok.entity.LatestStoneEntity
import woowacourse.omok.global.retryOnFailedToAddStone

class OmokGame(
    private val omokGameDto: OmokGameDto,
    private val omokOmokPlaceDao: OmokPlaceDao,
    private val latestStoneDao: LatestStoneDao,
    private val event: GameEventListener,
    private val omokRules: OmokRules = omokGameDto.board.omokRules,
) {
    fun startGame(target: Place) {
        event.onBoardView(omokGameDto.board)
        event.onStoneChange(target, onClickAction)
    }

    private val onClickAction = { stone: Place ->
        val latestStoneEntity =
            LatestStoneEntity(
                GARBAGE_ID,
                omokGameDto.nickname,
                stone,
            )
        retryOnFailedToAddStone(event) {
            omokGameDto.board.addStone(stone)
            event.onBoardView(omokGameDto.board)
            omokOmokPlaceDao.insertBoard(latestStoneEntity)
            latestStoneDao.updateBoard(latestStoneEntity)
            when {
                isFinished(stone) -> event.onFinished(stone)
                !omokGameDto.board.isNotFull() -> event.onFinished(null)
                else -> startGame(stone.opponent())
            }
        }
    }

    private fun isFinished(target: Place): Boolean {
        return omokRules.isOmok(target, omokGameDto.board)
    }

    companion object {
        private const val GARBAGE_ID = 1
    }
}
