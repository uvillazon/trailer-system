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
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.data.*;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.Renderer;
import org.trailer.client.MainEntryPoint;
import org.trailer.client.util.KMenu;

/**
 *
 * @author buggy
 */
public class Logos extends Panel {

    private KMenu kmenu;
    private MainEntryPoint pan;
    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig canalColumn;
    private ColumnConfig kardexColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig disenoColumn;
    private ColumnConfig empresaColumn;
//    private ColumnConfig grupoColumn;
    private ColumnConfig precioColumn;
    private ColumnConfig imagenColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarProducto;
    private ToolbarButton eliminarProducto;
    private ToolbarButton nuevoProducto;
    private ToolbarButton detalle;
    private ToolbarButton enlazarOP;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarempresa;
    protected String buscarnombre;
    protected String buscarcategoria;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
//    private ToolbarButton reporteCliente;
    private EditarNuevoLogo formulario;
    private EnlazarOpLogo formulario2;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;
    private Componentes formC;
    private ToolbarButton imagenproducto;

    public Logos(KMenu kmenu, MainEntryPoint panel) {
        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun2007");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Bordados");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Producto.php?funcion=ListarProductosLogos");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idproducto"),
                    new StringFieldDef("imagen"),
                    new StringFieldDef("canal"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("precio"),
                    new StringFieldDef("empresa"),
                    new StringFieldDef("puntadas"),
                    new StringFieldDef("ancho"),
                    new StringFieldDef("alto"),
                    new StringFieldDef("idempresa"),
                    new StringFieldDef("observacion"),
                    new StringFieldDef("kardex")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id", "idproducto", (ANCHO / 8), true);
        idColumn.setWidth(100);
        canalColumn = new ColumnConfig("Canal", "canal", (ANCHO / 6));
        kardexColumn = new ColumnConfig("Kardex", "kardex", (ANCHO / 6));
        disenoColumn = new ColumnConfig("Nombre Diseño", "nombre", (ANCHO / 6), true);
        disenoColumn.setId("expandible");
        empresaColumn = new ColumnConfig("Empresa", "empresa", (ANCHO / 6), true);
        imagenColumn = new ColumnConfig("Imagen", "imagen", (ANCHO / 6), true, new Renderer() {

            public String render(Object value, CellMetadata cellMetadata,
                    Record record, int rowIndex, int colNum, Store store) {
                return Format.format("<img src=\"images/jpg.php?name=../{0}&size=100\">",
                        new String[]{record.getAsString("imagen")});
            }
        }, "imagen");
        precioColumn = new ColumnConfig("Precio", "precio", (ANCHO / 6), true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    imagenColumn,
                    canalColumn,
                    kardexColumn,
                    disenoColumn,
                    precioColumn,
                    empresaColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Logos");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);

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
//                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");
                Record[] records = cbSelectionModel.getSelections();


                selecionado = records[0].getAsString("idproducto");
                String enlTemp = "funcion=reporteBordadoHTML&idproducto=" + selecionado;
                verReporte(enlTemp);
//                    Window.alert(enlTemp);
            }
        });

        nuevoProducto = new ToolbarButton("Nuevo");
        nuevoProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear Nuevo Producto");
        nuevoProducto.setTooltip(tipsConfig1);

        editarProducto = new ToolbarButton("Editar");
        editarProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Producto");
        editarProducto.setTooltip(tipsConfig);

        eliminarProducto = new ToolbarButton("Eliminar");
        eliminarProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar producto");
        eliminarProducto.setTooltip(tipsConfig2);

        detalle = new ToolbarButton("Detalle");
        detalle.setEnableToggle(true);
        QuickTipsConfig tipsConfig21 = new QuickTipsConfig();
        tipsConfig21.setText("Detalle");
        detalle.setTooltip(tipsConfig21);

        imagenproducto = new ToolbarButton("Cargar Imagen");
        imagenproducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig10 = new QuickTipsConfig();
        tipsConfig10.setText("Cargar Imagen al producto");
        //tipsConfig.setTitle("Tip Title");
        imagenproducto.setTooltip(tipsConfig10);

        enlazarOP = new ToolbarButton("Enlazar OP");
        enlazarOP.setEnableToggle(true);
        QuickTipsConfig tipsConfig101 = new QuickTipsConfig();
        tipsConfig101.setText("Bordado se enlazara con una Orden De Produccion");

        enlazarOP.setTooltip(tipsConfig101);



        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoProducto);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarProducto);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarProducto);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(detalle);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(imagenproducto);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(enlazarOP);


        String items[] = {"Empresa", "Nombre"};
        String tiposItems[] = {"text", "text"};
        buscadorToolBar = new BuscadorToolBar(items, tiposItems);
        grid.setTopToolbar(buscadorToolBar.getToolbar());
        grid.setBottomToolbar(pagingToolbar);
        buscar = buscadorToolBar.getBuscar();
//
        addListenersBuscador();

        addListenersBuscadoresText();

//        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);
        aniadirListenersProductos();
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

    private void CargarNuevoProducto() {
        String enlace = "php/Producto.php?funcion=CargarNuevoLogo";
        Utils.setErrorPrincipal("Cargando parametros de Nuevo Logo", "cargar");
        final Conector conec = new Conector(enlace, false);
        {
            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

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
                                    Object[][] empresa = Utils.getArrayOfJSONObject(marcaO, "empresaM", new String[]{"idempresa", "nombre"});
//                                    Object[][] categorias = Utils.getArrayOfJSONObject(marcaO, "categoriaM", new String[]{"idcategoriaproducto", "nombre"});

                                    formulario = null;
                                    formulario = new EditarNuevoLogo(null, "", "", "", "", "", 0.0, 0.0, "", "", "", empresa, Logos.this);
                                    formulario.show();
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

    private void cargarDatosEditarProductos(final String idproducto) {
        String enlace = "php/Producto.php?funcion=CargarNuevoLogo";
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {
            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

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
                                    Object[][] empresas = Utils.getArrayOfJSONObject(marcaO, "empresaM", new String[]{"idempresa", "nombre"});

                                    store.filter("idproducto", idproducto);
                                    Record r = store.getRecords()[0];

                                    String diseño = r.getAsString("nombre");
                                    String canal = r.getAsString("canal");
                                    String imagen = r.getAsString("imagen");
                                    String puntada = r.getAsString("puntadas");
                                    double anchos = r.getAsDouble("ancho");
                                    double altos = r.getAsDouble("alto");
                                    String empresa = r.getAsString("empresa");
                                    String idempresa = r.getAsString("idempresa");
                                    String observacion = r.getAsString("observacion");
                                    String precio = r.getAsString("precio");
                                    String kardex = r.getAsString("kardex");
//                                    Window.alert("datos "+" "+idproducto+" "+ imagen+" "+ canal+" "+ diseño+" "+ puntada+" "+ anchos+" "+ altos+" "+ observacion+" "+ idempresa+" "+empresa);
                                    store.clearFilter();
                                    formulario = null;
                                    formulario = new EditarNuevoLogo(idproducto, imagen, canal, kardex, diseño, puntada, anchos, altos, observacion, idempresa, empresa, empresas, Logos.this);
                                    formulario.show();
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

    private void cargarDatosEnlazarOP(final String idproducto) {
        String enlace = "php/Producto.php?funcion=CargarNuevoLogo";
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {
            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

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
                                    Object[][] empresas = Utils.getArrayOfJSONObject(marcaO, "empresaM", new String[]{"idempresa", "nombre"});

                                    store.filter("idproducto", idproducto);
                                    Record r = store.getRecords()[0];

                                    String diseño = r.getAsString("nombre");
                                    String canal = r.getAsString("canal");
                                    String imagen = r.getAsString("imagen");
                                    String puntada = r.getAsString("puntadas");
                                    double anchos = r.getAsDouble("ancho");
                                    double altos = r.getAsDouble("alto");
                                    String empresa = r.getAsString("empresa");
                                    String idempresa = r.getAsString("idempresa");
                                    String observacion = r.getAsString("observacion");
                                    String precio = r.getAsString("precio");
                                    String kardex = r.getAsString("kardex");
//                                    Window.alert("datos "+" "+idproducto+" "+ imagen+" "+ canal+" "+ diseño+" "+ puntada+" "+ anchos+" "+ altos+" "+ observacion+" "+ idempresa+" "+empresa);
                                    store.clearFilter();
                                    formulario2 = null;
                                    formulario2 = new EnlazarOpLogo(idproducto, imagen, canal, kardex, diseño, puntada, anchos, altos, observacion, idempresa, empresa, empresas, Logos.this);
                                    formulario2.show();
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

    private void aniadirListenersProductos() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarProducto.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idproducto");
                    MessageBox.confirm("Eliminar Producto", "Realmente desea eliminar este Producto??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Producto.php?funcion=EliminarProducto&idproducto=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando..", "cargar");
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
                eliminarProducto.setPressed(false);
            }
        });
        //**************************************************
        //***********NUEVO CLIENTE
        //**
        nuevoProducto.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                CargarNuevoProducto();
            }
        });

        //**************************************************
        //***********EDITAR CLIENTE
        //**************************************************
        editarProducto.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idproductos = records[0].getAsString("idproducto");

                            cargarDatosEditarProductos(idproductos);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un Producto para editar", "error");
                        }

                    }
                });

        enlazarOP.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idproductos = records[0].getAsString("idproducto");

                            cargarDatosEnlazarOP(idproductos);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un Producto para editar", "error");
                        }

                    }
                });

        detalle.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
//                            String idproducto = records[0].getAsString("idproducto");
//                            String nombre = records[0].getAsString("nombre");
//                            String descripcion = records[0].getAsString("descripcion");
//                            cargarDatosComponentes(idproducto, nombre, descripcion);
                            selecionado = records[0].getAsString("idproducto");
                            String enlTemp = "funcion=reporteBordadoHTML&idproducto=" + selecionado;
                            verReporte(enlTemp);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un bordado", "error");
                        }

                    }
                });
        imagenproducto.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {

                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idproducto");
                            String enlTemp = "php/uploadimagen.php?funcion=imagen&idproducto=" + selecionado;
                            com.google.gwt.user.client.Window.open(enlTemp, "_blank", "enlTemp");
//                            verReporte1(enlTemp);

                        } else {
                            MessageBox.alert("No hay producto selecionado para ver el reporte.");
                        }

                        imagenproducto.setPressed(false);
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

    }

    public void buscarSegunParametros() {
        buscarempresa = buscadorToolBar.getItemsText1().getText();
        buscarnombre = buscadorToolBar.getItemsText2().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarempresa", buscarempresa),
                    new UrlParam("buscarnombre", buscarnombre),}, false);
    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
