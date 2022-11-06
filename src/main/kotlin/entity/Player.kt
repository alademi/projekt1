package entity

/**
 * Diese Klasse ist dafür zuständig , das Entity [Player] darzustellen .
 *
 * @param name Name der Spieler
 * @param score Punktezahl der Spieler
 * @param playerCards die 3 Karten der Spieler
 */

data class Player(
    val name: String,
    var score: Double,
    var playerCards: MutableList<Card> = mutableListOf(),
    var hasPlayerKnocked: Boolean ,
) {
}