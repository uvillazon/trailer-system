/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.produccion;

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
public class ListarOrdenes extends Panel {

    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig ordenColumn;
    private ColumnConfig clienteColumn;
    private ColumnConfig fechaColumn;
    private ColumnConfig estadoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarOrden;
    private ToolbarButton eliminarOrden;
//    private ToolbarButton nuevoCliente;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarnroorden;
    protected String buscarempresa;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
    private ToolbarButton detalle;
    private OrdenProduccion formulario;
    public MainEntryPoint pan;
    public KMenu kmenu;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;
    private ColumnConfig itemColumn;
    private ColumnConfig cantidadColumn;
    private ColumnConfig estadoopColumn;

    public ListarOrdenes() {
        this.setClosable(true);
        this.setId("TPfun4001");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Lista Orden P.");
        onModuleLoad();
    }

    public ListarOrdenes(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun4001");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Lista Orden P.");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/OrdenProduccion.php?funcion=ListarOrdenProduccion");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idordenproduccion"),
                    new StringFieldDef("numeroorden"),
                    new StringFieldDef("cliente"),
                    new StringFieldDef("item"),
                    new StringFieldDef("cantidad"),
                    new StringFieldDef("fechaentrega"),
                    new StringFieldDef("estado"),
                     new StringFieldDef("estadoop"),//                    new StringFieldDef("observacion")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Orden", "idordenproduccion", 200, true);
        idColumn.setWidth(100);
        ordenColumn = new ColumnConfig("Nro. Orden Produccion", "numeroorden", (ANCHO / 8));
        ordenColumn.setId("expandible");

        clienteColumn = new ColumnConfig("Cliente/Empresa", "cliente", 200, true);
        cantidadColumn = new ColumnConfig("Cantidad", "cantidad", 200, true);
        itemColumn = new ColumnConfig("Item", "item", 200, true);
        fechaColumn = new ColumnConfig("Fecha Entrega", "fechaentrega", 200, true);
        estadoColumn = new ColumnConfig("Estado Item", "estado", 150, true);
        estadoopColumn = new ColumnConfig("Estado OP", "estadoop", 150, true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    ordenColumn,
                    clienteColumn,
                    itemColumn,
                    cantidadColumn,
                    fechaColumn,
                    estadoColumn,
                    estadoopColumn
//                    observacionColumn,
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-OP");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Orden Produccion");
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


                selecionado = records[0].getAsString("idordenproduccion");
                String enlTemp = "funcion=reporteOrdenHTML&idordenproduccion=" + selecionado;
                verReporte(enlTemp);
            }
        });

        editarOrden = new ToolbarButton("Editar");
        editarOrden.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Orden");
        editarOrden.setTooltip(tipsConfig);

        eliminarOrden = new ToolbarButton("Eliminar");
        eliminarOrden.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Orden");
        eliminarOrden.setTooltip(tipsConfig2);


        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        detalle = new ToolbarButton("Detalle");
        detalle.setEnableToggle(true);
        QuickTipsConfig tipsConfig4 = new QuickTipsConfig();
        tipsConfig4.setText("Ver Detalle de la Orden Produccion , Inacativo");
        detalle.setTooltip(tipsConfig3);

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarOrden);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarOrden);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(detalle);
        pagingToolbar.addSeparator();

        String items[] = {"Nro. Orden", "Empresa/Cliente"};
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

    private void cargarDatosEditarCompra(final String idorden) {
        String enlace = "php/OrdenProduccion.php?funcion=BuscarOrdenPorId&idordenproduccion=" + idorden;
        Utils.setErrorPrincipal("Cargando parametros de la compra", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

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



                                Object[][] productos = Utils.getArrayOfJSONObject(jsonObject, "productoM", new String[]{"id", "detalle", "unidad", "preciounitario"});
                                Object[][] clientes = Utils.getArrayOfJSONObject(jsonObject, "clienteM", new String[]{"idcliente", "nombre"});
                                Object[][] responsables = Utils.getArrayOfJSONObject(jsonObject, "responsableM", new String[]{"idresponsable", "nombre", "idempresa"});
                                Object[][] empresas = Utils.getArrayOfJSONObject(jsonObject, "empresaM", new String[]{"idempresa", "nombre"});
                                Object[][] colores = Utils.getArrayOfJSONObject(jsonObject, "colorM", new String[]{"id", "nombre"});
                                Object[][] telas = Utils.getArrayOfJSONObject(jsonObject, "telaM", new String[]{"id", "detalle"});

                                Object[][] orden = Utils.getArrayOfJSONObject(jsonObject, "orden", new String[]{"idordenproduccion", "fecharecepcion", "fechaentrega", "idcliente",
                                            "idempresa", "idresponsable", "montototal", "montoacuenta", "saldo", "numerorecibo"});
                                Object[][] detalles = Utils.getArrayOfJSONObject(jsonObject, "detalleM", new String[]{"id", "detalle", "unidad", "cantidad", "preciounitario", "preciototal", "tela", "color", "detalle1", "detallebordado", "detallecostura", "estado"});

                                String numeroproduccion = Utils.getStringOfJSONObject(jsonObject, "numeroproduccion");
//                                    OrdenProduccion ordenproduccion = new OrdenProduccion(clientes, productos, responsables, empresas, telas, colores, numeroproduccion);

                                formulario = null;
                                formulario = new OrdenProduccion(clientes, productos, responsables, empresas, telas, colores, numeroproduccion, orden, detalles);
                                kmenu.seleccionarOpcion(null, "fun4000", e, formulario);


                            }
                        }
                    }

                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });

            } catch (RequestException e) {
                e.getMessage();
                MessageBox.alert("Ocurrio un error al conectarse con el SERVIDOR");
            }

        }
    }

    private void aniadirListenersCompra() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarOrden.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idordenproduccion");
                    MessageBox.confirm("Eliminar Orden Produccion", "Realmente desea eliminar esta Orden??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/OrdenProduccion.php?funcion=EliminarOrdenProduccion&idordenproduccion=" + selecionado;
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
                eliminarOrden.setPressed(false);
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
        editarOrden.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idorden = records[0].getAsString("idordenproduccion");
                            cargarDatosEditarCompra(idorden);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione una compra para editar", "error");
                        }

                    }
                });
        detalle.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idordenproduccion");
                            String enlTemp = "funcion=detalleCompra&idordenproduccion=" + selecionado;
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
        buscarnroorden = buscadorToolBar.getItemsText1().getText();
        buscarempresa = buscadorToolBar.getItemsText2().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarnumeroorden", buscarnroorden),
                    new UrlParam("buscarempresa", buscarempresa),}, false);
    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
