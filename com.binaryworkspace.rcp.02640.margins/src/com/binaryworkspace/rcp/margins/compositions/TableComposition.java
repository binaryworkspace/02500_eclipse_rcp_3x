package com.binaryworkspace.rcp.margins.compositions;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.binaryworkspace.rcp.margins.core.compositions.Composition;
import com.binaryworkspace.rcp.margins.core.resources.ColorType;
import com.binaryworkspace.rcp.margins.core.resources.Margins;
import com.binaryworkspace.rcp.margins.resources.SwtColors;
import com.binaryworkspace.rcp.margins.resources.SwtFonts;
import com.binaryworkspace.rcp.margins.resources.SwtImagesSilk;

/**
 * A demonstration composition featuring a table that can be styled either as
 * Bold or Normal text.
 * 
 * @author Chris Ludka
 * 
 */
public class TableComposition implements Composition {

	private TableViewer tableViewer;

	private boolean useBoldCellText;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			// Do nothing.
		}

		public void dispose() {
			// Do nothing.
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			return new Object[0];
		}
	}

	private class TableLabelProvider extends StyledCellLabelProvider {

		/**
		 * @see org.eclipse.jface.viewers.StyledCellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
		 */
		@Override
		public void update(ViewerCell cell) {
			if (useBoldCellText) {
				// Style for Bold
				cell.setText("BOLD");
				cell.setFont(SwtFonts.TEXT_BOLD.font());
				cell.setImage(SwtImagesSilk.DELETE.image());
				cell.setBackground(SwtColors.color(ColorType.RED));
			} else {
				// Style for Normal
				cell.setText("Normal");
				cell.setFont(SwtFonts.TEXT.font());
				cell.setImage(SwtImagesSilk.ADD.image());
				cell.setBackground(SwtColors.color(ColorType.GREEN));
			}
			super.update(cell);
		}
	}

	/**
	 * Builds a table composition with either bold or normal cell text styling
	 * on the given composite.
	 * 
	 * @param parent
	 * @param useBoldCellText
	 */
	public TableComposition(Composite parent, boolean useBoldCellText) {
		// Bold Cell Text
		this.useBoldCellText = useBoldCellText;

		// Table Viewer
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tableViewer.setContentProvider(new ViewContentProvider());
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.getTable().setLayout(new FormLayout());
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, Margins.TOP_MARGIN.margin());
		formData.bottom = new FormAttachment(100, Margins.BOTTOM_MARGIN.margin());
		formData.left = new FormAttachment(0, Margins.LEFT_MARGIN.margin());
		formData.right = new FormAttachment(100, Margins.RIGHT_MARGIN.margin());
		tableViewer.getTable().setLayoutData(formData);
	}

	@Override
	public void init() {
		// Provide the input to the ContentProvider
		String tab = "\t";
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (int i = 0; i < 100; i++) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(tab);
			}
			sb.append(i);
		}
		tableViewer.setInput(sb.toString().split(tab));
	}

	@Override
	public void dispose() {
		// Do nothing
	}

}