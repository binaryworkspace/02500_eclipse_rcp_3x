package com.binaryworkspace.rcp.customcommands.commands;

import org.eclipse.core.commands.Category;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

/**
 * Enum for command categories.
 * <p>
 * This design conforms to org.eclipse.ui.commands.ICommandService nomenclature.
 * 
 * @author Chris Ludka
 * 
 */
public enum ApplicationCategories {

	DEFAULT("com.binaryworkspace.rcp.customcommands.commands.categories.default", "Default", "Default Category");

	private final String categoryId;
	private final String name;
	private final String description;
	private final Category category;

	/**
	 * Returns the category Id for this category.
	 * 
	 * @return categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * Returns the name for this category.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the description for this category.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the description for this category.
	 * 
	 * @return description
	 */
	public Category getCategory() {
		return category;
	}

	private ApplicationCategories(String categoryId, String name, String description) {
		this.description = description;
		this.name = name;
		this.categoryId = categoryId;
		
		// Define the category
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		category = commandService.getCategory(categoryId);
		if (!category.isDefined()) {
			category.define(name, description);
		}
	}
}