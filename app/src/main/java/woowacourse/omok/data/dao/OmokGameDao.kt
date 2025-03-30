package woowacourse.omok.data.dao

import woowacourse.omok.data.model.OmokGameDto

interface OmokGameDao {
    fun saveGame(game: OmokGameDto)

    fun fetchGame(): OmokGameDto?

    fun deleteGame()
}
