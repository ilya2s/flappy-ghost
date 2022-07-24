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
        }
    }
}
