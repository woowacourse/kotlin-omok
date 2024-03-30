package woowacourse.omok.data.repository

import woowacourse.omok.Coordinate

interface Repository {
    fun save(coordinate: Coordinate): Coordinate

    fun findAll(): List<Coordinate>

    fun drop()
}
