package entity

data class Card (
    val number : CardValue , val symbol : CardSuit
        )
{
    override fun toString(): String {
        return "$number$symbol"
    }
}