/*
 * MainEntryPoint.java
 *
 * Created on 9 de abril de 2010, 11:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.trailer.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootPanel;
import org.trailer.client.util.KMenu;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.*;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelListenerAdapter;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;
import org.trailer.client.system.PrincipalTab;
import org.trailer.client.system.ReportarErrorWindow;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author Foreground system
 */
public class MainEntryPoint {

    private KMenu kmenu;
    private RootPanel menuP;
    private RootPanel reportarP;
    private RootPanel cuerpoP;
    private RootPanel rootP;
    private TabPanel tabPanel;
    private Menu menu;
    private RootPanel cabeceraP;
    private Label cabeceraLP;
    private ToolbarButton reportarError;
    private ReportarErrorWindow reportarErrorW;
    private int anchoMEP = Utils.getScreenWidth() - 22;
    private int altoMEP = Utils.getScreenHeight() - 250;
    private Panel piePagina;

    /**
     * Entry point for this simple application. Currently, we build the
     * application's form and wait for events.
     */
    public void onModuleLoad() {

        initializeMainForm();

    }

    public boolean verificarPermiso(String id) {
        return true;
    }

    private void initializeMainForm() {
        /****** inicilizamos los cuerpos ******/
//        com.google.gwt.user.client.Window.alert("" + Utils.getScreenWidth());
        kmenu = new KMenu(this);
        menuP = RootPanel.get("menus");
        reportarP = RootPanel.get("reportarError");
        DOM.setInnerHTML(RootPanel.get("cuerpo").getElement(), "");
        cuerpoP = RootPanel.get("cuerpo");
        rootP = RootPanel.get("search");
        cabeceraP = RootPanel.get("cabeceraP");
        int aaa = anchoMEP - 129;
        cabeceraP.setWidth(aaa + "px");
        piePagina = new Panel();
        piePagina.setHtml("Copyright © 2011 Sistema de Produccion GROUP TRAILER");
        piePagina.setBorder(false);
//        piePagina.set
        /******************************** el frame principal de tabs ******************************/
        tabPanel = new TabPanel();
        tabPanel.setResizeTabs(true);
        tabPanel.setMinTabWidth(115);
        tabPanel.setTabWidth(135);
        tabPanel.setEnableTabScroll(true);
        tabPanel.setWidth(anchoMEP);
        tabPanel.setHeight(altoMEP);
        tabPanel.setActiveTab(0);
        tabPanel.setBorder(true);


        tabPanel.addListener(new TabPanelListenerAdapter() {

            public void onContextMenu(TabPanel source, Panel tab, EventObject e) {
                showMenu(tab, e);
            }
        });

        PrincipalTab pantP = new PrincipalTab();
        tabPanel.add(pantP);
        tabPanel.activate(pantP.getId());

        reportarError = new ToolbarButton("Error");
        reportarError.setTitle("Reporta este error");
      
        reportarError.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                reportarErrorFuncion();
            }
        });
        reportarP.add(reportarError);

        cuerpoP.add(tabPanel);
        cuerpoP.add(piePagina);

        /***************************************hasta aqui ***************************************/
        /***************inicializamos el menu******************/
        if (menuP == null) {
            Utils.setErrorPrincipal("No existe el panel del menu Error:E2", "error");
            return;
        } else {
            String enlace = "php/util/KMenu.php";
            Conector cones = new Conector(enlace, false);
            Utils.setErrorPrincipal("Cargando el menu", "cargar");
            try {
                cones.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    public void onResponseReceived(Request request, Response response) {
                        String dataGU = response.getText();
                        JSONValue jsonValue = JSONParser.parse(dataGU);
                        JSONObject jsonObjectGU;
                        if ((jsonObjectGU = jsonValue.isObject()) != null) {
                            String errorR = Utils.getStringOfJSONObject(jsonObjectGU, "error");
                            String mensajeR = Utils.getStringOfJSONObject(jsonObjectGU, "mensaje");
                            if (errorR.equalsIgnoreCase("True")) {

                                Utils.setErrorPrincipal(mensajeR, "mensaje");
                                kmenu.onModuleLoad(jsonObjectGU);
                                menuP.add(kmenu.getButton());
                            } else {
                                Utils.setErrorPrincipal(mensajeR, "error");
                                Location.replace("login.php");
                            }
                        }
                    }

                    public void onError(Request request, Throwable exception) {
                        Utils.setErrorPrincipal("No se pudo conectar con el servidor", "error");
                    }
                });

            } catch (RequestException ex) {
                Utils.setErrorPrincipal("No se pudo conectar con el servidor", "error");
            }
            String enlacec = "php/util/Cabecera.php";
            Conector conesc = new Conector(enlacec, false);
            cabeceraLP = new Label();
            try {
                conesc.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    public void onResponseReceived(Request request, Response response) {
                        String dataGU = response.getText();
                        if (cabeceraP != null) {
                            cabeceraLP.setHtml(dataGU);
                            cabeceraP.add(cabeceraLP);
                        }
                    }

                    public void onError(Request request, Throwable exception) {
                        cabeceraLP.setHtml("Sin datos");
                        cabeceraP.add(cabeceraLP);
                    }
                });

            } catch (RequestException ex) {
                cabeceraLP.setHtml("Sin datos");
                cabeceraP.add(cabeceraLP);
            }
        }
//        com.google.gwt.user.client.Window.alert("" + Utils.getScreenWidth() + "------" + Utils.getScreenHeight());

////        RootPanel.get("erroresP").setWidth(aaa+"px");
//        aaa = anchoMEP-550;
//        RootPanel.get("errores").setWidth(aaa+"px");
    /***************inicializamos el menu******************/
    }

    public RootPanel getCuerpoP() {
        return cuerpoP;
    }

    public void setCuerpoP(RootPanel cuerpoP) {
        this.cuerpoP = cuerpoP;
    }

    /**funciones de tab **/
    private void showMenu(final Panel tab,
            EventObject e) {
        if (menu == null) {
            menu = new Menu();
            Item close = new Item("Cerrar pestana");
            close.setId("close-tab-item");
            close.addListener(new BaseItemListenerAdapter() {

                public void onClick(BaseItem item, EventObject e) {
//                    com.google.gwt.user.client.Window.alert(tabPanel.getActiveTab().getId());
                    String tempCTab = tabPanel.getActiveTab().getId();
                    if (tempCTab.equalsIgnoreCase("tabPrincipal")) {
                        Utils.setErrorPrincipal("No no no... no puede cerrar la pestana principal.", "error");
                    } else {
                        tabPanel.remove(tabPanel.getActiveTab());
                    }
                }
            });
            menu.addItem(close);

            Item closeOthers = new Item("Cerrar las otras pestanas");
            closeOthers.setId("close-others-item");
            closeOthers.addListener(new BaseItemListenerAdapter() {

                public void onClick(BaseItem item, EventObject e) {
                    Component[] items = tabPanel.getItems();
                    for (int i = 0; i <
                            items.length; i++) {
                        Component component = items[i];
                        if (!component.getId().equals(tabPanel.getActiveTab().getId())) {
                            tabPanel.remove(component);
                        }

                    }
                }
            });
            menu.addItem(closeOthers);
        }

        BaseItem closeOthers = menu.getItem("close-others-item");
        if (tabPanel.getItems().length == 1) {
            closeOthers.disable();
        } else {
            closeOthers.enable();
        }

        menu.showAt(e.getXY());
    }

    public TabPanel getTabPanel() {
        return tabPanel;
    }

    public void setTabPanel(TabPanel tabPanel) {
        this.tabPanel = tabPanel;
    }

    private void reportarErrorFuncion() {
        if (reportarErrorW != null) {
            reportarErrorW.clear();
            reportarErrorW.destroy();
            reportarErrorW = null;
        }
        reportarErrorW = new ReportarErrorWindow();
        reportarErrorW.show();
    }
    /**** hasta aqui ***/
}
