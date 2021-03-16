package client;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;

public class SimpleTestClient {

	public static void main(String[] args) {
		final GameEngine gameEngine = new GameEngineImpl();


		 gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		 gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(gameEngine));
		
	}

}
