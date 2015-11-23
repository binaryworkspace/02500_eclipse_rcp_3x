package com.binaryworkspace.rcp.customcommands.commands;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.services.IServiceLocator;

/**
 * A convenience enumeration that can be used to access internal Eclipse RCP
 * Framework CommandContributionItems that can be used to contribute to menus
 * and toolbars.
 * 
 * @see http://blog.vogella.com/2009/12/03/commands-menu-runtime/
 * @see http://shinych.blogspot.com/2007/05/eclipse-shared-images.html
 * 
 * @author Chris Ludka
 * 
 */
public enum EclispeCommandContributionItemType {
	ABOUT("", "org.eclipse.ui.help.aboutAction", SWT.PUSH, "About", PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK)), //
	EXIT("", "org.eclipse.ui.file.exit", SWT.PUSH, "Exit", PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE)), //
	PREFERENCES("", "org.eclipse.ui.window.preferences", SWT.PUSH, "Preferences", PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD)); //

	private final String id;
	private final String commandId;
	private final int style;
	private final String label;
	private final ImageDescriptor imageDescriptor;

	private Map<String, CommandContributionItem> commandContributionItemMap = new LinkedHashMap<String, CommandContributionItem>();

	private Set<IServiceLocator> serviceLocatorSet = new LinkedHashSet<IServiceLocator>();

	private EclispeCommandContributionItemType(String id, String commandId, int style, String label, ImageDescriptor imageDescriptor) {
		this.id = id;
		this.commandId = commandId;
		this.style = style;
		this.label = label;
		this.imageDescriptor = imageDescriptor;
	}

	/**
	 * Returns the CommandContributionItem for a given IServiceLocator.
	 * 
	 * @param serviceLocator
	 * @return
	 */
	public CommandContributionItem getCommandContributionItemType(IServiceLocator serviceLocator) {
		/*
		 * Since the IServiceLocator is provided as a parameterization argument
		 * it cannot be guaranteed to be the same instance between calls.
		 * Therefore, examine if the unique pairing of {IServiceLocator,
		 * commandId} has already been encountered and if so do not create
		 * another instance of the CommandContributionItem. Otherwise, create an
		 * instance of CommandContributionItem and retain the {IServiceLocator,
		 * commandId} pairing.
		 */
		if (!serviceLocatorSet.contains(serviceLocator) || !commandContributionItemMap.containsKey(commandId)) {
			// Create a CommandContributionItemParameter
			CommandContributionItemParameter p = new CommandContributionItemParameter(serviceLocator, id, commandId, style);
			p.label = label;
			p.icon = imageDescriptor;

			/*
			 * Create the CommandContributionItem and retain the
			 * {IServiceLocator, commandId} pairing
			 */
			CommandContributionItem item = new CommandContributionItem(p);
			commandContributionItemMap.put(commandId, item);
			serviceLocatorSet.add(serviceLocator);
		}
		return commandContributionItemMap.get(commandId);
	}
}
