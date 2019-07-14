package ru.timeconqueror.lootgames.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import ru.timeconqueror.lootgames.LootGames;
import ru.timeconqueror.lootgames.config.LootGamesConfig;
import ru.timeconqueror.lootgames.minigame.ILootGame;

import static ru.timeconqueror.lootgames.world.gen.DungeonGenerator.*;

public class TileEntityPuzzleMaster extends TileEntityEnhanced {
    public void onBlockClickedByPlayer(EntityPlayer player) {
        try {//TODO 1.7.10 -> Change inactive msg
            if (!LootGamesConfig.areMinigamesEnabled) {
                player.sendMessage(new TextComponentTranslation("msg.lootgames.puzzle_master.turned_off"));
                return;
            }

            BlockPos bottomPos = new BlockPos(getPos().add(-PUZZLEROOM_CENTER_TO_BORDER, -PUZZLEROOM_MASTER_TE_OFFSET, -PUZZLEROOM_CENTER_TO_BORDER));
            BlockPos topPos = bottomPos.add(PUZZLEROOM_CENTER_TO_BORDER * 2 + 1, PUZZLEROOM_HEIGHT, PUZZLEROOM_CENTER_TO_BORDER * 2 + 1);

            ILootGame game = LootGames.gameManager.getRandomGame();
            game.generate(world, getPos(), bottomPos, topPos);

            if (world.getTileEntity(getPos()) == this) {
                world.setBlockToAir(getPos());
            }

        } catch (Throwable e) {
            LootGames.logHelper.error(e);
        }
    }
}