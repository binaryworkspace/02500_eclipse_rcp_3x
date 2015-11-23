package com.binaryworkspace.rcp.preferences.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

/**
 * Example 1 preference page.
 * 
 * @author Chris Ludka
 */
public class Example_3_PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private final int TOP_MARGIN = 10;
	
	private final int LEFT_MARGIN = 10;
	
	private final int RIGHT_MARGIN = -10;

	private Font TEXT_BOLD_FONT;
	
	/**
	 * View identifier for the RCP Framework
	 */
	public static final String ID = Example_3_PreferencePage.class.getName();

	/**
	 * Default constructor.
	 */
	public Example_3_PreferencePage() {
		// default
	}

	/**
	 * Constructs the instance with the given style.
	 * 
	 * @param style
	 */
	public Example_3_PreferencePage(int style) {
		super(style);
	}

	/**
	 * Constructs the instance with the given title and style.
	 * 
	 * @param title
	 * @param style
	 */
	public Example_3_PreferencePage(String title, int style) {
		super(title, style);
	}

	/**
	 * Constructs the instance with the given title, image and style.
	 * 
	 * @param title
	 * @param image
	 * @param style
	 */
	public Example_3_PreferencePage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		// Preference Store
		IPreferenceStore preferenceStore = PlatformUI.getPreferenceStore();
		setPreferenceStore(preferenceStore);
		
		// Define a Bold Font
		FontData fontData = new FontData("Arial", 10, SWT.BOLD);
		JFaceResources.getFontRegistry().put("TEXT BOLD", new FontData[] { fontData });
		TEXT_BOLD_FONT = JFaceResources.getFontRegistry().get("TEXT BOLD");
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// Base Composite
		Composite baseComposite = new Composite(parent, SWT.NONE);
		baseComposite.setLayout(new FormLayout());
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.bottom = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		baseComposite.setLayoutData(formData);

		// Header
		Label headerLabel = new Label(baseComposite, SWT.LEFT);
		headerLabel.setText("Page 3 Examples:");
		headerLabel.setFont(TEXT_BOLD_FONT);
		formData = new FormData();
		formData.top = new FormAttachment(0, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		headerLabel.setLayoutData(formData);

		// File Header
		Label fileLabel = new Label(baseComposite, SWT.LEFT);
		fileLabel.setText("File and Directory:");
		fileLabel.setFont(TEXT_BOLD_FONT);
		formData = new FormData();
		formData.top = new FormAttachment(headerLabel, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		fileLabel.setLayoutData(formData);
		
		// File Composite
		Composite fileComposite = new Composite(baseComposite, SWT.NONE);
		formData = new FormData();
		formData.top = new FormAttachment(fileLabel, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		fileComposite.setLayoutData(formData);
		fileComposite.setLayout(new GridLayout());
		
		// DIRECTORY_EXAMPLE_PREFERENCE
		DirectoryFieldEditor directoryFieldEditor = new DirectoryFieldEditor(PreferenceKeyType.DIRECTORY_EXAMPLE_PREFERENCE.getKey(), PreferenceKeyType.DIRECTORY_EXAMPLE_PREFERENCE.getDisplayName(), fileComposite);
		directoryFieldEditor.setPreferenceStore(getPreferenceStore());
		directoryFieldEditor.load();
		addField(directoryFieldEditor);
		
		// FILE_EXAMPLE_PREFERENCE
		FileFieldEditor fileFieldEditor = new FileFieldEditor(PreferenceKeyType.DIRECTORY_EXAMPLE_PREFERENCE.getKey(), PreferenceKeyType.DIRECTORY_EXAMPLE_PREFERENCE.getDisplayName(), fileComposite);
		fileFieldEditor.setPreferenceStore(getPreferenceStore());
		fileFieldEditor.load();
		addField(fileFieldEditor);
		
		// Network Header
		Label networkLabel = new Label(baseComposite, SWT.LEFT);
		networkLabel.setText("Network:");
		networkLabel.setFont(TEXT_BOLD_FONT);
		formData = new FormData();
		formData.top = new FormAttachment(fileComposite, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		networkLabel.setLayoutData(formData);
		
		// Network Composite
		Composite networkComposite = new Composite(baseComposite, SWT.NONE);
		formData = new FormData();
		formData.top = new FormAttachment(networkLabel, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		networkComposite.setLayoutData(formData);
		networkComposite.setLayout(new GridLayout());
		
		// IP_EXAMPLE_PREFERENCE
		StringFieldEditor stringFieldEditor = new StringFieldEditor(PreferenceKeyType.INT_EXAMPLE_PREFERENCE.getKey(),
				PreferenceKeyType.INT_EXAMPLE_PREFERENCE.getDisplayName(), networkComposite);
		stringFieldEditor.setPreferenceStore(getPreferenceStore());
		stringFieldEditor.load();
		addField(stringFieldEditor);
		
		// PORT_EXAMPLE_PREFERENCE
		stringFieldEditor = new StringFieldEditor(PreferenceKeyType.FLOAT_EXAMPLE_PREFERENCE.getKey(),
				PreferenceKeyType.FLOAT_EXAMPLE_PREFERENCE.getDisplayName(), networkComposite);
		stringFieldEditor.setPreferenceStore(getPreferenceStore());
		stringFieldEditor.load();
		addField(stringFieldEditor);
		
		return baseComposite;
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		// Do nothing.
	}

}
