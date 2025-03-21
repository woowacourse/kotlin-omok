package omok.domain.rule

import omok.domain.Board
import rule.WhiteRenjuRule

class WhiteStoneRule(
    boardSize: Int = Board.DEFAULT_BOARD_SIZE,
) : OmokGameRule(boardSize) {
    override val renjuRule = WhiteRenjuRule(boardSize)
}
