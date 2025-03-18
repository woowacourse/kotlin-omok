package omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGridTest {
    @Test
    fun `보드는 15x15 크기를 가진다`() {
        val omokGrid = OmokGrid()

        assertThat(omokGrid.board.size).isEqualTo(15)
        assertThat(omokGrid.board.first().size).isEqualTo(15)
    }
}
