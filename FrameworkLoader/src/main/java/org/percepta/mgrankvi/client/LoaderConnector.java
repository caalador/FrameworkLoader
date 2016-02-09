package org.percepta.mgrankvi.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.JavaScriptConnectorHelper;
import com.vaadin.client.communication.HasJavaScriptConnectorHelper;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.Loader;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(Loader.class)
public class LoaderConnector extends AbstractComponentConnector  implements HasJavaScriptConnectorHelper {

    // ServerRpc is used to send events to server. Communication implementation
    // is automatically created here
//    MyComponentServerRpc rpc = RpcProxy.create(MyComponentServerRpc.class, this);

    public LoaderConnector() {

        // To receive RPC events from server, we register ClientRpc implementation
/*
        registerRpc(MyComponentClientRpc.class, new MyComponentClientRpc() {
			public void alert(String message) {
				Window.alert(message);
			}
		});

		// We choose listed for mouse clicks for the widget
		getWidget().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
						.buildMouseEventDetails(event.getNativeEvent(),
								getWidget().getElement());
				
				// When the widget is clicked, the event is sent to server with ServerRpc
				rpc.clicked(mouseDetails);
			}
		});
*/
    }
    private final JavaScriptConnectorHelper helper = new JavaScriptConnectorHelper(
            this);

    @Override
    protected void init() {
        super.init();
        helper.init();
    }

    @Override
    public JavaScriptConnectorHelper getJavascriptConnectorHelper() {
        return helper;
    }

    @Override
    protected LoaderWidget createWidget() {
        return GWT.create(LoaderWidget.class);
    }

    @Override
    public LoaderWidget getWidget() {
        return (LoaderWidget) super.getWidget();
    }

    @Override
    public LoaderState getState() {
        return (LoaderState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        getWidget().setRunOnce(getState().runOnce);
        getWidget().setAsyncScripts(getState().asynchronousScripts);
        getWidget().setStyleSheets(getState().styleSheets);
        getWidget().setInitScript(getState().initScript);
        getWidget().setRunFunction(getState().runFunction);
        getWidget().setVariables(getState().variablesScript);
        getWidget().setLoading(getState().synchronousScripts);
        getWidget().addMetaFields(getState().metaMap);

    }

}
