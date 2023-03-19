package omok.domain.omokRule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class OmokRuleTest {
    private var board = MutableList(15) { MutableList(15) { 0 } }

    class TestOmokRule : OmokRule() {
        override fun validate(board: List<List<Int>>, position: Pair<Int, Int>): Boolean = true

        fun testSearch(board: List<List<Int>>, position: Pair<Int, Int>, direction: Pair<Int, Int>): Pair<Int, Int> =
            search(board, position, direction)

        fun testCountToWall(board: List<List<Int>>, position: Pair<Int, Int>, direction: Pair<Int, Int>): Int =
            countToWall(board, position, direction)
    }

    @BeforeEach
    fun setUp() {
        board = MutableList(15) { MutableList(15) { 0 } }
    }

    @Test
    fun `한 방향으로 자신의 돌이 어디까지 있는지 탐색한다1`() {
        val omokRule = TestOmokRule()
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[2][2] = 1
        board[4][4] = 1
        val (stoneCount, blinkCount) = omokRule.testSearch(board, nextPlace, Pair(-1, -1))

        // then
        assertThat(stoneCount).isEqualTo(2)
        assertThat(blinkCount).isEqualTo(1)
    }

    @Test
    fun `탐색중 빈공간이 두개 이상이면 탐색을 멈춘다`() {
        val omokRule = TestOmokRule()
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 1
        val (stoneCount, blinkCount) = omokRule.testSearch(board, nextPlace, Pair(-1, -1))

        // then
        assertThat(stoneCount).isEqualTo(0)
        assertThat(blinkCount).isEqualTo(0)
    }

    @Test
    fun `빈 공간 다음에 자신의 돌이 있어야 카운트 된다`() {
        val omokRule = TestOmokRule()
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[3][3] = 1
        board[4][4] = 1
        val (stoneCount, blinkCount) = omokRule.testSearch(board, nextPlace, Pair(-1, -1))

        // then
        assertThat(stoneCount).isEqualTo(2)
        assertThat(blinkCount).isEqualTo(0)
    }

    @Test
    fun `벽을 만나면 탐색을 멈춘다`() {
        val omokRule = TestOmokRule()
        val x = 1
        val y = 1

        val nextPlace = Pair(x, y)

        // when
        val (stoneCount, blinkCount) = omokRule.testSearch(board, nextPlace, Pair(-1, -1))

        // then
        assertThat(stoneCount).isEqualTo(0)
        assertThat(blinkCount).isEqualTo(0)
    }

    @Test
    fun `탐색중 상대 돌을 만나면 멈춘다`() {
        val omokRule = TestOmokRule()
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[2][2] = 2
        board[3][3] = 1
        board[4][4] = 1
        val (stoneCount, blinkCount) = omokRule.testSearch(board, nextPlace, Pair(-1, -1))

        // then
        assertThat(stoneCount).isEqualTo(2)
        assertThat(blinkCount).isEqualTo(0)
    }

    @Test
    fun `한 방향으로 자신의 돌이 어디까지 있는지 탐색한다2`() {
        val omokRule = TestOmokRule()
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 1
        board[4][4] = 1
        val (stoneCount, blinkCount) = omokRule.testSearch(board, nextPlace, Pair(-1, -1))

        // then
        assertThat(stoneCount).isEqualTo(1)
        assertThat(blinkCount).isEqualTo(0)
    }

    @Test
    fun `한 방향으로 자신의 돌이 어디까지 있는지 탐색한다3`() {
        val omokRule = TestOmokRule()
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 1
        board[2][2] = 1
        board[3][3] = 1
        board[4][4] = 1
        val (stoneCount, blinkCount) = omokRule.testSearch(board, nextPlace, Pair(-1, -1))

        // then
        assertThat(stoneCount).isEqualTo(4)
        assertThat(blinkCount).isEqualTo(0)
    }

    @Test
    fun `벽까지의 거리를 알 수 있다`() {
        // given
        val omokRule = TestOmokRule()
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 1
        board[4][4] = 1

        // then
        assertAll(
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(1, 0))).isEqualTo(9) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(-1, 0))).isEqualTo(5) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(0, 1))).isEqualTo(9) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(0, -1))).isEqualTo(5) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(1, 1))).isEqualTo(9) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(-1, -1))).isEqualTo(5) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(-1, 1))).isEqualTo(5) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(1, -1))).isEqualTo(5) },
        )
    }

    @Test
    fun `돌과 벽 사이에 상대 돌이 있으면 상대 돌까지 거리를 반환한다`() {
        // given
        val omokRule = TestOmokRule()

        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 2
        board[5][1] = 2
        // then
        assertAll(
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(-1, 0))).isEqualTo(3) },
            { assertThat(omokRule.testCountToWall(board, nextPlace, Pair(-1, -1))).isEqualTo(3) },
        )
    }
}
