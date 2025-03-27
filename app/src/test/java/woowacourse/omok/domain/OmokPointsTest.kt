package woowacourse.omok.domain

import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.point.OmokPoints
import woowacourse.omok.fixture.blackAByOne
import woowacourse.omok.fixture.blackBByTwo
import woowacourse.omok.fixture.blackCByThree
import woowacourse.omok.fixture.blackEByEight

class OmokPointsTest {
    @Test
    fun `movedPoints는 Empty가 아닌 Point만 반환해야 한다`() {
        val omokPoints = OmokPoints()
        omokPoints.moveStone(blackAByOne)
        omokPoints.moveStone(blackBByTwo)
        omokPoints.moveStone(blackCByThree)

        assertThat(omokPoints.movedPoints).allMatch { it.status != BoardStatus.Empty }
    }

    @Test
    fun `combine은 기존 Point를 업데이트하고 새로운 Point를 추가해야 한다`() {
        val omokPoints = OmokPoints()
        omokPoints.moveStone(blackAByOne)

        omokPoints.combine(listOf(blackBByTwo, blackCByThree, blackEByEight))

        assertThat(omokPoints.movedPoints).containsExactly(
            blackAByOne,
            blackBByTwo,
            blackCByThree,
            blackEByEight,
        )
    }

    @Test
    fun `clear는 points를 초기화해야 한다`() {
        val omokPoints = OmokPoints()
        omokPoints.moveStone(blackAByOne)
        omokPoints.moveStone(blackBByTwo)
        omokPoints.moveStone(blackCByThree)
        omokPoints.clear()

        val result = omokPoints.movedPoints
        result shouldBe emptyList()
    }
}
