package fruitjuice.cmd;

import fruitjuice.RemoteSession;
import fruitjuice.FruitJuicePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CmdPlayer {
    private final String preFix = "player.";
    private RemoteSession session;
   	private Player attachedPlayer = null;
 	private FruitJuicePlugin plugin;

    public CmdPlayer(RemoteSession session, FruitJuicePlugin plugin) {
        this.session = session;
        this.plugin = plugin;
    }

	 /**
     * Finds a player on the server by their entity ID.
     *
     * @param id The entity ID of the player.
     * @return The Player object if found, otherwise null.
     */
    private Player getPlayerById(int id) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getEntityId() == id) {
                return player;
            }
        }
        return null; // Return null if no player with the given ID is found.
    }

   public void execute(String command, String[] args) {
        // --- Start of Recommended Fix ---

        // 1. Check if the required ID argument was provided.
        if (args.length == 0) {
            session.send("Fail,Required player ID argument is missing.");
            return;
        }

        Player currentPlayer;
        try {
            // 2. Parse the ID, handling potential non-numeric input.
            int playerId = Integer.parseInt(args[0]);
            currentPlayer = getPlayerById(playerId);

        } catch (NumberFormatException e) {
            session.send("Fail,Invalid player ID format. ID must be an integer.");
            return;
        }

        // 3. Check if a player with that ID was actually found.
        if (currentPlayer == null) {
            // Using args[0] here to show the user which ID failed.
            session.send("Fail,No player found with ID: " + args[0]);
            return;
        }
			// player.getTile
		if (command.equals("getTile")) {

			session.send(session.blockLocationToRelative(currentPlayer.getLocation()));

			// player.setTile
		} else if (command.equals("setTile")) {
			String x = args[0], y = args[1], z = args[2];

			//get players current location, so when they are moved we will use the same pitch and yaw (rotation)
			Location loc = currentPlayer.getLocation();
			currentPlayer.teleport(session.parseRelativeBlockLocation(x, y, z, loc.getPitch(), loc.getYaw()));

			// player.getAbsPos
		} else if (command.equals("getAbsPos")) {

			session.send(currentPlayer.getLocation());

			// player.setAbsPos
		} else if (command.equals("setAbsPos")) {
			String x = args[0], y = args[1], z = args[2];

			//get players current location, so when they are moved we will use the same pitch and yaw (rotation)
			Location loc = currentPlayer.getLocation();
			loc.setX(Double.parseDouble(x));
			loc.setY(Double.parseDouble(y));
			loc.setZ(Double.parseDouble(z));
			currentPlayer.teleport(loc);

			// player.getPos
		} else if (command.equals("getPos")) {

			session.send(session.locationToRelative(currentPlayer.getLocation()));

			// player.setPos
		} else if (command.equals("setPos")) {
			String x = args[0], y = args[1], z = args[2];

			//get players current location, so when they are moved we will use the same pitch and yaw (rotation)
			Location loc = currentPlayer.getLocation();
			currentPlayer.teleport(session.parseRelativeLocation(x, y, z, loc.getPitch(), loc.getYaw()));

			// player.setPlayer
		} else if (command.equals("setPlayer")) {
//			String playerName = args[0];
//			getCurrentPlayer(playerName);
			// player.setDirection
		} else if (command.equals("setDirection")) {
			Double x = Double.parseDouble(args[0]);
			Double y = Double.parseDouble(args[1]);
			Double z = Double.parseDouble(args[2]);

			Location loc = currentPlayer.getLocation();
			loc.setDirection(new Vector(x, y, z));
			currentPlayer.teleport(loc);

			// player.getDirection
		} else if (command.equals("getDirection")) {

			session.send(currentPlayer.getLocation().getDirection().toString());

			// player.setRotation
		} else if (command.equals("setRotation")) {
			Float yaw = Float.parseFloat(args[0]);

			Location loc = currentPlayer.getLocation();
			loc.setYaw(yaw);
			currentPlayer.teleport(loc);

			// player.getRotation
		} else if (command.equals("getRotation")) {

			float yaw = currentPlayer.getLocation().getYaw();
			// turn bukkit's 0 - -360 to positive numbers
			if (yaw < 0) yaw = yaw * -1;
			session.send(yaw);

			// player.setPitch
		} else if (command.equals("setPitch")) {
			Float pitch = Float.parseFloat(args[0]);

			Location loc = currentPlayer.getLocation();
			loc.setPitch(pitch);
			currentPlayer.teleport(loc);

			// player.getPitch
		} else if (command.equals("getPitch")) {

			session.send(currentPlayer.getLocation().getPitch());

            // player.getFoodLevel
        } else if (command.equals("getFoodLevel")) {

            session.send(currentPlayer.getFoodLevel());

            // player.setFoodLevel
        } else if (command.equals("setFoodLevel")) {
			Integer foodLevel = Integer.parseInt(args[0]);

			currentPlayer.setFoodLevel(foodLevel);

			// player.getHealth
		} else if(command.equals("getHealth")) {

			session.send(currentPlayer.getHealth());

			// player.setHealth
		} else if(command.equals("setHealth")){
			Double health = Double.parseDouble(args[0]);

			currentPlayer.setHealth(health);

            // player.sendTitle
        } else if (command.equals("sendTitle")) {

			String title = args[0];
			String subTitle = args[1];
			Integer fadeIn = Integer.parseInt(args[2]);
			Integer stay = Integer.parseInt(args[3]);
			Integer fadeOut = Integer.parseInt(args[4]);
			currentPlayer.sendTitle(title, subTitle, fadeIn, stay, fadeOut);

		} else {
//			session.plugin.getLogger().warning(preFix + command + " is not supported.");
			session.send("Fail," + preFix + command + " is not supported.");
		}
	}

}
