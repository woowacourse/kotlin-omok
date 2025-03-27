package omok.model

import io.kotest.matchers.throwable.shouldHaveMessage
import omok.model.entity.position.DefaultGridElement
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GridElementTest {
    @Test
    fun `0 이상의 값이 아닐 경우 예외를 발생시킨다`() {
        assertThrows<IllegalArgumentException> { DefaultGridElement(-1) }
            .shouldHaveMessage("그리드 요소는 0 이상의 값만 가능합니다. value : -1")
    }
}
