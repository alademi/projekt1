package service
import service.*
import entity.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class MainServiceTest {

   @Test
    fun test ()
    {
        val playerlist = listOf<String>("Aziz" , "Ahmed" , "Ali" ,"Sam")
        val game = MainService()
        game.startNewGame(playerlist)
         assertNotNull(game.currentGame)
        assertEquals(game.currentGame!!.playerList[0].name,"Aziz")
        assertNotNull(game.currentGame!!.playerList[0].playerCards)
        assertEquals(game.currentGame!!.playerList[1].playerCards.size,3)
        assertEquals(game.currentGame!!.cardDeck.size,17)

    }


}