package woowacourse.omok.domain.repository

import woowacourse.omok.data.datasource.OmokGameLocalDataSource
import woowacourse.omok.domain.mapper.toData
import woowacourse.omok.domain.mapper.toDomain
import woowacourse.omok.domain.model.game.OmokGameEntity

class OmokGameRepositoryImpl(
    private val localDataSource: OmokGameLocalDataSource,
) : OmokGameRepository {
    override fun saveGame(game: OmokGameEntity) {
        val gameData = game.toData()
        localDataSource.save(gameData)
    }

    override fun loadGame(): OmokGameEntity = localDataSource.load()?.toDomain() ?: OmokGameEntity()

    override fun deleteGame() {
        localDataSource.delete()
    }
}
