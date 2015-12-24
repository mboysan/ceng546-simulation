/**
 * Factory class that produces <code>Rogue</code> objects
 * @see Rogue
 * @author Mehmet Can Boysan
 */
public class RogueFactory extends SoldierFactory {

    public RogueFactory() {
    }

    /**
     * Produces <code>Rogur</code> objects.
     * @see Rogue
     * @param x                     X position of the Knight
     * @param y                     Y position of the Knight
     * @param health                Current and Maximum health of the Knight
     * @param normalHitPoint        Normal hit damage
     * @param specialHitPoint       Special hit damage
     * @param specialSkillRadius    Radius of the Knight's special skill
     * @param normalAttackRadius    Radius of the Knight's special skill
     * @param speed                 Knight's speed
     * @param assignedTeam          Team of the knight that is assigned to
     * @return  new Rogue object
     */
    @Override
    public Knight produce(double x, double y, int health, int normalHitPoint, int specialHitPoint,
                          int specialSkillRadius, int normalAttackRadius, int speed,
                          Team assignedTeam)
    {
        return new Rogue(x,y,health,normalHitPoint,specialHitPoint,specialSkillRadius,normalAttackRadius,
                speed,assignedTeam);
    }
}