package woowacourse.omok.data

import android.content.Context
import woowacourse.omok.data.adapter.OmokEntityAdapter
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

class OmokEntities(
    context: Context,
    private val omokDao: OmokDao = OmokDaoImpl(context)
) {
    private val omoks = mutableListOf<OmokEntity>()

    fun add(position: Position, stone: Stone) {
        omoks.add(OmokEntityAdapter.omokEntity(position, stone))
    }

    fun reset() {
        omoks.clear()
    }

    fun save(isFinish: Boolean) {
        if (isFinish) return
        omokDao.saveAll(omoks)
    }
}