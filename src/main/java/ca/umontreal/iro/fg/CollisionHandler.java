package ca.umontreal.iro.fg;

public interface CollisionHandler {

    private static boolean intersects(Ghost ghost, Ghost other) {
        double dx = ghost.getX() - other.getX();
        double dy = ghost.getY() - other.getY();
        double dSquare = dx * dx + dy * dy;

        return dSquare < (Ghost.RADIUS + Ghost.RADIUS) * (Ghost.RADIUS + Ghost.RADIUS);
    }

    static void handle(Ghost ghost, Ghost other) {
        if (CollisionHandler.intersects(ghost, other)) {
            double sx = ghost.getSx();
            double sy = ghost.getSy();

            ghost.setSx(other.getSx());
            ghost.setSy(other.getSy());

            other.setSx(sx);
            other.setSy(sy);

            // Overlaping distance between the center of the two elements
            double dx = other.getX() - ghost.getX();
            double dy = other.getY() - ghost.getY();
            double d2 = dx * dx + dy * dy;
            double d = Math.sqrt(d2);

            // Overlap in pixels
            double overlap = d - (Ghost.RADIUS + Ghost.RADIUS);

            // Direction of movement
            double directionX = dx / d;
            double directionY = dy / d;

            // movement
            double moveX = directionX * overlap / 2;
            double moveY = directionY * overlap / 2;

            ghost.setX(ghost.getX() + moveX);
            ghost.setY(ghost.getY() + moveY);
            other.setX(other.getX() - moveX);
            other.setY(other.getY() - moveY);

        }
    }
}
