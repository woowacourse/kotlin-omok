package woowacourse.omok.model

import java.io.Serializable

enum class StoneColorModel(val text: String, val id: Int) : Serializable {
    BLACK("흑", 0),
    WHITE("백", 1);

    companion object {
        fun calcLatestTurn(sequence: Int): StoneColorModel? = StoneColorModel.values().find {
            it.id == (sequence) % StoneColorModel.values().size
        }
    }
}
