package woowacourse.omok.db

import android.content.Context
import woowacourse.omok.model.Color
import woowacourse.omok.model.Stone

class StoneConverterFromDB(context: Context) {
    private val stonesDao = StonesDao(context)

    fun loadStonesFromDb(): List<Stone> {
        val stoneEntities = stonesDao.getAllStones()
        return stoneEntities.map { StoneMapper.toStone(it) }
    }

    fun insertStoneToDb(
        color: Color,
        row: Int,
        col: Int,
        order: Int,
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

    fun clearAllStonesInDB() {
        stonesDao.deleteAllStones()
    }
}