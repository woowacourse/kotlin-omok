package woowacourse.omok.domain.repository

import android.content.Context
import woowacourse.omok.data.datasource.OmokDataSource
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.data.repository.OmokRepositoryImpl
import woowacourse.omok.domain.point.Point

interface OmokRepository {
    fun saveNewPoint(newPoint: Point)

    fun readAllPoint(): List<Point>

    fun drop()

    companion object {
        fun create(context: Context): OmokRepository {
            val dbHelper = OmokDbHelper(context)
            val dataSource = OmokDataSource(dbHelper)
            return OmokRepositoryImpl(dataSource)
        }
    }
}
