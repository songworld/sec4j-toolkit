package com.toolkit2.client.component2ex;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
//基本组件扩展
public class TextAreaOutputStream extends OutputStream {
	private JTextArea textControl;

	  public TextAreaOutputStream(JTextArea control)
	  {
	    this.textControl = control;
	  }

	  public void write(int b)
	    throws IOException
	  {
	    this.textControl.append(String.valueOf((char)b));
	  }
}
