import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Moves the knight to the closest opponent base
 * @author Mehmet Can Boysan
 */
public class MoveToClosestOpponentBase extends Strategy {

    @Override
    public void act()
    {
        this.getOwner().performAction();

        if(!this.getOwner().isStrategyOverridden())     //if not explicitly stated by a knight
        {
            Ellipse2D pAssigned = findClosestOpponentBase();
            if(pAssigned != null){
                stepMove(pAssigned);
            }else if(this.getOwner().getPointAssigned() != null)
                stepMove(this.getOwner().getPointAssigned());
        }
        else if(this.getOwner().getPointAssigned() != null){    //if knight has other objective
            stepMove(this.getOwner().getPointAssigned());
        }
    }

    /**
     * Finds the closest opponent base on the map
     * @return  Point (region) of the opponent base
     */
    private Ellipse2D findClosestOpponentBase()
    {
        double closest = Integer.MAX_VALUE;
        Ellipse2D candidate = null;
        for(Team opTeams: this.getOwner().getAssignedTeam().getOpponentTeams()){
            double distance = Point.distance(opTeams.getBaseShape().getCenterX(), opTeams.getBaseShape().getCenterY(),
                    this.getOwner().getTouchShape().getCenterX(),this.getOwner().getTouchShape().getCenterY());
            if(distance < closest){
                candidate = opTeams.getBaseShape();
                closest = distance;
            }
        }
        return candidate;
    }
}
