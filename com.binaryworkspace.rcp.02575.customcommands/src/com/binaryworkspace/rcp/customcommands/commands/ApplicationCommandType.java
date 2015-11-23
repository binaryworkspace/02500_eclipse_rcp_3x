package com.binaryworkspace.rcp.customcommands.commands;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IParameter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import com.binaryworkspace.rcp.customcommands.commands.handlers.BackHandler;
import com.binaryworkspace.rcp.customcommands.commands.handlers.ForwardHandler;

/**
 * Enum for commands. These commands are specific to use cases of this
 * application's functionality.
 * <p>
 * This design conforms to org.eclipse.ui.commands.Command nomenclature.
 * 
 * @see http://blog.vogella.com/2009/12/03/commands-menu-runtime/
 * 
 * @author Chris Ludka
 * 
 */
public enum ApplicationCommandType {
	BACK(new BackHandler(), ApplicationCommandType.class.getName() + ".BACK", "Back", "Go back.", ApplicationCategories.DEFAULT.getCategory(), PlatformUI.getWorkbench().getSharedImages()
			.getImageDescriptor(ISharedImages.IMG_TOOL_BACK), (IParameter[]) null), //
	FORWARD(new ForwardHandler(), ApplicationCommandType.class.getName() + ".FORWARD", "Forward", "Go forward.", ApplicationCategories.DEFAULT.getCategory(), PlatformUI.getWorkbench().getSharedImages()
			.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD), (IParameter[]) null); //

	private final IHandler handler;
	private final String commandId;
	private final String name;
	private final String description;
	private final Category category;
	private final IParameter[] parameters;
	private final Command command;

	/**
	 * Returns the command.
	 * 
	 * @return command
	 */
	public Command getCommand() {
		if (!isDefined()) {
			throw new RuntimeException(commandId + " is not defined.");
		}
		return command;
	};

	// Helper method to validate that the command has been defined
	public boolean isDefined() {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command commandCheck = commandService.getCommand(commandId);
		if (!command.equals(commandCheck)) {
			throw new RuntimeException(commandId + " was not properly registered -> [actual=" + commandCheck.toString() + ", expected=" + command.toString());
		}
		return commandCheck.isDefined();
	}

	private ApplicationCommandType(IHandler handler, String commandId, String name, String description, Category category, ImageDescriptor enabledIcon, IParameter... parameters) {
		this.handler = handler;
		this.commandId = commandId;
		this.name = name;
		this.description = description;
		this.category = category;
		this.parameters = parameters;

		// Construct the command
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		command = commandService.getCommand(this.commandId);
		if (this.parameters == null) {
			command.define(this.name, this.description, this.category);
		} else {
			command.define(this.name, this.description, this.category, this.parameters);
		}
		command.setHandler(this.handler);
	}
}