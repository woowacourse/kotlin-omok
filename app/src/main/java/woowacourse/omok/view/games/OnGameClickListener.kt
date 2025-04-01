package woowacourse.omok.view.games

interface OnGameClickListener {
    fun enterGame(
        gameId: Int,
        isFinished: Boolean,
    )

    fun deleteGame(gameId: Int)
}
