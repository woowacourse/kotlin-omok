package woowacourse.omok

import model.domain.OmokGame
import model.domain.tools.Dot
import model.domain.tools.Stone

object OmokViewUtil {
    private const val BLACK_STONE_MESSAGE = "흑돌"
    private const val WHITE_STONE_MESSAGE = "백돌"
    private const val EMPTY_STONE_MESSAGE = ""

    fun getDot(index: Int) = Dot(index / OmokGame.BOARD_SIZE, index % OmokGame.BOARD_SIZE)

    fun getStoneMessage(stone: Stone) =
        when (stone) {
            Stone.BLACK -> BLACK_STONE_MESSAGE
            Stone.WHITE -> WHITE_STONE_MESSAGE
            Stone.EMPTY -> EMPTY_STONE_MESSAGE
        }

    fun getStoneColor(stoneMessage: String) =
        when (stoneMessage) {
            BLACK_STONE_MESSAGE -> Stone.BLACK
            WHITE_STONE_MESSAGE -> Stone.WHITE
            EMPTY_STONE_MESSAGE -> Stone.EMPTY
            else -> null
        }
}
