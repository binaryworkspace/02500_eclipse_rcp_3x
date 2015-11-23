package com.binaryworkspace.rcp.preferences.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;

/**
 * Enum for all preference keys. This unit is used to demonstrate various
 * concepts and types of possible preferences.
 * 
 * @author Chris Ludka
 * 
 */
public enum PreferenceKeyType {

	// String and Numeric Values
	STRING_EXAMPLE_PREFERENCE("Example 'String' Preference", "A String as a reference Value."), //
	INT_EXAMPLE_PREFERENCE("Example 'int' Preference", "73"), //
	FLOAT_EXAMPLE_PREFERENCE("Example 'float' Preference", "2.71828"), //

	// Boolean
	BOOLEAN_EXAMPLE_PREFERENCE("Example 'boolean' Enabled Preference", "TRUE"), //

	// Modes
	MODE_EXAMPLE_PREFERENCE("Example 'mode' Preference", "MODE_1"), //

	// Scalar Value with Unit of Measure
	ITEM_1_SCALAR_EXAMPLE_PREFERENCE("Example 1 'SCALAR' Preference", "5.2"), //
	ITEM_1_UNIT_EXAMPLE_PREFERENCE("Example 1 'UNIT' Preference", "SECOND"), //
	ITEM_2_SCALAR_EXAMPLE_PREFERENCE("Example 2 'SCALAR' Preference", "22"), //
	ITEM_2_UNIT_EXAMPLE_PREFERENCE("Example 2 'UNIT' Preference", "HOUR"), //
	ITEM_3_SCALAR_EXAMPLE_PREFERENCE("Example 3 'SCALAR' Preference", "-12.5"), //
	ITEM_3_UNIT_EXAMPLE_PREFERENCE("Example 3 'UNIT' Preference", "MINUTE"), //
	ITEM_4_SCALAR_EXAMPLE_PREFERENCE("Example 4 'SCALAR' Preference", "1.7"), //
	ITEM_4_UNIT_EXAMPLE_PREFERENCE("Example 4 'UNIT' Preference", "DAY"), //
	
	// File and Directory
	DIRECTORY_EXAMPLE_PREFERENCE("Example 'directory' Preference", "None Selected"), //
	FILE_EXAMPLE_PREFERENCE("Example 'file' Preference", "None Selected"), //
	
	// Network: IP and Port
	IP_EXAMPLE_PREFERENCE("Example 'IP' Preference", "10.0.1.1"), //
	PORT_EXAMPLE_PREFERENCE("Example 'Port' Preference", "8080"); //

	/**
	 * This key is guaranteed to be unique since in Java both Pathnames and Enum
	 * names must be unique. Therefore, the concatenation of the Pathname and
	 * Enum name will form a unique string key for each Enum value specified.
	 * 
	 */
	private final String key = PreferenceKeyType.class.getSimpleName() + "." + this.name();

	private final String displayName;

	private final String defualtValue;

	private final IPreferenceStore preferenceStore = PlatformUI.getPreferenceStore();

	private PreferenceKeyType(String displayName, String defaultValue) {
		preferenceStore.setDefault(key, defaultValue);
		this.displayName = displayName;
		this.defualtValue = defaultValue;
	}

	/**
	 * Returns the key for this preference.
	 * 
	 * @return key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Returns the display name for this preference.
	 * 
	 * @return display name
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Returns the default value for this preference.
	 * 
	 * @return default value
	 */
	public String getDefaultValue() {
		return this.defualtValue;
	}
}