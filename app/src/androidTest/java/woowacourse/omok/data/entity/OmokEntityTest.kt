package woowacourse.omok.data.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import woowacourse.omok.data.db.OmokEntity
import woowacourse.omok.data.db.toEntity
import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.exception.RendjuExceptions
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.stone.StoneColor

class OmokEntityTest {
    @Nested
    @DisplayName("OmokEntity -> Point 변환 테스트")
    inner class ToDomainModelTest {
        @Test
        @DisplayName("BLACK는 BoardStatus.Moved(StoneColor.BLACK)로 변환되어야 한다")
        fun `BalckStoneEntityTest`() {
            // given
            val entity = OmokEntity(row = 3, column = 5, stone = OmokEntity.BLACK_STONE)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Moved::class.java)
            assertThat((point.status as BoardStatus.Moved).color).isEqualTo(StoneColor.BLACK)
            assertThat(point.x.value).isEqualTo(5)
            assertThat(point.y.value).isEqualTo(3)
        }

        @Test
        @DisplayName("WHITE는 BoardStatus.Moved(StoneColor.WHITE)로 변환되어야 한다")
        fun `WhiteStoneEntityTest`() {
            // given
            val entity = OmokEntity(row = 4, column = 6, stone = OmokEntity.WHITE_STONE)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Moved::class.java)
            assertThat((point.status as BoardStatus.Moved).color).isEqualTo(StoneColor.WHITE)
        }

        @Test
        @DisplayName("DOUBLE_THREE_EXCEPTIONS는 BoardStatus.Blocked(DoubleThreeExceptions)로 변환되어야 한다")
        fun `DoubleThreeTest`() {
            // given
            val entity = OmokEntity(row = 2, column = 7, stone = OmokEntity.DOUBLE_THREE_EXCEPTIONS)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Blocked::class.java)
            assertThat((point.status as BoardStatus.Blocked).cause).isEqualTo(RendjuExceptions.DoubleThreeExceptions)
        }

        @Test
        @DisplayName("DOUBLE_FOUR_EXCEPTIONS는 BoardStatus.Blocked(DoubleFourExceptions)로 변환되어야 한다")
        fun `DoubleFourTest`() {
            // given
            val entity = OmokEntity(row = 2, column = 7, stone = OmokEntity.DOUBLE_FOUR_EXCEPTIONS)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Blocked::class.java)
            assertThat((point.status as BoardStatus.Blocked).cause).isEqualTo(RendjuExceptions.DoubleFourExceptions)
        }

        @Test
        @DisplayName("OVER_LINE_EXCEPTIONS BoardStatus.Blocked(OverLineExceptions)로 변환되어야 한다")
        fun `OverLineTest`() {
            // given
            val entity = OmokEntity(row = 2, column = 7, stone = OmokEntity.OVER_LINE_EXCEPTIONS)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Blocked::class.java)
            assertThat((point.status as BoardStatus.Blocked).cause).isEqualTo(RendjuExceptions.OverLineExceptions)
        }

        @Test
        @DisplayName("stone이 null이면 BoardStatus.Empty로 변환되어야 한다")
        fun `EmptyStoneTest`() {
            // given
            val entity = OmokEntity(row = 1, column = 1, stone = null)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Empty::class.java)
        }
    }

    @Test
    @DisplayName("Point(StoneColor.BLACK)가 OmokEntity 변환 테스트")
    fun `PointToEntityTest`() {
        // given
        val point =
            Point(
                x = Column(8),
                y = Row(3),
                status = BoardStatus.Moved(StoneColor.BLACK),
            )

        // when
        val entity = point.toEntity()

        // then
        assertThat(entity.stone).isEqualTo(OmokEntity.BLACK_STONE)
        assertThat(entity.row).isEqualTo(3)
        assertThat(entity.column).isEqualTo(8)
    }

    @Test
    @DisplayName("Point(BoardStatus.Empty)가 OmokEntity(stone=null)로 변환 테스트")
    fun `EmptyPointToEntityTest`() {
        // given
        val point =
            Point(
                x = Column(2),
                y = Row(5),
                status = BoardStatus.Empty,
            )

        // when
        val entity = point.toEntity()

        // then
        assertThat(entity.stone).isNull()
    }
}
