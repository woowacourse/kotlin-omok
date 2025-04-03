package woowacourse.omok.data.dao

import woowacourse.omok.data.model.OmokGameDto
import woowacourse.omok.data.model.OmokGamesDto

interface OmokGameDao {
    fun saveGame(game: OmokGameDto)

    fun fetchGame(gameId: Int): OmokGameDto?

    fun deleteGame(gameId: Int)

    fun createGame(game: OmokGameDto): Int

    fun fetchAllGames(): OmokGamesDto
}
