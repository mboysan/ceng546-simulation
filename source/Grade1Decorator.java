import java.awt.*;

/**
 * Decoration class extending <code>KnightDecorator</code>
 * @see KnightDecorator
 * @author Mehmet Can Boysan
 */
public class Grade1Decorator extends KnightDecorator {

    public Grade1Decorator(Knight component) {
        this.setComponent(component);
    }

    /**
     * Finds the first region that needs to be decorated.
     */
    @Override
    protected void calculateDecorationShape() {
        double radius = (this.getComponent().getKnightShape().getWidth() / 4);

        double drawStartX = (this.getComponent().getKnightShape().getX()) + radius;
        double drawStartY = (this.getComponent().getKnightShape()).getY() +
                (this.getComponent().getKnightShape()).getHeight() + radius;

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
        g2d.setColor(Color.GREEN);
        g2d.fill(this.getDecorationShape());

        this.getComponent().draw(g2d);
    }
}