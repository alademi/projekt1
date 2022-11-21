package entity

/**
 * In dieser Data-Klasse werden die wichtigen Daten zur Erstellung eines Weltes im Spiel gespeichert .
 *
 * @param middleCards Die 3 mittleren Karten
 * @param moveCount Zähler zum Bestimmen des aktuellen Spieler
 * @param passCount Zähler für das Passen
 * @param playerList Liste aller Spieler ( min 2 , max 4 )
 * @param cardDeck liste der Karten im Nachziehstapel ( das sind genau 32 Karten )
 */

data class World(
    var middleCards: MutableList<Card> = mutableListOf(),
    var moveCount: Int,
    var passCount: Int,
    var playerList:MutableList<Player> = mutableListOf(),
    var cardDeck: CardDeckStack
) {


}