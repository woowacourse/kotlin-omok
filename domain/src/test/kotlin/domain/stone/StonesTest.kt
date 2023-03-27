package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StonesTest {

    @Test
    fun `가지고 있는 돌 중에 흑돌들만 반환한다`() {

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('A', 1)),
                BlackStone(PointAdapter.create('A', 2)),
                WhiteStone(PointAdapter.create('C', 1))
            )
        )

        //then
        assertThat(stones.blackStones).containsExactly(
            BlackStone(PointAdapter.create('A', 1)),
            BlackStone(PointAdapter.create('A', 2))
        )
    }

    @Test
    fun `가지고 있는 돌 중에 백돌들만 반환한다`() {

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('A', 1)),
                BlackStone(PointAdapter.create('A', 2)),
                WhiteStone(PointAdapter.create('C', 1))
            )
        )

        //then
        assertThat(stones.whiteStones).containsExactly(WhiteStone(PointAdapter.create('C', 1)))
    }

    @Test
    fun `가지고 있는 돌에 돌을 추가해 새로운 Stones 객체를 반환한다`() {

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('A', 1)),
                BlackStone(PointAdapter.create('A', 2)),
                WhiteStone(PointAdapter.create('C', 1))
            )
        )

        //when
        val newStones = stones.addStone(WhiteStone(PointAdapter.create('C', 10)))

        //then
        assertThat(newStones.stones).containsExactly(
            BlackStone(PointAdapter.create('A', 1)),
            BlackStone(PointAdapter.create('A', 2)),
            WhiteStone(PointAdapter.create('C', 1)),
            WhiteStone(PointAdapter.create('C', 10))
        )
    }
}