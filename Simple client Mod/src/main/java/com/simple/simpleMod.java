package com.simple;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.command.ICommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

@Mod(modid = SimpleMod.MODID, name = SimpleMod.NAME, version = SimpleMod.VERSION)
public class SimpleMod {
    public static final String MODID = "simple";
    public static final String NAME = "简单模组";
    public static final String VERSION = "1.0";

    // 定义快捷键
    public static final KeyBinding keyToggleAutoSprint = new KeyBinding("key.simplemod.toggleAutoSprint", Keyboard.KEY_S, "key.categories.simplemod");
    public static final KeyBinding keyToggleKillArea = new KeyBinding("key.simplemod.toggleKillArea", Keyboard.KEY_R, "key.categories.simplemod");
    public static final KeyBinding keyToggleScaffold = new KeyBinding("key.simplemod.toggleScaffold", Keyboard.KEY_Z, "key.categories.simplemod");

    // 功能状态变量
    private boolean isAutoSprintEnabled = false;
    private boolean isKillAreaEnabled = false;
    private boolean isScaffoldEnabled = false;

    // 模组初始化事件
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // 注册快捷键
        ClientRegistry.registerKeyBinding(keyToggleAutoSprint);
        ClientRegistry.registerKeyBinding(keyToggleKillArea);
        ClientRegistry.registerKeyBinding(keyToggleScaffold);

        // 注册服务器命令
        event.getServer().registerCommand(new FlightCommand());
    }

    // 客户端每帧事件处理
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        // 切换自动疾跑状态
        if (keyToggleAutoSprint.isPressed()) {
            toggleAutoSprint();
        }
        // 如果自动疾跑开启且玩家在地面上，则设置疾跑状态
        if (isAutoSprintEnabled && Minecraft.getMinecraft().player.onGround) {
            Minecraft.getMinecraft().player.setSprinting(true);
        }

        // 切换杀戮区域状态
        if (keyToggleKillArea.isPressed()) {
            toggleKillArea();
        }
        if (isKillAreaEnabled) {
                
                // 添加一个随机对象来模拟玩家行为
                private final Random rand = new Random();
                // 添加一个计时器来控制攻击频率
                private int attackTimer = 0;
                private final int attackCooldown = 20; // 攻击冷却时间

                @SubscribeEvent
                public void onPlayerTick(TickEvent.PlayerTickEvent event) {
                    EntityPlayer player = event.player;
                    World world = player.world;

                    // 只在玩家的主线程和玩家活跃时执行
                    if (event.phase == TickEvent.Phase.END && !player.isSpectator()) {
                        // 检查攻击计时器是否冷却完毕
                        if (attackTimer > 0) {
                            attackTimer--;
                        } else {
                            attackTimer = attackCooldown;

                            // 检测4格内的实体
                            List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().expand(4.0, 4.0, 4.0));
                            for (Entity entity : entities) {
                                // 确保实体不是太远，也不是玩家自己
                                if (entity.getDistanceToEntity(player) <= 4.0 && entity != player) {
                                    // 模拟玩家转向
                                    player.getLookHelper().setLookPositionWithEntity(entity, 30.0f, 30.0f);

                                    // 模拟玩家攻击
                                    player.swingArm(EnumHand.MAIN_HAND); // 挥动手臂
                                    player.attackEntityAsMob(entity); // 攻击实体

                                    // 随机等待，模拟玩家攻击间隙
                                    attackTimer += rand.nextInt(5);
                                    break; // 找到一个目标后跳出循环
                                }
                            }
                        }
                    }
                }

        // 切换脚手架状态
        if (keyToggleScaffold.isPressed()) {
            toggleScaffold();
        }
        if (isScaffoldEnabled) {
                // 检查玩家手中是否有方块
                ItemStack heldItem = player.getHeldItemMainhand();
                Block heldBlock = heldItem.getItem() instanceof ItemBlock ? ((ItemBlock) heldItem.getItem()).getBlock() : null;
                
                // 检查玩家脚下是否是空气
                BlockPos playerPos = player.getPosition();
                BlockPos belowPos = playerPos.down();
                if (player.world.isAirBlock(belowPos) && heldBlock != null && heldBlock.isFullBlock(heldBlock.getDefaultState()) == false) {
                    // 放置方块
                    player.world.setBlockState(belowPos, heldBlock.getDefaultState());

                    // 挥动手臂以模拟放置方块的动作
                    player.swingArm(EnumHand.MAIN_HAND);
                }
            }
    }

    // 切换自动疾跑功能
    private void toggleAutoSprint() {
        isAutoSprintEnabled = !isAutoSprintEnabled;
        updateDisplayStatus();
    }

    // 切换杀戮区域功能
    private void toggleKillArea() {
        isKillAreaEnabled = !isKillAreaEnabled;
        updateDisplayStatus();
    }

    // 切换脚手架功能
    private void toggleScaffold() {
        isScaffoldEnabled = !isScaffoldEnabled;
        updateDisplayStatus();
    }

    // 更新显示状态的方法
    private void updateDisplayStatus() {
        Minecraft mc = Minecraft.getMinecraft();
        String statusText = "自动疾跑: " + (isAutoSprintEnabled ? TextFormatting.GREEN + "开启" : TextFormatting.RED + "关闭") 
                         + " | 杀戮区域: " + (isKillAreaEnabled ? TextFormatting.GREEN + "开启" : TextFormatting.RED + "关闭") 
                         + " | 脚手架: " + (isScaffoldEnabled ? TextFormatting.GREEN + "开启" : TextFormatting.RED + "关闭");
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(statusText));
    }

    // 飞行命令实现
    public static class FlightCommand implements ICommand {
        @Override
        public String getCommandName() {
            return "fly";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/" + getCommandName() + " - 切换飞行模式";
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)sender;
                PlayerCapabilities capabilities = player.getEntityWorld().getGameRules().getBoolean("allowFlying") ? player.capabilities : null;
                capabilities.isFlying = !capabilities.isFlying;
                capabilities.setFlySpeed(0.05F);
                player.sendPlayerAbilities();
                player.sendMessage(new TextComponentString("飞行模式 " + (capabilities.isFlying ? "已开启" : "已关闭")));
            }
        }
    }
}
}