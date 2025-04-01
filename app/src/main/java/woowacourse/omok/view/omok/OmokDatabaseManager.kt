package woowacourse.omok.view.omok

import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.data.dao.GamesDao
import woowacourse.omok.data.dao.MovesDao
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point

class OmokDatabaseManager(
    dbHelper: OmokDatabaseHelper,
) {
    private val gamesDao = GamesDao(dbHelper)
    private val movesDao = MovesDao(dbHelper)

    fun getMoves(gameId: Int): Map<Point, CellState> = movesDao.getMoves(gameId).getOrDefault(emptyList()).toMap()

    fun saveMove(
        gameId: Int,
        move: Pair<Point, CellState>,
    ) {
        movesDao.saveMove(gameId, move)
    }

    fun updateGameStatus(gameId: Int) {
        gamesDao.updateGameStatus(gameId)
    }
}
