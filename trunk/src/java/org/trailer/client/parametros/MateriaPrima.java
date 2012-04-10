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
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.Renderer;

/**
 *
 * @author buggy
 */
public class MateriaPrima extends Panel {

    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig colorColumn;
    private ColumnConfig categoriaColumn;
    private ColumnConfig calidadColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig caracteristicaColumn;
    private ColumnConfig unidadColumn;
    private ColumnConfig cantidadColumn;
//    private ColumnConfig nitColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarMP;
    private ToolbarButton eliminarMP;
    private ToolbarButton nuevoMP;
    private BuscadorToolBar buscadorToolBar;
    protected String busarcolor;
    protected String buscarnombre;
    protected String buscarcategoria;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
//    private ToolbarButton inventarioMP;
    private ToolbarButton detalleMP;
    private EditarNuevoMateriaPrimaForm formulario;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;
    private ToolbarButton imagenproducto;
    private ColumnConfig imagenColumn;

    public MateriaPrima() {
        this.setClosable(true);
        this.setId("TPfun2000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Materia Prima");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/MateriaPrima.php?funcion=ListarMateriasPrimas");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idmateriaprima"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("color"),
                    new StringFieldDef("calidad"),
                    new StringFieldDef("categoria"),
                    new StringFieldDef("unidad"),
                    new StringFieldDef("cantidad"),
                    new StringFieldDef("imagen"),
                    new StringFieldDef("caracteristica")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Materia Prima", "idmateriaprima", (ANCHO / 8), true);
        idColumn.setWidth(100);
        codigoColumn = new ColumnConfig("Codigo", "codigo", (ANCHO / 8));


        colorColumn = new ColumnConfig("Color", "color", (ANCHO / 8), true);
        categoriaColumn = new ColumnConfig("Categoria", "categoria", (ANCHO / 8), true);

        calidadColumn = new ColumnConfig("Calidad", "calidad", (ANCHO / 8), true);
        nombreColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 8), true);
        nombreColumn.setId("expandible");
        caracteristicaColumn = new ColumnConfig("Caracteristicas", "caracteristica", (ANCHO / 8), true);
        unidadColumn = new ColumnConfig("Unidad", "unidad", (ANCHO / 8), true);
//        nitColumn = new ColumnConfig("Nit", "nit", (ANCHO / 8), true);
        cantidadColumn = new ColumnConfig("Cantidad", "cantidad", 100, true);
        imagenColumn = new ColumnConfig("Imagen", "imagen", 100, true, new Renderer() {

            public String render(Object value, CellMetadata cellMetadata,
                    Record record, int rowIndex, int colNum, Store store) {
                return Format.format("<img src=\"images/jpg.php?name=../{0}&size=100\">",
                        new String[]{record.getAsString("imagen")});
            }
        }, "imagen");
        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    imagenColumn,
                    nombreColumn,
                    colorColumn,
                    categoriaColumn,
                    calidadColumn,
                    unidadColumn,
                    caracteristicaColumn,
                    cantidadColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-MateriaPrima");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Materia Prima");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(true);
        grid.setAutoExpandColumn("expandible");
        grid.setLoadMask(true);
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setIconCls("grid-icon");
//        grid.addGridRowListener(new GridRowListenerAdapter() {
//
//            @Override
//            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
////                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");
//                Record[] records = cbSelectionModel.getSelections();
//
//
//                selecionado = records[0].getAsString("idmateriaprima");
//                String enlTemp = "funcion=reporteMateriaPrimaHTML&idmateriaprima=" + selecionado;
//                verReporte(enlTemp);
////                    Window.alert(enlTemp);
//            }
//        });

        nuevoMP = new ToolbarButton("Nuevo");
        nuevoMP.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear Materia Prima");
        nuevoMP.setTooltip(tipsConfig1);

        editarMP = new ToolbarButton("Editar");
        editarMP.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Materia Prima");
        editarMP.setTooltip(tipsConfig);

        eliminarMP = new ToolbarButton("Eliminar");
        eliminarMP.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Materia Prima");
        eliminarMP.setTooltip(tipsConfig2);


        detalleMP = new ToolbarButton("Detalle");
        detalleMP.setEnableToggle(true);
        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        tipsConfig3.setText("detalle de materia prima, Inactivo");
        detalleMP.setTooltip(tipsConfig3);


        imagenproducto = new ToolbarButton("Cargar Imagen   ");
        imagenproducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig10 = new QuickTipsConfig();
        tipsConfig10.setText("Cargar Imagen al producto");
        //tipsConfig.setTitle("Tip Title");
        imagenproducto.setTooltip(tipsConfig10);


        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoMP);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarMP);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarMP);
//        pagingToolbar.addSeparator();
//        pagingToolbar.addButton(reporteCliente);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(detalleMP);
        pagingToolbar.addSeparator();
//        pagingToolbar.addButton(inventarioMP);

        pagingToolbar.addButton(imagenproducto);
        pagingToolbar.addSeparator();
        String items[] = {"Color", "Nombre", "Categoria"};
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
        aniadirListenersCliente();
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

    private void CargarNuevoCliente() {
        String enlace = "php/MateriaPrima.php?funcion=CargarNuevaMateriaPrima";
        Utils.setErrorPrincipal("Cargando parametros de Nueva Materia Prima", "cargar");
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
                                    Object[][] categorias = Utils.getArrayOfJSONObject(marcaO, "categoriaM", new String[]{"idcategoria", "nombre"});
                                    Object[][] unidades = Utils.getArrayOfJSONObject(marcaO, "unidadM", new String[]{"idunidad", "nombre"});
                                    Object[][] colores = Utils.getArrayOfJSONObject(marcaO, "colorM", new String[]{"idcolor", "nombre"});

                                    formulario = null;
                                    formulario = new EditarNuevoMateriaPrimaForm(null, "", "", "", "", "", "", "", "", null, colores, unidades, categorias, MateriaPrima.this);
//                                    formulario = new EditarNuevoCliente(null,"","", "", "", "", "", "", "", "", "", ciudades, Cliente.this);
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

    private void cargarDatosEditarCliente(String idmateriaprima) {
        String enlace = "php/MateriaPrima.php?funcion=BuscarMateriaPrimaPorId&idmateriaprima=" + idmateriaprima;
        Utils.setErrorPrincipal("Cargando parametros de nueva materia prima", "cargar");
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
                                    Object[][] categorias = Utils.getArrayOfJSONObject(marcaO, "categoriaM", new String[]{"idcategoria", "nombre"});
                                    Object[][] unidades = Utils.getArrayOfJSONObject(marcaO, "unidadM", new String[]{"idunidad", "nombre"});
                                    Object[][] colores = Utils.getArrayOfJSONObject(marcaO, "colorM", new String[]{"idcolor", "nombre"});
                                    String idmateriaprima = Utils.getStringOfJSONObject(marcaO, "idmateriaprima");
                                    String codigo = Utils.getStringOfJSONObject(marcaO, "codigo");
                                    String categoria = Utils.getStringOfJSONObject(marcaO, "idcategoria");
                                    String unidad = Utils.getStringOfJSONObject(marcaO, "idunidad");
                                    String color = Utils.getStringOfJSONObject(marcaO, "idcolor");
                                    String nombre = Utils.getStringOfJSONObject(marcaO, "nombre");
                                    String stock = Utils.getStringOfJSONObject(marcaO, "stockminimo");
                                    String caracteristica = Utils.getStringOfJSONObject(marcaO, "caracteristica");
                                    String calidad = Utils.getStringOfJSONObject(marcaO, "calidad");
                                    String estado = Utils.getStringOfJSONObject(marcaO, "estado");
                                    formulario = null;
                                    formulario = new EditarNuevoMateriaPrimaForm(idmateriaprima, nombre, codigo, categoria, calidad, unidad, color, stock, caracteristica, estado, colores, unidades, categorias, MateriaPrima.this);
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

    private void aniadirListenersCliente() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarMP.addListener(new ButtonListenerAdapter() {

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
                eliminarMP.setPressed(false);
            }
        });
        //**************************************************
        //***********NUEVO CLIENTE
        //**
        nuevoMP.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                CargarNuevoCliente();
            }
        });

        //**************************************************
        //***********EDITAR CLIENTE
        //**************************************************
        editarMP.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idmateriaprima = records[0].getAsString("idmateriaprima");
                            cargarDatosEditarCliente(idmateriaprima);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione una materia Prima para editar", "error");
                        }

                    }
                });

        detalleMP.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idmateriaprima = records[0].getAsString("idmateriaprima");

                            String enlTemp = "funcion=detalleMatriaPrima&idmateriaprima=" + idmateriaprima;
                            verReporte(enlTemp);

                        } else {

                            Utils.setErrorPrincipal("seleccione una Materia Prima para cambiar estado", "error");
                        }

                    }
                });
        imagenproducto.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {

                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idmateriaprima");
                            String enlTemp = "php/uploadimagen.php?funcion=imagenmateriaprima&idmateriaprima=" + selecionado;
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

        addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    CargarNuevoCliente();
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
        busarcolor = buscadorToolBar.getItemsText1().getText();
        buscarnombre =
                buscadorToolBar.getItemsText2().getText();
        buscarcategoria =
                buscadorToolBar.getItemsText3().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarcolor", busarcolor),
                    new UrlParam("buscarnombre", buscarnombre),
                    new UrlParam("buscarcategoria", buscarcategoria)
                }, false);
    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
