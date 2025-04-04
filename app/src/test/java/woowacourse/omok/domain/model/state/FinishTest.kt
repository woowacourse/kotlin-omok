package woowacourse.omok.domain.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.stone.StoneType

class FinishTest {
    lateinit var finish: Finish

    @BeforeEach
    fun setUp() {
        finish = Finish(StoneType.BLACK)
    }

    @Test
    fun `게임이 끝나는 상태를 출력하는 기능 구현`() {
        assertThat(finish.finish()).isInstanceOf(Finish::class.java)
    }

    @Test
    fun `피니쉬는 플레이를 호출할 경우 Turn 타입이 된다`() {
        assertThat(finish.play()).isInstanceOf(Turn::class.java)
    }

    @Test
    fun `피니쉬는 플레이를 호출할 경우 stoneType이 시작돌인 BLACK이 된다 `() {
        assertThat(finish.play().stoneType).isEqualTo(StoneType.BLACK)
    }

    @Test
    fun `피니쉬는 끝난 상태다`() {
        assertThat(finish.isFinished()).isTrue()
    }
}
