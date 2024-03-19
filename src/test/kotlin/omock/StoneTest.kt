package omock

import omock.model.Column
import omock.model.Coordinate
import omock.model.Row
import omock.model.Stone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class StoneTest {
    @Test
    fun `돌은 좌표를 가지고 있다`() {
        val coordinate = Coordinate(row = Row("1"), column = Column("A"))
        val stone = Stone(coordinate)
        Assertions.assertThat(stone.coordinate).isEqualTo(coordinate)
    }
}
