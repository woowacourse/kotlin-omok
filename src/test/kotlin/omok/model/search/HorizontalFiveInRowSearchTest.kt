package omok.model.search

import omok.model.Color
import omok.model.Color.BLACK
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HorizontalFiveInRowSearchTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,13,3,4",
        "WHITE,4,2,2",
    )
    fun `수평 dfs를 할 수 있다`(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        count: Int,
    ) {
        // given
        val horizontalDfs = HorizontalFiveInRowSearch(createPlayingBoard())
        // when
        horizontalDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        Assertions.assertThat(horizontalDfs.count).isEqualTo(count)
    }

    @Test
    fun `가장자리 수평 dfs 테스트`() {
        val horizontalDfs =
            HorizontalFiveInRowSearch(
                arrayOf(
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, BLACK, BLACK, BLACK, BLACK, BLACK, null, null, null, null, null, null, null, null, null, null),
                ),
            )
        horizontalDfs.search(BLACK, 15, 2)
        Assertions.assertThat(horizontalDfs.count).isEqualTo(5)
    }
}
