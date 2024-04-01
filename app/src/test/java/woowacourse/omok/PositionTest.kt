package omok

import io.kotest.matchers.shouldBe
import omok.fixtures.createPosition
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PositionTest {
    @ParameterizedTest
    @CsvSource(value = ["1:1", "1:2", "15:15", "30:30"], delimiter = ':')
    fun `Position은 좌표가 같으면 동등성 검사가 성립한다`(
        x: Int,
        y: Int,
    ) {
        createPosition(x, y) shouldBe createPosition(x, y)
    }
}
