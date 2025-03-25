package omok.model.rule

import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

interface RenjuRule {
    fun checkLastBlackStoneFoul(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): RenjuFoul

    fun isOmok(board: Board): Boolean
}
