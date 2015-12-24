/**
 * Factory class that produces <code>Mage</code> objects
 * @see Mage
 * @author Mehmet Can Boysan
 */
public class MageFactory extends SoldierFactory {

    public MageFactory() {
    }

    /**
     * Produces <code>Mage</code> objects.
     * @see Mage
     * @param x                     X position of the Knight
     * @param y                     Y position of the Knight
     * @param health                Current and Maximum health of the Knight
     * @param normalHitPoint        Normal hit damage
     * @param specialHitPoint       Special hit damage
     * @param specialSkillRadius    Radius of the Knight's special skill
     * @param normalAttackRadius    Radius of the Knight's special skill
     * @param speed                 Knight's speed
     * @param assignedTeam          Team of the knight that is assigned to
     * @return  new Mage object
     */
    @Override
    public Knight produce(double x, double y, int health, int normalHitPoint, int specialHitPoint,
                          int specialSkillRadius, int normalAttackRadius, int speed,
                          Team assignedTeam)
    {
        return new Mage(x,y,health,normalHitPoint,specialHitPoint,specialSkillRadius,normalAttackRadius,
                speed,assignedTeam);
    }
}