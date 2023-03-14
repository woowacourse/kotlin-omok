package view

import org.junit.jupiter.api.Test

class RenderBoardTest {
    @Test
    fun `프린트 테스트`() {
        RenderBoard().renderBoard(listOf(0 to (1 to 2)))
    }
}
