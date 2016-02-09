package org.percepta.mgrankvi;

import com.vaadin.server.JavaScriptCallbackHelper;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.ui.JavaScriptFunction;
import org.percepta.mgrankvi.client.LoaderState;

import java.util.List;
import java.util.Map;

/**
 * Loader component that injects scripts and stylesheets from the VAADIN directory
 */
public class Loader extends com.vaadin.ui.AbstractComponent {

    private JavaScriptCallbackHelper callbackHelper = new JavaScriptCallbackHelper(
            this);

    @Override
    protected <T extends ServerRpc> void registerRpc(T implementation,
                                                     Class<T> rpcInterfaceType) {
        super.registerRpc(implementation, rpcInterfaceType);
        callbackHelper.registerRpc(rpcInterfaceType);
    }

    /**
     * Register a {@link JavaScriptFunction} that can be called from the
     * JavaScript using the provided name. A JavaScript function with the
     * provided name will be added to the connector wrapper object (initially
     * available as <code>this</code>). Calling that JavaScript function will
     * cause the call method in the registered {@link JavaScriptFunction} to be
     * invoked with the same arguments.
     *
     * @param functionName the name that should be used for client-side function
     * @param function     the {@link JavaScriptFunction} object that will be invoked
     *                     when the JavaScript function is called
     */
    protected void addFunction(String functionName, JavaScriptFunction function) {
        callbackHelper.registerCallback(functionName, function);
    }

    /**
     * Invoke a named function that the connector JavaScript has added to the
     * JavaScript connector wrapper object. The arguments should only contain
     * data types that can be represented in JavaScript including primitives,
     * their boxed types, arrays, String, List, Set, Map, Connector and
     * JavaBeans.
     *
     * @param name      the name of the function
     * @param arguments function arguments
     */
    protected void callFunction(String name, Object... arguments) {
        callbackHelper.invokeCallback(name, arguments);
    }

    public Loader() {
    }

    /**
     * Loader with initScript and scripts that should be loaded before initScript
     *
     * @param initScript Script that initialises the javascript
     * @param scripts    Scripts to load synchronously
     */
    public Loader(String initScript, List<String> scripts) {
        getState().synchronousScripts.addAll(scripts);
        getState().initScript = initScript;
    }

    /**
     * @param initScript
     * @param scripts
     * @param styleSheets
     */
    public Loader(String initScript, List<String> scripts, List<String> styleSheets) {
        getState().synchronousScripts.addAll(scripts);
        getState().styleSheets.addAll(styleSheets);
        getState().initScript = initScript;
    }

    /**
     * @param initScript
     * @param variableScript
     * @param scripts
     */
    public Loader(String initScript, String variableScript, List<String> scripts) {
        getState().synchronousScripts.addAll(scripts);
        getState().variablesScript = variableScript;
        getState().initScript = initScript;
    }

    /**
     * @param initScript
     * @param variableScript
     * @param scripts
     * @param styleSheets
     */
    public Loader(String initScript, String variableScript, List<String> scripts, List<String> styleSheets) {
        getState().synchronousScripts.addAll(scripts);
        getState().styleSheets.addAll(styleSheets);
        getState().variablesScript = variableScript;
        getState().initScript = initScript;
    }

    /**
     * Add synchronousScripts to be loaded synchronously in given order
     *
     * @param scripts
     */
    public void addScripts(List<String> scripts) {
        getState().synchronousScripts.addAll(scripts);
    }


    /**
     * Add synchronousScripts to be loaded synchronously in given order
     *
     * @param scripts
     */
    public void addScripts(String... scripts) {
        for (String script : scripts) {
            getState().synchronousScripts.add(script);
        }
    }

    /**
     * Add scripts that can be loaded asynchronously
     *
     * @param scripts
     */
    public void addAsyncScripts(List<String> scripts) {
        getState().asynchronousScripts.addAll(scripts);
    }

    /**
     * Add scripts that can be loaded asynchronously
     *
     * @param scripts
     */
    public void addAsyncScripts(String... scripts) {
        for (String script : scripts) {
            getState().asynchronousScripts.add(script);
        }
    }

    public void setRunFunction(String function) {
        getState().runFunction = function;
    }

    /**
     * Add styleSheets to be injected asynchronously
     *
     * @param styleSheets
     */
    public void addStyleSheets(List<String> styleSheets) {
        getState().styleSheets.addAll(styleSheets);
    }


    /**
     * Add styleSheets to be injected asynchronously
     *
     * @param styleSheets
     */
    public void addStyleSheets(String... styleSheets) {
        for (String sheet : styleSheets) {
            getState().styleSheets.add(sheet);
        }
    }

    /**
     * Set initialization script to be run after all other synchronousScripts have been loaded
     *
     * @param script
     */
    public void setInitScript(String script) {
        getState().initScript = script;
    }

    /**
     * Add script with variables
     *
     * @param script
     */
    public void setVariables(String script) {
        getState().variablesScript = script;
    }

    /**
     * Add a meta field to head
     *
     * @param metaMap
     */
    public void setMetaField(Map<String, String> metaMap) {
        getState().metaMap.putAll(metaMap);
    }

    /**
     * Set if the init script should be run only once or for each content change
     *
     * @param onlyOnce
     */
    public void setLoadOnce(boolean onlyOnce) {
        getState().runOnce = onlyOnce;
    }

    @Override
    public LoaderState getState() {
        return (LoaderState) super.getState();
    }

}
