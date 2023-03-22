package library.combinerule

import domain.CoordinateState
import domain.Position
import domain.library.combinerule.BlackWinRule
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BlackWinRuleTest {
    //    15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
    //    14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    9  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    8  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    7  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    6  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    5  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    4  ├──┼──┼──w──w──w──w──?──┼──┼──┼──┼──┼──┼──┤
    //    3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    //       A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
    @Test
    fun `(5,3)(5,4)(5,5)(5,6)가 흑돌인 조건일 때 (5,7)에 흑돌을 두면 5목조건으로 흑돌이 승리한다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[5][3] = CoordinateState.BLACK
        board[5][4] = CoordinateState.BLACK
        board[5][5] = CoordinateState.BLACK
        board[5][6] = CoordinateState.BLACK
        val targetCoordinate = Position(5, 7)

        Assertions.assertThat(
            BlackWinRule.validate(
                toCombineRuleBoard(board),
                targetCoordinate.coordinateX to targetCoordinate.coordinateY,
            ),
        ).isTrue
    }

    //    15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
    //    14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    9  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    8  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    7  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    6  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    5  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    4  ├──┼──┼──w──w──w──?──w──w──┼──┼──┼──┼──┼──
    //    3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    //       A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
    @Test
    fun `(5,3)(5,4)(5,5)(5,7)(5,8)가 흑돌인 조건일 때 (5,6)에 흑돌을 두면 6목조건으로 흑돌이 승리하지 못한다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[5][3] = CoordinateState.BLACK
        board[5][4] = CoordinateState.BLACK
        board[5][5] = CoordinateState.BLACK
        board[5][7] = CoordinateState.BLACK
        board[5][8] = CoordinateState.BLACK
        val targetCoordinate = Position(5, 6)

        Assertions.assertThat(
            BlackWinRule.validate(
                toCombineRuleBoard(board),
                targetCoordinate.coordinateX to targetCoordinate.coordinateY,
            ),
        ).isFalse
    }

    private fun convertCoordinateStateToCombineRuleNumber(coordinateState: CoordinateState): Int {
        return when (coordinateState) {
            CoordinateState.BLACK -> 1
            CoordinateState.WHITE -> 2
            CoordinateState.EMPTY -> 0
        }
    }

    private fun toCombineRuleBoard(board: List<List<CoordinateState>>) =
        board.map { it.map { coordinateState -> convertCoordinateStateToCombineRuleNumber(coordinateState) } }
}
