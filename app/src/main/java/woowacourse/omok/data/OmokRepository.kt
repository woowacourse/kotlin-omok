package woowacourse.omok.data

import omock.model.Position
import omock.model.board.Block
import omock.model.board.BlockState
import omock.model.board.buildOmokBoard
import omock.model.game.OmokGame
import omock.model.rule.RenjuRule
import woowacourse.omok.db.GameRecordDao
import woowacourse.omok.db.GameTurnEntity

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

    private fun GameTurnEntity.toOmokStone(): Block {
        val (id, x, y, color) = this
        return Block(Position(x, y), BlockState.valueOf(color))
    }

    private fun Block.toGameTurnEntity(): GameTurnEntity {
        val (position, color) = this
        val (x, y) = position
        return GameTurnEntity(NO_ID, x, y, color.name)
    }

    companion object {
        private const val NO_ID = -1L
        private const val DEFAULT_BOARD_SIZE = 15
        private val DEFAULT_RULE = RenjuRule
    }
}
