package service


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 *  Diese Klasse ist fürs Testen von [MainService]
 */
class MainServiceTest {

    //intializiere eine Liste von Namen , um das Spiel zu starten
    val playerlist = listOf<String>("Aziz", "Ahmed", "Ali", "Sam")


    /**
     * testet die Funktionalität der Methoden in [MainService]
     */
    @Test
    fun test() {

        val game = MainService()
        game.startNewGame(playerlist)
        assertNotNull(game.currentGame)
        assertEquals(game.currentGame!!.playerList[0].name, "Aziz")
        assertNotNull(game.currentGame!!.playerList[0].playerCards)
        assertEquals(game.currentGame!!.playerList[1].playerCards.size, 3)
        assertNotNull(game.currentGame!!.middleCards)
        assertEquals(game.currentGame!!.middleCards.size, 3)
        assertNotNull(game.currentGame!!.cardDeck)
        assertEquals(game.currentGame!!.cardDeck.size, 17)


        val highScore = game.exitGame()
        assertNotNull(highScore)
        val game2 = MainService()
        game2.startNewGame(playerlist)
        game2.exitGame()
        assertTrue(game2.currentGame!!.playerList[0].score >= game2.currentGame!!.playerList[1].score)
        assertTrue(game2.currentGame!!.playerList[1].score >= game2.currentGame!!.playerList[2].score)
        assertTrue(game2.currentGame!!.playerList[2].score >= game2.currentGame!!.playerList[3].score)



    }


}