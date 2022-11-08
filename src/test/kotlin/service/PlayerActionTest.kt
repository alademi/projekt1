package service

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class PlayerActionTest {
    private val playerList = listOf("Aziz", "Ahmed", "Ali", "Sam")

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

    @Test
    fun testPass()
    {
        val game = MainService()
        game.startNewGame(playerList)
        assertEquals(game.currentGame!!.passCount,0)
        game.playerActionService.pass()
        assertEquals(game.currentGame!!.passCount,1)
    }

}