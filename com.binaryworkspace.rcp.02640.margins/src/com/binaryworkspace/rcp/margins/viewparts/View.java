package com.binaryworkspace.rcp.margins.viewparts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.binaryworkspace.rcp.margins.compositions.TableComposition;
import com.binaryworkspace.rcp.margins.core.resources.ColorType;
import com.binaryworkspace.rcp.margins.core.resources.Margins;
import com.binaryworkspace.rcp.margins.resources.SwtColors;

/**
 * A JFace TableViewer that will be used to demonstrate the basics for SWT
 * Fonts, Icons/Images, Colors and the concept of composition.
 * 
 * @author Chris Ludka
 * 
 */
public class View extends ViewPart {

	public static final String ID = View.class.getName();

	TableComposition tableCompositionNormalCellText;

	TableComposition tableCompositionBoldCellText;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		// Left Hand Side Composite
		Composite lhsComposite = new Composite(parent, SWT.NONE);
		lhsComposite.setBackground(SwtColors.color(ColorType.GREEN_DARK));
		lhsComposite.setLayout(new FormLayout());
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, Margins.TOP_MARGIN.margin());
		formData.bottom = new FormAttachment(100, Margins.BOTTOM_MARGIN.margin());
		formData.left = new FormAttachment(0, Margins.LEFT_MARGIN.margin());
		formData.right = new FormAttachment(50, Margins.RIGHT_MARGIN.margin());
		lhsComposite.setLayoutData(formData);

		// Right Hand Side Composite
		Composite rhsComposite = new Composite(parent, SWT.NONE);
		rhsComposite.setBackground(SwtColors.color(ColorType.RED_DARK));
		rhsComposite.setLayout(new FormLayout());
		formData = new FormData();
		formData.top = new FormAttachment(0, Margins.TOP_MARGIN.margin());
		formData.bottom = new FormAttachment(100, Margins.BOTTOM_MARGIN.margin());
		formData.left = new FormAttachment(lhsComposite, Margins.LEFT_MARGIN.margin());
		formData.right = new FormAttachment(100, Margins.RIGHT_MARGIN.margin());
		rhsComposite.setLayoutData(formData);
		
		// Table Composition for Normal Cell Text
		tableCompositionNormalCellText = new TableComposition(lhsComposite, false);
		tableCompositionNormalCellText.init();

		// Table Composition for Bold Cell Text
		tableCompositionBoldCellText = new TableComposition(rhsComposite, true);
		tableCompositionBoldCellText.init();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// Do nothing
	}

	@Override
	public void dispose() {
		// Call dispose on compositions
		tableCompositionNormalCellText.dispose();
		tableCompositionBoldCellText.dispose();
		super.dispose();
	}
}