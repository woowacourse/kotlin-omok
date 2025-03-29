package woowacourse.omok.domain.repository

import woowacourse.omok.data.datasource.OmokGameLocalDataSource
import woowacourse.omok.domain.mapper.toData
import woowacourse.omok.domain.mapper.toDomain
import woowacourse.omok.domain.model.game.OmokGameEntity

class OmokGameRepositoryImpl(
    private val localDataSource: OmokGameLocalDataSource,
) : OmokGameRepository {
    override suspend fun saveGame(game: OmokGameEntity) {
        val gameData = game.toData()
        localDataSource.save(gameData)
    }

    override suspend fun fetchGame(): OmokGameEntity = localDataSource.load()?.toDomain() ?: OmokGameEntity()

    override suspend fun deleteGame() {
        localDataSource.delete()
    }
}
