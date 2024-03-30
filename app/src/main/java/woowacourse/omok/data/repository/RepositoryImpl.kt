package woowacourse.omok.data.repository

import android.content.Context
import woowacourse.omok.Coordinate
import woowacourse.omok.data.local.LocalDataSource
import woowacourse.omok.data.local.SQLiteDataSource
import woowacourse.omok.data.local.mapper.CoordinateMapper
import woowacourse.omok.data.local.mapper.OmokEntityMapper

class RepositoryImpl(private val context: Context, private val localDataSource: LocalDataSource = SQLiteDataSource(context)) :
    Repository {
    override fun save(coordinate: Coordinate): Coordinate {
        return localDataSource.save(
            OmokEntityMapper.map(
                coordinate = coordinate,
            ),
        ).run {
            CoordinateMapper.map(this)
        }
    }

    override fun findAll(): List<Coordinate> {
        return localDataSource.findAll()
            .map {
                CoordinateMapper.map(it)
            }.toList()
    }

    override fun drop() {
        localDataSource.drop()
    }
}
