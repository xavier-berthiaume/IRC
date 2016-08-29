package server;

import java.io.*;
import java.text.*;
import java.util.*;

class LogWriter {

	private String path;
	private final boolean appendToFile = true;
	
	public LogWriter(){
		final DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		final Date date = new Date();
		path = "server.Logs/log@" + dateformat.format(date);
		
	}
	
	public void writeToFile(String text) throws IOException{
		FileWriter writer = new FileWriter(path, appendToFile);
		PrintWriter printer = new PrintWriter(writer);
		printer.printf("%s + %n", text);
		printer.close();
	}
}
