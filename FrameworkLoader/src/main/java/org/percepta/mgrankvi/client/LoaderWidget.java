package org.percepta.mgrankvi.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.vaadin.client.ResourceLoader;
import com.vaadin.client.Util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoaderWidget extends FlowPanel {

    public static final String CLASSNAME = "FrameworkLoader";

    private ResourceLoader loader = ResourceLoader.get();

    private LinkedList<String> loading = new LinkedList<String>();
    private LinkedList<String> asyncLoading = new LinkedList<String>();
    private String initScript;
    private String variablesScript;
    private String runFunction;

    Label l = new Label();

    private boolean runOnce = true;

    private boolean hasRun = false;

    public LoaderWidget() {

        // CSS class-name should not be v- prefixed
        setStyleName(CLASSNAME);
        getElement().setClassName(CLASSNAME);

        l.getElement().setInnerHTML("<span class=\"v-icon\" style=\"font-family: FontAwesome;\">&#x"+Integer.toHexString(0XF110)+";</span>");
        add(l);

        getLogger().log(Level.INFO, Window.Location.getHost());

    }

    public void setStyleSheets(List<String> styleSheets) {
        for (String styleSheet : styleSheets) {
            injectStyleSheets(styleSheet);
        }
    }

    public void setRunFunction(String runFunction) {
        this.runFunction = runFunction;
    }

    public void setVariables(String variablesScript) {
        this.variablesScript = variablesScript;
    }

    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }

    public void setInitScript(String initScript) {
        this.initScript = initScript;
    }

    public void setLoading(List<String> loading) {
        this.loading.clear();

        if (variablesScript != null) {
            loading.add(variablesScript);
        }

        this.loading.addAll(loading);

        if (!hasRun) {
            injectScript(this.loading.getFirst());
            if (runOnce) {
                hasRun = true;
            }
        }
    }

    public void setAsyncScripts(List<String> async) {
        asyncLoading.addAll(async);
        for (String script : async) {
            asyncInject(script);
        }
    }

    public void addMetaFields(Map<String, String> metaFields) {
        Element meta = DOM.createElement("meta");
        for (Map.Entry<String, String> entry : metaFields.entrySet()) {
            meta.setAttribute(entry.getKey(), entry.getValue());
        }
        Document.get().getElementsByTagName("head").getItem(0)
                .appendChild(meta);
    }

    private void injectScript(final String url) {
        getLogger().log(Level.INFO, " == " + Util.getAbsoluteUrl(url));
        loader.loadScript("./VAADIN/" + url, new ResourceLoader.ResourceLoadListener() {
            @Override
            public void onLoad(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.INFO, "Success " + url);
                loading.remove(url);
                if (loading.isEmpty()) {
                    initEditorUI();
                } else {
                    injectScript(loading.getFirst());
                }
            }

            @Override
            public void onError(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.SEVERE, "Error " + url);
                loading.remove(url);
                l.setText("Encountered error while loading: " + url);
            }
        });
    }

    private void initEditorUI() {
        if (initScript == null) {
            return;
        }
        loader.loadScript("./VAADIN/" + initScript, new ResourceLoader.ResourceLoadListener() {
            @Override
            public void onLoad(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.INFO, "Success " + initScript);
                if (runFunction != null) {
                    runScript(runFunction);
                }
            }

            @Override
            public void onError(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.SEVERE, "Failed to load: " + initScript);
            }
        });
    }

    private void asyncInject(final String url) {
        loader.loadScript("VAADIN/" + url, new ResourceLoader.ResourceLoadListener() {
            @Override
            public void onLoad(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.INFO, "Async load success " + url);
                asyncLoading.remove(url);
            }

            @Override
            public void onError(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.SEVERE, "Failed to async load: " + url);
                asyncLoading.remove(url);
            }
        });
    }

    private void injectStyleSheets(final String url) {
        loader.loadStylesheet("VAADIN/" + url, new ResourceLoader.ResourceLoadListener() {
            @Override
            public void onLoad(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.INFO, "Success " + url);
            }

            @Override
            public void onError(ResourceLoader.ResourceLoadEvent event) {
                getLogger().log(Level.SEVERE, "Failed to load: " + url);
            }
        });
    }

    public static native void runScript(String initFunctionName) /*-{
        $wnd[initFunctionName]();
    }-*/;


    private static Logger getLogger() {
        return Logger.getLogger(LoaderWidget.class.getName());
    }
}