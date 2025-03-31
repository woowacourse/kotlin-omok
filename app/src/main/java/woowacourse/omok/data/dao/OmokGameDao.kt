package woowacourse.omok.data.dao

import woowacourse.omok.data.model.OmokGameDto

interface OmokGameDao {
    fun saveGame(game: OmokGameDto)

    fun fetchGame(gameId: Int): OmokGameDto?

    fun deleteGame(gameId: Int)
}
