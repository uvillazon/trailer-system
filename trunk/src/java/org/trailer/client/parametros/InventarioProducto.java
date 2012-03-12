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
import org.trailer.client.util.BuscadorToolBar;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.data.*;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.GridEditor;

/**
 *
 * @author buggy
 */
public class InventarioProducto extends Panel {

    private EditorGridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig caracteristicaColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig telaColumn;
    private ColumnConfig cantidadColumn;
    private ColumnConfig categoriaColumn;
    private ColumnConfig costoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton guardar;
    private ToolbarButton detalle;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarcodigo;
    protected String buscarnombre;
    protected String buscarcategoria;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public InventarioProducto() {
        this.setClosable(true);
        this.setId("TPfun2010");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Inventario Producto");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/InventarioProducto.php?funcion=ListarInventariosProductos");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idproducto"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("descripcion"),
                    new StringFieldDef("tela"),
                    new StringFieldDef("categoria"),
                    new StringFieldDef("cantidad"),
                    new StringFieldDef("costoreal")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Materia Prima", "idproducto", (ANCHO / 8), true);
        idColumn.setWidth(100);
        codigoColumn = new ColumnConfig("Codigo", "codigo", (ANCHO / 8));

        codigoColumn.setId("expandible");
//        categoriaColumn = new ColumnConfig("Categoria", "categoria", (ANCHO / 8), true);
        caracteristicaColumn = new ColumnConfig("Caracteristicas", "descripcion", (ANCHO / 8), true);
        nombreColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 8), true);
//        stockColumn = new ColumnConfig("Stock Minimo", "stockminimo", (ANCHO / 8), true);
        telaColumn = new ColumnConfig("Tela", "tela", (ANCHO / 8), true);
        costoColumn = new ColumnConfig("Costo real", "costoreal", (ANCHO / 8), true);
        costoColumn.setAlign(TextAlign.RIGHT);
        costoColumn.setEditor(new GridEditor(new NumberField()));
        cantidadColumn = new ColumnConfig("Cantidad", "cantidad", (ANCHO / 8), true);
        cantidadColumn.setAlign(TextAlign.RIGHT);
        cantidadColumn.setEditor(new GridEditor(new NumberField()));
        categoriaColumn = new ColumnConfig("Categoria", "categoria", (ANCHO / 8), true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    codigoColumn,
                    nombreColumn,
                    telaColumn,
                    caracteristicaColumn,
                    categoriaColumn,
                    cantidadColumn,
                    costoColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-InventarioProducto");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Inventario Producto");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(true);
        grid.setAutoExpandColumn("expandible");
        grid.setLoadMask(true);
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setIconCls("grid-icon");


        guardar = new ToolbarButton("Guardar");
        guardar.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Guardar datos Productos");
        guardar.setTooltip(tipsConfig);
        guardar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                guardarCambiosInventarioMateriaPrima();
                guardar.setPressed(false);
            }
        });

        detalle = new ToolbarButton("Detalle");
        detalle.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Detalle Producto");
        detalle.setTooltip(tipsConfig2);

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(guardar);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(detalle);
        pagingToolbar.addSeparator();

        String items[] = {"Codigo", "Nombre", "Categoria"};
        String tiposItems[] = {"text", "text", "text"};
        buscadorToolBar = new BuscadorToolBar(items, tiposItems);
        grid.setTopToolbar(buscadorToolBar.getToolbar());
        grid.setBottomToolbar(pagingToolbar);
        buscar = buscadorToolBar.getBuscar();
//
        addListenersBuscador();

        addListenersBuscadoresText();


        add(grid);
        //panel.add(grid);
        aniadirListenersCliente();
    }

    public EditorGridPanel getGrid() {
        return grid;
    }

    public void setGrid(EditorGridPanel grid) {
        this.grid = grid;
    }

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    private void guardarCambiosInventarioMateriaPrima() {
        Record[] records;

        records = grid.getStore().getModifiedRecords();

        if (records.length >= 1) {
            JSONArray funcS = new JSONArray();
            JSONObject pa = null;
            String temp;
            for (int i = 0; i < records.length; i++) {
                pa = new JSONObject();
                pa.put("idproducto", new JSONString(records[i].getAsString("idproducto")));
                pa.put("cantidad", new JSONString(records[i].getAsString("cantidad")));
                pa.put("costoreal", new JSONString(records[i].getAsString("costoreal")));
                funcS.set(i, pa);
                pa = null;
            }
            JSONObject joo = new JSONObject();
            joo.put("materias", funcS);

            String datos = "resultado=" + joo.toString();
            String enlace = "php/InventarioProducto.php?funcion=GuardarInventarioProducto&" + datos;
            Utils.setErrorPrincipal("Guardando los datos de inventario", "cargar");
            final Conector conec = new Conector(enlace, false, "GET");
            try {
                conec.getRequestBuilder().sendRequest(datos, new RequestCallback() {

                    public void onResponseReceived(Request request, Response response) {

                        String data = response.getText();
                        JSONValue jsonValue = JSONParser.parse(data);
                        JSONObject jsonObject;
                        if ((jsonObject = jsonValue.isObject()) != null) {
                            String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                            String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                            if (errorR.equalsIgnoreCase("true")) {
                                Utils.setErrorPrincipal(mensajeR, "mensaje");
                            } else {
                                Utils.setErrorPrincipal(mensajeR, "error");
                            }
                        }
                    }

                    public void onError(Request request, Throwable exception) {
                        //Window.alert("Ocurrio un error al conectar con el servidor ");
                        Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                    }
                });
            } catch (RequestException ex) {
                Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
            }
        } else {
            MessageBox.alert("No existen cambios para guardar.. ");
        }
    }

    private void aniadirListenersCliente() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        detalle.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idmateriaprima");
                    MessageBox.confirm("Eliminar Materia Prima", "Realmente desea eliminar esta Materia Prima??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/MateriaPrima.php?funcion=EliminarMateriaPrima&idmateriaprima=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando la Materia Prima", "cargar");
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
                                                    //Window.alert(mensajeR);
                                                    Utils.setErrorPrincipal(mensajeR, "error");
                                                }
                                            }
                                        }

                                        public void onError(Request request, Throwable exception) {
                                            //Window.alert("Ocurrio un error al conectar con el servidor ");
                                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                        }
                                    });
                                } catch (RequestException ex) {
                                    //Window.alert("Ocurrio un error al conectar con el servidor");
                                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                }
                            }
                        }
                    });
                } else {
                    MessageBox.alert("No hay Cliente selecionado para el Cliente y/o selecciono mas de uno.");
                }
                detalle.setPressed(false);
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
        //***************BUSCADOR NIT************************************
        //*********************************************************************
        buscadorToolBar.getItemsText1().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //*********************************************************************
        //***************BUSCADOR DE NOMBRE************************************
        //*********************************************************************
        buscadorToolBar.getItemsText2().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //*********************************************************************
        //***************BUSCADOR DE APELLIDO************************************
        //*********************************************************************
        buscadorToolBar.getItemsText3().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

    }

    public void buscarSegunParametros() {
        buscarcodigo = buscadorToolBar.getItemsText1().getText();
        buscarnombre = buscadorToolBar.getItemsText2().getText();
        buscarcategoria = buscadorToolBar.getItemsText3().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarcodigo", buscarcodigo),
                    new UrlParam("buscarnombre", buscarnombre),
                    new UrlParam("buscarcategoria", buscarcategoria)
                }, false);
    }
}
