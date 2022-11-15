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

  init {
   mainService.addRefreshables(
    this,
   gameScene,
    startGameMenuScene
   )

   mainService.startNewGame(startGameMenuScene.playerList)
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

 }

 override fun refreshGameSetting() {

 }

}




