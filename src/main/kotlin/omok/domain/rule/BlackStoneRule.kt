package omok.domain.rule

import omok.domain.Board
import rule.BlackRenjuRule

class BlackStoneRule(
    boardSize: Int = Board.DEFAULT_BOARD_SIZE,
) : OmokGameRule(boardSize) {
    override val renjuRule = BlackRenjuRule(boardSize)
}
