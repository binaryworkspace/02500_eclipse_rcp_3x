package com.binaryworkspace.rcp.sleak.viewparts;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.binaryworkspace.rcp.sleak.resources.SwtFonts;

/**
 * A JFace TableViewer that will be used to demonstrate the basics for SWT
 * Fonts, Icons/Images and Colors.
 * 
 * @author Chris Ludka
 * 
 */
public class View extends ViewPart {
	
	public static final String ID = View.class.getName();

	private TableViewer viewer;

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
			// Randomly assign cell styles
			if (Math.random() < 0.5) {
				// Font
				cell.setText("BOLD");
				cell.setFont(SwtFonts.TEXT_BOLD.font());
			} else {
				// Font
				cell.setText("Normal");
				cell.setFont(SwtFonts.TEXT.font());
			}

			super.update(cell);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new TableLabelProvider());

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
		viewer.setInput(sb.toString().split(tab));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}