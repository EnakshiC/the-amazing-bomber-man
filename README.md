# The Amazing Bomber Man

## How to Test and Play the Game
- Run the game and it will start in the MenuScreen
- "Start Game" Button to play with the current settings
  - Will be a "Continue Game" Button when a game is already running
- "End Game" Button ends a game - only if one is running
- Top arrow keys iterate over our pre-set maps
  - "Map 1" and "Map 2" are the ones provided by the project, but a couple of power-ups have been exchanged with our new ones (see below)
  - "Test Extra Time" is a map to test the ExtraTime PowerUp
  - "Test Enemy" is a map to test and see the movement of different enemies
    - There is a line with destructible walls in front of the player on the bottom row. Here are all upgrades waiting for the player. Especially the first three are ExtraTime PowerUps to extend the games time.
  - "Test Extra Speed" is a map to test the ExtraSpeed PowerUp
- - "Load map from file..." opens a native file selector
- Search and select a .properties file
- On opening, the map is created and a game started immediately
- Middle arrow keys:  iterate over different enemy settings
  - More about the different enemies below
  - "RANDOM ENEMY" places a random enemy (of our 4) in the map each time a game is started
  - The other four settings will insert only this enemy on every enemy marked field in the game
- Bottom arrow keys: iterate over different fog of war settings
  - No Fog: map fully visible
  - Easy Fog: fog with a radius around the player of r=5
  - Medium Fog: fog with a radius around the player of r=3
  - Hard Fog: fog with a radius around the player of r=1
  - Extreme Fog: fog with a radius around the player of r=0


**Important: If a game is running, all settings are disabled until the game is ended.** The game can end by either the game mechanics (win/loose) or by pressing Esc (to go to menu) and pressing the "End Game" button.

---

## Explanation of Game Elements and Functionality
The player is controlled on the map via the **Arrow Keys**. He can only move in one of the cardinal directions at a time. The **SPACE** key places a bomb on the current tile, if there is still a bomb left that can be placed. **ESC** opens the menu to end it or continue it again.

### Enemies:
- Basic Enemy 1 (``EnemyWithBasicMovement``): is walking in a straight line until facing a wall, then decides where to go next.
- [Bonus:] Basic Enemy 2 (``EnemyWithDecisiveMovement``): is walking in a straight line, but at every crossing (even if straight is possible), it decides where to go next.
- [Bonus:] Ghost Enemy (``EnemyGhost``): same movement pattern as Basic Enemy 2, but the ghost switches between visible and invisible every 4 seconds. Be careful! You also die if hitting an invisible ghost.
- [Super Bonus] Smart Bat Enemy (``EnemySmartSearcher``): The bat is staying put as long as there is no clear path to the player. As soon as a path is bombed free, the bat starts moving towards the player on an optimized, shortest path (A* algorithm).

---

## Bonus Tasks
The game implements all needed basic functionalities as described in the project requirements. This is a list of all the amazing bonus stuff we added to the game:
- 2 different basic enemies
- A ghost enemy that changes opacity every 4 seconds
- **A smart bat that finds the shortest path to the player via an A\* algorithm**
- A smoke animation when enemies die hitting a bomb blast
- The player is covering his ears as long as there is a bomb on the field or exploding. Extra animations for both standing and walking in all directions.
- **ExtraTimePowerUp** ("7" in -property files): adds another 20 seconds to the remaining game time
- **ExtraSpeedPowerUp** ("8" in .property files): adds another .2 multiplier to the starting speed of the player (1.0 -> 1.2 -> 1.4 -> ...)
- Dynamic timer: the time of the game is dependent on the enemies at the start (20 seconds each) plus 60 seconds to find the exit
- Choose your enemy: select an exclusive enemy type or scatter enemies randomly throughout the map each time!
- Extra Animations for bomb pulsating and multiple stages of bomb explosion
- **Fog of War** in different difficulty settings. Shows only the parts of the map that the player has discovered in different radius around the player. Tiles that are adjacent to discovered tiles are displayed in a semi transparency.

___
## Code Structure
This section describes our code and file structure of the game. It only goes into more detail about the very specific choices of our structure.

This is all in the "core/src/de/tum/cit/ase/bomberquest/..." directory

- audio/
  - The two classes handle the music and sound used in the game
- map/
  - basic_tiles/ [home of all static game elements]
    - ``Wall`` is a abstract class extended by ``IndestructibleWall`` and ``DestructibleWall``
  - bomb/ [home of bomb and explosions]
    - ``BombExplosionTile`` is an enum handling the different animations for all directions of the ``BombExplosion``
    - ``SelfRemovingElement`` is a abstract class inheriting to both ``Bomb`` and ``BombExplosion``. They remove themselves after a certain period of time from the map.
  - enemies/ [home of our enemies]
    - ``Enemy`` is the abstract class inheriting to all our enemies
  - power_up/ [home of our power-ups]
    - ``PowerUp`` is the Abstract class to all PowerUps
    - ``PowerUpEffect`` is the Interface to the actual effects to all PowerUps
  - ``Exit``, ``GameMap`` and ``Player``
- screen/ [home of all screens]
- textures/
  - Holds the pre-defined classes of the project (``Animations``, ``Drawable``, ``SpriteSheet`` and ``Textures``)
  - ``Destroyable`` is an interface to all classes that have a physical body that can be destroy and should therefore be removed as a body from the physical world of the game
- utils/
  - ``GameContactListener``: listens to all collisions in the physical game world and handles all events based on the type of the colliding objects
  - ``HitBoxHelper``: helps with the creation of bodies for all objects having a physical body in the world
  - ``PropertiesHelper``: handles the creation of all elements on the map out of the .property file. Is most often called from the ``GameMap`` constructor and returns enemies, positions, walls, etc. from the currently selected .property file
___
## My Contributions

My contributions focused on gameplay interactions and player experience, including:

* Animation systems
* Gameplay interaction logic
* Fog-of-war implementation
* Menu and game-state transitions
* Player feedback systems

___
## Screenshots
### Main Menu
<p align="center">
  <img width="759" height="415" alt="image" src="https://github.com/user-attachments/assets/d5bd447c-e4a3-4dd4-ae96-a84199e79a95" />
</p>

### Gameplay
<p align="center">
  <img width="758" height="434" alt="image" src="https://github.com/user-attachments/assets/4d68b992-b248-4b41-90bf-1f6ffe7f3b18" />
</p>

### HUD and Exit State
<p align="center">
  <img width="812" height="482" alt="image" src="https://github.com/user-attachments/assets/12a37912-ac5d-4ad6-8e9d-6739a31c9763" />
</p>

### Fog of War
<p align="center">
  <img width="717" height="432" alt="image" src="https://github.com/user-attachments/assets/b52341f1-7030-4a16-a381-8ec2eeaf7226" />
</p>

### Victory Screen
<p align="center">
  <img width="952" height="482" alt="image" src="https://github.com/user-attachments/assets/5478548f-d47c-415c-b95d-24afb63340f2" />
</p>

### Game Over Screen
<p align="center">
  <img width="948" height="486" alt="image" src="https://github.com/user-attachments/assets/12be4808-1418-4025-acb6-01f88171a8b1" />
</p>
___