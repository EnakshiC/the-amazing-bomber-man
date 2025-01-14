package de.tum.cit.ase.bomberquest.map.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.map.basic_tiles.DestructibleWall;
import de.tum.cit.ase.bomberquest.map.basic_tiles.IndestructibleWall;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

import java.util.*;

/**
 * The smartest Enemy on the field. It stays still until it 'smells' the player (read: has a free path to the player).
 * When a free path is available, an A* algorithm searches for the closest path to the player and starts hunting.
 *
 * SOURCE: The Wikipedia entry for the A* algorithm was used as a inspiration and for understanding of the algorithm:
 * <a href="https://de.wikipedia.org/wiki/A">A*-Algorithm on Wikipedia.de</a> - Source in German
 */
public class EnemySmartSearcher extends Enemy {

    private final GameMap gameMap;

    /**
     * Constructs a Smart Enemy object in the game world.
     *
     * @param world   The physics world in which the enemy resides, used to manage collisions and dynamics.
     * @param x       The x-coordinate of the enemy's initial position in the game world.
     * @param y       The y-coordinate of the enemy's initial position in the game world.
     * @param gameMap The game map where the enemy exists, providing context for positioning and interactions.
     */
    public EnemySmartSearcher(World world, float x, float y, GameMap gameMap) {
        super(world, x, y, gameMap);

        this.gameMap = gameMap;
    }

    @Override
    public void tick(float deltaTime) {
        super.tick(deltaTime);
        super.currentDirection = findPathToPlayer(gameMap);
    }

    private Direction findPathToPlayer(GameMap gameMap) {
        int startX = Math.round(super.getX());
        int startY = Math.round(super.getY());
        int targetX = Math.round(this.gameMap.getPlayer().getX());
        int targetY = Math.round(this.gameMap.getPlayer().getY());

        // System.out.println("Player X: " + targetX + " Player Y: " + targetY);
        // System.out.println("Start X: " + startX + " Start Y: " + startY);

        // If we are already at the target --> success --> exit
        if (startX == targetX && startY == targetY) {
            return Direction.NONE;
        }

        // Define open list for A* algorithm
        // These are nodes that we already found a path to, but we are not sure if it is the shortest/best one yet
        // Elements are sorted for ascending fCost
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(node -> node.fCost));

        // Define close list for A* algorithm
        // These are the nodes that we already know the shortest path to for sure
        Set<Node> closedList = new HashSet<>();

        // Initialize the start node
        // 0 cost to go there = gCost, hCost = Manhattan Distance, no parent
        Node startNode = new Node(startX, startY, null, 0, heuristic(startX, startY, targetX, targetY));
        // Add it to open List
        openList.add(startNode);

        // While the openList still has element, we will continue to search for new optimal paths
        while (!openList.isEmpty()) {
            // We always start/continue with the node with the lowest fCost
            Node currentNode = openList.poll();

            // We add it to the closed list
            closedList.add(currentNode);

            // If we reached our target at this node, we need to trace our steps back along the path to find our start direction
            if (currentNode.x == targetX && currentNode.y == targetY) {
                return findFirstDirectionAlongBestPath(currentNode);
            }

            // If we are not at the target currently, we explore the neighbours to our currentNode
            for (Direction direction : Direction.values()) {
                // If no directions are available, move on
                if (direction == Direction.NONE) continue;

                // Get coordinates of neighbour
                int neighbourX = currentNode.x + Direction.dx(direction);
                int neighbourY = currentNode.y + Direction.dy(direction);

                // Skip if the neighbour cannot be passed or if it is already in the closed list
                if (!isPassable(neighbourX, neighbourY, this.gameMap) || closedList.contains(new Node(neighbourX, neighbourY))) {
                    continue;
                }

                // Calculate the cost to get to this neighbour node
                int gCost = currentNode.gCost + 1;
                // Calculate the heuristic hCost to target - again with Manhattan Distance
                int hCost = heuristic(neighbourX, neighbourY, targetX, targetY);
                // Create new neighbour node
                Node neighbourNode = new Node(neighbourX, neighbourY, currentNode, gCost, hCost);

                // Finally we add this Node to the openList if it is not already present OR has lower cost
                if (!openList.contains(neighbourNode) || gCost < neighbourNode.gCost) {
                    openList.add(neighbourNode);
                }
            }
        }

        return Direction.NONE;
    }

    /**
     * Returns the simple Manhattan Distance between two points
     */
    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Check if a tile is passable
     */
    private boolean isPassable(int x, int y, GameMap gameMap) {
        for (Drawable element : gameMap.getWallElements()) {
            if (Math.round(element.getX()) == x && Math.round(element.getY()) == y) {
                // Return false for IndestructibleWall and solid DestructibleWall
                if (element instanceof IndestructibleWall) return false;
                if (element instanceof DestructibleWall && ((DestructibleWall) element).isSolid()) return false;
            }
        }
        return true;
    }

    /**
     * Determines the first direction to take along a path to a target node.
     * This method navigates up the chain of parent nodes from the given target node
     * to identify the second node along the computed path, calculates the relative
     * position to determine the movement direction, and returns it.
     *
     * @param targetNode the target node in the pathfinding grid, representing the
     *                   destination to which the best path has been calculated.
     * @return the first direction to take along the best path as a {@code Direction}
     *         enum value. Returns {@code Direction.NONE} if the direction cannot
     *         be determined.
     */
    private Direction findFirstDirectionAlongBestPath(Node targetNode) {
        Node currentNode = targetNode;

        while (currentNode.parent != null && currentNode.parent.parent != null) {
            currentNode = currentNode.parent;
        }

        // We found the second node from the start along our path
        // Find deltaX and deltaY next
        assert currentNode.parent != null;
        int deltaX = currentNode.x - currentNode.parent.x;
        int deltaY = currentNode.y - currentNode.parent.y;

        // If we already move, the enemy should only cut a corner when it is in the middle of a tile
        if (currentDirection != Direction.NONE) {
            // If enemy is currently moving up or down, but should go left or right next,
            // Makes sure that it only changes direction in the middle of the tile.
            // Same goes for left or right...
            if ((currentDirection == Direction.UP || currentDirection == Direction.DOWN) && (deltaX == 1 || deltaX == -1)) {
                if (Math.abs(Math.round(getY()) - getY()) > 0.02f) return currentDirection;
            } else if ((currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) && (deltaY == 1 || deltaY == -1)) {
                if (Math.abs(Math.round(getX()) - getX()) > 0.02f) return currentDirection;
            }
        }

        // Return current direction based on the deltaX/deltaY
        if (deltaX == -1) return Direction.LEFT;
        if (deltaX == 1) return Direction.RIGHT;
        if (deltaY == 1) return Direction.UP;
        if (deltaY == -1) return Direction.DOWN;

        return Direction.NONE;
    }

    /**
     * Helper class to represent a node in the grid
     */
    private static class Node {
        int x, y;
        Node parent;
        // Cost from start to this node
        int gCost;

        // Estimated cost from this node to target
        int hCost;

        // fValue/fCost = total cost
        int fCost; // Total cost

        Node(int x, int y) {
            this(x, y, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        Node(int x, int y, Node parent, int gCost, int hCost) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }

        /**
         * Compares this node to the specified object for equality.
         * Two nodes are considered equal if their x and y coordinates are the same.
         *
         * @param o the object to compare with this node for equality
         * @return true if the specified object is equal to this node, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node node)) return false;
            return x == node.x && y == node.y;
        }

        /**
         * Returns the hash code value for this node based on its x and y coordinates.
         * The hash code is computed using the {@code Objects.hash} method.
         *
         * @return the hash code value for this node
         */
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    @Override
    protected TextureRegion getStandingFrame(float elapsedTime) {
        return Animations.BAT_ENEMY_WALK_DOWN.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkUpFrame(float elapsedTime) {
        return Animations.BAT_ENEMY_WALK_UP.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkDownFrame(float elapsedTime) {
        return Animations.BAT_ENEMY_WALK_DOWN.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkLeftFrame(float elapsedTime) {
        return Animations.BAT_ENEMY_WALK_LEFT.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkRightFrame(float elapsedTime) {
        return Animations.BAT_ENEMY_WALK_RIGHT.getKeyFrame(elapsedTime, true);
    }
}
