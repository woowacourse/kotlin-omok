package library.subdividerule.exceedfive

import domain.CoordinateState
import domain.Position
import domain.library.cldhfleks2.ExceedFive
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class ExceedFiveTest {
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

    @CsvSource(value = ["BLACK", "WHITE"])
    @ParameterizedTest
    fun `(4,3)(4,4)(4,5)(4,7)(4,8)에 같은 색의 돌이 놓여 있을 때 (4,6)에 같은 돌을 두면 장목이다`(coordinateState: CoordinateState) {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[4][3] = coordinateState
        board[4][4] = coordinateState
        board[4][5] = coordinateState
        board[4][7] = coordinateState
        board[4][8] = coordinateState

        val targetCoordinate = Position(4, 6)

        assertThat(ExceedFive.isExceedFive(board, targetCoordinate, coordinateState)).isTrue
    }
}
