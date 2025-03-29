package woowacourse.omok.domain.repository

import woowacourse.omok.data.datasource.OmokGameLocalDataSource
import woowacourse.omok.domain.mapper.toData
import woowacourse.omok.domain.mapper.toDomain
import woowacourse.omok.domain.model.game.OmokGameEntity
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.StoneColor

class OmokGameRepositoryImpl(
    private val localDataSource: OmokGameLocalDataSource,
) : OmokGameRepository {
    override fun saveGame(game: OmokGameEntity) {
        val gameData = game.toData()
        localDataSource.save(gameData)
    }

    override fun loadGame(): OmokGameEntity =
        localDataSource.load()?.toDomain() ?: OmokGameEntity(
            StoneColor.BLACK,
            OmokBoard.create(),
        )

    override fun deleteGame() {
        localDataSource.delete()
    }
}
