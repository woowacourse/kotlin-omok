package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var board: Board
    private lateinit var ableCoordinate: Coordinate
    private lateinit var unableCoordinate: Coordinate

    @BeforeEach
    fun setUp() {
        board =
            Board(
                listOf(
                    Stone("black", Coordinate(5, "A")),
                    Stone("white", Coordinate(8, "H")),
                ),
            )

        ableCoordinate = Coordinate(6, "C")
        unableCoordinate = Coordinate(5, "A")
    }

    @Test
    fun `오목판은 판 위에 놓인 돌을 가지고 있다`() {
        assertThat(board.stones.size).isEqualTo(2)
    }

    @Test
    fun `입력받은 좌표가 이미 오목판 위에 돌이 놓인 좌표라면 false를 반환한다`() {
        val actual = board.checkOccupiedCoordinate(unableCoordinate)

        assertThat(actual).isFalse()
    }

    @Test
    fun `입력받은 좌표가 오목판 위에 돌이 없는 좌표라면 true를 반환한다`() {
        val actual = board.checkOccupiedCoordinate(ableCoordinate)

        assertThat(actual).isTrue()
    }

    @Test
    fun `오목판에 돌을 착수할 수 있다`() {
        val stone = Stone("white", ableCoordinate)
        val possibility = board.checkOccupiedCoordinate(ableCoordinate)
        val beforeSize = board.stones.size

        board.putStoneOnBoard(possibility, stone)
        assertThat(board.stones.size).isGreaterThan(beforeSize)
    }

    @Test
    fun `오목판에 돌을 착수할 수 없으면 에러를 던져야한다`() {
        val stone = Stone("white", unableCoordinate)
        val possibility = board.checkOccupiedCoordinate(unableCoordinate)

        assertThrows<IllegalStateException> {
            board.putStoneOnBoard(possibility, stone)
        }
    }
}
