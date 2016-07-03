package helper;

public class GameSettings {
    public int initDelay;
    public int startDelay;
    public int delayDiff;
    public int minDelay;
    public int blockSize;
    public int startSize;
    public int playerCount;

    public GameSettings(Integer playerCount){
        this.playerCount = playerCount;
        startDelay = 5000;
        initDelay = 200;
        delayDiff = 10;
        minDelay = 20;
        startSize = 1;
        blockSize = 15;
    }
}
