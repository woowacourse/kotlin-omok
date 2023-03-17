package domain

import domain.board.Board
import domain.board.OmokBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import view.OutputView

class RefereeTest {
    @Test
    fun `가로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..5).forEach { column ->
            board.move(Stone(1, column), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isTrue
    }

    @Test
    fun `세로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..5).forEach { row ->
            board.move(Stone(row, 1), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isTrue
    }

    @Test
    fun `좌상단에서 우하단 대각선으로 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..5).forEach { index ->
            board.move(Stone(index, index), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isTrue
    }

    @Test
    fun `우상단에서 좌하단 대각선으로 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..5).forEach { index ->
            board.move(Stone(index, 15 - index), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isTrue
    }

    @Test
    fun `가로로 연속 4개 놓여있으면 승리가 아니다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..4).forEach { column ->
            board.move(Stone(1, column), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isFalse
    }

    @Test
    fun `3*3 test1`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('C', 12), State.BLACK)
        myBoard.move(Stone.create('D', 13), State.BLACK)
        myBoard.move(Stone.create('D', 14), State.BLACK)
        myBoard.move(Stone.create('E', 12), State.BLACK)

        // when
        val stone = Stone.create('D', 12)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `3*3 test2`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('B', 6), State.BLACK)
        myBoard.move(Stone.create('C', 5), State.BLACK)
        myBoard.move(Stone.create('E', 5), State.BLACK)
        myBoard.move(Stone.create('E', 6), State.BLACK)

        // when
        val stone = Stone.create('E', 3)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `3*3 test3`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('K', 3), State.BLACK)
        myBoard.move(Stone.create('K', 6), State.BLACK)
        myBoard.move(Stone.create('N', 4), State.BLACK)
        myBoard.move(Stone.create('M', 4), State.BLACK)

        // when
        val stone = Stone.create('K', 4)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `3*3 test4`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('J', 9), State.BLACK)
        myBoard.move(Stone.create('M', 10), State.BLACK)
        myBoard.move(Stone.create('N', 9), State.BLACK)
        myBoard.move(Stone.create('M', 12), State.BLACK)

        // when
        val stone = Stone.create('L', 11)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `4*4 test1`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('C', 12), State.BLACK)
        myBoard.move(Stone.create('D', 12), State.BLACK)
        myBoard.move(Stone.create('G', 12), State.BLACK)
        myBoard.move(Stone.create('I', 12), State.BLACK)
        myBoard.move(Stone.create('J', 12), State.BLACK)

        // when
        val stone = Stone.create('F', 12)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `4*4 test2`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('J', 12), State.BLACK)
        myBoard.move(Stone.create('J', 9), State.BLACK)
        myBoard.move(Stone.create('J', 8), State.BLACK)
        myBoard.move(Stone.create('J', 6), State.BLACK)

        // when
        val stone = Stone.create('J', 10)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `4*4 test3`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('E', 5), State.BLACK)
        myBoard.move(Stone.create('F', 5), State.BLACK)
        myBoard.move(Stone.create('G', 5), State.BLACK)
        myBoard.move(Stone.create('H', 6), State.BLACK)
        myBoard.move(Stone.create('H', 7), State.BLACK)
        myBoard.move(Stone.create('H', 8), State.BLACK)

        myBoard.move(Stone.create('D', 5), State.WHITE)
        myBoard.move(Stone.create('H', 9), State.WHITE)

        // when
        val stone = Stone.create('H', 5)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `4*4 test4`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('H', 8), State.BLACK)
        myBoard.move(Stone.create('J', 8), State.BLACK)
        myBoard.move(Stone.create('K', 8), State.BLACK)
        myBoard.move(Stone.create('J', 9), State.BLACK)
        myBoard.move(Stone.create('H', 7), State.BLACK)
        myBoard.move(Stone.create('F', 5), State.BLACK)

        // when
        val stone = Stone.create('I', 8)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `jangmok test`() {
        val referee = Referee()
        val myBoard = OmokBoard()

        myBoard.move(Stone.create('C', 15), State.BLACK)
        myBoard.move(Stone.create('C', 14), State.BLACK)
        myBoard.move(Stone.create('C', 12), State.BLACK)
        myBoard.move(Stone.create('C', 11), State.BLACK)
        myBoard.move(Stone.create('C', 10), State.BLACK)

        // when
        val stone = Stone.create('C', 13)
        val actual = referee.isMovable(myBoard, stone)

        OutputView.printOmokState(myBoard.board, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }
}
