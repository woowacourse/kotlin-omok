package library.combinerule.modifiedcombinerule

import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.EMPTY
import domain.CoordinateState.WHITE
import domain.Position
import domain.library.combinerule.modifiedcombinerule.ExceedFive
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExceedFiveTest {

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
    //    5  ├──┼──┼──x──x──x──?──x──x──┼──┼──┼──┼──┼──┤
    //    4  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    //       A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

    @Test
    fun `(4,3)(4,4)(4,5)(4,7)(4,8)에 같은 색의 돌이 놓여 있을 때 (4,6)에 같은 돌을 두면 장목이다(흑만 검사)`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[4][3] = BLACK
        board[4][4] = BLACK
        board[4][5] = BLACK
        board[4][7] = BLACK
        board[4][8] = BLACK

        val targetCoordinate = Position(4, 6)

        assertThat(
            ExceedFive.validate(
                toCombineRuleBoard(board),
                targetCoordinate.coordinateX to targetCoordinate.coordinateY,
            ),
        ).isTrue
    }

    private fun convertCoordinateStateToCombineRuleNumber(coordinateState: CoordinateState): Int {
        return when (coordinateState) {
            BLACK -> 1
            WHITE -> 2
            EMPTY -> 0
        }
    }

    private fun toCombineRuleBoard(board: List<List<CoordinateState>>) =
        board.map { it.map { coordinateState -> convertCoordinateStateToCombineRuleNumber(coordinateState) } }
}
