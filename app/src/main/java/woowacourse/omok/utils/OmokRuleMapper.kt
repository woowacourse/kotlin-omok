package woowacourse.omok.utils

import omok.library.OmokRule
import woowacourse.omok.model.state.CoordinateState
import woowacourse.omok.model.state.Turn

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
                        is CoordinateState.Placed ->
                            when (state.turn) {
                                is Turn.Black -> 1
                                is Turn.White -> 2
                            }
                        is CoordinateState.Empty -> 3
                        is CoordinateState.Forbidden -> 4
                    }
                }
            }
        }
    }
}
