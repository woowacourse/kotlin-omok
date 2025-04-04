package woowacourse.omok.domain.event

interface GamesItemEvent {
    fun onGame(id: Long)

    fun onDelete(id: Long)
}
