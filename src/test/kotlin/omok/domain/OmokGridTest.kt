package omok.domain

import omok.domain.point.Column
import omok.domain.point.OmokPoint
import omok.domain.point.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokGridTest {
    private lateinit var omokGrid: OmokGrid

    @BeforeEach
    fun setUp() {
        omokGrid = OmokGrid()
    }

    @Test
    fun `좌표와 검정 돌을 받으면 검정 돌 리스트에 추가한다`() {
        // given
        val point = OmokPoint(Row(1), Column(2))
        omokGrid.putStone(point, StoneState.BLACK)
        // when
        val actual = omokGrid.blackStones.stones.contains(point)
        // then
        assertThat(actual).isTrue()
    }

    @Test
    fun `좌표와 흰 돌을 받으면 흰 돌 리스트에 추가한다`() {
        // given
        val point = OmokPoint(Row(1), Column(2))
        omokGrid.putStone(point, StoneState.WHITE)
        // when
        val actual = omokGrid.whiteStones.stones.contains(point)
        // then
        assertThat(actual).isTrue()
    }

    @Test
    fun `좌표에 이미 돌이 있으면 예외를 던진다`() {
        // given
        val row = 1
        val col = 2
        // when
        omokGrid.putStone(OmokPoint(Row(1), Column(col)), StoneState.BLACK)
        // then
        assertThrows<IllegalStateException> {
            omokGrid.validateEmptyPoint(OmokPoint(Row(row), Column(col)))
        }
    }
}
