package com.binaryworkspace.rcp.perspectives.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.binaryworkspace.rcp.perspectives.viewparts.ViewPart_1;
import com.binaryworkspace.rcp.perspectives.viewparts.ViewPart_2;
import com.binaryworkspace.rcp.perspectives.viewparts.ViewPart_3;

/**
 * A Perspective Example using Layout.
 * 
 * @author Chris Ludka
 *
 */
public class Perspective_1 implements IPerspectiveFactory {

	public static final String ID = Perspective_1.class.getName();
	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView(ViewPart_1.ID, IPageLayout.LEFT, 1.0f,layout.getEditorArea());
		layout.addView(ViewPart_2.ID, IPageLayout.LEFT, 0.3f, ViewPart_1.ID);
		layout.addView(ViewPart_3.ID, IPageLayout.BOTTOM, 0.7f, ViewPart_2.ID);
	}
}
