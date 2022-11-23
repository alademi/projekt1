package service

import org.junit.jupiter.api.Test
import kotlin.test.*


/**
 *  Diese Klasse ist fürs Testen von [MainService]
 */

class PlayerActionTest {
    //intializiere eine Liste von Namen , um das Spiel zu starten
    private val playerList = listOf("Aziz", "Ahmed", "Ali", "Sam")


    /**
     * testet die methode SwapOne in [PlayerActionService]
     */
    @Test
    fun testSwapOne() {
        val game = MainService()
        game.startNewGame(playerList)
        val player = game.dealerService.getCurrentPlayer()
        assertNotNull(player)
        val playerCards = game.dealerService.getPlayerCards(player)
        val middleCards = game.dealerService.getMiddleCards()
        assertNotNull(playerCards)
        assertNotNull(middleCards)
        val pCard = playerCards[0]
        val mCard = middleCards[1]
        assertNotNull(pCard)
        assertNotNull(mCard)
        game.playerActionService.changeSingleCard(pCard, mCard)
        assertEquals(middleCards[2], pCard)
        assertEquals(playerCards[2], mCard)
    }

    /**
     * testet die methode SwapAll in [PlayerActionService]
     */
    @Test
    fun testSwapAll() {
         val game = MainService()
        game.startNewGame(playerList)
        val player = game.dealerService.getCurrentPlayer()
        val playerCards = game.dealerService.getPlayerCards(player)
        val middleCards = game.dealerService.getMiddleCards()
        game.playerActionService.changeAllCards()
        assertEquals(game.currentGame!!.middleCards, playerCards)
        assertEquals(player.playerCards, middleCards)
    }

    /**
     * testet die methode Knock in [PlayerActionService]
     */
    @Test
    fun testKnock()
    {
         val game = MainService()
        game.startNewGame(playerList)
        val player1 = game.dealerService.getCurrentPlayer()
        assertFalse(player1.hasPlayerKnocked)
        game.playerActionService.knock()
        val player2 = game.dealerService.getPreviousPlayer()
        assertTrue(player2.hasPlayerKnocked)


    }

    /**
     * testet die methode Pass in [PlayerActionService]
     */
    @Test
    fun testPass()
    {
        val game = MainService()
        game.startNewGame(playerList)
        assertEquals(game.currentGame!!.passCount,0)
        game.playerActionService.pass()
        assertEquals(game.currentGame!!.passCount,1)
        val middleCards1 = game.dealerService.getMiddleCards()
        game.currentGame!!.passCount=game.currentGame!!.playerList.size-1
        game.playerActionService.pass()
        val middleCards2 = game.dealerService.getMiddleCards()
        assertNotEquals(middleCards1,middleCards2)
    }

}