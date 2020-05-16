package dev.foltz.stoneage.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.foltz.stoneage.item.SAItems;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SAScreenCreateWorld extends Screen {

    private CreateWorldScreen createWorldScreen;
    private SAScreenCreateWorld.DetailsList optionList;

    public SAScreenCreateWorld(CreateWorldScreen gui) {
        super(new TranslationTextComponent("createWorld.customize.stoneage.title"));
        createWorldScreen = gui;
    }

    @Override
    protected void init() {
        optionList = new SAScreenCreateWorld.DetailsList();

        this.addButton(new Button(this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel"), (button) -> {
            this.minecraft.displayGuiScreen(this.createWorldScreen);
        }));
    }

    @Override
    public void render(int p1, int p2, float p3) {
        // Render background
        this.renderBackground();
        // Draw custom stuff
        this.optionList.render(p1, p2, p3);
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 8, 16777215);
        // Draw buttons
        super.render(p1, p2, p3);
    }

    @OnlyIn(Dist.CLIENT)
    class DetailsList extends ExtendedList<DetailsList.OptionEntry> {
        public DetailsList() {
            super(SAScreenCreateWorld.this.minecraft, SAScreenCreateWorld.this.width, SAScreenCreateWorld.this.height, 43, SAScreenCreateWorld.this.height - 60, 24);

            this.addEntry(new OptionEntry());
            this.addEntry(new OptionEntry());
        }

        public void setSelected(@Nullable OptionEntry p_setSelected_1_) {
            super.setSelected(p_setSelected_1_);
        }

        protected void moveSelection(int p_moveSelection_1_) {
            super.moveSelection(p_moveSelection_1_);
        }

        protected boolean isFocused() {
            return SAScreenCreateWorld.this.getFocused() == this;
        }

        protected int getScrollbarPosition() {
            return this.width - 70;
        }

        @OnlyIn(Dist.CLIENT)
        class OptionEntry extends ExtendedList.AbstractListEntry<OptionEntry> {
            private OptionEntry() {
            }

            public void render(int p_render_1_, int p_render_2_, int p_render_3_, int p_render_4_, int p_render_5_, int p_render_6_, int p_render_7_, boolean p_render_8_, float p_render_9_) {
                Item item = SAItems.HANDAX_FLINT.get();
                ItemStack itemstack = new ItemStack(item);
                String s = item.getDisplayName(itemstack).getFormattedText();
                this.func_214389_a(p_render_3_, p_render_2_, itemstack);
                SAScreenCreateWorld.this.font.drawString(s, (float)(p_render_3_ + 18 + 5), (float)(p_render_2_ + 3), 16777215);
            }

            private void func_214389_a(int p_214389_1_, int p_214389_2_, ItemStack p_214389_3_) {
                this.func_214390_a(p_214389_1_ + 1, p_214389_2_ + 1);
                GlStateManager.enableRescaleNormal();
                if (!p_214389_3_.isEmpty()) {
                    RenderHelper.enableGUIStandardItemLighting();
                    SAScreenCreateWorld.this.itemRenderer.renderItemIntoGUI(p_214389_3_, p_214389_1_ + 2, p_214389_2_ + 2);
                    RenderHelper.disableStandardItemLighting();
                }

                GlStateManager.disableRescaleNormal();
            }

            private void func_214390_a(int p_214390_1_, int p_214390_2_) {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                SAScreenCreateWorld.DetailsList.this.minecraft.getTextureManager().bindTexture(AbstractGui.STATS_ICON_LOCATION);
                AbstractGui.blit(p_214390_1_, p_214390_2_, SAScreenCreateWorld.this.blitOffset, 0.0F, 0.0F, 18, 18, 128, 128);
            }

            public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
                return false;
            }
        }
    }
}
