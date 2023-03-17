package domain.stone

enum class Color {
    BLACK,
    WHITE;

    /**
     * TODO:not 이라는 것을 아래와 같은 형태로 재정의하기 위해서는
     * Color 의 element 가 BLACK 과 WHITE 밖에 존재하지 않을 것이라는 강력한 제약이 있어야할 것 같은데요.
     * enum 은 그러한 제약을 걸 수 없고, 확장에 열려있는 방식인 것 같아요.
     * BLACK 과 WHITE 로 구성되어 있다는 제약을 걸 수 있는 구현 방법이 있을까요?
     */
    operator fun not(): Color = when (this) {
        BLACK -> WHITE
        WHITE -> BLACK
    }
}
