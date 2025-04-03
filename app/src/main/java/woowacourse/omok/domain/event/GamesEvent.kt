package woowacourse.omok.domain.event

import woowacourse.omok.domain.model.Game

interface GamesEvent {
    fun updateGames(games: List<Game>)
}
