package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var emptyBoard: Board
    private lateinit var player: Player
    private val playerStone = Stone.BLACK

    @BeforeEach
    fun setUp() {
        emptyBoard = Board()
        player = Player(playerStone)
    }

    @Test
    fun `오목판에 돌을 놓는다`() {
        emptyBoard.place(Position(3, 3), player)

        val actual = emptyBoard.find(Position(3, 3))
        assertThat(actual).isEqualTo(playerStone)
    }

    @Test
    fun `오목판에 이미 돌이 있는 곳에 놓으면 예외가 발생한다`() {
        val board = initBoard(
            StonePosition(Position(3, 3), playerStone)
        )
        assertThrows<IllegalArgumentException> { board.place(Position(3, 3), player) }
    }

    @Test
    fun `오목이 되면 승리한다`() {
        // given
        val board = initBoard(
            StonePosition(Position(3, 3), playerStone),
            StonePosition(Position(3, 4), playerStone),
            StonePosition(Position(3, 5), playerStone),
            StonePosition(Position(3, 6), playerStone),
            StonePosition(Position(3, 7), playerStone),
        )

        // when
        val actual = board.isWin(Position(3, 7))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `오목이 안 되면 승리하지 않는다`() {
        // given
        val board = initBoard(
            StonePosition(Position(3, 3), playerStone),
            StonePosition(Position(4, 3), playerStone),
            StonePosition(Position(5, 3), playerStone),
            StonePosition(Position(6, 3), playerStone),
            StonePosition(Position(1, 3), playerStone),
        )

        // when
        val actual = board.isWin(Position(1, 3))

        // then
        assertThat(actual).isFalse
    }
//
//    @Test
//    fun `검정 돌이 3-3인 경우에 놓으면 예외가 발생한다`() {
//        board.place(Position(5, 5), Stone.BLACK)
//        board.place(Position(8, 5), Stone.BLACK)
//        board.place(Position(7, 7), Stone.BLACK)
//        board.place(Position(7, 8), Stone.BLACK)
//
//        assertThrows<IllegalArgumentException> { board.place(Position(7, 5), Stone.BLACK) }
//    }
//
//    @Test
//    fun `검정 돌이 4-4인 경우에 놓으면 예외가 발생한다`() {
//        board.place(Position(3, 9), Stone.BLACK)
//        board.place(Position(6, 9), Stone.BLACK)
//        board.place(Position(7, 9), Stone.BLACK)
//        board.place(Position(9, 9), Stone.BLACK)
//
//        assertThrows<IllegalArgumentException> { board.place(Position(5, 9), Stone.BLACK) }
//    }
//
//    @Test
//    fun `검정 돌이 장목인 경우에 놓으면 예외가 발생한다`() {
//        board.place(Position(0, 0), Stone.WHITE)
//        board.place(Position(0, 7), Stone.WHITE)
//        board.place(Position(0, 1), Stone.BLACK)
//        board.place(Position(0, 2), Stone.BLACK)
//        board.place(Position(0, 4), Stone.BLACK)
//        board.place(Position(0, 5), Stone.BLACK)
//        board.place(Position(0, 6), Stone.BLACK)
//
//        assertThrows<IllegalArgumentException> { board.place(Position(0, 3), Stone.BLACK) }
//    }
//
//    @Test
//    fun `하얀 돌은 4-4인 경우에 놓을 수 있다`() {
//        board.place(Position(3, 9), Stone.WHITE)
//        board.place(Position(6, 9), Stone.WHITE)
//        board.place(Position(7, 9), Stone.WHITE)
//        board.place(Position(9, 9), Stone.WHITE)
//
//        assertDoesNotThrow { board.place(Position(5, 9), Stone.WHITE) }
//    }
//
//    @Test
//    fun `하얀 돌은 장목인 경우에 놓을 수 있다`() {
//        board.place(Position(0, 1), Stone.WHITE)
//        board.place(Position(0, 2), Stone.WHITE)
//        board.place(Position(0, 4), Stone.WHITE)
//        board.place(Position(0, 5), Stone.WHITE)
//        board.place(Position(0, 6), Stone.WHITE)
//
//        assertDoesNotThrow { board.place(Position(0, 3), Stone.WHITE) }
//    }
}
