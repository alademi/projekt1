package service

import entity.Card

/**
 * eine Klasse von Server-Schicht, um die Logik für die vier möglichen Aktionen eines Spielers zu implementieren
 */
class PlayerActionService(private var mainService: MainService) : RefreshableService() {

    /**
     * Bei der Methode knock wird erstmal überprüft , ob es schon geklopft wurde.
     * falls ja , wird eine Error gezeigt .
     * falls nein , wird geklopft , indem das Variable(hasPlayerKnocked) von dem aktuellen Spieler auf true gesetzt wird.
     * Danach sind alle andere Spieler genau einmal an der Reihe
     * und damit wird das Spiel beendet
     *
     */


    fun knock() {
        val game = mainService.currentGame
        val playerList = game!!.playerList

        for (player in playerList) {
            if (player.hasPlayerKnocked) {
                throw IllegalArgumentException("already knocked")
            }
        }
        val currentPlayer = mainService.dealerService.getCurrentPlayer()
        currentPlayer.hasPlayerKnocked = true
        resetPass()
        mainService.dealerService.endOfMove()
    }

    /**
     * bei dieser Methode wird der Passcounter von dem aktuellen Spieler inkrementiert , falls ein Spieler gepasst hat .
     */
    fun pass() {
        val game = mainService.currentGame
        game!!.passCount++
        mainService.dealerService.endOfMove()
    }

    /**
     * eine Methode zum Austausch einer einzelnen Karte.
     *
     * @param pCard die gewählte Karte von dem Spieler
     * @param mCard die gewählte Karte von der Mitte
     *
     */
    fun changeSingleCard(pCard: Card, mCard: Card) {

        val player = mainService.dealerService.getCurrentPlayer()
        val playerCards = mainService.dealerService.getPlayerCards(player)
        val middleCards = mainService.dealerService.getMiddleCards()

        playerCards.remove(pCard)
        playerCards.add(mCard)
        middleCards.remove(mCard)
        middleCards.add(pCard)

        resetPass()
        mainService.dealerService.endOfMove()
    }

    /**
     * Eine Methode zum Austausch aller Karten.
     * Dabei werden alle Karten der Spieler mit den 3 mittleren Karten ausgetauscht
     */
    fun changeAllCards() {
        val game = mainService.currentGame
      //  val index = game!!.moveCount
        val currentPlayer = mainService.dealerService.getCurrentPlayer()
        val player = mainService.dealerService.getCurrentPlayer()
        val playerCards = mainService.dealerService.getPlayerCards(player)
        val middleCards = mainService.dealerService.getMiddleCards()
        mainService.currentGame!!.middleCards = playerCards
        currentPlayer.playerCards = middleCards
        resetPass()
        mainService.dealerService.endOfMove()
    }

    /**
     * eine Hilfsmethode um den PassCounter zurückzusetzen .
     * wird nur verwendet, falls ein Spieler nicht gepasst hat.
     */
    private fun resetPass() {
        val game = mainService.currentGame
        game!!.passCount = 0
    }

}