package omok.model

import io.kotest.matchers.booleans.shouldBeFalse
import omok.fixtures.createBoard
import omok.fixtures.createWhiteStone
import omok.model.rule.WhiteCanPutRule
import org.junit.jupiter.api.Test

class WhitePutRuleTest {
    @Test
    fun `이미 알이 있으면 금수`() {
        val whiteBoard =
            createBoard(
                createWhiteStone(1, 2),
            )
        val whiteStone = createWhiteStone(1, 2)
        // when
        val canPut = WhiteCanPutRule.canPut(whiteStone, whiteBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `범위 밖에 놓으면 금수`() {
        val whiteBoard = createBoard()
        val whiteStone = createWhiteStone(0, 0)
        // when
        val canPut = WhiteCanPutRule.canPut(whiteStone, whiteBoard)
        // then
        canPut.shouldBeFalse()
    }
}
