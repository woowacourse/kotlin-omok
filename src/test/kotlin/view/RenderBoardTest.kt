package view

import dto.ColorDTO
import dto.PointDTO
import dto.StoneDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenderBoardTest {
    @Test
    fun `프린트 테스트`() {
        val result = ConsoleRenderBoard().render(
            listOf(
                StoneDTO(ColorDTO.BLACK, PointDTO(0, 0)),
                StoneDTO(ColorDTO.WHITE, PointDTO(0, 1)),
                StoneDTO(ColorDTO.BLACK, PointDTO(1, 0)),
                StoneDTO(ColorDTO.WHITE, PointDTO(1, 1)),
                StoneDTO(ColorDTO.BLACK, PointDTO(2, 0)),
                StoneDTO(ColorDTO.WHITE, PointDTO(2, 1)),
                StoneDTO(ColorDTO.BLACK, PointDTO(3, 0)),
                StoneDTO(ColorDTO.WHITE, PointDTO(3, 1)),
                StoneDTO(ColorDTO.BLACK, PointDTO(4, 0))
            ),
            PointDTO(15, 15)
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
}
