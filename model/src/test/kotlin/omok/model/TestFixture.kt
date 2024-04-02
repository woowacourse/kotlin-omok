package omok.model

fun resetPosition(
    target: Any,
    fieldName: String,
) {
    val field = target.javaClass.getDeclaredField(fieldName)

    with(field) {
        isAccessible = true
        set(target, null)
    }
}
