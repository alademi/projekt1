package service

import entity.*
import view.Refreshable

/**
 * Main-Klasse der Service-Schicht für das Spiel Schwimmen . Diese Klasse dient dazu , den Zugriff auf alle
 * anderen Services-Klassen zu ermöglichen und die Daten des aktuellen Spiels zu halten
 */

class MainService() : RefreshableService() {

    /**
     * das aktuelle Spiel is in dem Variable currentGame gepeichert .
     * wäre null , falls das Spiel noch nicht gestartet ist
     */
    var currentGame: World? = null
    var playerActionService = PlayerActionService(this)
    var dealerService = DealerService(this)
    var cardStack: CardDeckStack? = null

    /**
     * startet ein neues Spiel
     * @param namesList eine liste mit den Namen der Spieler
     */
    fun startNewGame(namesList: List<String>) {
        val cardDeck = defaultCardDeck()
        cardStack = CardDeckStack()
        cardStack!!.putOnTop(cardDeck)
        cardStack!!.shuffle()
        val middleCards = cardStack!!.draw(3)
        val playerList = playerSetting(namesList)
        val passCount = 0
        val moveCount = 0
        val game = World(middleCards, moveCount, passCount, playerList, cardStack!!)
        currentGame = game

        onAllRefreshables { refreshAfterStartNewGame() }

    }

    /**
     * Hilfsmethode für die methode [startNewGame] , um [World.cardDeck] mit allen möglichen Kombinationen zurückzugeben
     */
    private fun defaultCardDeck() = MutableList(32) { index ->
        Card(
            CardValue.values()[(index % 8) + 5],
            CardSuit.values()[index / 8]
        )
    }.shuffled()

    /**
     * Hilfsmethode für die methode [startNewGame] , um die Spieler zu konfigurieren .
     *
     * @param namesList eine liste mit den Namen der Spieler
     * gibt eine Liste von Spielern mit den angegebenen Namen zurück
     */
    private fun playerSetting(namesList: List<String>): MutableList<Player> {

        val playerList = mutableListOf<Player>()

        for (playerName in namesList) {
            val card = cardStack!!.draw(3)
            val player = Player(playerName, 0.0, card, false)
            playerList.add(player)
        }
        return playerList
    }

    /**
     * am Ende des Spiels wird diese Methode aufgerufen,um die Rangliste des Spiels zu ziegen .
     */
    fun exitGame() {
        highScoreList()
        onAllRefreshables { refreshAfterGameEnd() }
    }

    /**
     * Hilfemethode für die methode [exitGame] , um die Punkte der Spieler zu aktualisieren
     * und die Rangliste zurückzugeben
     */
    private fun highScoreList()  {
        val playersList = currentGame!!.playerList
        for (player in playersList) {
            player.score = dealerService.calculatePoints(player.playerCards)
        }
        playersList.sortByDescending{it.score}
    }



    /**
     * fügt allen Services, die zu MainService verbunden sind , die breitgestellten [re] hinzu
     */
    override fun addRefreshable(re: Refreshable) {
        this.addRefreshable(re)
        playerActionService.addRefreshable(re)
        dealerService.addRefreshable(re)
    }



    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }

}