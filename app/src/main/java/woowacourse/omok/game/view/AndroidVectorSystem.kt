package woowacourse.omok.game.view

import domain.Board
import dto.VectorDTO
import dto.VectorSystem

class AndroidVectorSystem(private val index: Int) : VectorSystem {
    override fun toDTO(): VectorDTO {
        return VectorDTO(
            index % Board.BOARD_SIZE.x,
            Board.BOARD_SIZE.y - (index / Board.BOARD_SIZE.y) - FACTOR_ROW_FIXER
        )
    }

    companion object {
        private const val FACTOR_ROW_FIXER = 1
    }
}
