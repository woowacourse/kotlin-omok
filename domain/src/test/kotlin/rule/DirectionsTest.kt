package rule

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.wrapper.direction.Directions

class DirectionsTest {
    @Test
    fun `더 이상 반환할 방향이 없으면 에러를 발생한다`() {
        val iterator = Directions().iterator()
        assertThrows<IllegalStateException> {
            while (true) {
                iterator.next()
            }
        }
    }
}
