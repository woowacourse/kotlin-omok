package woowacourse.omok.domain

import woowacourse.omok.domain.event.GamesEvent
import woowacourse.omok.domain.repository.GameRepository
import woowacourse.omok.domain.repository.StoneRepository

class Games(
    private val gameRepository: GameRepository,
    private val stonesRepository: StoneRepository,
    private val gamesEvent: GamesEvent,
) {
    fun insertGame(name: String) {
        gameRepository.insert(name)
    }

    fun updateGames() {
        gamesEvent.updateGames(gameRepository.getAll())
    }

    fun deleteGame(id: Long) {
        gameRepository.delete(id)
        clearStonesByGameId(id)
    }

    private fun clearStonesByGameId(gameId: Long) {
        stonesRepository.clear(gameId)
    }
}
