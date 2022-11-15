package view

import service.MainService
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

class StartGameMenuScene(private val mainService: MainService) : MenuScene(400, 1080), Refreshable {

    var playerList = mutableListOf<String>()
    var player3 : Boolean = false
    var player4 : Boolean = false
    var addPlayerCounter = 0


    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 50,
        text = "Start New Game",
        font = Font(size = 22)
    )

    private val p1Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 125,
        text = "Player 1:"
    )

    private val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 125,
        text = "Player1"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }

    private val p2Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 170,
        text = "Player 2:"
    )

    private val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 170,
        text = "Player2"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
    }

    private val p3Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 215,
        text = "Player 3:"
    )

    private val p3Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 215,
        text = "Player3"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
        player3 = true
    }

    private val p4Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 260,
        text = "Player 4:"
    )

    private val p4Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 260,
        text = "Player4"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
        player4 = true
    }

    val quitButton = Button(
        width = 140, height = 35,
        posX = 180, posY = 400,
        text = "Quit"
    ).apply {
        visual = ColorVisual(221, 136, 136)
    }



    private val startButton = Button(
        width = 140, height = 35,
        posX = 180, posY = 360,
        text = "Start"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
           mainService.startNewGame(playerList)
        }
    }

    val addPlayerButton = Button(
        width = 140, height = 35,
        posX = 180, posY = 320,
        text = "Add Player"
    ).apply {
        visual = ColorVisual(136,136,221)
        onMouseClicked = {
            addPlayerCounter++
            addPlayer(addPlayerCounter)
        }
    }

    init {
        opacity = .5
        addComponents(
            headlineLabel,
            p1Label, p1Input,
            p2Label, p2Input,
            addPlayerButton,
            startButton, quitButton
        )
    }

    private fun addNames()
    {
        playerList.add(p1Input.text)
        playerList.add(p2Input.text)

        if(player3) {
            playerList.add(p3Input.text)
        }

        if(player4)
        {
            playerList.add(p4Input.text)
        }
    }

    private fun addPlayer(countPlayer : Int)
    {
        if (addPlayerCounter == 1)
        {
            addComponents(p3Label,p3Input)
        }

        if (addPlayerCounter == 2 )
        {
            addComponents(p4Label,p4Input)
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

    override fun refreshGameSetting() {

    }


}