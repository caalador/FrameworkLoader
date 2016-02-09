package org.percepta.mgrankvi.client;

import com.vaadin.shared.ui.JavaScriptComponentState;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LoaderState extends JavaScriptComponentState {

	public List<String> synchronousScripts = new LinkedList<String>();
	public List<String> styleSheets = new LinkedList<String>();
    public String initScript;
    public String runFunction;
    public String variablesScript;

    public Map<String, String> metaMap = new HashMap<String,String>();

    public List<String> asynchronousScripts = new LinkedList<String>();

    public boolean runOnce = true;
}