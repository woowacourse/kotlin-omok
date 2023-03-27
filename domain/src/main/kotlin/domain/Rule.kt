package domain

interface Rule {
    fun getWinner(stones: Stones): Color?
}
