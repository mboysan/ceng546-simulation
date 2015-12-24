
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Finds the nearest opponent knight and moves towards it.
 * @author Mehmet Can Boysan
 */
public class AttackClosest extends Strategy {

    private List<Knight> exclusionList;

    /**
     * Constructor
     */
    public AttackClosest()
    {
        exclusionList = new ArrayList<Knight>();
    }

    @Override
    public void act() {

        this.getOwner().performAction();

        if(!this.getOwner().isStrategyOverridden())      //if not explicitly stated by a knight
        {
            Ellipse2D pAssigned = findClosest();
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
     * Recursively finds a knight that is close enough. This is done to prevent knight overlapping.
     * @return  Point(Region) to move
     */
    private Ellipse2D findClosest(){

        int opponentTeamsTotalSize = 0;
        for(Team opponentTeam:this.getOwner().getAssignedTeam().getOpponentTeams()){
            opponentTeamsTotalSize += opponentTeam.getKnights().size();
        }

        if(exclusionList.size() >= opponentTeamsTotalSize)
            return null;

        double closest = Integer.MAX_VALUE;
        Knight candidate = null;
        double selfX = this.getOwner().getX();
        double selfY = this.getOwner().getY();
        for (Team opponentTeams : this.getOwner().getAssignedTeam().getOpponentTeams()) {
            double opX;
            double opY;
            for (Knight opponentKnight : opponentTeams.getKnights()) {
                if(!exclusionList.contains(opponentKnight)) {
                    opX = opponentKnight.getX();
                    opY = opponentKnight.getY();
                    double distance = Point.distance(selfX, selfY, opX, opY);
                    if (distance < closest) {
                        candidate = opponentKnight;
                        closest = distance;
                    }
                }
            }
        }

        if(candidate != null) {
            Ellipse2D pAssigned = new Ellipse2D.Double();
            pAssigned.setFrameFromCenter(
                    candidate.getKnightShape().getCenterX(),
                    candidate.getKnightShape().getCenterY(),
                    candidate.getKnightShape().getCenterX() + Knight.TOUCH_SHAPE_RADIUS,
                    candidate.getKnightShape().getCenterY() + Knight.TOUCH_SHAPE_RADIUS);
            if(pAssigned.intersects(this.getOwner().getTouchShape().getBounds2D())){
                exclusionList.add(candidate);
                return findClosest();
            }else {
                return pAssigned;
            }
        }else {
            return null;
        }
    }
}