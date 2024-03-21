package omok.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OverlineForbiddenPlaceTest {
    private lateinit var board: Board
    private lateinit var forbiddenPlace: ForbiddenPlace

    @BeforeEach
    fun setUp() {
        board = Board()
        forbiddenPlace = OverlineForbiddenPlace()
    }

    @Test
    fun `돌을 두려는 위치로 장목이 되면 놓을 수 없다`() {
        board.place(Position(0, 0), Stone.WHITE)
        board.place(Position(0, 7), Stone.WHITE)
        board.place(Position(0, 1), Stone.BLACK)
        board.place(Position(0, 2), Stone.BLACK)
        board.place(Position(0, 4), Stone.BLACK)
        board.place(Position(0, 5), Stone.BLACK)
        board.place(Position(0, 6), Stone.BLACK)

        val actual = forbiddenPlace.availablePosition(board, Position(0, 3))
        Assertions.assertThat(actual).isFalse
    }
}
