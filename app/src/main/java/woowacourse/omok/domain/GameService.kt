package woowacourse.omok.domain

class GameService(
    private val boardService: BoardService,
) {
    fun getOrCreateGame(gameId: Long): OmokGame {
        val board = boardService.loadBoard(gameId)
        return OmokGame(board)
    }
}
