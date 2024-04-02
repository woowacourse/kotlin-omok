package woowacourse.omok.data.adapter

import woowacourse.omok.data.OmokEntity
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

object OmokEntityAdapter {
    private const val BLACK_STONE = "black"
    private const val WHITE_STONE = "white"

    fun omokEntity(
        position: Position,
        stone: Stone,
    ): OmokEntity {
        val entityStone = if (stone == Stone.BLACK) BLACK_STONE else WHITE_STONE
        return OmokEntity(position.row, position.col, entityStone)
    }

    fun Board(
        size: Int,
        omokEntities: List<OmokEntity>,
    ): Board {
        val stonePositions = omokEntities.map { stonePosition(it) }
        return Board(size, stonePositions)
    }

    fun stonePosition(omokEntity: OmokEntity): StonePosition {
        return StonePosition(
            Position(omokEntity.row, omokEntity.col),
            stone(omokEntity),
        )
    }

    fun index(
        boardSize: Int,
        omokEntity: OmokEntity,
    ) = omokEntity.row * boardSize + omokEntity.col

    fun stone(omokEntity: OmokEntity): Stone {
        return if (omokEntity.stone == BLACK_STONE) Stone.BLACK else Stone.WHITE
    }
}
