package nl.lang2619.whitelistmod.sql;

import com.mojang.authlib.GameProfile;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import net.minecraft.server.MinecraftServer;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tim on 7/9/2014.
 */
public class ModMySQL {
    public static void checkWhitelist() {
        PreparedStatement preparedStatement = null;
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://" + DatabaseConfig.host + ":3306/" + DatabaseConfig.database;
        String user = DatabaseConfig.username;
        String password = DatabaseConfig.password;

        try {
            con = DriverManager.getConnection(url, user, password);

            preparedStatement = con.prepareStatement(DatabaseConfig.query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                GameProfile gameprofile;
                String name = rs.getString(1).trim().toLowerCase();

                gameprofile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
                MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameprofile);
            }
            MinecraftServer.getServer().getConfigurationManager().loadWhiteList();

        } catch(CommunicationsException ex){
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
}
