package view

import tools.aqua.bgw.core.BoardGameApplication
import service.MainService

class SwimApplication : BoardGameApplication("SwimGame"), Refreshable {

    private val mainService = MainService()

    private val startGameMenuScene = StartGameMenuScene(mainService).apply {
        quitButton.onMouseClicked = {
            exit()
        }
    }

    private val gameScene = GameTableScene(mainService)


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

    override fun refreshHandCards() {
    }

    override fun refreshPlayerLabel() {

    }

    override fun refreshMiddleCard() {

    }

    override fun refreshAfterMove() {

    }

    override fun refreshAfterGameEnd() {
        val game = mainService.currentGame
        checkNotNull(game)
        this.showMenuScene(rankingScene)

        for (player in game.playerList) {
            startGameMenuScene.nameList.remove(player.name)
        }


    }


}




