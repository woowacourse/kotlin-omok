package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.grid.Stone

class OmokGridTest {
    private lateinit var omokGrid: OmokGrid

    @BeforeEach
    fun setUp() {
        omokGrid = OmokGrid()
    }

    @Test
    @DisplayName("돌을 받으면 리스트에 추가한다")
    fun putBlackStone() {
        // given
        val blackStone = Stone(Point(Row(1), Column(2)), StoneColor.BLACK)
        val whiteStone = Stone(Point(Row(2), Column(2)), StoneColor.WHITE)
        omokGrid.putStone(blackStone)
        omokGrid.putStone(whiteStone)

        // when
        val omokList =
            omokGrid.getStonesByColor(StoneColor.BLACK) +
                omokGrid.getStonesByColor(
                    StoneColor.WHITE,
                )
        val actual = omokList.contains(blackStone) && omokList.contains(whiteStone)

        // then
        assertThat(actual).isTrue()
    }

    @Test
    @DisplayName("흑돌의 개수가 흰돌의 개수보다 많으면 true를 반환한다")
    fun checkStonesCount() {
        // given
        val blackStone = Stone(Point(Row(1), Column(2)), StoneColor.BLACK)
        omokGrid.putStone(blackStone)

        // when
        val actual = omokGrid.isBlackMoreThanWhite()

        // then
        assertThat(actual).isTrue()
    }

    @Test
    @DisplayName("해당 좌표의 돌이 있으면 돌의 색깔을 반환한다")
    fun getStoneColor_whenStoneExistsAt() {
        // given
        val blackStone = Stone(Point(Row(1), Column(2)), StoneColor.BLACK)
        val whiteStone = Stone(Point(Row(2), Column(2)), StoneColor.WHITE)
        omokGrid.putStone(blackStone)
        omokGrid.putStone(whiteStone)

        // when
        val actual = omokGrid.getStoneColorByPoint(Point(Row(1), Column(2)))
        val actual2 = omokGrid.getStoneColorByPoint(Point(Row(2), Column(2)))
        val expected = StoneColor.BLACK
        val expected2 = StoneColor.WHITE

        // then
        assertThat(actual).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    @DisplayName("해당 좌표에 돌이 없으면 null을 반환한다")
    fun getStoneColor_whenNoStoneExistsAt() {
        // when
        val actual = omokGrid.getStoneColorByPoint(Point(Row(1), Column(2)))

        // then
        assertThat(actual).isNull()
    }

    @Test
    @DisplayName("오목 판이 다 차면 true를 반환한다")
    fun checkBoardIsFull() {
        // given
        (1..15).forEach { row ->
            (1..15).forEach { column ->
                omokGrid.putStone(
                    Stone(Point(Row(row), Column(column)), StoneColor.BLACK),
                )
            }
        }

        // when
        val actual = omokGrid.isFull()

        // then
        assertThat(actual).isTrue()
    }
}
