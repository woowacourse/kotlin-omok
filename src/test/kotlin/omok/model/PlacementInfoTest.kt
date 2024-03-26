package omok.model

import omok.model.fixture.createEdgePlacedBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlacementInfoTest {
    @Test
    fun `특정 위치에 대한 착수를 진행하면, 올바른 착수 결과를 반환한다`() {
        val placementInfo =
            PlacementInfo().apply {
                markSinglePlace(1, 1, Color.BLACK)
                markSinglePlace(1, 15, Color.WHITE)
                markSinglePlace(15, 1, Color.BLACK)
                markSinglePlace(15, 15, Color.WHITE)
            }

        assertThat(placementInfo.status).isEqualTo(createEdgePlacedBoard())
    }
}
