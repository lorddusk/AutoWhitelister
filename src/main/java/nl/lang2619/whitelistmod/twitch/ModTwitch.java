package nl.lang2619.whitelistmod.twitch;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tim on 10/18/2014.
 */
public class ModTwitch {
    private static final HashSet<String> users = new HashSet<String>();

    public static HashSet<String> getUsers(){
        return users;
    }

    public static void checkWhitelist() {
        try{
            setUsers();
            Iterator<String> names;
            names = users.iterator();

            while(names.hasNext()) {
                GameProfile gameProfile;
                gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(names.next());
                MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameProfile);
            }
            MinecraftServer.getServer().getConfigurationManager().loadWhiteList();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setUsers() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://whitelist.twitchapps.com/list.php?id="+ TwitchConfig.id).openStream()));
        String l;

        while((l = in.readLine()) != null){
            users.add(l.trim().toLowerCase());
        }
    }
}
