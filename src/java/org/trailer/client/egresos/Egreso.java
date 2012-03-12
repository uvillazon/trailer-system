/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.egresos;

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
public class Egreso extends Panel {

     private KMenu kmenu;
    private MainEntryPoint pan;
    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig fechaColumn;
    private ColumnConfig cuentaColumn;
    private ColumnConfig encargadoColumn;
    private ColumnConfig totalColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarCliente;
    private ToolbarButton eliminarCliente;
//    private ToolbarButton nuevoCliente;
    private BuscadorToolBar buscadorToolBar;

    protected String buscardetalle;
    protected String buscarfecha;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
//    private ToolbarButton reporteCliente;
//    private ToolbarButton cambiarestado;
//    private EditarNuevoCliente formulario;
    private RealizarEgreso formulario;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public Egreso() {
        this.setClosable(true);
        this.setId("TPfun2003");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Compras");
        onModuleLoad();
    }
    public Egreso(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun3001");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Egresos");
        onModuleLoad();
    }

    public void onModuleLoad() {

       DataProxy dataProxy = new ScriptTagProxy("php/Egreso.php?funcion=ListarEgresos");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idegreso"),
                    new StringFieldDef("detalle"),
                    new StringFieldDef("fecha"),
                    new StringFieldDef("total"),
                    new StringFieldDef("idcuenta"),
                    new StringFieldDef("empleado"),
                    new StringFieldDef("idempleado")
                });
               JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Egreso", "idegreso", (ANCHO / 8), true);
        idColumn.setWidth(100);
        fechaColumn = new ColumnConfig("Fecha", "fecha", 200, true);


        cuentaColumn = new ColumnConfig("Cuenta", "detalle", (200), true);

        encargadoColumn = new ColumnConfig("Encargado", "empleado", 200, true);
        totalColumn = new ColumnConfig("Total", "total", 200, true);
        totalColumn.setId("expandible");


        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    fechaColumn,
                    encargadoColumn,
                    cuentaColumn,
                    totalColumn
                };


        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Egresos");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Egresos");
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


                selecionado = records[0].getAsString("idcompra");
                String enlTemp = "funcion=reporteCompraHTML&idcpompra=" + selecionado;
                verReporte(enlTemp);
            }
        });

        editarCliente = new ToolbarButton("Editar");
        editarCliente.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Compra");
        editarCliente.setTooltip(tipsConfig);

        eliminarCliente = new ToolbarButton("Eliminar");
        eliminarCliente.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Compra");
        eliminarCliente.setTooltip(tipsConfig2);

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarCliente);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarCliente);
       
        pagingToolbar.addSeparator();

        String items[] = {"Detalle", "Fecha"};
        String tiposItems[] = {"text", "date"};
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


   private void cargarDatosEditarColor( final String idegreso) {
        String enlace = "php/Egreso.php?funcion=BuscarEgresoPorId&idegreso=" + idegreso;
        Utils.setErrorPrincipal("Cargando parametros para Egreso", "cargar");
        final Conector conec = new Conector(enlace, false);

        try {
            conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                private EventObject e;

                public void onResponseReceived(Request request, Response response) {

                    String data = response.getText();
                    JSONValue jsonValue = JSONParser.parse(data);
                    JSONObject jsonObject;
                    if ((jsonObject = jsonValue.isObject()) != null) {
                        String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                        String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                        if (errorR.equalsIgnoreCase("true")) {
                            Utils.setErrorPrincipal(mensajeR, "mensaje");

                            JSONValue productoV = jsonObject.get("resultado");
                            JSONObject productoO;
                             //com.google.gwt.user.client.Window.alert("Cuantas veces");
                            if ((productoO = productoV.isObject()) != null) {
                                Object[][] cuentas = Utils.getArrayOfJSONObject(productoO, "cuentaM", new String[]{"idcuenta", "nombre"});
                                Object[][] encargados = Utils.getArrayOfJSONObject(productoO, "encargadoM", new String[]{"idempleado", "nombre"});
                                String idegreso = Utils.getStringOfJSONObject(productoO, "idegreso");
                                String detalle = Utils.getStringOfJSONObject(productoO, "detalle");
                                String fecha = Utils.getStringOfJSONObject(productoO, "fecha");
                                String total = Utils.getStringOfJSONObject(productoO, "total");
                                String idcuenta = Utils.getStringOfJSONObject(productoO, "idcuenta");

                                String idempleado = Utils.getStringOfJSONObject(productoO, "idempleado");
                                formulario = new RealizarEgreso(cuentas, idcuenta, encargados, idempleado, fecha, total, detalle, idegreso);
                                kmenu.seleccionarOpcion(null, "fun2002", e, formulario);

                            } else {
                                Utils.setErrorPrincipal("Error en el objeto Egreso", "error");
                            }
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
            ex.getMessage();
            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
        }


    }

    private void aniadirListenersCompra() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarCliente.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idcompra");
                    MessageBox.confirm("Eliminar Compra", "Realmente desea eliminar esta compra??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Compras.php?funcion=EliminarCompras&idcompra=" + selecionado;
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
                eliminarCliente.setPressed(false);
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
        editarCliente.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idegreso = records[0].getAsString("idegreso");
                                cargarDatosEditarColor(idegreso);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione una compra para editar", "error");
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

    public String getLinksaveUpdate(String idrol, String nombre, String estado, Object[] seleccionados) {
        String dev = "";
        if (seleccionados.length >= 1) {

            if (idrol == null) {
                idrol = "nuevo";
            }
            JSONObject todos = new JSONObject();
            todos.put("idrol", new JSONString(idrol));
            todos.put("nombre", new JSONString(nombre));
            todos.put("estado", new JSONString(estado));
            JSONArray funcS = new JSONArray();
            for (int i = 0; i < seleccionados.length; i++) {
                Checkbox che = (Checkbox) seleccionados[i];
                funcS.set(i, new JSONString(che.getId().substring(1)));
            }
            todos.put("funciones", funcS);
            dev = todos.toString();
        } else {
            Utils.setErrorPrincipal("Por favor seleccione por lo menos una funacion", "error");
        }
        return dev;
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
        buscadorToolBar.getItemsDate2().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                }
            }
        });

    }

    public void buscarSegunParametros() {
        buscardetalle = buscadorToolBar.getItemsText1().getText();
        buscarfecha = DateUtil.format(buscadorToolBar.getItemsDate2().getValue(), "Y-m-d");
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscardetalle", buscardetalle),
                    new UrlParam("buscarfecha", buscarfecha),
                }, false);
    }


    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
