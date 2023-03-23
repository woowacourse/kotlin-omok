package woowacourse.omok.model

import java.io.Serializable

enum class StoneColorModel(val text: String, val value: Int) : Serializable {
    BLACK("흑", 0),
    WHITE("백", 1),
}
