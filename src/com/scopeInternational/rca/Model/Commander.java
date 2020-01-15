package com.scopeInternational.rca.Model;

import java.util.ArrayList;

public class Commander {

private String command;
	
	private String commandArguments;
	private String textArea;
	public String getTextArea() {
		return textArea;
	}

	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}

	private ArrayList outList;

	public String getCommand() {
		return command;
	}

	public ArrayList getOutList() {
		return outList;
	}

	public void setOutList(ArrayList outList) {
		this.outList = outList;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCommandArguments() {
		return commandArguments;
	}

	public void setCommandArguments(String commandArguments) {
		this.commandArguments = commandArguments;
	}

	@Override
	public String toString() {
		return "Commander [command=" + command + ", commandArguments=" + commandArguments + ", textArea=" + textArea
				+ ", outList=" + outList + "]";
	}

	
	
}
