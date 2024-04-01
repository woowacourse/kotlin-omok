package woowacourse.omok.data

import omock.model.board.Block
import omock.model.board.buildOmokBoard
import omock.model.game.OmokGame
import omock.model.rule.RenjuRule
import woowacourse.omok.db.GameRecordDao

class OmokRepository(private val dao: GameRecordDao) {
    fun saveGame(block: Block) {
        val gameTurns = listOf(block.toGameTurnEntity())
        dao.saveGameRecord(gameTurns)
    }

    fun fetchGame(): OmokGame {
        val blocks = dao.selectAll().map { it.toOmokStone() }
        val board =
            buildOmokBoard {
                size(DEFAULT_BOARD_SIZE)
                blocks(blocks)
                rule(DEFAULT_RULE)
            }
        return OmokGame(board)
    }

    fun resetGame() {
        dao.resetTable()
    }

    companion object {
        private const val DEFAULT_BOARD_SIZE = 15
        private val DEFAULT_RULE = RenjuRule
    }
}
