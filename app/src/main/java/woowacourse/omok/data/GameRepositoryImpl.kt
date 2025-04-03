package woowacourse.omok.data

import woowacourse.omok.domain.model.Game
import woowacourse.omok.domain.repository.GameRepository

class GameRepositoryImpl(private val gameDao: GameDao) : GameRepository {
    override fun insert(name: String) {
        gameDao.insert(name)
    }

    override fun getAll(): List<Game> = gameDao.getAll().map { it.toGame() }

    override fun delete(id: Long) {
        gameDao.delete(id)
    }
}
