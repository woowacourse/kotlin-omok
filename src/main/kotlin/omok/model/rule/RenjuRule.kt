package omok.model.rule

import omok.model.board.Board

interface RenjuRule {
    fun checkLastBlackStoneFoul(board: Board): RenjuFoul

    fun isOmok(board: Board): Boolean
}
