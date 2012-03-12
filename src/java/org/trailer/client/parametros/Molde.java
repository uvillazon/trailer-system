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
public class Molde extends Panel {

    private KMenu kmenu;
    private MainEntryPoint pan;
    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig numeroColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig categoriaColumn;
    private ColumnConfig imagenColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarMolde;
    private ToolbarButton eliminarMolde;
    private ToolbarButton nuevoMolde;
//    private ToolbarButton componentes;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarcodigo;
    protected String buscarnombre;
    protected String buscarcategoria;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
//    private ToolbarButton reporteCliente;
    private EditarNuevoMolde formulario;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;
    private Componentes formC;
    private ToolbarButton imagenproducto;

    public Molde(KMenu kmenu, MainEntryPoint panel) {
        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun2011");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Moldes");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Molde.php?funcion=ListarMoldes");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idmolde"),
                    new StringFieldDef("idcategoria"),
                    new StringFieldDef("observacion"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("imagen"),
                    new StringFieldDef("medidas"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("categoria"),
                    new StringFieldDef("sastre"),
                    new StringFieldDef("hilo")

                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id", "idmolde", (ANCHO / 8), true);
        idColumn.setWidth(100);
        numeroColumn = new ColumnConfig("# Numero", "codigo", (ANCHO / 7));

        numeroColumn.setId("expandible");

        nombreColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 7), true);
        categoriaColumn = new ColumnConfig("Categoria", "categoria", (ANCHO / 7), true);

        imagenColumn = new ColumnConfig("Imagen", "imagen", (ANCHO / 7), true, new Renderer() {

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
                    numeroColumn,
                    nombreColumn,
                    categoriaColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-moldes");
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


                selecionado = records[0].getAsString("idmolde");
                String enlTemp = "funcion=reporteMoldeHTML&idmolde=" + selecionado;
                verReporte(enlTemp);
//                    Window.alert(enlTemp);
            }
        });

        nuevoMolde = new ToolbarButton("Nuevo");
        nuevoMolde.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear Nuevo Molde");
        nuevoMolde.setTooltip(tipsConfig1);

        editarMolde = new ToolbarButton("Editar");
        editarMolde.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Molde");
        editarMolde.setTooltip(tipsConfig);

        eliminarMolde = new ToolbarButton("Eliminar");
        eliminarMolde.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Molde");
        eliminarMolde.setTooltip(tipsConfig2);



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
        pagingToolbar.addButton(nuevoMolde);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarMolde);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarMolde);

        pagingToolbar.addSeparator();
        pagingToolbar.addButton(imagenproducto);


        String items[] = {"# numero", "Nombre", "Categoria"};
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
        String enlace = "php/Molde.php?funcion=CargarNuevoMolde";
        Utils.setErrorPrincipal("Cargando parametros....", "cargar");
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

                                    formulario = null;
                                    Float sastre = new Float(0);
                                    Float hilo = new Float(0);
                                    formulario = new EditarNuevoMolde(null, "", "", "", "", "", "", categorias, Molde.this,sastre,hilo);
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
        String enlace = "php/Molde.php?funcion=CargarNuevoMolde";
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
                                    store.filter("idmolde", idproducto);
                                    Record r = store.getRecords()[0];

                                    String nombre = r.getAsString("nombre");
                                    String imagen = r.getAsString("imagen");
                                    String codigo = r.getAsString("codigo");
                                    String categoria = r.getAsString("categoria");
                                    String idcategoria = r.getAsString("idcategoria");
                                    String observacion = r.getAsString("observacion");
                                    Float sastre = r.getAsFloat("sastre");
                                    Float hilo = r.getAsFloat("hilo");

//                                    Window.alert("datos "+" "+idproducto+" "+ imagen+" "+ canal+" "+ diseño+" "+ puntada+" "+ anchos+" "+ altos+" "+ observacion+" "+ idempresa+" "+empresa);
                                    store.clearFilter();
                                    formulario = null;
                                    formulario = new EditarNuevoMolde(idproducto, imagen, codigo, nombre, observacion, idcategoria, categoria, categorias, Molde.this,sastre,hilo);
                                    formulario.show();
//                               com.google.gwt.user.client.Window.alert(idgrupo);
//                               com.google.gwt.user.client.Window.alert(idcategoria);
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

        eliminarMolde.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idmolde");
                    MessageBox.confirm("Eliminar Molde", "Realmente desea eliminar este Molde??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Molde.php?funcion=EliminarMolde&idmolde=" + selecionado;
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
                eliminarMolde.setPressed(false);
            }
        });
        //**************************************************
        //***********NUEVO CLIENTE
        //**
        nuevoMolde.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                CargarNuevoProducto();
            }
        });

        //**************************************************
        //***********EDITAR CLIENTE
        //**************************************************
        editarMolde.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idproductos = records[0].getAsString("idmolde");
                            cargarDatosEditarProductos(idproductos);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un Producto para editar", "error");
                        }

                    }
                });



        imagenproducto.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {

                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idmolde");
                            String enlTemp = "php/uploadimagen.php?funcion=imagenmolde&idmolde=" + selecionado;
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
