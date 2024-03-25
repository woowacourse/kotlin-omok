package omok.model

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.math.PI

class BlackPlayerTest {
    private lateinit var board: Board
    private lateinit var blackStonePlayer: BlackStonePlayer

    @BeforeEach
    fun setUp() {
        board = Board()
        blackStonePlayer = BlackStonePlayer(board)
    }


    private fun stone(
        color: Color,
        vararg positions: Int,
    ): Stone {
        return Stone(Point(positions[0], positions[1]), color)
    }

    private fun blackStone(
        row: String,
        col: Int,
    ): Stone {
        return Stone(Point(row[0] - 'A', col - 1), Color.BLACK)
    }

    @Test
    fun `흑이 33이면 백의 승리이다_1(흑의 패배이다)`() {
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
    fun `흑이 33이면 백의 승리이다_2(흑의 패배이다)`() {
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
    fun `흑이 33이면 백의 승리이다_3(흑의 패배이다)`() {
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
    fun `흑이 33이면 백의 승리이다_4(흑의 패배이다)`() {
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
}
