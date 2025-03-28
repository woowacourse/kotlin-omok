package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row

class OmokGridTest {
    private lateinit var omokGrid: OmokGrid

    @BeforeEach
    fun setUp() {
        omokGrid = OmokGrid()
    }

    @Test
    @DisplayName("좌표와 검정 돌을 받으면 리스트에 추가한다")
    fun putBlackStone() {
        // given
        val blackPoint = OmokPoint(Point(Row(1), Column(2)), StoneColor.BLACK)
        val whitePoint = OmokPoint(Point(Row(2), Column(2)), StoneColor.WHITE)
        omokGrid.putStone(blackPoint)
        omokGrid.putStone(whitePoint)

        // when
        val omokList =
            omokGrid.getStonesByColor(StoneColor.BLACK) +
                omokGrid.getStonesByColor(
                    StoneColor.WHITE,
                )
        val actual = omokList.contains(blackPoint) && omokList.contains(whitePoint)

        // then
        assertThat(actual).isTrue()
    }
}
