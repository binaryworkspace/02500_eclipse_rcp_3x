package com.binaryworkspace.rcp.customcommands.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Back handler is a 'stub' command handler used to demonstrate how to create
 * custom commands. This handler simply reports that it was called as a
 * System.out.println() to the Console View during runtime.
 * 
 * @author Chris Ludka
 * 
 */
public class BackHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		/*
		 * This handler simply reports that it was called as a
		 * System.out.println() to the Console View during runtime.
		 */
		System.out.println("The Back handler was executed.");
		return null;
	}

}
