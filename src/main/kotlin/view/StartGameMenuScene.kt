package view

import service.MainService
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color
import kotlin.coroutines.coroutineContext

class StartGameMenuScene(private val mainService: MainService) : MenuScene(600, 800, ImageVisual("StartGame_Box.png")),
    Refreshable {

    var nameList = mutableListOf<String>()
    var player3: Boolean = false
    var player4: Boolean = false
    var addPlayerCounter = 0


    private val p1Label = Label(
        width = 140, height = 35,
        posX = 90, posY = 300,
        visual = ImageVisual("StartGame_Button_p1.png")
    )


    private val p1Input: TextField = TextField(
        width = 180, height = 35,
        posX = 270, posY = 300,
        text = "Player1",
        font = Font(fontStyle = Font.FontStyle.NORMAL, fontWeight = Font.FontWeight.BOLD)
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }

    private val p2Label = Label(
        width = 140, height = 35,
        posX = 90, posY = 350,
        visual = ImageVisual("StartGame_Button_p2.png")
    )

    private val p2Input: TextField = TextField(
        width = 180, height = 35,
        posX = 270, posY = 350,
        text = "Player2",
        font = Font(fontStyle = Font.FontStyle.NORMAL, fontWeight = Font.FontWeight.BOLD)
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
    }


    private val p3Label = Label(
        width = 140, height = 35,
        posX = 90, posY = 400,
        visual = ImageVisual("StartGame_Button_p3.png")
    )

    private val p3Input: TextField = TextField(
        width = 180, height = 35,
        posX = 270, posY = 400,
        text = "Player3",
        font = Font(fontStyle = Font.FontStyle.NORMAL, fontWeight = Font.FontWeight.BOLD)
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }

    }

    private val p4Label = Label(
        width = 140, height = 35,
        posX = 90, posY = 450,
        visual = ImageVisual("StartGame_Button_p4.png")
    )

    private val p4Input: TextField = TextField(
        width = 180, height = 35,
        posX = 270, posY = 450,
        text = "Player4",
        font = Font(fontStyle = Font.FontStyle.NORMAL, fontWeight = Font.FontWeight.BOLD)
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
    }


    val quitButton = Button(
        width = 300, height = 50,
        posX = 130, posY = 680,
        text = "Quit Game",
        font = Font(
            fontWeight = Font.FontWeight.BOLD,
            fontStyle = Font.FontStyle.NORMAL,
            size = 15,
            color = Color.white
        ),
        visual = ColorVisual(Color(227, 23, 54))
    ).apply {

    }


    private val startButton = Button(
        width = 300, height = 50,
        posX = 130, posY = 600,
        visual = ColorVisual(Color(54, 147, 145)),
        text = "Start Game",
        font = Font(
            fontWeight = Font.FontWeight.BOLD,
            fontStyle = Font.FontStyle.NORMAL,
            size = 15,
            color = Color.white
        ),


    ).apply {
        onMouseClicked = {
            addNames()
            mainService.startNewGame(nameList)
        }
    }

    val addPlayerButton = Button(
        width = 50, height = 30,
        posX = 360, posY = 220,
        visual = ImageVisual("StartGame_Number_plus.png")
    ).apply {
        onMouseClicked = {
            addPlayerCounter++
            numberOfPlayers.text = "${addPlayerCounter + 2}"
            addPlayer(addPlayerCounter)
        }
    }

    val removePlayerButton = Button(
        width = 50, height = 30,
        posX = 140, posY = 220,
        visual = ImageVisual("StartGame_Number_minus.png")
    ).apply {
        onMouseClicked = {
           if (addPlayerCounter == 1)
           {
             //  nameList.remove(nameList[2])
               removeComponents(p3Label)
               removeComponents(p3Input)
           }
            if (addPlayerCounter == 2)
            {
               // nameList.remove(nameList[3])
                removeComponents(p4Label)
                removeComponents(p4Input)
            }

        }
    }

    private val numberOfPlayersBox = Label(
        width = 500,
        height = 100,
        posX = 40,
        posY = 170,
        visual = ImageVisual("StartGame_Number_Box.png")
    )

    private val numberOfPlayers = Label(
        width = 50,
        height = 30,
        posX = 250,
        posY = 220,
        text = "${addPlayerCounter + 2}",
        visual = ImageVisual("StartGame_Number_Int2.png"),
        font = Font(fontStyle = Font.FontStyle.OBLIQUE, fontWeight = Font.FontWeight.BOLD)
    )

    private val playersNameBox = Label(
        width = 500,
        height = 300,
        posX = 40,
        posY = 280,
        visual = ImageVisual("StartGame_Name_Box.png")
    )

    init {
        opacity = .5
        addComponents(
            numberOfPlayersBox,
            numberOfPlayers,
            addPlayerButton,
            removePlayerButton,
            playersNameBox,
            p1Label, p1Input,
            p2Label, p2Input,
            startButton,
            quitButton
        )
    }


    private fun addNames() {
        this.nameList.add(p1Input.text)
        this.nameList.add(p2Input.text)

        if (player3) {
            this.nameList.add(p3Input.text)
        }

        if (player4) {
            this.nameList.add(p4Input.text)
        }


    }


    private fun addPlayer(countPlayer: Int) {
        if (addPlayerCounter == 1) {
            player3 = true
            addComponents(p3Label, p3Input)
        }

        if (addPlayerCounter == 2) {
            player4 = true
            addComponents(p4Label, p4Input)
        }
    }


    override fun refreshAfterStartNewGame() {

    }

    override fun refreshHandCards() {
    }

    override fun refreshPlayerLabel() {

    }

    override fun refreshMiddleCard() {

    }

    override fun refreshAfterMove() {
    }

    override fun refreshAfterGameEnd() {
    }


}