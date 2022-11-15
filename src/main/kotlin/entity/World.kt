package entity

/**
 * In dieser Data-Klasse werden die wichtigen Daten zur Erstellung eines Weltes im Spiel gespeichert .
 *
 * @param middlecards Die 3 mittleren Karten
 * @param movecount Zähler zum Bestimmen des aktuellen Spieler
 * @param passCount Zähler für das Passen
 * @param players Liste aller Spieler ( min 2 , max 4 )
 * @param cardDeck liste der Karten im Nachziehstapel ( das sind genau 32 Karten )
 */

data class World(
    var middleCards: MutableList<Card> = mutableListOf(),
    var moveCount: Int,
    var passCount: Int,
    var playerList:List<Player>,
    var cardDeck: CardDeckStack
) {


}