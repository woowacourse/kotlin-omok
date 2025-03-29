package woowacourse.omok.domain.repository

import woowacourse.omok.domain.model.game.OmokGameEntity

interface OmokGameRepository {
    suspend fun saveGame(game: OmokGameEntity)

    suspend fun fetchGame(): OmokGameEntity

    suspend fun deleteGame()
}
