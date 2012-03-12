/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.parametros;

import org.trailer.client.sistema.*;
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
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.Renderer;
import org.trailer.client.MainEntryPoint;
import org.trailer.client.util.KMenu;

/**
 *
 * @author buggy
 */
public class Producto extends Panel {

    private KMenu kmenu;
    private MainEntryPoint pan;
    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig medidaColumn;
    private ColumnConfig categoriaColumn;
    private ColumnConfig grupoColumn;
    private ColumnConfig telaColumn;
    private ColumnConfig imagenColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarProducto;
    private ToolbarButton eliminarProducto;
    private ToolbarButton nuevoProducto;
    private ToolbarButton cotizacion;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarcodigo;
    protected String buscarnombre;
    protected String buscarcategoria;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
//    private ToolbarButton reporteCliente;
    private EditarNuevoProducto formulario;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;
    private Componentes formC;
    private ToolbarButton imagenproducto;

    public Producto(KMenu kmenu, MainEntryPoint panel) {
        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun2006");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Productos");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Producto.php?funcion=ListarProductos");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idproducto"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("imagen"),
                    new StringFieldDef("medidas"),
                    new StringFieldDef("descripcion"),
                    new StringFieldDef("tela"),
                    new StringFieldDef("categoria"),
                    new StringFieldDef("precio"),
                    new StringFieldDef("utilidad"),
                    new StringFieldDef("grupo")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id", "idproducto", (ANCHO / 8), true);
        idColumn.setWidth(100);
        codigoColumn = new ColumnConfig("Codigo", "codigo", 100);

        codigoColumn.setId("expandible");
        medidaColumn = new ColumnConfig("Medidas", "medidas", 150, true);
        nombreColumn = new ColumnConfig("Nombre", "nombre", 300, true);
        categoriaColumn = new ColumnConfig("Categoria", "categoria", (ANCHO / 7), true);
        grupoColumn = new ColumnConfig("Grupo", "grupo", (ANCHO / 7), true);
        imagenColumn = new ColumnConfig("Imagen", "imagen", (ANCHO / 7), true, new Renderer() {

            public String render(Object value, CellMetadata cellMetadata,
                    Record record, int rowIndex, int colNum, Store store) {
                return Format.format("<img src=\"images/jpg.php?name=../{0}&size=100\">",
                        new String[]{record.getAsString("imagen")});
            }
        }, "imagen");
        telaColumn = new ColumnConfig("Tela", "tela", (ANCHO / 7), true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    imagenColumn,
                    codigoColumn,
                    nombreColumn,
                    medidaColumn,
                    telaColumn,
                  
                    categoriaColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-producto");
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
                String enlTemp = "funcion=reporteProductoHTML&idproducto=" + selecionado;
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

        cotizacion = new ToolbarButton("Cotizar");
        cotizacion.setEnableToggle(true);
        QuickTipsConfig tipsConfig21 = new QuickTipsConfig();
        tipsConfig21.setText("Cotizar Producto");
        cotizacion.setTooltip(tipsConfig21);

        imagenproducto = new ToolbarButton("Cargar Imagen   ");
        imagenproducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig10 = new QuickTipsConfig();
        tipsConfig10.setText("Cargar Imagen al producto");
        //tipsConfig.setTitle("Tip Title");
        imagenproducto.setTooltip(tipsConfig10);

//        reporteCliente = new ToolbarButton("Reporte");
//        reporteCliente.setEnableToggle(true);
//        tipsConfig3.setText("Reporte Cliente");
//        reporteCliente.setTooltip(tipsConfig3);

//

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
        pagingToolbar.addButton(cotizacion);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(imagenproducto);


        String items[] = {"Codigo", "Nombre", "Categoria"};
        String tiposItems[] = {"text", "text", "text"};
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
        String enlace = "php/Producto.php?funcion=CargarNuevoProducto";
        Utils.setErrorPrincipal("Cargando parametros de Nuevo Cliente", "cargar");
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
                                    Object[][] grupos = Utils.getArrayOfJSONObject(marcaO, "grupoM", new String[]{"idgrupo", "nombre"});
                                    Object[][] categorias = Utils.getArrayOfJSONObject(marcaO, "categoriaM", new String[]{"idcategoriaproducto", "nombre"});

                                    formulario = null;
                                    formulario = new EditarNuevoProducto(null, "", "", "", "", "", "", "", "", grupos, categorias, Producto.this);
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

    private void cargarDatosEditarProductos(String idproducto) {
        String enlace = "php/Producto.php?funcion=BuscarProductoPorId&idproducto=" + idproducto;
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
                                    Object[][] categorias = Utils.getArrayOfJSONObject(marcaO, "categoriaM", new String[]{"idcategoriaproducto", "nombre"});
                                    Object[][] grupos = Utils.getArrayOfJSONObject(marcaO, "grupoM", new String[]{"idgrupo", "nombre"});
                                    String idproducto = Utils.getStringOfJSONObject(marcaO, "idproducto");
                                    String codigo = Utils.getStringOfJSONObject(marcaO, "codigo");
                                    String idcategoria = Utils.getStringOfJSONObject(marcaO, "idcategoriaproducto");
                                    String nombre = Utils.getStringOfJSONObject(marcaO, "nombre");
                                    String tela = Utils.getStringOfJSONObject(marcaO, "tela");
                                    String medidas = Utils.getStringOfJSONObject(marcaO, "medidas");
                                    String imagen = Utils.getStringOfJSONObject(marcaO, "imagen");
                                    String idgrupo = Utils.getStringOfJSONObject(marcaO, "idgrupo");
                                    String descripcion = Utils.getStringOfJSONObject(marcaO, "descripcion");
//                               com.google.gwt.user.client.Window.alert(idgrupo);
//                               com.google.gwt.user.client.Window.alert(idcategoria);
                                    formulario = null;

                                    formulario = new EditarNuevoProducto(idproducto, nombre, imagen, codigo, medidas, tela, idgrupo, idcategoria, descripcion, grupos, categorias, Producto.this);
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

    private void cargarDatosComponentes(final String idproducto, final String nombre, final String descripcion,final Float precio,final Float utilidad) {
        String enlace = "php/Componentes.php?funcion=BuscarComponentePorIdProducto&idproducto=" + idproducto;
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
                                    Object[][] procesos = Utils.getArrayOfJSONObject(marcaO, "procesoM", new String[]{"id", "costo", "unidad", "detalle"});
                                    Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoM", new String[]{"id", "costo", "unidad", "detalle"});
                                    Object[][] material = Utils.getArrayOfJSONObject(marcaO, "materiaM", new String[]{"id", "costo", "unidad", "detalle"});
//                                    Object[][] compra = Utils.getArrayOfJSONObject(marcaO, "compra", new String[]{"idcompra", "numerodocumento","idproveedor","idempleado",
//                                    "fecha","tipodocumento","montototal","montoapagar","descuentoporcentaje","descuento","observacion"});
                                    Object[][] detalles = Utils.getArrayOfJSONObject(marcaO, "detalleM", new String[]{"id", "detalle", "unidad", "cantidad", "preciounitario", "preciototal"});
//                                     MessageBox.alert("cargo los arreglos .");
                                    formC = null;
                                    formC = new Componentes(idproducto, nombre, descripcion, precio , utilidad,procesos, productos, material, detalles, "producto");
                                    kmenu.seleccionarOpcion(null, "fun2005", e, formC);

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


        cotizacion.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idproducto = records[0].getAsString("idproducto");
                            String nombre = records[0].getAsString("nombre");
                            String descripcion = records[0].getAsString("descripcion");
                            Float precio = records[0].getAsFloat("precio");
                            Float utilidad = records[0].getAsFloat("utilidad");
                            cargarDatosComponentes(idproducto, nombre, descripcion,precio,utilidad);
//                             cargarDatosComponentes(idproducto, nombre, descripcion);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un Grupo", "error");
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

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
