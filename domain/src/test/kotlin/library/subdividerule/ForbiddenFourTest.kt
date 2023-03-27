package library.subdividerule

import domain.domain.CoordinateState
import domain.domain.Position
import domain.library.subdividerule.ForbiddenFour
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ForbiddenFourTest {

    //    15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
    //    14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    9  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    8  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    7  ├──┼──┼──b──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    6  ├──┼──┼──b──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    5  ├──┼──┼──b──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    4  ├──┼──┼──?──b──b──b──┼──┼──┼──┼──┼──┼──┼──┤
    //    3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    //       A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
    @Test
    fun `(3,4)(3,5)(3,6)(4,3)(5,3)(6,3)가 흑돌인 조건일 때 (3,3)에 흑돌을 두면 44금수이다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.BLACK
        board[3][5] = CoordinateState.BLACK
        board[3][6] = CoordinateState.BLACK
        board[4][3] = CoordinateState.BLACK
        board[5][3] = CoordinateState.BLACK
        board[6][3] = CoordinateState.BLACK
        val targetCoordinate = Position(3, 3)

        assertThat(ForbiddenFour.isForbiddenFour(board, targetCoordinate)).isTrue
    }

    //    15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
    //    14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    9  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    8  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    7  ├──┼──┼──┼──┼──┼──b──┼──┼──┼──┼──┼──┼──┼──┤
    //    6  ├──┼──┼──┼──┼──b──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    5  ├──┼──┼──┼──b──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    4  ├──┼──┼──?──b──b──b──┼──┼──┼──┼──┼──┼──┼──┤
    //    3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
    //    1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    //       A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
    @Test
    fun `(3,4)(3,5)(3,6)(4,4)(5,5)(6,6)가 흑돌인 조건일 때 (3,3)에 흑돌을 두면 44금수이다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.BLACK
        board[3][5] = CoordinateState.BLACK
        board[3][6] = CoordinateState.BLACK
        board[4][4] = CoordinateState.BLACK
        board[5][5] = CoordinateState.BLACK
        board[6][6] = CoordinateState.BLACK
        val targetCoordinate = Position(3, 3)

        assertThat(ForbiddenFour.isForbiddenFour(board, targetCoordinate)).isTrue
    }
}
