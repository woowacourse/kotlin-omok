package woowacourse.omok.domain.model.player

@JvmInline
value class PlayerName private constructor(
    val value: String = "",
) {
    companion object {
        fun create(name: String? = null): PlayerName {
            val names: List<String> =
                listOf(
                    "공백",
                    "비비",
                    "오이",
                    "메다",
                    "조이",
                    "타마",
                    "제이",
                    "디랙",
                )
            return when {
                name.isNullOrBlank() -> PlayerName(names.random())
                else -> PlayerName(name)
            }
        }
    }
}
