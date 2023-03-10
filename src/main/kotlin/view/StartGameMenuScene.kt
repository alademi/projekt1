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

/**
 * [StartGameMenuScene] fürs Starten ein neues Spiel. Das wird direkt angezeigt, wenn man [SwimApplication] startet
 * oder Beim anklickén von "new game" in [RankingScene]. Man muss erstmal die Spieler konfigurieren
 * (Anzahl der Spieler bestimmen, Namen eingeben ) und dann [startButton] klicken, um das spiel zu starten.
 * Mit [quitButton] kann man das Spiel schließen.
 */

class StartGameMenuScene(private val mainService: MainService) : MenuScene(600, 800, ImageVisual("StartGame_Box.png")),
    Refreshable {

    var nameList = mutableListOf<String>()
    private var counter = 2


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

            nameList.add(p1Input.text)
            nameList.add(p2Input.text)

            if (counter == 3) {
                nameList.add(p3Input.text)
            }

            if (counter == 4) {
                nameList.add(p3Input.text)
                nameList.add(p4Input.text)
            }

            mainService.startNewGame(nameList)
        }
    }

    private val addPlayerButton = Button(
        width = 50, height = 30,
        posX = 360, posY = 220,
        visual = ImageVisual("StartGame_Number_plus.png")
    ).apply {
        onMouseClicked = {
            if (counter < 4) {
                counter++
            }

            if (counter == 3) {
                addComponents(p3Label)
                addComponents(p3Input)
            }

            if (counter == 4) {
                if (!components.contains(p4Label) && !components.contains(p4Input)) {
                    addComponents(p4Label)
                    addComponents(p4Input)
                }
            }

            numberOfPlayers.text = "$counter"
        }
    }

    private val removePlayerButton = Button(
        width = 50, height = 30,
        posX = 140, posY = 220,
        visual = ImageVisual("StartGame_Number_minus.png")
    ).apply {
        onMouseClicked = {
            if (counter > 2) {
                counter--
            }
            if (counter == 2) {
                removeComponents(p3Label)
                removeComponents(p3Input)
            }

            if (counter == 3) {
                removeComponents(p4Label)
                removeComponents(p4Input)
            }

            numberOfPlayers.text = "$counter"
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
        text = "$counter",
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


    override fun refreshAfterStartNewGame() = Unit

    override fun refreshHandCards() = Unit


    override fun refreshMiddleCard() = Unit

    override fun refreshAfterMove() = Unit

    override fun refreshAfterGameEnd() = Unit


}