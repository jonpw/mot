package com.doober.mot.client.gui;

import java.io.IOException;

import com.doober.mot.MotManagerTileEntity;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiManager extends GuiContainer {

	private GuiButton a;
	private GuiButton b;
	private GuiTextField namedChannel;
	private MotManagerTileEntity tileEntity;

	public GuiManager (MotManagerTileEntity tile, Container container) {
		super(container);
		tileEntity = tile;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
	    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	    namedChannel.drawTextBox();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	@Override
	public void initGui() {
	    this.buttonList.add(this.a = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "This is button a"));
	    this.buttonList.add(this.b = new GuiButton(1, this.width / 2 - 100, this.height / 2 + 4, "This is button b"));
	    namedChannel = new GuiTextField(5, fontRendererObj, this.width /2 -100, this.height + 32, 86,11);
	    namedChannel.setEnableBackgroundDrawing(false);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
	    if (button == this.a) {
	        //Main.packetHandler.sendToServer(...);
	        this.mc.displayGuiScreen(null);
	        if (this.mc.currentScreen == null)
	            this.mc.setIngameFocus();
	    }
	    if (button == this.b){
	        //Main.packetHandler.sendToServer(...);
	        this.mc.displayGuiScreen(null);
	        if (this.mc.currentScreen == null)
	            this.mc.setIngameFocus();
	    }
	}
}
