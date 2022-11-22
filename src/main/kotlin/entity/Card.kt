package entity

/**
 * Diese Data-Klasse ist dafür zutändig , das Spielelement [Card] darzustellen .
 *
 * Durch die Attribute number(CardValue) und symbol(CardSuit) lässt sich ein Objekt dieser Klasse eindeutig erkennen .
 */

data class Card(
    val number: CardValue,
    val symbol: CardSuit
)