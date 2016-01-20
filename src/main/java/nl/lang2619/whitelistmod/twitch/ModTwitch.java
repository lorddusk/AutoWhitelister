package nl.lang2619.whitelistmod.twitch;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListWhitelist;
import nl.lang2619.whitelistmod.config.ModConfig;

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
    private static final HashSet<String> whitelisted = new HashSet<String>();

    public static HashSet<String> getUsers(){
        return users;
    }

    public static void checkWhitelist() {
        try{
            setUsers();
            Whitelisted();
            Iterator<String> names;
            names = users.iterator();

            if (ModConfig.version.equalsIgnoreCase("add")) {

                while (names.hasNext()) {
                    GameProfile gameProfile;
                    String name = names.next();
                    if (!whitelisted.contains(name)) {
                        gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
                        try {
                            MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameProfile);
                        } catch (NullPointerException e) {
                            System.out.println(name + " doesn't exist");
                        }
                    }
                }

            } else if (ModConfig.version.equalsIgnoreCase("sync")) {

                while (names.hasNext()) {
                    GameProfile gameProfile;
                    String name = names.next();
                    if (!whitelisted.contains(name)) {
                        gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
                        try {
                            MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameProfile);
                        } catch (NullPointerException e) {
                            System.out.println(name + " doesn't exist");
                        }
                    }
                }

                Iterator<String> names2;
                names2 = whitelisted.iterator();
                while (names2.hasNext()) {
                    GameProfile gameProfile1;
                    String name2 = names2.next();
                    if (!users.contains(name2)) {
                        gameProfile1 = MinecraftServer.getServer().func_152358_ax().func_152655_a(name2);
                        try {
                            MinecraftServer.getServer().getConfigurationManager().func_152597_c(gameProfile1);
                        } catch (NullPointerException e) {

                        }
                    }
                }

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

    public static void Whitelisted() {
        String[] names = MinecraftServer.getServer().getConfigurationManager().func_152599_k().func_152685_a();

        for (int i = 0; i < names.length; i++) {
            whitelisted.add(names[i].toLowerCase());
        }
    }
}
