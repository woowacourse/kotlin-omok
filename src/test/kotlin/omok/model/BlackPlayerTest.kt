package omok.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BlackPlayerTest {
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
        val blackStonePlayer = BlackStonePlayer()
        blackStonePlayer.apply {
            add(stone(Color.BLACK, 2, 2))
            add(stone(Color.BLACK, 2, 3))
            add(stone(Color.BLACK, 3, 4))
            add(stone(Color.BLACK, 4, 4))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(stone(Color.BLACK, 2, 4))
        }
    }

    @Test
    fun `흑이 33이면 백의 승리이다_2(흑의 패배이다)`() {
        val blackStonePlayer = BlackStonePlayer()
        blackStonePlayer.apply {
            add(stone(Color.BLACK, 6, 2))
            add(stone(Color.BLACK, 5, 3))
            add(stone(Color.BLACK, 5, 5))
            add(stone(Color.BLACK, 6, 5))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(stone(Color.BLACK, 3, 5))
        }
    }

    @Test
    fun `흑이 33이면 백의 승리이다_3(흑의 패배이다)`() {
        val blackStonePlayer = BlackStonePlayer()
        blackStonePlayer.apply {
            add(stone(Color.BLACK, 5, 10))
            add(stone(Color.BLACK, 2, 10))
            add(stone(Color.BLACK, 3, 12))
            add(stone(Color.BLACK, 3, 13))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(stone(Color.BLACK, 3, 10))
        }
    }

    @Test
    fun `흑이 33이면 백의 승리이다_4(흑의 패배이다)`() {
        val blackStonePlayer = BlackStonePlayer()
        blackStonePlayer.apply {
            add(stone(Color.BLACK, 8, 9))
            add(stone(Color.BLACK, 11, 12))
            add(stone(Color.BLACK, 9, 12))
            add(stone(Color.BLACK, 8, 13))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(stone(Color.BLACK, 10, 11))
        }
    }

    @Test
    fun `흑이 44이면 예외를 던진다_1`() {
        val blackStonePlayer = BlackStonePlayer()
        blackStonePlayer.apply {
            add(blackStone("C", 12))
            add(blackStone("D", 12))
            add(blackStone("G", 12))
            add(blackStone("I", 12))
            add(blackStone("J", 12))
        }
        assertThrows<IllegalArgumentException> {
            blackStonePlayer.add(blackStone("F", 12))
        }
    }
}
