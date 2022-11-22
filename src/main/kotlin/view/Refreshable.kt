package view


/**
 *  Dieses Interface ist dafür zuständig, der Service-Schicht einen Mechanismus zur Kommunikation mit View-Schicht
 *  bereitzustellen, damit die Änderungen in der Entity-Schicht vorgenommen werden können.
 *  Dabei werden die Methoden dieses Interface nur dann in den View-klassen implementiert, wenn ein bestimmtes Update
 *  in UI nötig ist.
 */


interface Refreshable {

    /**
     *  führt die notwendigen Updates, wenn ein Spiel startet
     */
    fun refreshAfterStartNewGame()

    /**
     *  updatet nach jedem Zug die Karten der Spieler
     */
    fun refreshHandCards()

    /**
     *  updatet nach jedem Zug die mittleren Karten
     */
    fun refreshMiddleCard()

    /**
     *  updatet nach jedem Zug die CardDeck, passCounter und hasPlayerKnocked.
     */
    fun refreshAfterMove()

    /**
     * updated die Punkte der Spieler , wenn das Spiel beendet ist
     */
    fun refreshAfterGameEnd()


}

