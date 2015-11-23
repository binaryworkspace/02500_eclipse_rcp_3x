package com.binaryworkspace.rcp.perspectives.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.binaryworkspace.rcp.perspectives.viewparts.ViewPart_1;
import com.binaryworkspace.rcp.perspectives.viewparts.ViewPart_3;
import com.binaryworkspace.rcp.perspectives.viewparts.ViewPart_4;

/**
 * A Perspective Example using IFolderLayout.
 * 
 * @author Chris Ludka
 *
 */
public class Perspective_2 implements IPerspectiveFactory {

	public static final String ID = Perspective_2.class.getName();
	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView(ViewPart_1.ID, IPageLayout.LEFT, 1.0f,layout.getEditorArea());
		
		// Add a Folder
		IFolderLayout folderReport = layout.createFolder(ID, IPageLayout.RIGHT, 0.5f, ViewPart_1.ID);
		folderReport.addView(ViewPart_3.ID);
		folderReport.addView(ViewPart_4.ID);
	}
}