package woowacourse.omok.data

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import woowacourse.omok.db.GameRecordDao
import woowacourse.omok.db.GameTurnEntity

class OmokRepository(private val dao: GameRecordDao) {
    fun saveGameRecord(stones: List<OmokStone>) {
        val gameTurns = stones.map { it.toGameTurnEntity() }
        dao.saveGameRecord(gameTurns)
    }

    fun selectAll(): List<OmokStone> {
        return dao.selectAll().map { it.toOmokStone() }
    }

    fun resetTable() {
        dao.resetTable()
    }

    private fun GameTurnEntity.toOmokStone(): OmokStone {
        val (id, x, y, color) = this
        return OmokStone(Position.of(x, y), StoneColor.valueOf(color))
    }

    private fun OmokStone.toGameTurnEntity(): GameTurnEntity {
        val (position, color) = this
        val (x, y) = position.x to position.y
        return GameTurnEntity(NO_ID, x, y, color.name)
    }

    companion object {
        private const val NO_ID = -1L
    }
}
