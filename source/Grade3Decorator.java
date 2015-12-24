import java.awt.*;

/**
 * Decoration class extending <code>KnightDecorator</code>
 * @see KnightDecorator
 * @author Mehmet Can Boysan
 */
public class Grade3Decorator extends KnightDecorator {

    public Grade3Decorator(Knight component) {
        this.setComponent(component);
    }

    /**
     * Finds the third region that needs to be decorated.
     */
    @Override
    protected void calculateDecorationShape() {
        double radius = (this.getComponent().getKnightShape().getWidth() / 4);

        double drawStartX = (this.getComponent().getKnightShape().getX()) +
                (this.getComponent().getKnightShape().getWidth() / 2);
        double drawStartY = (this.getComponent().getKnightShape()).getY() +
                (this.getComponent().getKnightShape()).getHeight() + 3*radius;

        this.getDecorationShape().setFrameFromCenter(drawStartX, drawStartY,
                drawStartX + radius,
                drawStartY + radius);
    }

    /**
     * Decorates the knight
     * @param g Graphics object
     */
    @Override
    public void draw(Graphics g) {
        calculateDecorationShape();     //find appropriate region first

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fill(this.getDecorationShape());

        this.getComponent().draw(g2d);
    }
}