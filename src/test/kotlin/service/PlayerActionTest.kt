package service

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class PlayerActionTest {
    val playerlist = listOf<String>("Aziz", "Ahmed", "Ali", "Sam")

    @Test
    fun test()
    {
        val game = MainService()
        game.startNewGame(playerlist)
        val player = game.dealerService.getCurrentPlayer()
        assertNotNull(player)
        val playerCards = game.dealerService.getPlayerCards(player)
        val middleCards = game.dealerService.getMiddleCards()
        assertNotNull(playerCards)
        assertNotNull(middleCards)
        val pcard = playerCards[0]
        val mcard = middleCards[1]
        assertNotNull(pcard)
        assertNotNull(mcard)
        game.playerActionService.changeSingleCard(pcard,mcard)
        assertEquals(middleCards[2],pcard)
        assertEquals(playerCards[2],mcard)
        val player2 = game.dealerService.getCurrentPlayer()
        val playerCards2 = game.dealerService.getPlayerCards(player2)
        val middleCards2 = game.dealerService.getMiddleCards()
        game.playerActionService.changeAllCards()
        assertEquals(game.currentGame!!.middleCards,playerCards2)
        assertEquals(player2.playerCards,middleCards2)
        game.playerActionService.knock()
        val player3 = game.dealerService.getPreviousPlayer()
        assertTrue(player3.hasPlayerKnocked)

    }
}