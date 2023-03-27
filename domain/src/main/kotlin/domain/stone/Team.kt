package domain.stone

enum class Team {
    BLACK {
        override fun next(): Team = WHITE
    },
    WHITE {
        override fun next(): Team = BLACK
    };

    abstract fun next(): Team
}