package service

import entity.Card
import entity.CardDeckStack
import entity.CardValue
import entity.Player
import kotlin.math.max

/**
 * eine Klasse von Server-Schicht, die bei der implementierung der Aktionen von Spieler verwendet wird.
 */

class DealerService(private val mainService: MainService) : RefreshableService() {


    /**
     * eine Methode zur Berechnung der Punkte der 3 Karten
     * @param playerCards die 3 Karten der Spieler
     * gibt die Anzahl Punkte als Double zurück
     */
    fun calculatePoints(playerCards: List<Card>): Double {
        require(playerCards.size == 3) {
            "Every Player should have 3 Cards , was ${playerCards.size}"
        }

        val points: Double
        val card1 = playerCards[0]
        val card2 = playerCards[1]
        val card3 = playerCards[2]
        val cardPoint1 = cardValues(playerCards[0])
        val cardPoint2 = cardValues(playerCards[1])
        val cardPoint3 = cardValues(playerCards[2])

        if (card1.symbol == card2.symbol) {
            if (card2.symbol == card3.symbol) {
                points = cardPoint1 + cardPoint2 + cardPoint3
                return points

            }
            return cardPoint1 + cardPoint2


        } else if (card1.symbol == card3.symbol) {
            return cardPoint1 + cardPoint3


        } else if (card2.symbol == card3.symbol) {
            return cardPoint2 + cardPoint3

        } else {

            if (card1.number == card2.number && card1.number == card3.number) {
                return 30.5

            }
            return max(cardPoint1, max(cardPoint2, cardPoint3))
        }

    }

    /**
     * Hilfsmethode für die Methode [calculatePoints]
     */

    private fun cardValues(card: Card): Double {
        val points = when (card.number) {
            CardValue.SEVEN -> 7.0
            CardValue.EIGHT -> 8.0
            CardValue.NINE -> 9.0
            CardValue.TEN -> 10.0
            CardValue.QUEEN -> 10.0
            CardValue.KING -> 10.0
            CardValue.JACK -> 10.0
            CardValue.ACE -> 11.0
            else -> 0.0
        }
        return points
    }

    /**
     * Diese Methode gibt die mittleren Karten des aktuellen Spiels zurück
     */
    fun getMiddleCards(): MutableList<Card> {
        val game = mainService.currentGame
        return game!!.middleCards
    }

    /**
     * Diese Methode gibt die CardDeck des aktuellen Spiels zurück
     */
    fun getDeck(): CardDeckStack {
        val game = mainService.currentGame
        return game!!.cardDeck
    }

    /**
     * Diese Methode gibt die Karten eines bestimmten Spielers zurück
     * @param player der Spieler, dessen Karten wir suchen.
     */
    fun getPlayerCards(player: Player): MutableList<Card> {
        return player.playerCards
    }

    /**
     * Diese Methode wird nach jeder Spieler Aktion aufgerufen , um das Hotseat-Modus zu erfüllen
     * Dabei wird immer anhand die Hilfsmethode [gameEnd] überprüft , ob das Spiel zu beenden ist.
     * Falls nicht, wird zusätzlich überprüft , dass der Fall (passCounter == Player.list.size ) erfüllt wurde.
     *
     */

    fun endOfMove() {

        val game = mainService.currentGame

        if (!gameEnd()) {
            if (game!!.passCount == game.playerList.size) {
                game.middleCards = game.cardDeck.draw(3)
                game.passCount = 0
            }
            game.moveCount++
            onAllRefreshables { refreshMiddleCard() }
            onAllRefreshables { refreshAfterMove() }
            onAllRefreshables { refreshHandCards() }
        } else {
            mainService.exitGame()
        }
    }

    /**
     *  HilfsMethode, um zu überprüfen, ob das Spiel beendet werden muss.
     */
    private fun gameEnd(): Boolean {
        val game = mainService.currentGame
        if (game!!.passCount == game.playerList.size && game.cardDeck.size <= 2) {
            return true
        }
        if (getNextPlayer().hasPlayerKnocked) {
            return true
        }
        return false
    }

    /**
     *  Diese Methode gibt den aktuellen Spieler zurück
     */
    fun getCurrentPlayer(): Player {
        val game = mainService.currentGame
        val index = game!!.moveCount
        val size = game.playerList.size
        return game.playerList[index % size]
    }


    /**
     *  Diese Methode gibt den vorherigen Spieler zurück
     */
    fun getPreviousPlayer(): Player {
        val game = mainService.currentGame
        val index = game!!.moveCount
        val size = game.playerList.size
        return game.playerList[(index - 1) % size]
    }

    /**
     *  Diese Methode gibt den nächsten Spieler zurück
     */
    fun getNextPlayer(): Player {
        val game = mainService.currentGame
        val index = game!!.moveCount
        val size = game.playerList.size
        return game.playerList[(index + 1) % size]
    }


}