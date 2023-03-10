package view

import tools.aqua.bgw.core.BoardGameApplication
import service.MainService

/**
 *  Implementation von [BoardGameApplication] für das Spiel Schwimmen "Swim"
 */
class SwimApplication : BoardGameApplication("SwimGame"), Refreshable {

    //mit mainservice kann man auf das aktuelle Spiel immer zugreifen
    private val mainService = MainService()


    //schließt das Spiel, falls man quitButton anklickt
    private val startGameMenuScene = StartGameMenuScene(mainService).apply {
        quitButton.onMouseClicked = {
            exit()
        }
    }

    private val gameScene = GameTableScene(mainService)

//startet das Spiel wieder , falls auf newGameButton gedrückt wurde
    private val rankingScene = RankingScene(mainService).apply {
        newGameButton.onMouseClicked = {
            this@SwimApplication.showMenuScene(startGameMenuScene)
        }
        quitButton.onMouseClicked =
            {
                exit()
            }
    }


    init {
        mainService.addRefreshables(
            this,
            gameScene,
            startGameMenuScene,
            rankingScene
        )


        // mainService.startNewGame(startGameMenuScene.)
        this.showGameScene(gameScene)
        this.showMenuScene(startGameMenuScene, 0)
    }

    override fun refreshAfterStartNewGame() {
        this.hideMenuScene()
    }

    override fun refreshHandCards() = Unit


    override fun refreshMiddleCard() = Unit

    override fun refreshAfterMove() = Unit

    override fun refreshAfterGameEnd() {
        val game = mainService.currentGame
        checkNotNull(game)
        this.showMenuScene(rankingScene)

        for (player in game.playerList) {
            startGameMenuScene.nameList.remove(player.name)
        }


    }


}




