package com.binaryworkspace.rcp.images.resources;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.binaryworkspace.rcp.images.Activator;

/**
 * Enum for Silk Icons.
 * <p>
 * The Silk Icons library which can be found at {@link URL
 * http://www.famfamfam.com/lab/icons/silk/}
 * <p>
 * This resource provides an enumerated reference to subset of the Silk Icons.
 * This repository is used directly in conjunction with the icon's folder and
 * should remain one-to-one with the contents of the icon's folders. Below are
 * examples on how to reference and obtain images.
 * <p>
 * Image Example: ImageRepository.ECLIPSE_HELP_CONTENTS.image()<br>
 * ImageDescription Example: ImageRepository.ADD.descriptor()
 * <p>
 * To access an icon outside of the plug-in from XML use the reference path of:
 * "platform:plugin/com.x.plugin.core/icons/" as shown below.
 * 
 * <pre>
 *       {@literal <}view
 *             allowMultiple="true"
 *             class="com.x.views.FooView"
 *             icon="platform:plugin/com.x.plugin.core/icons/custom_icons/foo_icon.png"
 *             id="com.x.views.FooView"
 *             name="Foo View"
 *             restorable="true"{@literal >}
 *       {@literal </}view{@literal >}
 * </pre>
 * 
 * @author Chris Ludka
 */
public enum SwtImagesSilk {
	/***************************************************************************
	 * The following items are from the Silk Icons library which can be found at
	 * {@link URL http://www.famfamfam.com/lab/icons/silk/}
	 * <p>
	 * Only a subset (in this case, application*.png) of icons are included here
	 * as an example in order not to redistribute the entire Silk icon set which
	 * should be obtained from the official web site.
	 **************************************************************************/
	ADD("icons/silk_icons/add.png"), //
	DELETE("icons/silk_icons/delete.png"), //
	RECORD_BLUE("icons/silk_icons/record_blue.png"), //
	RECORD_GREEN("icons/silk_icons/record_green.png"), //
	RECORD_RED("icons/silk_icons/record_red.png") //
	; //

	// Register the image
	private SwtImagesSilk(String path) {
		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.getDefault().getBundle().getSymbolicName(), path);
		JFaceResources.getImageRegistry().put(name(), descriptor);
	}

	/**
	 * ImageDescriptor for this Enum.
	 * 
	 * @return image descriptor
	 */
	public ImageDescriptor descriptor() {
		return JFaceResources.getImageRegistry().getDescriptor(name());
	}

	/**
	 * Image for this Enum.
	 * 
	 * @return image
	 */
	public Image image() {
		return JFaceResources.getImageRegistry().get(name());
	}
}