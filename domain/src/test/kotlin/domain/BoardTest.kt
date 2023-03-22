package domain

import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.WhiteStone
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BoardTest {

    @Test
    fun `BlackTurn 상태 에서 둘 수 있는 자리에 돌을 두면 WhiteTurn 상태로 변경된다`() {
        val board = Board()
        board.put(Point.create('A', 1))
        assertTrue(board.isWhiteTurn())
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

        val board = Board()
        board.put(Point.create('A', 1))
        board.put(Point.create('O', 1))
        board.put(Point.create('B', 1))
        board.put(Point.create('O', 15))
        board.put(Point.create('C', 1))
        board.put(Point.create('O', 10))
        board.put(Point.create('D', 1))
        board.put(Point.create('O', 13))
        board.put(Point.create('E', 1))

        assertTrue(board.isFinished())
    }

    @Test
    fun `WhiteTurn 상태 에서 둘 수 있는 자리에 돌을 두면 BlackTurn 상태로 변경된다`() {
        val board = Board()
        board.put(Point.create('A', 1))
        board.put(Point.create('A', 2))

        assertTrue(board.isBlackTurn())
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

        val board = Board()
        board.put(Point.create('A', 1))
        board.put(Point.create('o', 1))
        board.put(Point.create('B', 1))
        board.put(Point.create('o', 15))
        board.put(Point.create('C', 1))
        board.put(Point.create('o', 10))
        board.put(Point.create('D', 1))
        board.put(Point.create('o', 13))
        board.put(Point.create('E', 1))

        assertTrue(board.isBlackWin())
    }

    @Test
    fun `주어진 돌이 놓여진 흑돌 중에 있다면 true이다`() {
        val board = Board()
        board.put(Point.create('A', 1))
        val actual = board.blackStoneIsPlaced(BlackStone(Point.create('A', 1)))
        assertTrue(actual)
    }

    @Test
    fun `주어진 돌이 놓여진 백돌 중에 있다면 true이다`() {
        val board = Board()
        board.put(Point.create('A', 1))
        board.put(Point.create('B', 1))
        val actual = board.whiteStoneIsPlaced(WhiteStone(Point.create('B', 1)))
        assertTrue(actual)
    }
}