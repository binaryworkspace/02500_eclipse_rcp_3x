package com.binaryworkspace.rcp.customcommands;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.services.IServiceLocator;

import com.binaryworkspace.rcp.customcommands.commands.ApplicationCommandContributionItemType;
import com.binaryworkspace.rcp.customcommands.commands.EclispeCommandContributionItemType;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 * 
 * This class is modified to use programmatic contributions rather then
 * declarative contributions from Extensions in the plugin.xml file.
 * 
 * @author Chris Ludka
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private final String MENU_FILE = "File";

	private final String MENU_WINDOW = "Window";

	private final String TOOLBAR_WINDOW_ID = "TOOLBAR_WINDOW_ID";

	private final IServiceLocator serviceLocator;

	/**
	 * Actions - important to allocate these only in makeActions, and then use
	 * them in the fill methods. This ensures that the actions aren't recreated
	 * when fillActionBars is called with FILL_PROXY.
	 * 
	 * This is an extension of org.eclipse.ui.application.ActionBarAdvisor. All
	 * top-level workbench menu and toolbars should be configured here.
	 * 
	 * @see org.eclipse.ui.application.ActionBarAdvisor
	 * 
	 * @param configurer
	 */
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);

		/*
		 * Retain a reference to the IServiceLocator in order to obtain
		 * IContributionItems when building toolbars and menus later in the
		 * ApplicationActionBarAdvisor's lifecycle.
		 */
		serviceLocator = configurer.getWindowConfigurer().getWindow();
	}

	/**
	 * @see org.eclipse.ui.application.ActionBarAdvisor#fillCoolBar(org.eclipse.jface.action.ICoolBarManager)
	 */
	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		/**
		 * Clear 'old' items.
		 * <p>
		 * Note (in the conversion from Eclipse 3.x to 4.x with compatibility
		 * layer back to 3.x) the toolbars are being persisted (see
		 * configurer.setSaveAndRestore(true)) into the workbench.xmi file. This
		 * results in old command contributions will throw a
		 * NullPointerException on their handlers.
		 * <p>
		 * To work around this issue be sure all 'old' contributions are removed
		 * from the toolbar and then add the new contributions.
		 */
		int length = coolBar.getItems().length;
		for (int i = 0; i < length; i++) {
			IContributionItem[] items = coolBar.getItems();
			coolBar.remove(items[0]);
		}

		/**
		 * Note (in the conversion from 3.x to 4.x with compatibility layer back
		 * to 3.x) that an ID must be provided to the ToolBarContributionItem in
		 * order to prevent duplication contributions, e.g. TOOLBAR_WINDOW_ID.
		 */
		// Window Toolbar
		IToolBarManager manager = new ToolBarManager();
		manager.add(ApplicationCommandContributionItemType.BACK.getCommandContributionItemType(serviceLocator));
		manager.add(ApplicationCommandContributionItemType.FORWARD.getCommandContributionItemType(serviceLocator));
		manager.add(EclispeCommandContributionItemType.PREFERENCES.getCommandContributionItemType(serviceLocator));
		manager.add(EclispeCommandContributionItemType.ABOUT.getCommandContributionItemType(serviceLocator));
		coolBar.add(new ToolBarContributionItem(manager, TOOLBAR_WINDOW_ID));
	}

	/**
	 * @see org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		// File
		IMenuManager manager = new MenuManager(MENU_FILE);
		manager.add(EclispeCommandContributionItemType.EXIT.getCommandContributionItemType(serviceLocator));
		menuBar.add(manager);

		// Window
		manager = new MenuManager(MENU_WINDOW);
		manager.add(ApplicationCommandContributionItemType.BACK.getCommandContributionItemType(serviceLocator));
		manager.add(ApplicationCommandContributionItemType.FORWARD.getCommandContributionItemType(serviceLocator));		
		manager.add(EclispeCommandContributionItemType.PREFERENCES.getCommandContributionItemType(serviceLocator));
		manager.add(EclispeCommandContributionItemType.ABOUT.getCommandContributionItemType(serviceLocator));
		menuBar.add(manager);
	}

}
