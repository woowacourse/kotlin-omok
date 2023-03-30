package domain

enum class Team {
    BLACK {
        override fun previous(): Team = WHITE

        override fun next(): Team = WHITE
    },
    WHITE {
        override fun previous(): Team = BLACK

        override fun next(): Team = BLACK
    };

    abstract fun previous(): Team
    abstract fun next(): Team

    companion object {
        inline fun <V> associateWith(valueSelector: (Team) -> V): Map<Team, V> =
            Team.values().associateWith(valueSelector)
    }
}