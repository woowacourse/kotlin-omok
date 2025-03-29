package woowacourse.omok.domain.repository

import woowacourse.omok.domain.model.game.OmokGameEntity

interface OmokGameRepository {
    fun saveGame(game: OmokGameEntity)

    fun loadGame(): OmokGameEntity

    fun deleteGame()
}
