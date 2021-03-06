package nl.lang2619.whitelistmod.config;

import net.minecraftforge.common.config.Configuration;
import nl.lang2619.whitelistmod.sql.DatabaseConfig;
import nl.lang2619.whitelistmod.text.TextConfig;
import nl.lang2619.whitelistmod.twitch.TwitchConfig;

import java.io.File;

/**
 * Created by Tim on 7/10/2014.
 */
public class ModConfig {
    private static final String HOST_DEFAULT = "localhost";
    private static final String DATABASE_DEFAULT = "databasename";
    private static final String USERNAME_DEFAULT = "username";
    private static final String PASSWORD_DEFAULT = "password";
    private static final String QUERY_DEFAULT = "SELECT names FROM tablename";
    private static final int TIME_DEFAULT = 5;

    private static final String HOST_KEY = "Set the hostname ( or the ip) of the database.";
    private static final String DATABASE_KEY = "The name for the database.";
    private static final String USERNAME_KEY = "The username for the database.";
    private static final String PASSWORD_KEY = "The password for the database.";
    private static final String QUERY_KEY = "Your select query to get all the names to be added to the whitelist.";
    private static final String TIME_KEY = "Time in minutes you want to have the mod check for new entries.";

    private static final String FUNCTION_TOGGLE = "";
    private static final String FUNCTION_KEY = "Choose your function. 'TEXT'|'MYSQL'|'TWITCH'";

    private static final String TEXT_DEFAULT = "";
    private static final String TEXT_KEY = "Put the full path to the text file in here don't forget to do double slashes, otherwise it will crash. (For example 'C:\\whitelist.txt')";

    private static final String VERSION_DEFAULT = "ADD";
    private static final String VERSION_KEY = "What type of whitelisting do you want? 'ADD' will only add people to the whitelist, while 'SYNC' also removes them if they don't exist anymore.";

    private static final String TWITCH_ID_KEY = "Input the id for the channel you want to check";
    private static final String TWITCH_DEFAULT = "";

    private static final String DATABASE = "MySQL Database";
    private static final String TWITCH = "Twich Whitelist";
    private static final String TEXT = "Text Whitelist";

    public static String toggle;
    public static int time;
    public static String version;




    public static void init(File file){
        Configuration config = new Configuration(file);
        config.load();

        toggle = config.get(config.CATEGORY_GENERAL,FUNCTION_KEY,FUNCTION_TOGGLE).getString();
        time = config.get(config.CATEGORY_GENERAL,TIME_KEY,TIME_DEFAULT).getInt();
        version = config.get(config.CATEGORY_GENERAL,VERSION_KEY,VERSION_DEFAULT).getString();

        DatabaseConfig.host = config.get(DATABASE,HOST_KEY,HOST_DEFAULT).getString();
        DatabaseConfig.database = config.get(DATABASE,DATABASE_KEY,DATABASE_DEFAULT).getString();
        DatabaseConfig.username = config.get(DATABASE,USERNAME_KEY,USERNAME_DEFAULT).getString();
        DatabaseConfig.password = config.get(DATABASE,PASSWORD_KEY,PASSWORD_DEFAULT).getString();
        DatabaseConfig.query = config.get(DATABASE,QUERY_KEY,QUERY_DEFAULT).getString();

        TwitchConfig.id = config.get(TWITCH,TWITCH_ID_KEY,TWITCH_DEFAULT).getString();

        TextConfig.path = config.get(TEXT,TEXT_KEY,TEXT_DEFAULT).getString();

        config.save();
    }
}
