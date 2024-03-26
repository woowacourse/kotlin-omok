package omok.model

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BlackPlayerTest {
    private lateinit var board: Board
    private lateinit var blackStonePlayer: BlackStonePlayer

    @BeforeEach
    fun setUp() {
        board = Board()
        blackStonePlayer = BlackStonePlayer(board)
    }

    @Test
    fun `흑이 33이면 백의 승리이다_1`() {
        blackStonePlayer.apply {
            add(Point(2, 2))
            add(Point(2, 3))
            add(Point(3, 4))
            add(Point(4, 4))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(2, 4))
        }
    }

    @Test
    fun `흑이 33이면 백의 승리이다_2`() {
        blackStonePlayer.apply {
            add(Point(6, 2))
            add(Point(5, 3))
            add(Point(5, 5))
            add(Point(6, 5))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(3, 5))
        }
    }

    @Test
    fun `흑이 33이면 백의 승리이다_3`() {
        blackStonePlayer.apply {
            add(Point(5, 10))
            add(Point(2, 10))
            add(Point(3, 12))
            add(Point(3, 13))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(3, 10))
        }
    }

    @Test
    fun `흑이 33이면 백의 승리이다_4`() {
        blackStonePlayer.apply {
            add(Point(8, 9))
            add(Point(11, 12))
            add(Point(9, 12))
            add(Point(8, 13))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(10, 11))
        }
    }

    @Test
    fun `흑이 44이면 예외를 던진다_1`() {
        blackStonePlayer.apply {
            add(Point(2, 12))
            add(Point(3, 12))
            add(Point(6, 12))
            add(Point(8, 12))
            add(Point(9, 12))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(5, 12))
        }
    }

    @Test
    fun `흑이 44이면 예외를 던진다_2`() {
        blackStonePlayer.apply {
            add(Point(2, 14))
            add(Point(2, 13))
            add(Point(2, 11))
            add(Point(2, 10))
            add(Point(2, 9))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(2, 12))
        }
    }

    @Test
    fun `흑이 44이면 예외를 던진다_3`() {
        blackStonePlayer.apply {
            add(Point(2, 11))
            add(Point(2, 10))
            add(Point(2, 9))
            add(Point(4, 5))
            add(Point(5, 4))
            add(Point(6, 3))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(2, 7))
        }
    }

    @Test
    fun `흑이 44이면 예외를 던진다_4`() {
        blackStonePlayer.apply {
            add(Point(4, 4))
            add(Point(5, 4))
            add(Point(6, 4))
            add(Point(7, 5))
            add(Point(7, 6))
            add(Point(7, 7))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(7, 4))
        }
    }

    @Test
    fun `흑이 44이면 예외를 던진다_5`() {
        blackStonePlayer.apply {
            add(Point(5, 4))
            add(Point(7, 6))
            add(Point(7, 7))
            add(Point(9, 7))
            add(Point(10, 7))
            add(Point(9, 8))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(Point(8, 7))
        }
    }
}
