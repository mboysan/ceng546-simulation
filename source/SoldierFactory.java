
/**
 * Base abstract Factory class
 * @author Mehmet Can Boysan
 */
public abstract class SoldierFactory {

    public SoldierFactory(){}

    /**
     * Produces <code>Rogue</code>, <code>Mage</code> or <code>Priest </code> objects.
     * @see Rogue
     * @see Mage
     * @see Priest
     * @param x                     X position of the Knight
     * @param y                     Y position of the Knight
     * @param health                Current and Maximum health of the Knight
     * @param normalHitPoint        Normal hit damage
     * @param specialHitPoint       Special hit damage
     * @param specialSkillRadius    Radius of the Knight's special skill
     * @param normalAttackRadius    Radius of the Knight's special skill
     * @param speed                 Knight's speed
     * @param assignedTeam          Team of the knight that is assigned to
     */
    abstract Knight produce(double x, double y, int health, int normalHitPoint, int specialHitPoint,
                            int specialSkillRadius, int normalAttackRadius, int speed,
                            Team assignedTeam);

}