package domain.stone

import domain.Direction
import domain.Inclination
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class StonesTest {

    @Test
    fun `마지막으로 넣은 돌의 좌표를 알 수 있다`() {
        val stones = Stones().apply {
            add(Stone('H', 6))
            add(Stone('H', 7))
            add(Stone('H', 8))
            add(Stone('H', 9))
            add(Stone('H', 10))
        }

        assertThat(stones.lastPoint).isEqualTo(Point('H', 10))
    }

    @Test
    fun `특정 좌표에서 특정 방향으로 진행했을 때 다음 돌이 없는 좌표를 구할 수 있다`() {
        val stones = Stones().apply {
            add(Stone('H', 6))
            add(Stone('H', 7))
            add(Stone('H', 8))
            add(Stone('H', 9))
            add(Stone('H', 10))
        }

        val actual = stones.getNextBlankPointAt(Point('H', 6), Direction.UP)

        assertThat(actual).isEqualTo(Point('H', 11))
    }

    @Test
    fun `특정 좌표에서 특정 기울기의 직선 위에 연결된 돌들의 개수를 알 수 있다`() {
        val stones = Stones().apply {
            add(Stone('H', 6))
            add(Stone('H', 7))
            add(Stone('H', 8))
            add(Stone('H', 9))
            add(Stone('H', 10))
        }

        val actual = stones.getLinkedStonesCountAt(Point('H', 8), Inclination.VERTICAL)

        assertThat(actual).isEqualTo(5)
    }

    @Test
    fun `연속된 돌들의 개수가 5인 돌들이 있다면 오목이 완성된 것이다`() {
        val stones = Stones().apply {
            add(Stone('H', 6))
            add(Stone('H', 7))
            add(Stone('H', 8))
            add(Stone('H', 9))
            add(Stone('H', 10))
        }

        assertThat(stones.completeOmok()).isTrue
    }

    @Test
    fun `연속된 돌들의 개수가 5인 돌들이 없다면 오목이 완성되지 않은 것이다`() {
        val stones = Stones().apply {
            add(Stone('H', 6))
            add(Stone('H', 7))
            add(Stone('H', 8))
            add(Stone('H', 9))
            add(Stone('A', 1))
            add(Stone('B', 1))
            add(Stone('C', 1))
            add(Stone('D', 1))
        }

        assertThat(stones.completeOmok()).isFalse
    }
}