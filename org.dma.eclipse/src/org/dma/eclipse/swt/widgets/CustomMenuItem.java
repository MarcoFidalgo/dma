/*******************************************************************************
 * 2008-2020 Public Domain
 * Contributors
 * Marco Lopes (marcolopespt@gmail.com)
 *******************************************************************************/
package org.dma.eclipse.swt.widgets;

import java.awt.image.BufferedImage;

import org.dma.eclipse.swt.graphics.ImageManager;
import org.dma.java.awt.ImageUtils;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class CustomMenuItem extends MenuItem {

	@Override //subclassing
	protected void checkSubclass() {}

	private IAction selectionAction;

	/** @see MenuItem#MenuItem(Menu, int) */
	public CustomMenuItem(Menu parent, int style) {
		super(parent, style);
	}


	public void setImage(String imagePath) {
		setImage(ImageManager.getImage(imagePath));
	}

	public void setImage(String imagePath, int imageSize) {
		setImage(ImageUtils.createImage(imagePath, imageSize));
	}

	public void setImage(BufferedImage bufferedImage) {
		setImage(ImageManager.getImage(bufferedImage));
	}


	/*
	 * Selection
	 */
	public void setSelectionAction(final IAction action) {
		action.setEnabled(isEnabled()); //synchronize states
		action.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				setEnabled(action.isEnabled());
			}
		});
		addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionAction.run();
			}
		});
		this.selectionAction=action;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (selectionAction!=null) selectionAction.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	public IAction getSelectionAction() {
		return selectionAction;
	}


}