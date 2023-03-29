package view

import console.ConsoleRenderBoard
import domain.Board
import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenderBoardTest {
    @Test
    fun `프린트 테스트`() {
        val result = ConsoleRenderBoard().render(
            mapOf(
                StoneDTO(ColorDTO.BLACK, VectorDTO(0, 0)),
                StoneDTO(ColorDTO.WHITE, VectorDTO(0, 1)),
                StoneDTO(ColorDTO.BLACK, VectorDTO(1, 0)),
                StoneDTO(ColorDTO.WHITE, VectorDTO(1, 1)),
                StoneDTO(ColorDTO.BLACK, VectorDTO(2, 0)),
                StoneDTO(ColorDTO.WHITE, VectorDTO(2, 1)),
                StoneDTO(ColorDTO.BLACK, VectorDTO(3, 0)),
                StoneDTO(ColorDTO.WHITE, VectorDTO(3, 1)),
                StoneDTO(ColorDTO.BLACK, VectorDTO(4, 0))
            ),
            VectorDTO(15, 15)
        )
        assertThat(result).isEqualTo(
            "15\t┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐\n" +
                "14\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "13\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "12\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "11\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "10\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "9\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "8\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "7\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "6\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "5\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "4\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "3\t├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "2\t○──○──○──○──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "1\t●──●──●──●──●──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘\n" +
                "    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  \n"
        )
    }

    private fun mapOf(vararg stones: StoneDTO): Map<Int, StoneDTO> {
        return stones.associateBy { vectorToScalar(it.coordinate) }
    }

    private fun vectorToScalar(vector: VectorDTO): Int {
        val stoneY = (Board.BOARD_SIZE.y - vector.y - 1) * Board.BOARD_SIZE.y
        return (stoneY + vector.x)
    }
}
