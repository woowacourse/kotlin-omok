package woowacourse.omok.database

import woowacourse.omok.model.board.Stone

interface GameDao {
    fun saveGame(board: Array<Array<Stone>>)
    fun loadGame(): Array<Array<Stone>>
    fun resetGame()
}
