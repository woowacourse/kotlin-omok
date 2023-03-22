package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StonesTest {

    @Test
    fun `가지고 있는 돌 중에 흑돌들만 반환한다`() {
        val stones = Stones(
            setOf(
                BlackStone(Point.create('A', 1)),
                BlackStone(Point.create('A', 2)),
                WhiteStone(Point.create('C', 1))
            )
        )
        assertThat(stones.blackStones).containsExactly(
            BlackStone(Point.create('A', 1)),
            BlackStone(Point.create('A', 2))
        )
    }

    @Test
    fun `가지고 있는 돌 중에 백돌들만 반환한다`() {
        val stones = Stones(
            setOf(
                BlackStone(Point.create('A', 1)),
                BlackStone(Point.create('A', 2)),
                WhiteStone(Point.create('C', 1))
            )
        )
        assertThat(stones.whiteStones).containsExactly(WhiteStone(Point.create('C', 1)))
    }

    @Test
    fun `가지고 있는 돌에 돌을 추가해 새로운 Stones 객체를 반환한다`() {
        val stones = Stones(
            setOf(
                BlackStone(Point.create('A', 1)),
                BlackStone(Point.create('A', 2)),
                WhiteStone(Point.create('C', 1))
            )
        )
        val newStones = stones.addStone(WhiteStone(Point.create('C', 10)))
        assertThat(newStones.stones).containsExactly(
            BlackStone(Point.create('A', 1)),
            BlackStone(Point.create('A', 2)),
            WhiteStone(Point.create('C', 1)),
            WhiteStone(Point.create('C', 10))
        )
    }
}