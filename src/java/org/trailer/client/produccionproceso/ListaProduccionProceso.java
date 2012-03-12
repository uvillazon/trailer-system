/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.produccionproceso;

import org.trailer.client.produccion.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import org.trailer.client.util.BuscadorToolBar;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.data.*;
import com.gwtext.client.util.DateUtil;
import com.gwtext.client.widgets.form.Checkbox;
import org.trailer.client.MainEntryPoint;
import org.trailer.client.util.KMenu;

/**
 *
 * @author buggy
 */
public class ListaProduccionProceso extends Panel {

    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig ordenColumn;
    private ColumnConfig produccionColumn;
    private ColumnConfig fechaColumn;
    private ColumnConfig estadoColumn;
    private ColumnConfig operarioColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
//    private ToolbarButton editarOrden;
    private ToolbarButton detalle;
    private ToolbarButton anularProduccion;
    private ToolbarButton aprobarProduccion;
//    private ToolbarButton nuevoCliente;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarnorden;
    protected String buscarnproduccion;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
    public MainEntryPoint pan;
    public KMenu kmenu;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public ListaProduccionProceso() {
        this.setClosable(true);
        this.setId("TPfun5001");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Lista Produccion de Procesos.");
        onModuleLoad();
    }

    public ListaProduccionProceso(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun5001");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Lista Produccion de Procesos.");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Produccion.php?funcion=ListarProduccionProcesos");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idproduccionproceso"),
                    new StringFieldDef("numeroorden"),
                    new StringFieldDef("numeroproduccion"),
                    new StringFieldDef("fechaentrega"),
                    new StringFieldDef("empleado"),
                    new StringFieldDef("estado"), //                    new StringFieldDef("observacion")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Orden", "idproduccionproceso", (ANCHO / 8), true);
        idColumn.setWidth(100);
        ordenColumn = new ColumnConfig("Nro. Orden Produccion", "numeroorden", (ANCHO / 5));
        ordenColumn.setId("expandible");

        produccionColumn = new ColumnConfig("Nro. Prod Procesos", "numeroproduccion", (ANCHO / 5), true);
        fechaColumn = new ColumnConfig("Fecha Entrega", "fechaentrega", (ANCHO / 5), true);
        estadoColumn = new ColumnConfig("Estado", "estado", (ANCHO / 5), true);
        operarioColumn = new ColumnConfig("Operario", "empleado", (ANCHO / 5), true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    produccionColumn,
                    ordenColumn,
                    operarioColumn,
                    fechaColumn,
                    estadoColumn
//                    observacionColumn,
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Prod-Proce");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Produccion de Procesos");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(true);
        grid.setAutoExpandColumn("expandible");
        grid.setLoadMask(true);
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setIconCls("grid-icon");
        grid.addGridRowListener(new GridRowListenerAdapter() {

            @Override
            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();


                selecionado = records[0].getAsString("idproduccionproceso");
                String enlTemp = "funcion=reporteProduccionHTML&idproduccionproceso=" + selecionado;
                verReporte(enlTemp);
            }
        });



        anularProduccion = new ToolbarButton("Anular Produccion Proceso");
        anularProduccion.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Anular Produccion de Procesos");
        anularProduccion.setTooltip(tipsConfig2);

        aprobarProduccion = new ToolbarButton("Aprobar Produccion");
        aprobarProduccion.setEnableToggle(true);
        QuickTipsConfig tipsConfig21 = new QuickTipsConfig();
        tipsConfig21.setText("Aprobar Produccion de Procesos");
        aprobarProduccion.setTooltip(tipsConfig21);


        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        detalle = new ToolbarButton("Detalle");
        detalle.setEnableToggle(true);
        QuickTipsConfig tipsConfig4 = new QuickTipsConfig();
        tipsConfig4.setText("Ver Detalle de Produccion de Procesos");
        detalle.setTooltip(tipsConfig3);

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();

        pagingToolbar.addButton(anularProduccion);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(aprobarProduccion);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(detalle);
        pagingToolbar.addSeparator();

        String items[] = {"Nro. Orden", "Nro. Produccion"};
        String tiposItems[] = {"text", "text"};
        buscadorToolBar = new BuscadorToolBar(items, tiposItems);
        grid.setTopToolbar(buscadorToolBar.getToolbar());
        grid.setBottomToolbar(pagingToolbar);
        buscar = buscadorToolBar.getBuscar();
        addListenersBuscador();

        addListenersBuscadoresText();
        add(grid);
        aniadirListenersCompra();
    }

    public GridPanel getGrid() {
        return grid;
    }

    public void setGrid(GridPanel grid) {
        this.grid = grid;
    }

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    private void aniadirListenersCompra() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        anularProduccion.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idproduccionproceso");
                    MessageBox.confirm("Anular Produccion de Procesos", "Realmente desea eliminar esta Produccion??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Produccion.php?funcion=EliminarProduccionProceso&idproduccionproceso=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el Compra", "cargar");
                                final Conector conec = new Conector(enlace, false);
                                try {
                                    conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {

                                        public void onResponseReceived(Request request, Response response) {
                                            String data = response.getText();
                                            JSONValue jsonValue = JSONParser.parse(data);
                                            JSONObject jsonObject;
                                            if ((jsonObject = jsonValue.isObject()) != null) {
                                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                                                if (errorR.equalsIgnoreCase("true")) {
                                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                                    reload();
                                                } else {
                                                    Utils.setErrorPrincipal(mensajeR, "error");
                                                }
                                            }
                                        }

                                        public void onError(Request request, Throwable exception) {
                                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                        }
                                    });
                                } catch (RequestException ex) {
                                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                }
                            }
                        }
                    });
                } else {
                    MessageBox.alert("No hay Compra selecionado  y/o selecciono mas de uno.");
                }
                anularProduccion.setPressed(false);
            }
        });

         aprobarProduccion.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idproduccionproceso");
                    MessageBox.confirm("Aprobar Produccion de Procesos", "Realmente desea Aprobar esta Produccion??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Produccion.php?funcion=AprobarProduccionProceso&idproduccionproceso=" + selecionado;
                                Utils.setErrorPrincipal("Aprobando produccion de procesos", "cargar");
                                final Conector conec = new Conector(enlace, false);
                                try {
                                    conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {

                                        public void onResponseReceived(Request request, Response response) {
                                            String data = response.getText();
                                            JSONValue jsonValue = JSONParser.parse(data);
                                            JSONObject jsonObject;
                                            if ((jsonObject = jsonValue.isObject()) != null) {
                                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                                                if (errorR.equalsIgnoreCase("true")) {
                                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                                    reload();
                                                } else {
                                                    Utils.setErrorPrincipal(mensajeR, "error");
                                                }
                                            }
                                        }

                                        public void onError(Request request, Throwable exception) {
                                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                        }
                                    });
                                } catch (RequestException ex) {
                                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                }
                            }
                        }
                    });
                } else {
                    MessageBox.alert("No hay Compra selecionado  y/o selecciono mas de uno.");
                }
                aprobarProduccion.setPressed(false);
            }
        });
        //**************************************************
        //***********NUEVO CLIENTE
        //**
//        nuevoCliente.addListener(new ButtonListenerAdapter() {
//
//            @Override
//            public void onClick(Button button, EventObject e) {
//                CargarNuevoCompra();
//            }
//        });

        //**************************************************
        //***********EDITAR Compra
        //**************************************************

        detalle.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idproduccionproceso");
                            String enlTemp = "funcion=reporteProduccionHTML&idproduccionproceso=" + selecionado;
                            verReporte(enlTemp);

                        } else {

                            Utils.setErrorPrincipal("seleccione un compra para cambiar estado", "error");
                        }

                    }
                });



        //**************************************************
        //*********** LISTENERS DE LA TABLA
        //**************************************************
        grid.addListener(
                new PanelListenerAdapter() {

                    @Override
                    public void onRender(Component component) {
                        store.load(0, 100);
                    }
                });
        grid.addGridCellListener(
                new GridCellListenerAdapter() {

                    @Override
                    public void onCellClick(GridPanel grid, int rowIndex, int colIndex, EventObject e) {
                        if (grid.getColumnModel().getDataIndex(colIndex).equals("indoor")) {
                            Record record = grid.getStore().getAt(rowIndex);
                            record.set("indoor", !record.getAsBoolean("indoor"));
                        }

                    }
                });

    }

    private void addListenersBuscador() {
        buscar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                buscarSegunParametros();
            }
        });
    }

    private void addListenersBuscadoresText() {

        //*********************************************************************
        //***************BUSCADOR NUMERO DOCUMENTO************************************
        //*********************************************************************
        buscadorToolBar.getItemsText1().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                }
            }
        });

        //*********************************************************************
        //***************BUSCADOR FECHA************************************
        //*********************************************************************
        buscadorToolBar.getItemsText2().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                }
            }
        });

    }

    public void buscarSegunParametros() {
        buscarnorden = buscadorToolBar.getItemsText1().getText();
        buscarnproduccion = buscadorToolBar.getItemsText2().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarnproduccion", buscarnproduccion),
                    new UrlParam("buscarnumeroorden", buscarnorden),}, false);
    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
