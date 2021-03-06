package me.olliem5.past.api.util.player;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Credit Yoink for block placement
 * https://github.com/Katatje/Dream/blob/master/src/main/java/cat/yoink/dream/api/util/PlayerUtil.java
 *
 * Credit ionar2 for the facing util
 * https://github.com/ionar2/spidermod/blob/master/src/main/java/me/ionar/salhack/util/entity/PlayerUtil.java
 */

public class PlayerUtil {

    //TODO: Blacklist shulkers & like blocks for placements

    public static void placeBlock(BlockPos pos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {

            if (!Minecraft.getMinecraft().world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(pos)) {

                Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

                float[] old = new float[]{Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch};

                Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec.z - Minecraft.getMinecraft().player.posZ), (vec.x - Minecraft.getMinecraft().player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (Minecraft.getMinecraft().player.posY + (double) Minecraft.getMinecraft().player.getEyeHeight())), (Math.sqrt((vec.x - Minecraft.getMinecraft().player.posX) * (vec.x - Minecraft.getMinecraft().player.posX) + (vec.z - Minecraft.getMinecraft().player.posZ) * (vec.z - Minecraft.getMinecraft().player.posZ)))))), Minecraft.getMinecraft().player.onGround));
                Minecraft.getMinecraft().player.connection.sendPacket(new CPacketEntityAction(Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
                Minecraft.getMinecraft().playerController.processRightClickBlock(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
                Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                Minecraft.getMinecraft().player.connection.sendPacket(new CPacketEntityAction(Minecraft.getMinecraft().player, CPacketEntityAction.Action.STOP_SNEAKING));
                Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], Minecraft.getMinecraft().player.onGround));

                return;
            }
        }
    }

    public static boolean isIntercepted(BlockPos pos) {
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }

    public static int getBlockInHotbar(Block block) {
        for (int i = 0; i < 9; i++) {
            Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().equals(block)) {
                return i;
            }
        }
        return -1;
    }

    public static int getAnyBlockInHotbar() {
        for (int i = 0; i < 9; i++) {
            Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock) {
                return i;
            }
        }
        return -1;
    }

    public static int getItemInHotbar(Item designatedItem) {
        for (int i = 0; i < 9; i++) {
            Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
            if (item instanceof Item && item.equals(designatedItem)) {
                return i;
            }
        }
        return -1;
    }

    public enum FacingDirection {
        NORTH,
        SOUTH,
        EAST,
        WEST,
    }

    public static FacingDirection getFacing() {
        switch (MathHelper.floor((double) (Minecraft.getMinecraft().player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7) {
            case 0:
            case 1:
                return FacingDirection.SOUTH;
            case 2:
            case 3:
                return FacingDirection.WEST;
            case 4:
            case 5:
                return FacingDirection.NORTH;
            case 6:
            case 7:
                return FacingDirection.EAST;
        }
        return FacingDirection.NORTH;
    }
}
