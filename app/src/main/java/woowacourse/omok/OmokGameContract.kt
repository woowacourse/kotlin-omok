package woowacourse.omok

import android.provider.BaseColumns

object OmokGameContract {
    // Table contents are grouped together in an anonymous object.
    object Stone : BaseColumns {
        const val TABLE_NAME = "stone"
        const val COLOR = "color"
        const val X = "x"
        const val Y = "y"
    }

    // Stone
    // stoneId
    // color
    // row
    // column

    // Player
    // userId
    // name

    // GameRoom
    // Id
    // roomName
    // BlackPlayerId
    // WhitePlayerId

}