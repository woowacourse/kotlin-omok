package woowacourse.omok.ui.main

import android.content.Context
import woowacourse.omok.db.omok.OmokDao
import woowacourse.omok.db.omok.OmokDaoHandler
import woowacourse.omok.db.omok.OmokDbHelper
import woowacourse.omok.domain.point.Point

class OmokDataManager(context: Context) {
    private val omokDaoHandler: OmokDaoHandler

    init {
        val dbHelper = OmokDbHelper(context)
        val dataSource = OmokDao(dbHelper)
        omokDaoHandler = OmokDaoHandler(dataSource)
    }

    fun saveStone(
        point: Point,
        roomId: Long,
    ) {
        omokDaoHandler.saveNewPoint(point, roomId)
    }

    fun loadSavedStones(roomId: Long): List<Point> {
        return omokDaoHandler.readAllPoint(roomId)
    }

    fun clearData() {
        omokDaoHandler.drop()
    }
}
