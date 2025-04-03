package woowacourse.omok.domain

import woowacourse.omok.domain.event.GamesEvent
import woowacourse.omok.domain.repository.GameRepository
import woowacourse.omok.domain.repository.StoneRepository

class Games(
    private val gameRepository: GameRepository,
    private val stonesRepository: StoneRepository,
    private val gamesEvent: GamesEvent,
) {
    fun insert(name: String) {
        gameRepository.insert(name)
    }

    fun update() {
        gamesEvent.updateGames(gameRepository.getAll())
    }

    fun deleteGame(id: Long) {
        gameRepository.delete(id)
        clearStonesById(id)
    }

    private fun clearStonesById(gameId: Long) {
        stonesRepository.clear(gameId)
    }
}
