package pmeras.cmdfurnace;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Commande implements CommandExecutor
{
    private static List<String[]> recipes = new ArrayList<>();
    private final static String LOG = "_Commande_";

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        isCookable(sender);
        return false;
    }

    private static void isCookable(CommandSender sender)
    {
        ItemStack baseItem = new ItemStack(((Player) sender).getInventory().getItemInMainHand());
        for(int i = 1;i < recipes.size();++i)
        {
            if(baseItem.getType().name().toLowerCase().equals(recipes.get(i)[1]))
            {
                ItemStack cookedItem = new ItemStack(Material.matchMaterial(recipes.get(i)[2]), baseItem.getAmount());
                ((Player) sender).getInventory().setItemInMainHand(cookedItem);
                ((Player) sender).giveExp((Math.round(baseItem.getAmount() * Float.parseFloat(recipes.get(i)[5]))));
                break;
            }
        }
    }
    
    public static void initCommande()
    {
        try
        {
            List<String> tmp = Files.readAllLines(Paths.get(System.getProperty("user.dir"),"/plugins/config/recipes.txt").toAbsolutePath());
            String tmpCut;
            for(int i = 0;i < tmp.size();i++)
            {
                tmpCut = tmp.get(i);
                recipes.add(i, tmpCut.split(";"));
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
