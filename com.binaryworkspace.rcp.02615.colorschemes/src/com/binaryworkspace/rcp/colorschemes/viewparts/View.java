package com.binaryworkspace.rcp.colorschemes.viewparts;

import java.util.Arrays;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.binaryworkspace.rcp.colorschemes.resources.SwtFunctionBasedColorSchemes;
import com.binaryworkspace.rcp.colorschemes.resources.SwtImagesSilk;
import com.binaryworkspace.rcp.colorschemes.resources.SwtPointBasedColorSchemes;

/**
 * A JFace TableViewer that will be used to demonstrate the basics for SWT
 * Fonts, Icons/Images and Colors.
 * 
 * @author Chris Ludka
 * 
 */
public class View extends ViewPart {

	public static final String ID = View.class.getName();

	public static final int MAX_ROW_COUNT = 25;

	private boolean isPointBased = true;

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
			// Assign cell styles
			int rowIndex = Arrays.asList(viewer.getTable().getItems()).indexOf(cell.getItem());
			
			cell.setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_WHITE));
			float percentage = (float) rowIndex / (float) (MAX_ROW_COUNT - 1);
			if (isPointBased) {
				cell.setText(Integer.toString(rowIndex) + ": Point Based");
				cell.setImage(SwtImagesSilk.RECORD_BLUE.image());
				cell.setBackground(SwtPointBasedColorSchemes.getVisibleSpectrum(percentage));
			} else {
				cell.setText(Integer.toString(rowIndex) + ": Function Based");
				cell.setImage(SwtImagesSilk.RECORD_GREEN.image());
				cell.setBackground(SwtFunctionBasedColorSchemes.getVisibleSpectrum(percentage));
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
		refresh();
	}

	private void refresh() {
		Runnable timer = new Runnable() {

			@Override
			public void run() {
				if (!viewer.getTable().isDisposed()) {
					// Switch mode
					isPointBased = !isPointBased;

					// Provide the input to the ContentProvider
					String tab = "\t";
					StringBuilder sb = new StringBuilder();
					boolean isFirst = true;
					for (int i = 0; i < MAX_ROW_COUNT; i++) {
						if (isFirst) {
							isFirst = false;
						} else {
							sb.append(tab);
						}
						sb.append(i);
					}
					viewer.setInput(sb.toString().split(tab));
					refresh();
				}
			}
		};

		// Loop in milliseconds
		PlatformUI.getWorkbench().getDisplay().timerExec(2000, timer);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}