package com.mpango.Util.log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.jpos.util.SimpleLogListener;

public class LMSLogListener extends SimpleLogListener {

	FileOutputStream f;

	public LMSLogListener() {
	}

	public LMSLogListener(String logName) {
		try {
			openLogFile(logName);			
		} catch (IOException e) {
			System.out.println(logName + ".FileNotFoundException");
		}
	}

	protected synchronized void openLogFile(String logName) throws IOException {
		if (f != null)
			f.close();
		f = new FileOutputStream(logName, true);
		setPrintStream(new PrintStream(f));
	}

	protected synchronized void closeLogFile() throws IOException {
		if (f != null)
			f.close();
		f = null;
	}

}
