package nl.lang2619.whitelistmod.sql;

import com.mojang.authlib.GameProfile;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import net.minecraft.server.MinecraftServer;
import nl.lang2619.whitelistmod.config.ModConfig;
import nl.lang2619.whitelistmod.text.TextConfig;

import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tim on 7/9/2014.
 */
public class ModMySQL {
    private static final HashSet<String> users = new HashSet<String>();
    private static final HashSet<String> whitelisted = new HashSet<String>();

    static PreparedStatement preparedStatement = null;
    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;

    static String url = "jdbc:mysql://" + DatabaseConfig.host + ":3306/" + DatabaseConfig.database;
    static String user = DatabaseConfig.username;
    static String password = DatabaseConfig.password;

    public static void checkWhitelist() {


        try {
            setUsers();
            Whitelisted();

            con = DriverManager.getConnection(url, user, password);

            preparedStatement = con.prepareStatement(DatabaseConfig.query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                if (ModConfig.version.equalsIgnoreCase("add")) {
                    GameProfile gameProfile;
                    String name = rs.getString(1).trim().toLowerCase();
                    gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
                    try {
                        MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameProfile);
                    } catch (NullPointerException e) {
                        System.out.println(name + " doesn't exist");
                    }
                }
                if (ModConfig.version.equalsIgnoreCase("sync")) {
                    String name = null;
                    try {
                        GameProfile gameProfile;
                        name = rs.getString(1).trim().toLowerCase();
                        gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
                        MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameProfile);
                    } catch (NullPointerException e) {
                        System.out.println(name + " doesn't exist");
                    }
                    try {
                        Iterator<String> names2;
                        names2 = whitelisted.iterator();
                        while (names2.hasNext()) {
                            GameProfile gameProfile1;
                            String name2 = names2.next();
                            if (!users.contains(name2)) {
                                gameProfile1 = MinecraftServer.getServer().func_152358_ax().func_152655_a(name2);
                                MinecraftServer.getServer().getConfigurationManager().func_152597_c(gameProfile1);
                            }
                        }
                    } catch (Exception e) {

                    }
                }


            }
            MinecraftServer.getServer().getConfigurationManager().loadWhiteList();

        } catch (CommunicationsException ex) {
            Logger logger = Logger.getLogger(ModMySQL.class.getName());
            logger.log(Level.SEVERE, "Can't communicate to the server", ex);
        } catch (Exception ex) {
            Logger logger = Logger.getLogger(ModMySQL.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ModMySQL.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static void Whitelisted() {
        String[] names = MinecraftServer.getServer().getConfigurationManager().func_152599_k().func_152685_a();

        for (int i = 0; i < names.length; i++) {
            whitelisted.add(names[i].toLowerCase());
        }
    }

    private static void setUsers() throws IOException, SQLException {
        con = DriverManager.getConnection(url, user, password);

        preparedStatement = con.prepareStatement(DatabaseConfig.query);
        rs = preparedStatement.executeQuery();

        while(rs.next()){
            String name = rs.getString(1).trim().toLowerCase();
            users.add(name);
        }
    }

}
