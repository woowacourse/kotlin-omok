package domain

import domain.stone.BlackStone
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BoardTest {

    @Test
    fun `BlackTurn 상태 에서 둘 수 있는 자리에 돌을 두면 WhiteTurn 상태로 변경된다`() {

        //given
        val board = Board()

        //when
        board.put(PointAdapter.create('A', 1))

        //then
        assertThat(board.isWhiteTurn()).isTrue
    }

    @Test
    fun `어떤 돌의 오목이 완성되면 Finish 상태로 변경된다`() {

//        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──○
//        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──○
//        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//       ... ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 ●──●──●──●──●──┴──┴──┴──┴──┴──┴──┴──┴──┴──○
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

        //given
        val board = Board()

        //when
        board.put(PointAdapter.create('A', 1))
        board.put(PointAdapter.create('O', 1))
        board.put(PointAdapter.create('B', 1))
        board.put(PointAdapter.create('O', 15))
        board.put(PointAdapter.create('C', 1))
        board.put(PointAdapter.create('O', 10))
        board.put(PointAdapter.create('D', 1))
        board.put(PointAdapter.create('O', 13))
        board.put(PointAdapter.create('E', 1))


        //then
        assertThat(board.isFinished()).isTrue
    }

    @Test
    fun `WhiteTurn 상태 에서 둘 수 있는 자리에 돌을 두면 BlackTurn 상태로 변경된다`() {

        //given
        val board = Board()

        //when
        board.put(PointAdapter.create('A', 1))
        board.put(PointAdapter.create('A', 2))

        //then
        assertThat(board.isBlackTurn()).isTrue
    }

    @Test
    fun `흑돌의 오목이 완성되면 BlackWin 상태가 된다`() {

//        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──○
//        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──○
//        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//       ... ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 ●──●──●──●──●──┴──┴──┴──┴──┴──┴──┴──┴──┴──○
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

        //given
        val board = Board()

        //when
        board.put(PointAdapter.create('A', 1))
        board.put(PointAdapter.create('o', 1))
        board.put(PointAdapter.create('B', 1))
        board.put(PointAdapter.create('o', 15))
        board.put(PointAdapter.create('C', 1))
        board.put(PointAdapter.create('o', 10))
        board.put(PointAdapter.create('D', 1))
        board.put(PointAdapter.create('o', 13))
        board.put(PointAdapter.create('E', 1))

        //then
        assertThat(board.isBlackWin()).isTrue
    }

    @Test
    fun `주어진 돌이 놓여진 흑돌 중에 있다면 true이다`() {

        //given
        val board = Board()

        //when
        board.put(PointAdapter.create('A', 1))
        val actual = board.blackStoneIsPlaced(BlackStone(PointAdapter.create('A', 1)))

        //then
        assertThat(actual).isTrue
    }

    @Test
    fun `주어진 돌이 놓여진 백돌 중에 있다면 true이다`() {

        //given
        val board = Board()

        //when
        board.put(PointAdapter.create('A', 1))
        board.put(PointAdapter.create('B', 1))
        val actual = board.whiteStoneIsPlaced(WhiteStone(PointAdapter.create('B', 1)))

        //then
        assertThat(actual).isTrue
    }
}