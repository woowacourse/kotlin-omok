package woowacourse.omok.view

import android.widget.ImageView
import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import view.RenderBoard
import woowacourse.omok.R

class AndroidRenderBoard(private val board: List<ImageView>) : RenderBoard {
    override fun render(stones: Map<Int, StoneDTO>, size: VectorDTO): String {
        board.forEachIndexed { index, imageView ->
            val stone = stones[index]
            if (stone != null) {
                imageView.setImageResource(stone.color.toResource())
                return@forEachIndexed
            }
        }
        return ""
    }

    private fun ColorDTO.toResource(): Int {
        return when (this) {
            ColorDTO.BLACK -> BLACK_STONE_RESOURCE
            ColorDTO.WHITE -> WHITE_STONE_RESOURCE
        }
    }

    companion object {
        const val BLACK_STONE_RESOURCE = R.drawable.black_stone
        const val WHITE_STONE_RESOURCE = R.drawable.white_stone
    }
}
