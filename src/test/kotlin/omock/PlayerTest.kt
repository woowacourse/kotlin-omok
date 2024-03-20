package omock

import omock.model.Column
import omock.model.Row
import omock.model.WhitePlayer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PlayerTest {

    @Test
    fun `백돌 플레이어는 원하는 좌표 돌을 뽑는다`() {
        val whiteStone = WhitePlayer.generateStone(row = Row("1"), column = Column("A"))

        Assertions.assertThat(whiteStone.column.comma).isEqualTo("A")
        Assertions.assertThat(whiteStone.row.comma).isEqualTo("1")
    }
}
