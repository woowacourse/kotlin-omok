package woowacourse.omok.db

import woowacourse.omok.model.Color
import woowacourse.omok.model.Stone

object StoneConverter {
    fun loadStonesFromDb(stonesDao: StonesDao): List<Stone> {
        val stoneEntities = stonesDao.getAllStones()
        return stoneEntities.map { StoneMapper.toStone(it) }
    }

    fun insertStoneToDb(
        color: Color,
        row: Int,
        col: Int,
        order: Int,
        stonesDao: StonesDao,
    ) {
        val stoneEntity =
            StoneEntity(
                color = color.name.lowercase(),
                row = row,
                column = col,
                order = order,
            )
        stonesDao.insertStone(stoneEntity)
    }
}
