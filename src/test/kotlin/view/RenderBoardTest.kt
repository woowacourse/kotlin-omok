package view

import org.junit.jupiter.api.Test

class RenderBoardTest {
    @Test
    fun `프린트 테스트`() {
        ConsoleRenderBoard().render(listOf(0 to (1 to 2)), 15 to 15)
    }
}
