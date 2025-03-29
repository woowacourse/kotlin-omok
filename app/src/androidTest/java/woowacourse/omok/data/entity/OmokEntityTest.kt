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
import woowacourse.omok.domain.exception.RendjuException
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.stone.StoneColor

class OmokEntityTest {
    @Nested
    @DisplayName("OmokEntity to Point 변환 테스트")
    inner class ToDomainModelTest {
        @Test
        @DisplayName("BLACK는 BoardStatus.Moved(StoneColor.BLACK)로 변환 되어야 한다")
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
        @DisplayName("WHITE to BoardStatus.Moved(StoneColor.WHITE)로 변환 되어야 한다")
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
        @DisplayName("DOUBLE_THREE_STONE to BoardStatus.Blocked(DoubleThreeExceptions) 변환 되어야 한다")
        fun `DoubleThreeTest`() {
            // given
            val entity = OmokEntity(row = 2, column = 7, stone = OmokEntity.DOUBLE_THREE_STONE)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Blocked::class.java)
            assertThat((point.status as BoardStatus.Blocked).cause).isEqualTo(RendjuException.DoubleThreeException)
        }

        @Test
        @DisplayName("DOUBLE_FOUR_STONE to BoardStatus.Blocked(DoubleFourExceptions) 변환 테스트")
        fun `DoubleFourTest`() {
            // given
            val entity = OmokEntity(row = 2, column = 7, stone = OmokEntity.DOUBLE_FOUR_STONE)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Blocked::class.java)
            assertThat((point.status as BoardStatus.Blocked).cause).isEqualTo(RendjuException.DoubleFourException)
        }

        @Test
        @DisplayName("OVER_LINE_STONE to BoardStatus.Blocked(OverLineExceptions) 변환 테스트")
        fun `OverLineTest`() {
            // given
            val entity = OmokEntity(row = 2, column = 7, stone = OmokEntity.OVER_LINE_STONE)

            // when
            val point = entity.toDomainModel()

            // then
            assertThat(point.status).isInstanceOf(BoardStatus.Blocked::class.java)
            assertThat((point.status as BoardStatus.Blocked).cause).isEqualTo(RendjuException.OverLineException)
        }
    }

    @Nested
    @DisplayName("OmokEntity to Point 변환 테스트")
    inner class ToEntityTest {
        @Test
        @DisplayName("Point(StoneColor.BLACK) to OmokEntity 변환 테스트")
        fun `BlackPointToEntityTest`() {
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
        @DisplayName("Point(StoneColor.WHITE) to OmokEntity 변환 테스트")
        fun `WhitePointToEntityTest`() {
            // given
            val point =
                Point(
                    x = Column(8),
                    y = Row(3),
                    status = BoardStatus.Moved(StoneColor.WHITE),
                )

            // when
            val entity = point.toEntity()

            // then
            assertThat(entity.stone).isEqualTo(OmokEntity.WHITE_STONE)
            assertThat(entity.row).isEqualTo(3)
            assertThat(entity.column).isEqualTo(8)
        }

        @Test
        @DisplayName("3x3 Stone to OmokEntity 변환 테스트")
        fun `DoubleThreePointToEntityTest`() {
            // given
            val point =
                Point(
                    x = Column(8),
                    y = Row(3),
                    status = BoardStatus.Blocked(RendjuException.DoubleThreeException),
                )

            // when
            val entity = point.toEntity()

            // then
            assertThat(entity.stone).isEqualTo(OmokEntity.DOUBLE_THREE_STONE)
            assertThat(entity.row).isEqualTo(3)
            assertThat(entity.column).isEqualTo(8)
        }

        @Test
        @DisplayName("4x4 Stone to OmokEntity 변환 테스트")
        fun `DoubleFourPointToEntityTest`() {
            // given
            val point =
                Point(
                    x = Column(8),
                    y = Row(3),
                    status = BoardStatus.Blocked(RendjuException.DoubleFourException),
                )

            // when
            val entity = point.toEntity()

            // then
            assertThat(entity.stone).isEqualTo(OmokEntity.DOUBLE_FOUR_STONE)
            assertThat(entity.row).isEqualTo(3)
            assertThat(entity.column).isEqualTo(8)
        }

        @Test
        @DisplayName("OverLine Stone to OmokEntity 변환 테스트")
        fun `OverLinePointToEntityTest`() {
            // given
            val point =
                Point(
                    x = Column(8),
                    y = Row(3),
                    status = BoardStatus.Blocked(RendjuException.OverLineException),
                )

            // when
            val entity = point.toEntity()

            // then
            assertThat(entity.stone).isEqualTo(OmokEntity.OVER_LINE_STONE)
            assertThat(entity.row).isEqualTo(3)
            assertThat(entity.column).isEqualTo(8)
        }
    }
}
