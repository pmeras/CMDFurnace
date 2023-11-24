package pmeras.cmdfurnace;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
    public static Player joueur;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        joueur = (Player) sender;
        isCookable(sender);
        return false;
    }

    private static void isCookable(CommandSender sender)
    {

        int charbon = (joueur.getInventory().getItemInMainHand().getAmount() % 8) + 1;
        removeItem(joueur,new ItemStack(Material.COAL),charbon);
        //On check s'il a du charbon sur lui
        if(!(joueur.getInventory().contains(Material.COAL)))
        {

            return;
        }
        ItemStack baseItem = new ItemStack((joueur.getInventory().getItemInMainHand()));
        for(int i = 1;i < recipes.size();++i)
        {

            if(baseItem.getType().name().toLowerCase().equals(recipes.get(i)[1]))
            {
                ItemStack cookedItem = new ItemStack(Material.matchMaterial(recipes.get(i)[2]), baseItem.getAmount());
                joueur.getInventory().setItemInMainHand(cookedItem);
                joueur.giveExp((Math.round(baseItem.getAmount() * Float.parseFloat(recipes.get(i)[5]))));
                break;
            }
        }
    }

    public static void removeItem(Player player, ItemStack itemStack, int quantity) {
        Inventory inventory = player.getInventory();

        // Vérifie si le joueur a suffisamment de l'objet dans son inventaire
        if (inventory.containsAtLeast(itemStack, quantity))
        {
            // Retire l'objet de l'inventaire du joueur
            inventory.removeItem(itemStack);
            player.sendMessage(quantity + " " + itemStack.getType() + "(s) retiré(s) de votre inventaire.");
        }
        else
        {
            joueur.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Vous n'avez pas assez de charbon sur vous !"));
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
