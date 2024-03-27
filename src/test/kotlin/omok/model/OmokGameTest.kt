package omok.model
import omok.library.RenjuRule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `게임 상태가 실행 중인지 확인`() {
        val omokGame = OmokGame(rule = RenjuRule)
        Assertions.assertTrue(omokGame.isRunning())
    }

    @Test
    fun `유효한 값으로 CoordsNumber 객체 생성 성공`() {
        val validCoordsNumber = CoordsNumber.fromNumber(5)
        assertNotNull(validCoordsNumber)
    }

    @Test
    fun `유효하지 않은 값으로 CoordsNumber 객체 생성 실패`() {
        val invalidCoordsNumber = CoordsNumber.fromNumber(15)
        assertNull(invalidCoordsNumber)
    }
}
