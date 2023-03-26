package woowacourse.omok.home

import woowacourse.omok.data.entity.UserEntity

data class Room(
    val roomId: Int,
    val roomTitle: String,
    val firstUserEntity: UserEntity,
    val secondUserEntity: UserEntity,
)
