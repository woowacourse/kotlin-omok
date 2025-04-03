package woowacourse.omok.domain.repository

import woowacourse.omok.domain.model.Game

interface GameRepository {
    fun insert(name: String)

    fun getAll(): List<Game>

    fun delete(id: Long)
}
