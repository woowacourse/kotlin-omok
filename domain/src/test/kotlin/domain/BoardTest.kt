package domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    private fun Stone(color: Color, vararg positions: Int): Stone {
        return Stone(color, Position(positions[0], positions[1]))
    }

    @Test
    fun `보드에 바둑돌을 놓으면,보드에 바둑돌이 추가된다`() {
        // given
        val board = Board(rule = RenjuRuleAdapter())
        val newStone = Stone(Color.WHITE, Position(1, 2))
        // when
        board.placeStone(newStone)
        // then
        val actual = board.stones.values
        val expected = listOf<Stone>(newStone)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은 위치에 바둑돌은 놓을 수 없다`() {
        // given
        val stone = Stone(Color.WHITE, Position(1, 2))
        val board = Board(Stones(listOf(stone)), rule = RenjuRuleAdapter())
        val samePositionStone = Stone(Color.BLACK, Position(1, 2))
        // when
        val actual = board.isEmpty(samePositionStone)
        // then
        Assertions.assertThat(actual).isFalse
    }

    @Test
    fun `다른 위치에 바둑돌을 놓을 수 있다`() {
        // given
        val stone = Stone(Color.WHITE, Position(1, 2))
        val board = Board(rule = RenjuRuleAdapter())
        val differentPositionStone = Stone(Color.WHITE, Position(2, 3))
        // when
        val actual = board.isEmpty(differentPositionStone)
        // then
        Assertions.assertThat(actual).isTrue
    }

    // 1
    // 2 ◎ ●
    // 3 ◎ ●
    // 4 ◎ ● ●
    // 5 ◎
    // 6 ?
    //   1 2 3 4 5 6
    @Test
    fun `흑의 오목이 완성되면 흑의의 승리이다`() {
        // given
        val board = generateBlackWinOmokBoard()
        val stone = Stone(Color.BLACK, 1, 6)
        // when
        val actual = board.getWinnerColor()
        // then
        assertThat(actual).isEqualTo(Color.BLACK)
    }

    private fun generateBlackWinOmokBoard(): Board {
        val board = Board(rule = RenjuRuleAdapter()).apply {
            placeStone(Stone(Color.BLACK, 1, 2))
            placeStone(Stone(Color.WHITE, 2, 2))
            placeStone(Stone(Color.BLACK, 1, 3))
            placeStone(Stone(Color.WHITE, 2, 3))
            placeStone(Stone(Color.BLACK, 1, 4))
            placeStone(Stone(Color.WHITE, 2, 4))
            placeStone(Stone(Color.BLACK, 1, 5))
            placeStone(Stone(Color.WHITE, 4, 8))
            placeStone(Stone(Color.BLACK, 1, 6))
        }
        return board
    }

    // 1
    // 2 ● ◎
    // 3 ● ◎
    // 4 ● ◎ ◎     ◎
    // 5 ●
    // 6 ?
    //   1 2 3 4 5 6
    @Test
    fun `백의 오목이 완성되면 백의 승리이다`() {
        // given
        val board = generateWhiteWinOmokBoard()
        // when
        val actual = board.getWinnerColor()
        // then
        assertThat(actual).isEqualTo(Color.WHITE)
    }

    private fun generateWhiteWinOmokBoard(): Board {
        val board = Board(rule = RenjuRuleAdapter()).apply {
            placeStone(Stone(Color.BLACK, 2, 2))
            placeStone(Stone(Color.WHITE, 1, 2))
            placeStone(Stone(Color.BLACK, 2, 3))
            placeStone(Stone(Color.WHITE, 1, 3))
            placeStone(Stone(Color.BLACK, 2, 4))
            placeStone(Stone(Color.WHITE, 1, 4))
            placeStone(Stone(Color.BLACK, 2, 5))
            placeStone(Stone(Color.WHITE, 1, 5))
            placeStone(Stone(Color.BLACK, 2, 10))
            placeStone(Stone(Color.WHITE, 1, 6))
        }
        return board
    }

    // 1
    // 2 ● ◎
    // 3 ● ◎
    // 4 ● ? ◎ ◎   ●
    // 5
    // 6
    //   1 2 3 4 5 6
    @Test
    fun `흑이 33이면 백의 승리이다(흑의 패배이다)`() {
        // given
        val board = generateThreeThreeBoard()
        // when
        val actual = board.getWinnerColor()
        // then
        assertThat(actual).isEqualTo(Color.WHITE)
    }

    private fun generateThreeThreeBoard(): Board {
        val board = Board(rule = RenjuRuleAdapter()).apply {
            placeStone(Stone(Color.BLACK, 2, 2))
            placeStone(Stone(Color.WHITE, 4, 7))
            placeStone(Stone(Color.BLACK, 2, 3))
            placeStone(Stone(Color.WHITE, 4, 8))
            placeStone(Stone(Color.BLACK, 3, 4))
            placeStone(Stone(Color.WHITE, 5, 13))
            placeStone(Stone(Color.BLACK, 4, 4))
            placeStone(Stone(Color.WHITE, 6, 10))
            placeStone(Stone(Color.BLACK, 2, 4))
        }
        return board
    }

    // 3   ? ◎   ◎ ◎ ●
    // 4     ◎
    // 5
    // 6         ◎
    // 7   ● ● ● ● ◎ ●
    //     3 4 5 6 7 8 9 10
    @Test
    fun `흑이 44이면 백의 승리이다(흑의 패배이다)`() {
        // given
        val board = generateFourFourBoard()
        // when
        val actual = board.getWinnerColor()
        // then
        assertThat(actual).isEqualTo(Color.WHITE)
    }

    private fun generateFourFourBoard(): Board {
        val board = Board(rule = RenjuRuleAdapter()).apply {
            placeStone(Stone(Color.BLACK, 4, 3))
            placeStone(Stone(Color.WHITE, 3, 7))
            placeStone(Stone(Color.BLACK, 6, 3))
            placeStone(Stone(Color.WHITE, 4, 7))
            placeStone(Stone(Color.BLACK, 7, 3))
            placeStone(Stone(Color.WHITE, 5, 7))
            placeStone(Stone(Color.BLACK, 4, 4))
            placeStone(Stone(Color.WHITE, 6, 7))
            placeStone(Stone(Color.BLACK, 6, 6))
            placeStone(Stone(Color.WHITE, 8, 7))
            placeStone(Stone(Color.BLACK, 7, 7))
            placeStone(Stone(Color.WHITE, 8, 3))
            placeStone(Stone(Color.BLACK, 3, 3))
        }
        return board
    }

    // 3   ◎ ◎ ? ◎ ◎ ◎
    // 4
    // 5
    // 6
    // 7   ● ● ● ●   ●
    //     3 4 5 6 7 8 9 10
    @Test
    fun `흑이 장목이면 백의 승리이다(흑의 패배이다)`() {
        // given
        val board = generateMoreFiveBoard()
        // when
        val actual = board.getWinnerColor()
        // then
        assertThat(actual).isEqualTo(Color.WHITE)
    }

    private fun generateMoreFiveBoard(): Board {
        val board = Board(rule = RenjuRuleAdapter()).apply {
            placeStone(Stone(Color.BLACK, 3, 3))
            placeStone(Stone(Color.WHITE, 3, 7))
            placeStone(Stone(Color.BLACK, 4, 3))
            placeStone(Stone(Color.WHITE, 4, 7))
            placeStone(Stone(Color.BLACK, 6, 3))
            placeStone(Stone(Color.WHITE, 5, 7))
            placeStone(Stone(Color.BLACK, 7, 3))
            placeStone(Stone(Color.WHITE, 6, 7))
            placeStone(Stone(Color.BLACK, 8, 3))
            placeStone(Stone(Color.WHITE, 8, 7))
            placeStone(Stone(Color.BLACK, 5, 3))
        }
        return board
    }
}
