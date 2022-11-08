package service

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
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
        val player2 = game.dealerService.getCurrentPlayer()
        val playerCards2 = game.dealerService.getPlayerCards(player2)
        val middleCards2 = game.dealerService.getMiddleCards()
        game.playerActionService.changeAllCards()
        assertEquals(game.currentGame!!.middleCards, playerCards2)
        assertEquals(player2.playerCards, middleCards2)
    }

    @Test
    fun testKnock()
    {
         val game = MainService()
        game.startNewGame(playerList)
        game.playerActionService.knock()
        val player3 = game.dealerService.getPreviousPlayer()
        assertTrue(player3.hasPlayerKnocked)

    }
}