package woowacourse.omok.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.data.History
import woowacourse.omok.data.OmokHistory
import woowacourse.omok.omok.model.testDouble.FakeOmokHistoryStorage

class OmokHistoryTest {
    @Test
    fun `오목의 게임 히스토리를 가져올 수 있다`() {
        val histories = listOf(History("BLACK", 0, 0))
        val omokHistory = OmokHistory(FakeOmokHistoryStorage(histories))
        assertThat(omokHistory.fetch()).isEqualTo(listOf(History("BLACK", 0, 0)))
    }

    @Test
    fun `오목의 게임 히스토리를 추가할 수 있다`() {
        val omokHistory = OmokHistory(FakeOmokHistoryStorage())
        omokHistory.add(History("BLACK", 0, 0))
        assertThat(omokHistory.fetch()).isEqualTo(listOf(History("BLACK", 0, 0)))
    }

    @Test
    fun `오목의 게임 히스토리를 초기화할 수 있다`() {
        val histories = listOf(History("BLACK", 0, 0))
        val omokHistory = OmokHistory(FakeOmokHistoryStorage(histories))
        omokHistory.clear()
        assertThat(omokHistory.fetch()).isEqualTo(emptyList<History>())
    }
}
