package woowacourse.omok

import omok.model.OmokGameState
import woowacourse.omok.db.StoneDao

class OmokBoardDbHandler(
    private val stoneDao: StoneDao,
) : OmokStateListener {
    override fun listen(omokState: OmokGameState) {
        val lastStone = omokState.turn.board.previousStone()
        if (lastStone == null) {
            stoneDao.deleteAll()
            return
        }
        stoneDao.save(lastStone)
    }
}
