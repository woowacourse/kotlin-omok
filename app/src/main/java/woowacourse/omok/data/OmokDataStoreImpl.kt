package woowacourse.omok.data

import android.content.Context
import woowacourse.omok.data.adapter.OmokEntityAdapter
import woowacourse.omok.data.adapter.StonePosition
import woowacourse.omok.data.`interface`.Dao
import woowacourse.omok.data.`interface`.OmokDataStore
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

class OmokDataStoreImpl(
    context: Context,
    private val omokDao: Dao<OmokEntity> = OmokDao(context)
): OmokDataStore {
    private val omoks = mutableListOf<OmokEntity>()

    override fun add(position: Position, stone: Stone) {
        omoks.add(OmokEntityAdapter.omokEntity(position, stone))
    }

    override fun reset() {
        omoks.clear()
        omokDao.deleteAll()
    }

    override fun save(isFinish: Boolean) {
        if (isFinish) return
        omokDao.saveAll(omoks)
    }

    override fun lastStonePosition(): StonePosition? {
        val omokEntity = omokDao.findLastOrNull()
        return omokEntity?.run { OmokEntityAdapter.stonePosition(this) }
    }

    override fun omokEntities(): List<OmokEntity> = omokDao.findAll()
}