package com.binaryworkspace.rcp.customcommands;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.binaryworkspace.rcp.customcommands.commands.ApplicationCommandType;
import com.binaryworkspace.rcp.customcommands.perspectives.Perspective;

/**
 * Configures the workbench to set default the perspective to PERSPECTIVE_ID and
 * save user settings from the previous session.
 * 
 * @author Chris Ludka
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = Perspective.class.getName();

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);

		// Save the Workbench state between user sessions.
		configurer.setSaveAndRestore(true);

		// Initialize Commands
		for (ApplicationCommandType cmd : ApplicationCommandType.values()) {
			if (!cmd.isDefined()) {
				throw new RuntimeException(cmd.getCommand().getId() + " is not defined.");
			}
		}
	}
}
