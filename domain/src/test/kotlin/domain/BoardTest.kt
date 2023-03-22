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
        val board = Board()
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
        val board = Board(Stones(listOf(stone)))
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
        val board = Board()
        val differentPositionStone = Stone(Color.WHITE, Position(2, 3))
        // when
        val actual = board.isEmpty(differentPositionStone)
        // then
        Assertions.assertThat(actual).isTrue
    }

    @Test
    fun `보드에 바둑돌이 없다면, 마지막 바둑돌의 위치를 가져오려고 할 때 null을 반환한다`() {
        // given
        val board = Board()
        // when
        val actual = board.getLastPosition()
        val expected = null
        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막으로 둔 바둑돌의 위치를 알 수 있다`() {
        // given
        val stone = Stone(Color.WHITE, Position(1, 2))
        val board = Board(Stones(listOf(stone)))
        // when
        val actual = board.getLastPosition()
        val expected = Position(1, 2)
        // then
        Assertions.assertThat(actual).isEqualTo(expected)
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
        val actual = board.isBlackWin(stone)
        // then
        assertThat(actual).isEqualTo(true)
    }

    private fun generateBlackWinOmokBoard(): Board {
        val board = Board().apply {
            placeStone(Stone(getCurrentTurn(), 1, 2))
            placeStone(Stone(getCurrentTurn(), 2, 2))
            placeStone(Stone(getCurrentTurn(), 1, 3))
            placeStone(Stone(getCurrentTurn(), 2, 3))
            placeStone(Stone(getCurrentTurn(), 1, 4))
            placeStone(Stone(getCurrentTurn(), 2, 4))
            placeStone(Stone(getCurrentTurn(), 1, 5))
            placeStone(Stone(getCurrentTurn(), 4, 8))
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
        val stone = Stone(Color.WHITE, 1, 6)
        // when
        val actual = board.isWhiteWin(stone)
        // then
        assertThat(actual).isEqualTo(true)
    }

    private fun generateWhiteWinOmokBoard(): Board {
        val board = Board().apply {
            placeStone(Stone(getCurrentTurn(), 2, 2))
            placeStone(Stone(getCurrentTurn(), 1, 2))
            placeStone(Stone(getCurrentTurn(), 2, 3))
            placeStone(Stone(getCurrentTurn(), 1, 3))
            placeStone(Stone(getCurrentTurn(), 2, 4))
            placeStone(Stone(getCurrentTurn(), 1, 4))
            placeStone(Stone(getCurrentTurn(), 2, 5))
            placeStone(Stone(getCurrentTurn(), 1, 5))
            placeStone(Stone(getCurrentTurn(), 2, 10))
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
        val stone = Stone(Color.BLACK, 2, 4)
        // when
        val actual = board.isWhiteWin(stone)
        // then
        assertThat(actual).isEqualTo(true)
    }

    private fun generateThreeThreeBoard(): Board {
        val board = Board().apply {
            placeStone(Stone(getCurrentTurn(), 2, 2))
            placeStone(Stone(getCurrentTurn(), 4, 7))
            placeStone(Stone(getCurrentTurn(), 2, 3))
            placeStone(Stone(getCurrentTurn(), 4, 8))
            placeStone(Stone(getCurrentTurn(), 3, 4))
            placeStone(Stone(getCurrentTurn(), 5, 13))
            placeStone(Stone(getCurrentTurn(), 4, 4))
            placeStone(Stone(getCurrentTurn(), 6, 10))
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
        val stone = Stone(Color.BLACK, 3, 3)
        // when
        val actual = board.isWhiteWin(stone)
        // then
        assertThat(actual).isEqualTo(true)
    }

    private fun generateFourFourBoard(): Board {
        val board = Board().apply {
            placeStone(Stone(getCurrentTurn(), 4, 3))
            placeStone(Stone(getCurrentTurn(), 3, 7))
            placeStone(Stone(getCurrentTurn(), 6, 3))
            placeStone(Stone(getCurrentTurn(), 4, 7))
            placeStone(Stone(getCurrentTurn(), 7, 3))
            placeStone(Stone(getCurrentTurn(), 5, 7))
            placeStone(Stone(getCurrentTurn(), 4, 4))
            placeStone(Stone(getCurrentTurn(), 6, 7))
            placeStone(Stone(getCurrentTurn(), 6, 6))
            placeStone(Stone(getCurrentTurn(), 8, 7))
            placeStone(Stone(getCurrentTurn(), 7, 7))
            placeStone(Stone(getCurrentTurn(), 8, 3))
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
        val stone = Stone(Color.BLACK, 5, 3)
        // when
        val actual = board.isWhiteWin(stone)
        // then
        assertThat(actual).isEqualTo(true)
    }

    private fun generateMoreFiveBoard(): Board {
        val board = Board().apply {
            placeStone(Stone(getCurrentTurn(), 3, 3))
            placeStone(Stone(getCurrentTurn(), 3, 7))
            placeStone(Stone(getCurrentTurn(), 4, 3))
            placeStone(Stone(getCurrentTurn(), 4, 7))
            placeStone(Stone(getCurrentTurn(), 6, 3))
            placeStone(Stone(getCurrentTurn(), 5, 7))
            placeStone(Stone(getCurrentTurn(), 7, 3))
            placeStone(Stone(getCurrentTurn(), 6, 7))
            placeStone(Stone(getCurrentTurn(), 8, 3))
            placeStone(Stone(getCurrentTurn(), 8, 7))
        }
        return board
    }
}
