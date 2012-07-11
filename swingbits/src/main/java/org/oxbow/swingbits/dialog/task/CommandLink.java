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

package org.oxbow.swingbits.dialog.task;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.oxbow.swingbits.dialog.task.TaskDialog.CommandTag;

public class CommandLink implements TaskDialog.Command {

	private final String instruction;
	private final String text;
	private final Icon icon;

	public CommandLink( Icon icon, String instruction, String text ) {
		this.instruction = instruction;
		this.text = text;
		this.icon = icon;
	}
	
	public CommandLink( String instruction, String text ) {
		this( null, instruction, text );
	}

	public String getInstruction() {
		return instruction;
	}

	public String getText() {
		return text;
	};
	
	public Icon getIcon() {
		return icon;
	}

	
	
	@Override
	public String getTitle() {
		return instruction;
	}

	@Override
	public CommandTag getTag() {
		return null;
	}

	@Override
	public String getDescription() {
		return text;
	}

	@Override
	public boolean isClosing() {
		return true;
	}

	@Override
	public int getWaitInterval() {
		return 0;
	}

	@Override
	public boolean isEnabled(boolean validationResult) {
		return true;
	}

	@Override
	public KeyStroke getKeyStroke() {
		return null;
	}

}
