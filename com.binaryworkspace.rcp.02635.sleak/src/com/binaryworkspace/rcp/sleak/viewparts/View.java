package com.binaryworkspace.rcp.sleak.viewparts;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.binaryworkspace.rcp.sleak.utils.SleakUtil;

/**
 * A JFace TableViewer that will be used to demonstrate the basics for SWT
 * Fonts, Icons/Images and Colors.
 * 
 * @author Chris Ludka
 * 
 */
public class View extends ViewPart {

	public static final String ID = View.class.getName();

	private final int TABLE_CELL_PADDING = 4;

	private final int TABLE_MAX_PIXEL_WIDTH = 10000;

	private final int MAX_ROW_COUNT = 20;

	private int maxCellTextLength;

	private SleakUtil sleakUtil;

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
			// Simulate text
			int randomLength = (int) (Math.random() * 50);
			StringBuilder cellTextSb = new StringBuilder();
			cellTextSb.append("Random Length (");
			cellTextSb.append(randomLength);
			cellTextSb.append("): ");
			for (int j = 0; j < randomLength; j++) {
				cellTextSb.append("x");
			}

			// Update simulated backend model
			String cellText = cellTextSb.toString();
			cell.setText(cellText);
			if (cellText.length() > maxCellTextLength) {
				maxCellTextLength = cellText.length();
			}
			super.update(cell);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		// TableViewer
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new TableLabelProvider());
		
		// Header
		String[] headers = new String[1];
		headers[0] = "Random Text";
		viewer.setColumnProperties(headers);
		viewer.getTable().setHeaderVisible(true);
		
		// Columns
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText("Random Text");
		column.getColumn().setResizable(true);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new TableLabelProvider());

		// Sleak Util
		sleakUtil = new SleakUtil(PlatformUI.getWorkbench().getDisplay());

		// Refresh
		refresh();
	}

	// Simulated backend model updates
	private void refresh() {
		Runnable timer = new Runnable() {

			@Override
			public void run() {
				if (!viewer.getTable().isDisposed()) {
					maxCellTextLength = 0;

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

					// Simulate model update() to all the table cells
					viewer.setInput(sb.toString().split(tab));

					// Dynamically resize the column width
					Table table = viewer.getTable();
					TableColumn[] tableColumns = table.getColumns();
					for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
						TableColumn tableColumn = tableColumns[columnIndex];
						int width = getTableCellWidth(table, maxCellTextLength);
						tableColumn.setWidth(width);
					}

					// Simulate model update
					refresh();
				}
			}
		};

		// Report to Console
		System.out.println(sleakUtil.getReport());

		// Loop in milliseconds
		PlatformUI.getWorkbench().getDisplay().timerExec(2000, timer);
	}

	// Helper function to determine the cell width
	private int getTableCellWidth(Drawable drawable, int maximumTextLength) {
		// Determine string length
		int textLengthWithPad = maximumTextLength + TABLE_CELL_PADDING;

		// Compute character font width in terms of pixels
		GC gc = new GC(drawable);
		FontMetrics fm = gc.getFontMetrics();
		int charWidth = (int) Math.round((float) fm.getAverageCharWidth() * 1.25);
		gc.dispose();

		// Transform string length to pixels
		int pixels = textLengthWithPad * charWidth;
		pixels = Math.min(pixels, TABLE_MAX_PIXEL_WIDTH);
		return pixels;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}