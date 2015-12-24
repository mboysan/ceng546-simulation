
import java.awt.geom.Ellipse2D;

public abstract class Strategy {

    private Knight owner;

    public abstract void act();

    public Knight getOwner() {
        return owner;
    }

    public void setOwner(Knight owner) {
        this.owner = owner;
    }

    private boolean checkScreenBounds()
    {
        return this.getOwner().getAssignedTeam().getTeamPlayground().contains(
                this.getOwner().getTouchShape().getBounds2D());
    }

    protected void stepMove(Ellipse2D pointToMove)
    {
        double deltaX = pointToMove.getCenterX() - this.getOwner().getTouchShape().getCenterX();
        double deltaY = pointToMove.getCenterY() - this.getOwner().getTouchShape().getCenterY();

        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double speed = this.getOwner().getSpeed() / Simulation.GAME_LOOP_CURRENT_TIME_STEP;
        deltaX *= speed/magnitude;
        deltaY *= speed/magnitude;

        if(checkScreenBounds() && !this.getOwner().getTouchShape().intersects(pointToMove.getBounds2D())) {
            this.getOwner().setX(this.getOwner().getX() + deltaX);
            this.getOwner().setY(this.getOwner().getY() + deltaY);
        }
    }
}