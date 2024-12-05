package websocket.commands;

//import chess.model.GameModel;

import model.GameModel;

public class ResignCommand implements GameCommand {
    private final GameModel gameModel;

    public ResignCommand(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void execute() {
        //gameModel.resign(); // Logic to handle resignation
        System.out.print("resign not implemented");
    }
}
