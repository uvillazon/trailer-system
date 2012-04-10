/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.parametros;

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
public class Compras extends Panel {

    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig nroDocColumn;
    private ColumnConfig proveedorColumn;
    private ColumnConfig fechaColumn;
    private ColumnConfig montoTotalColumn;
    private ColumnConfig observacionColumn;
    private ColumnConfig ItemColumn;
    private ColumnConfig UnidadColumn;
    private ColumnConfig CantidadColumn;
    private ColumnConfig PrecioUnitColumn;
    private ColumnConfig OrdenProduccion;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarCompra;
    private ToolbarButton eliminarCompra;
//    private ToolbarButton nuevoCliente;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarnrodocumento;
    protected String buscarfecha;
    protected String buscarItem;
    protected String buscarOP;
    protected String buscarProveedor;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();  
    private ToolbarButton detalle;
    private RealizarCompra formulario;
    public MainEntryPoint pan;
	public KMenu kmenu;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public Compras() {
        this.setClosable(true);
        this.setId("TPfun2003");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Compras");
        onModuleLoad();
    }
    public Compras(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun2003");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Compras");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Compras.php?funcion=ListarCompras");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idcompra"),
                    new StringFieldDef("numerodocumento"),
                    new StringFieldDef("proveedor"),
                    new StringFieldDef("fecha"),
                    new StringFieldDef("montototal"),
                    new StringFieldDef("observacion"),
                    new StringFieldDef("item"),
                    new StringFieldDef("unidad"),
                    new StringFieldDef("cantidad"),
                    new StringFieldDef("preciounitario"),
                    new StringFieldDef("op")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Compra", "idcompra",(ANCHO / 8), true);
        idColumn.setWidth(100);
        nroDocColumn = new ColumnConfig("Nro. Documento", "numerodocumento",(ANCHO / 8),true);

        ItemColumn = new ColumnConfig("Item", "item", (ANCHO/8), true);
        UnidadColumn = new ColumnConfig("Unidad", "unidad", 80, true);
        CantidadColumn = new ColumnConfig("Cantidad", "cantidad", 80, true);
        PrecioUnitColumn = new ColumnConfig("P/Unit.", "preciounitario", 100, true);
        OrdenProduccion = new ColumnConfig("OP", "op", 100, true);
        proveedorColumn = new ColumnConfig("Proveedor", "proveedor", (ANCHO / 8), true);
        proveedorColumn.setId("expandible");
        fechaColumn = new ColumnConfig("Fecha", "fecha", 100, true);
        montoTotalColumn = new ColumnConfig("Monto Total", "montototal", (ANCHO / 8), true);
        observacionColumn = new ColumnConfig("Observacion", "observacion", (ANCHO / 8), true);
        observacionColumn.setId("expandible");

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    nroDocColumn,
                    proveedorColumn,
                    fechaColumn,
                    ItemColumn,
                    UnidadColumn,
                    CantidadColumn,
                    PrecioUnitColumn,
                    montoTotalColumn,
                    OrdenProduccion,
                    observacionColumn,
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Compras");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Compras");
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

        editarCompra = new ToolbarButton("Editar");
        editarCompra.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Compra");
        editarCompra.setTooltip(tipsConfig);

        eliminarCompra = new ToolbarButton("Eliminar");
        eliminarCompra.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Compra");
        eliminarCompra.setTooltip(tipsConfig2);


        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
         detalle = new ToolbarButton("Detalle");
        detalle.setEnableToggle(true);
        QuickTipsConfig tipsConfig4 = new QuickTipsConfig();
        tipsConfig4.setText("Ver Detalle de la compra , Inacativo");
        detalle.setTooltip(tipsConfig3);

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarCompra);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarCompra);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(detalle);
        pagingToolbar.addSeparator();

        String items[] = {"Nro. Documento", "Fecha","Item","Nun.OP","Proveedor"};
        String tiposItems[] = {"text", "date","text","text","text"};
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


    private void cargarDatosEditarCompra(final String idcompra) {
        String enlace = "php/Compras.php?funcion=BuscarCompraPorId&idcompra=" + idcompra;
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

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {
                                    Object[][] proveedores = Utils.getArrayOfJSONObject(marcaO, "proveedorM", new String[]{"idproveedor", "nombre"});
                                    Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoM", new String[]{"id", "nombre","unidad"});
                                    Object[][] encargados = Utils.getArrayOfJSONObject(marcaO, "encargadoM", new String[]{"idempleado", "nombre"});
                                    Object[][] compra = Utils.getArrayOfJSONObject(marcaO, "compra", new String[]{"idcompra", "numerodocumento","idproveedor","idempleado",
                                    "fecha","tipodocumento","montototal","montoapagar","descuentoporcentaje","descuento","observacion"});
                                    Object[][] detalles = Utils.getArrayOfJSONObject(marcaO, "detalles", new String[]{"id", "unidad","cantidad","preciounitario","preciototal","detalle"});
                                    formulario = null;
                                    formulario = new RealizarCompra(proveedores,productos, encargados, compra, detalles,Compras.this);
                                    kmenu.seleccionarOpcion(null, "fun2002", e, formulario);

                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        } else {
                        }
                        throw new UnsupportedOperationException("Not supported yet.");
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

        eliminarCompra.addListener(new ButtonListenerAdapter() {

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
                eliminarCompra.setPressed(false);
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
        editarCompra.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idcompra = records[0].getAsString("idcompra");
                            cargarDatosEditarCompra(idcompra);
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
                            selecionado = records[0].getAsString("idcompra");
                            String enlTemp = "funcion=detalleCompra&idcompra=" + selecionado;
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
        //***************BUSCADOR ITEMS************************************
        //*********************************************************************
        buscadorToolBar.getItemsText3().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                }
            }
        });

         //*********************************************************************
        //***************BUSCADOR OrdenProduccion*******************************
        //*********************************************************************
        buscadorToolBar.getItemsText4().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                }
            }
        });
        
        
         //*********************************************************************
        //***************BUSCADOR Proveedor************************************
        //*********************************************************************
        buscadorToolBar.getItemsText5().addListener(new TextFieldListenerAdapter() {

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
        buscarnrodocumento = buscadorToolBar.getItemsText1().getText();
        buscarfecha = DateUtil.format(buscadorToolBar.getItemsDate2().getValue(), "Y-m-d");
        buscarItem = buscadorToolBar.getItemsText3().getText();
        buscarOP = buscadorToolBar.getItemsText4().getText();
        buscarProveedor = buscadorToolBar.getItemsText5().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarnumerodocumento", buscarnrodocumento),
                    new UrlParam("buscarfecha", buscarfecha),
                    new UrlParam("bucarItems",buscarItem),
                    new UrlParam("buscarOP",buscarOP),
                    new UrlParam("buscarProveedor",buscarProveedor),
                    
                }, false);
    }


    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
