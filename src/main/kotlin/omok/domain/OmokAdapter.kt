package omok.domain

import omok.domain.lib.RenjuRule

class OmokAdapter(private val rule: RenjuRule = RenjuRule(15)) {
    fun isViolate(
        board: OmokBoard,
        stone: Stone,
    ): Boolean {
        val convertedBoard = convertBoard(board)
        return rule.isViolate(convertedBoard, stone.position.x, stone.position.y)
    }

    private fun convertBoard(board: OmokBoard): List<List<Int>> {
        return board.board.map { row ->
            row.map { stoneState ->
                when (stoneState) {
                    StoneState.BLACK -> 1
                    StoneState.WHITE -> 2
                    StoneState.BLANK -> 0
                }
            }
        }
    }
}
