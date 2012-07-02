/*
 * Copyright (c) 2009-2011, EzWare
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.Redistributions
 * in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.Neither the name of the
 * EzWare nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.oxbow.swingbits.dialog.task.design;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.UIManager;

import org.oxbow.swingbits.dialog.task.ICommandLinkPainter;
import org.oxbow.swingbits.dialog.task.IContentDesign;

public class DefaultCommandLinkPainter implements ICommandLinkPainter {

	@Override
	public void intialize(JComponent source) {
		
		if ( source instanceof AbstractButton ) {

			AbstractButton button = (AbstractButton)source;
			
			button.setOpaque(false);
			button.setBorderPainted(false);
			button.setContentAreaFilled(false);
			button.setFocusPainted(false);
		
		}
	}

	@Override
	public void paint(Graphics g, JComponent source) {

		if ( !(source instanceof AbstractButton) ) return;

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
			
		AbstractButton button = (AbstractButton)source;
		ButtonModel model = button.getModel();

		if ( button.isSelected() ) {

			drawButton( button, g2,
				Color.LIGHT_GRAY.brighter(),
				Color.GRAY.brighter(),
				Color.LIGHT_GRAY,
				5
			);
			
		} 
		
		Color messageBackground = normalize(UIManager.getColor( IContentDesign.COLOR_MESSAGE_BACKGROUND ));
		Color instructionForeground = normalize(UIManager.getColor( IContentDesign.COLOR_INSTRUCTION_FOREGROUND ));
		
		if ( model.isArmed() ) {

			drawButton( button, g2,
			    messageBackground,
			    instructionForeground,
			    instructionForeground,
				3
			);

		} else
		if ( model.isRollover()) {

			drawButton( button, g2,
				messageBackground,
				instructionForeground,
				instructionForeground,
				6
			);

		}
		g2.dispose();
	}
	
	private Color normalize( Color color ) {
		return color == null ? Color.BLACK: color;
	}
	
	private static final int ARC_SIZE = 5;

	private void drawButton( AbstractButton button, Graphics2D g, Color startColor, Color endColor, Color borderColor, int gradientHeightFactor ) {

		GradientPaint paint = new GradientPaint(
				0, 0,  startColor,
				0, button.getHeight()*gradientHeightFactor, endColor );

		g.setPaint( paint );
		g.fillRoundRect(0,0, button.getWidth()-1, button.getHeight()-1, ARC_SIZE, ARC_SIZE);
		g.setColor( borderColor );
		g.drawRoundRect(0,0, button.getWidth()-1, button.getHeight()-1, ARC_SIZE, ARC_SIZE);

	}

}
