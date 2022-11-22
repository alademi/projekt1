package view

import service.MainService
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class RankingScene(private val mainService: MainService) : MenuScene(400, 1080),
    Refreshable {


    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 50,
        text = "Game Over",
        font = Font(size = 22)
    )

    private val p1Score = Label(width = 300, height = 35, posX = 50, posY = 125)

    private val p2Score = Label(width = 300, height = 35, posX = 50, posY = 160)

    val p3score = Label(width = 300, height = 35, posX = 50, posY = 195)

    val p4score = Label(width = 300, height = 35, posX = 50, posY = 230)


    val quitButton = Button(width = 140, height = 35, posX = 50, posY = 295, text = "Quit").apply {
        visual = ColorVisual(Color(221, 136, 136))
    }

    val newGameButton = Button(width = 140, height = 35, posX = 210, posY = 295, text = "New Game").apply {
        visual = ColorVisual(Color(136, 221, 136))

    }

    init {
        opacity = .5
        addComponents(headlineLabel, p1Score, p2Score, newGameButton, quitButton)
    }

    override fun refreshAfterStartNewGame() {
        if (components.contains(p3score)) {
            removeComponents(p3score)
        }
        if (components.contains(p4score)) {
            removeComponents(p4score)
        }
    }

    override fun refreshHandCards() = Unit

    override fun refreshMiddleCard() = Unit

    override fun refreshAfterMove() = Unit

    override fun refreshAfterGameEnd() {
        val game = mainService.currentGame
        checkNotNull(game) { "No game running" }
        p1Score.text = "${game.playerList[0].name} scored ${game.playerList[0].score}"
        p2Score.text = "${game.playerList[1].name} scored ${game.playerList[1].score}"

        if (game.playerList.size >= 3) {
            p3score.text = "${game.playerList[2].name} scored ${game.playerList[2].score}"
            if (!components.contains(p3score)) {
                addComponents(p3score)
            }
            if (game.playerList.size == 4) {
                p4score.text = "${game.playerList[3].name} scored ${game.playerList[3].score}"
                if (!components.contains(p4score)) {
                    addComponents(p4score)
                }

            }

        }

    }

}