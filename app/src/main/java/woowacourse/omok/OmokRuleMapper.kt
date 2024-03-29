package woowacourse.omok

import omok.library.OmokRule

class OmokRuleMapper {
    companion object {
        private const val BLACK_STONE = 1
        private const val WHITE_STONE = 2

        fun map(
            currentTurn: Turn,
            board: List<List<CoordinateState>>,
            boardSize: Int,
        ): OmokRule {
            return OmokRule(
                currentStone = formatTurn(currentTurn),
                otherStone = formatTurn(currentTurn.nextTurn()),
                board = formatBoard(board),
                boardSize = boardSize,
            )
        }

        private fun formatTurn(turn: Turn): Int {
            return when (turn) {
                is Turn.Black -> {
                    BLACK_STONE
                }
                is Turn.White -> {
                    WHITE_STONE
                }
            }
        }

        private fun formatBoard(board: List<List<CoordinateState>>): List<List<Int>> {
            return board.map { row ->
                row.map { state ->
                    when (state) {
                        CoordinateState.BlackStone -> 1
                        CoordinateState.WhiteStone -> 2
                        CoordinateState.Empty -> 3
                        CoordinateState.Forbidden -> 4
                    }
                }
            }
        }
    }
}
