package dungeon.engine;
import java.io.Serializable;
import java.util.Random;
/**
 *  Stationary, attacks 2 tiles away in 4 directions, -2hp and +2 Score.
 */
public class RangedMutant implements MapItem, Serializable {

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public String interact (Player player){
        player.addNewScore(2);
        return "You Fought against a ranged mutant, +2 Score!";
    }

    @Override
    public boolean wallBoundary() {
        return false;
    }

    public String tryAttack(Player player, int mutantX, int mutantY, int playerX, int playerY) {
        // Check if player is 1 or 2 tiles away in a cardinal direction
        if ((playerX == mutantX && (Math.abs(playerY - mutantY) == 1 || Math.abs(playerY - mutantY) == 2)) ||
                (playerY == mutantY && (Math.abs(playerX - mutantX) == 1 || Math.abs(playerX - mutantX) == 2))) {

            Random rand = new Random();
            if (rand.nextBoolean()) {  // 50% chance to hit
                player.updateHp(-2);
                return "Ranged Mutant from (" + mutantX + "," + mutantY + ") Hit You! -2 HP.";
            } else {
                return "Ranged Mutant from (" + mutantX + "," + mutantY + ") Missed!";
            }
        }

        return "";
    }

}
