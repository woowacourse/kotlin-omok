package domain.library.combinerule

import domain.domain.BoardState
import domain.domain.CoordinateState
import domain.domain.GameRule
import domain.domain.Position
import domain.library.combinerule.modifiedcombinerule.ExceedFive

class CombinedRuleAdapter : GameRule {
    override fun isBlackWin(position: Position, board: BoardState): Boolean =
        BlackWinRule.validate(
            toCombineRuleBoard(board.value),
            position.coordinateX to position.coordinateY
        )

    override fun isWhiteWin(position: Position, board: BoardState): Boolean =
        WhiteWinRule.validate(
            toCombineRuleBoard(board.value),
            position.coordinateX to position.coordinateY
        )

    override fun checkAddablePosition(
        board: BoardState,
        turn: CoordinateState,
        position: Position
    ): Boolean {
        if (!board.isEmpty(position)) return false
        if (turn == CoordinateState.BLACK && isBlackForbidden(position, board)) return false
        return true
    }

    private fun isBlackForbidden(position: Position, board: BoardState): Boolean =
        isForbiddenThree(position, board.value) or isForbiddenFour(
            position,
            board.value
        ) or isExceedFive(
            position,
            board.value
        )

    private fun isForbiddenThree(position: Position, board: List<List<CoordinateState>>): Boolean =
        ThreeThreeRule.validate(
            toCombineRuleBoard(board),
            position.coordinateX to position.coordinateY
        )

    private fun isForbiddenFour(position: Position, board: List<List<CoordinateState>>): Boolean =
        FourFourRule.validate(
            toCombineRuleBoard(board),
            position.coordinateX to position.coordinateY
        )

    private fun convertCoordinateStateToCombineRuleNumber(coordinateState: CoordinateState): Int {
        return when (coordinateState) {
            CoordinateState.BLACK -> 1
            CoordinateState.WHITE -> 2
            CoordinateState.EMPTY -> 0
        }
    }

    private fun isExceedFive(position: Position, board: List<List<CoordinateState>>): Boolean =
        ExceedFive.validate(toCombineRuleBoard(board), position.coordinateX to position.coordinateY)

    private fun toCombineRuleBoard(board: List<List<CoordinateState>>) =
        board.map {
            it.map { coordinateState ->
                convertCoordinateStateToCombineRuleNumber(
                    coordinateState
                )
            }
        }
}
