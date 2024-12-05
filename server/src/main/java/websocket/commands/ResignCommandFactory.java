package websocket.commands;


import model.GameModel;

public class ResignCommandFactory implements CommandFactory {
    @Override
    public GameCommand create(Object... params) {
        GameModel gameModel = (GameModel) params[0];
        return new ResignCommand(gameModel);
    }
}
