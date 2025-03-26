package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.grid.Row

class OmokGridTest {
    private lateinit var omokGrid: OmokGrid

    @BeforeEach
    fun setUp() {
        omokGrid = OmokGrid()
    }

    @Test
    @DisplayName("좌표와 검정 돌을 받으면 검정 돌 리스트에 추가한다")
    fun putBlackStone() {
        // given
        val point = OmokPoint(Row(1), Column(2))
        omokGrid.putStone(point, StoneColor.BLACK)
        val blackStones = omokGrid.getStones(StoneColor.BLACK)

        // when
        val actual = blackStones.contains(point)

        // then
        assertThat(actual).isTrue()
    }

    @Test
    @DisplayName("좌표와 흰 돌을 받으면 흰 돌 리스트에 추가한다")
    fun putWhiteStone() {
        // given
        val point = OmokPoint(Row(1), Column(2))
        omokGrid.putStone(point, StoneColor.WHITE)
        val whiteStones = omokGrid.getStones(StoneColor.WHITE)

        // when
        val actual = whiteStones.contains(point)

        // then
        assertThat(actual).isTrue()
    }
}
