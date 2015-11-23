package com.binaryworkspace.rcp.preferences.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
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
 * Example 2 preference page.
 * 
 * @author Chris Ludka
 */
public class Example_2_PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private final int TOP_MARGIN = 10;

	private final int LEFT_MARGIN = 10;

	private final int RIGHT_MARGIN = -10;

	private Font TEXT_BOLD_FONT;

	/**
	 * View identifier for the RCP Framework
	 */
	public static final String ID = Example_2_PreferencePage.class.getName();

	/**
	 * Default constructor.
	 */
	public Example_2_PreferencePage() {
		// default
	}

	/**
	 * Constructs the instance with the given style.
	 * 
	 * @param style
	 */
	public Example_2_PreferencePage(int style) {
		super(style);
	}

	/**
	 * Constructs the instance with the given title and style.
	 * 
	 * @param title
	 * @param style
	 */
	public Example_2_PreferencePage(String title, int style) {
		super(title, style);
	}

	/**
	 * Constructs the instance with the given title, image and style.
	 * 
	 * @param title
	 * @param image
	 * @param style
	 */
	public Example_2_PreferencePage(String title, ImageDescriptor image, int style) {
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
		headerLabel.setText("Page 2 Examples:");
		headerLabel.setFont(TEXT_BOLD_FONT);
		formData = new FormData();
		formData.top = new FormAttachment(0, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		headerLabel.setLayoutData(formData);

		// Mode Header
		Label modeLabel = new Label(baseComposite, SWT.LEFT);
		modeLabel.setText("Modes:");
		modeLabel.setFont(TEXT_BOLD_FONT);
		formData = new FormData();
		formData.top = new FormAttachment(headerLabel, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		modeLabel.setLayoutData(formData);

		// Mode RadioGroupFieldEditor Composite
		Composite radioGroupFieldEditorComposite = new Composite(baseComposite, SWT.NONE);
		formData = new FormData();
		formData.top = new FormAttachment(modeLabel, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		radioGroupFieldEditorComposite.setLayoutData(formData);
		radioGroupFieldEditorComposite.setLayout(new GridLayout());

		// MODE_EXAMPLE_PREFERENCE
		String[][] modeRadioValues = new String[4][2];
		modeRadioValues[0][0] = "Mode 1";
		modeRadioValues[0][1] = "MODE_1";
		modeRadioValues[1][0] = "Mode 2";
		modeRadioValues[1][1] = "MODE_2";
		modeRadioValues[2][0] = "Mode 3";
		modeRadioValues[2][1] = "MODE_3";
		modeRadioValues[3][0] = "Mode 4";
		modeRadioValues[3][1] = "MODE_4";
		RadioGroupFieldEditor radioGroupFieldEditor = new RadioGroupFieldEditor(PreferenceKeyType.MODE_EXAMPLE_PREFERENCE.getKey(),
				PreferenceKeyType.MODE_EXAMPLE_PREFERENCE.getDisplayName(), 1, modeRadioValues, radioGroupFieldEditorComposite);
		radioGroupFieldEditor.setPreferenceStore(getPreferenceStore());
		radioGroupFieldEditor.load();
		addField(radioGroupFieldEditor);

		// Scalar Unit Header
		Label scalarUnitLabel = new Label(baseComposite, SWT.LEFT);
		scalarUnitLabel.setText("Scalar Value with Unit of Measure:");
		scalarUnitLabel.setFont(TEXT_BOLD_FONT);
		formData = new FormData();
		formData.top = new FormAttachment(radioGroupFieldEditorComposite, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		scalarUnitLabel.setLayoutData(formData);

		// Scalar Unit Composite
		Composite scalarUnitComposite = new Composite(baseComposite, SWT.NONE);
		scalarUnitComposite.setLayout(new FormLayout());
		formData = new FormData();
		formData.top = new FormAttachment(scalarUnitLabel, TOP_MARGIN);
		formData.left = new FormAttachment(0, LEFT_MARGIN);
		formData.right = new FormAttachment(100, RIGHT_MARGIN);
		scalarUnitComposite.setLayoutData(formData);

		// Prepare for unit combo boxes
		String[][] strUnits = new String[][] { //
		{ "NANO SECOND", "NANO SECOND" }, //
				{ "MILLI SECOND", "MILLI SECOND" }, //
				{ "SECOND", "SECOND" }, //
				{ "MINUTE", "MINUTE" }, //
				{ "HOUR", "HOUR" }, //
				{ "DAY", "DAY" }, //
				{ "YEAR", "YEAR" } //
		};

		// Key List
		List<PreferenceKeyType> keyList = new ArrayList<PreferenceKeyType>();
		keyList.add(PreferenceKeyType.ITEM_1_SCALAR_EXAMPLE_PREFERENCE);
		keyList.add(PreferenceKeyType.ITEM_1_UNIT_EXAMPLE_PREFERENCE);
		keyList.add(PreferenceKeyType.ITEM_2_SCALAR_EXAMPLE_PREFERENCE);
		keyList.add(PreferenceKeyType.ITEM_2_UNIT_EXAMPLE_PREFERENCE);
		keyList.add(PreferenceKeyType.ITEM_3_SCALAR_EXAMPLE_PREFERENCE);
		keyList.add(PreferenceKeyType.ITEM_3_UNIT_EXAMPLE_PREFERENCE);
		keyList.add(PreferenceKeyType.ITEM_4_SCALAR_EXAMPLE_PREFERENCE);
		keyList.add(PreferenceKeyType.ITEM_4_UNIT_EXAMPLE_PREFERENCE);

		/*
		 * Create a 'row' composite for each 'String' and 'Combo' box pairing
		 * for the keyList. Then create sub-composites for specifically the
		 * 'String' and 'Combo' box. This is necessary because of the GridLayout
		 * requirement with preference fields. If only the 'String' and 'Combo'
		 * sub-composites are created without a 'row' composite then the
		 * GridLayout will eventually align the left-hand-side of the 'String'
		 * with the right-hand-side of the 'Combo' because the 'Combo' has a
		 * greater control height. The 'row' composite allows us to therefore
		 * keep the 'String' and 'Combo' sub-composites and controls in
		 * reasonable alignment.
		 */
		Control previous = scalarUnitLabel;
		Composite rowComposite = null;
		Composite scalarComposite = null;
		Composite unitComposite = null;
		boolean isStringFieldEditor = true;
		for (PreferenceKeyType key : keyList) {
			if (isStringFieldEditor) {
				// Row Composite
				rowComposite = new Composite(scalarUnitComposite, SWT.NONE);
				rowComposite.setLayout(new FormLayout());
				formData = new FormData();
				formData.top = new FormAttachment(previous, 0);
				formData.left = new FormAttachment(0, 0);
				formData.right = new FormAttachment(100, 0);
				rowComposite.setLayoutData(formData);
				previous = rowComposite;

				// Scalar Composite
				scalarComposite = new Composite(rowComposite, SWT.NONE);
				formData = new FormData();
				formData.top = new FormAttachment(0, TOP_MARGIN);
				formData.left = new FormAttachment(0, LEFT_MARGIN);
				formData.right = new FormAttachment(50, RIGHT_MARGIN);
				scalarComposite.setLayoutData(formData);
				scalarComposite.setLayout(new GridLayout());

				// Add StringFieldEditor
				StringFieldEditor stringFieldEditor = new StringFieldEditor(key.getKey(), key.getDisplayName(), scalarComposite);
				stringFieldEditor.setPreferenceStore(getPreferenceStore());
				stringFieldEditor.load();
				addField(stringFieldEditor);
			} else {
				// Expiration Unit Composite
				unitComposite = new Composite(rowComposite, SWT.NONE);
				formData = new FormData();
				formData.top = new FormAttachment(0, TOP_MARGIN);
				formData.left = new FormAttachment(scalarComposite, LEFT_MARGIN);
				formData.right = new FormAttachment(100, RIGHT_MARGIN);
				unitComposite.setLayoutData(formData);
				unitComposite.setLayout(new GridLayout());

				// Add ComboFieldEditor
				ComboFieldEditor comboFieldEditor = new ComboFieldEditor(key.getKey(), "", strUnits, unitComposite);
				comboFieldEditor.setPreferenceStore(getPreferenceStore());
				comboFieldEditor.load();
				addField(comboFieldEditor);
			}

			// Alternate between the StringFieldEditor and the ComboFieldEditor
			isStringFieldEditor = !isStringFieldEditor;
		}

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