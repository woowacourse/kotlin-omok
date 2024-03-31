package omok.model.board

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import omock.model.board.buildOmokBoard
import omok.fixtures.createBlackBlock
import omok.fixtures.createBlocks
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OmokBoardBuilderTest {
    @Test
    fun `blocks 을 지정하면 해당 블록만 채워진다`() {
        // given
        val width = 5
        val expectNotEmptyBlocks =
            createBlocks(
                createBlackBlock(1, 1),
                createBlackBlock(2, 2),
            )
        // when
        val board =
            buildOmokBoard {
                size(width)
                blocks(
                    listOf(
                        createBlackBlock(1, 1),
                        createBlackBlock(2, 2),
                    ),
                )
            }
        val notEmptyBlocks = board.blockRecords
        // then
        notEmptyBlocks shouldBe expectNotEmptyBlocks
    }

    @ParameterizedTest
    @CsvSource(value = ["15:16:15", "15:16:15", "15:16:16"], delimiter = ':')
    fun `Board 의 size 를 넘어가면 돌을 놓을 수 없음`(
        size: Int,
        x: Int,
        y: Int,
    ) {
        shouldThrow<IllegalArgumentException> {
            buildOmokBoard {
                size(5)
                blocks(
                    listOf(
                        createBlackBlock(x, y),
                    ),
                )
            }
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["1:0", "0:1", "0:0"], delimiter = ':')
    fun `Board 에 x 가 1보다 작거나, y가 1보다 작으면 돌을 놓을 수 없음`(
        x: Int,
        y: Int,
    ) {
        shouldThrow<IllegalArgumentException> {
            buildOmokBoard {
                blocks(
                    listOf(
                        createBlackBlock(x, y),
                    ),
                )
            }
        }
    }
}
