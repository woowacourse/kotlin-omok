package omok.model

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

        val expectedResult = createEdgePlacedBoard()
        assertThat(placementInfo.status).isEqualTo(expectedResult)
    }

    private fun createEdgePlacedBoard() =
        arrayOf(
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, Color.BLACK, null, null, null, null, null, null, null, null, null, null, null, null, null, Color.WHITE),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, Color.BLACK, null, null, null, null, null, null, null, null, null, null, null, null, null, Color.WHITE),
        )
}
