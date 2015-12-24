import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Finds a random point on the knight's team's playground and assigns that point to the knight.
 * @author Mehmet Can Boysan
 */
public class MoveRandom extends Strategy {

    public MoveRandom() {
    }

    /**
     * If there is no point assigned to the Knight object, assigns new random point and moves the knight to that
     * destination until it reaches there
     */
    @Override
    public void act() {

        this.getOwner().performAction();

        if(this.getOwner().getPointAssigned() == null)
        {
            Random random = new Random();
            double minX = this.getOwner().getAssignedTeam().getTeamPlayground().getMinX();
            double maxX = this.getOwner().getAssignedTeam().getTeamPlayground().getMaxX();
            double minY = this.getOwner().getAssignedTeam().getTeamPlayground().getMinY();
            double maxY = this.getOwner().getAssignedTeam().getTeamPlayground().getMaxY();

            double randomX = minX + (maxX - minX) * random.nextDouble();
            double randomY = minY + (maxY - minY) * random.nextDouble();

            Ellipse2D pointAssigned = new Ellipse2D.Double();
            pointAssigned.setFrameFromCenter(randomX, randomY,
                    randomX + Knight.TOUCH_SHAPE_RADIUS, randomY + Knight.TOUCH_SHAPE_RADIUS);
            this.getOwner().setPointAssigned(pointAssigned);
            stepMove(pointAssigned);
        }else {
            if(this.getOwner().getPointAssigned().intersects(this.getOwner().getTouchShape().getBounds2D())){
                findRandomPoint();
            }

            stepMove(this.getOwner().getPointAssigned());
        }
    }

    /**
     * Finds random point on the team's playground
     */
    private void findRandomPoint(){

        Random random = new Random();
        double minX = this.getOwner().getAssignedTeam().getTeamPlayground().getMinX();
        double maxX = this.getOwner().getAssignedTeam().getTeamPlayground().getMaxX();
        double minY = this.getOwner().getAssignedTeam().getTeamPlayground().getMinY();
        double maxY = this.getOwner().getAssignedTeam().getTeamPlayground().getMaxY();

        double randomX = minX + (maxX - minX) * random.nextDouble();
        double randomY = minY + (maxY - minY) * random.nextDouble();

        Ellipse2D pointAssigned = new Ellipse2D.Double();
        pointAssigned.setFrameFromCenter(randomX, randomY,
                randomX + Knight.TOUCH_SHAPE_RADIUS, randomY + Knight.TOUCH_SHAPE_RADIUS);

        this.getOwner().setPointAssigned(pointAssigned);
    }
}