package omok.study

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import omok.domain.model.position.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MapTest {
    @Test
    fun `데이터 클래스 key로 value 탐색`() {
        val position = Position.of(1, 1, 15)
        val map = mapOf(position to 3)
        assertThat(map[Position.of(1, 1, 15)]).isEqualTo(3)
    }

    @Test
    fun `Map 마지막 요소 참조`() {
        val firstMap = mapOf("One" to 1)
        val secondMap = firstMap + mapOf("Two" to 2)
        val thirdMap = secondMap + mapOf("Third" to 3)

        val lastKey = thirdMap.keys.lastOrNull()
        val lastValue = thirdMap[lastKey]

        assertSoftly {
            lastKey shouldBe "Third"
            lastValue shouldBe 3
        }
    }
}
