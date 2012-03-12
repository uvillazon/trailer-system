/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.system;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.ScriptTagProxy;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author Ubaldo  Villazon */
public class Usuario extends Panel {

    private GridPanel gridUsuarios;
    private ColumnConfig loginColumn;
    private ColumnConfig ciColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig primerApColumn;
    private ColumnConfig rolColumn;
    private ColumnConfig sucursalColumn;
    private ColumnConfig almacenColumn;
    private ColumnConfig telefonoColumn;
    private ColumnConfig estadoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarUsuario;
    private ToolbarButton eliminarUsuario;
    private ToolbarButton nuevoUsuario;
    private ToolbarButton configuracionUsuario;
    protected EditarUsuarioForm formulario;
//    protected ConfUsuarioWindow formularioConf;
    protected ExtElement ext_elementUsuario;
    private CheckboxSelectionModel cbSelectionModelUsuario;
    private Store storeUsuarios;
    private ColumnConfig idColumnUsuario;
    private BaseColumnConfig[] columnsUsuario;
    private ColumnModel columnModelUsuario;
    private DataProxy dataProxyUsuarios;
    private JsonReader readerUsuarios;
    private PagingToolbar pagingToolbar;
    private String selecionado = "";
    private TextField tex_ciPBU;
    private TextField tex_nombrePBU;
    private TextField tex_apellidosPBU;
    private TextField tex_loginPBU;
    private Toolbar too_busquedaPBW;
    private ToolbarButton too_buscarPBW;

    public Usuario() {
        this.setClosable(true);
        this.setId("TPfun1005");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Usuarios");
        onModuleLoad();
    }

    public void onModuleLoad() {
        dataProxyUsuarios = new ScriptTagProxy("php/Usuario.php?funcion=findAllUsuario");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idusuario"),
                    new StringFieldDef("login"),
                    new StringFieldDef("carnetidentidad"),
                    new StringFieldDef("nombres"),
                    new StringFieldDef("apellido1"),
                    new StringFieldDef("rol"),
               
                    new StringFieldDef("telefono"),
                    new StringFieldDef("estado")
                });
        readerUsuarios = new JsonReader(recordDef);
        readerUsuarios.setRoot("resultado");
        readerUsuarios.setTotalProperty("totalCount");

        storeUsuarios = new Store(dataProxyUsuarios, readerUsuarios, true);
        idColumnUsuario = new ColumnConfig("Id usuario", "idusuario", 100, true);
        
        loginColumn = new ColumnConfig("Login", "login", 100, true, null, "login");
        ciColumn = new ColumnConfig("Ci", "carnetidentidad", 100);
        nombreColumn = new ColumnConfig("Nombre", "nombres", 110, true);
        nombreColumn.setId("expandible");
        primerApColumn = new ColumnConfig("Apellido", "apellido1", 120, true);
        rolColumn = new ColumnConfig("Rol", "rol", 100, true);
    
       
        telefonoColumn = new ColumnConfig("Telefono", "telefono", 100, true);
        estadoColumn = new ColumnConfig("Estado", "estado", 100, true);

        cbSelectionModelUsuario = new CheckboxSelectionModel();
        columnsUsuario = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModelUsuario),
                    loginColumn,
                    ciColumn,
                    nombreColumn,
                    primerApColumn,
                    rolColumn,
                 
                  
                    telefonoColumn,
                    estadoColumn
                };

        columnModelUsuario = new ColumnModel(columnsUsuario);

        gridUsuarios = new EditorGridPanel();
        gridUsuarios.setId("grid-lista-usuarios");

        gridUsuarios.setTitle("Lista de usuarios");
        gridUsuarios.setStore(storeUsuarios);
        gridUsuarios.setColumnModel(columnModelUsuario);
        gridUsuarios.setAutoExpandColumn("expandible");
        gridUsuarios.setTrackMouseOver(true);
        gridUsuarios.setLoadMask(true);
        gridUsuarios.setSelectionModel(cbSelectionModelUsuario);
        gridUsuarios.setFrame(true);
        gridUsuarios.setStripeRows(true);
        gridUsuarios.setIconCls("grid-icon");

        nuevoUsuario = new ToolbarButton("Nuevo");
        nuevoUsuario.setEnableToggle(true);
        nuevoUsuario.setTitle("Crear nuevo usuario");

        eliminarUsuario = new ToolbarButton("Eliminar");
        eliminarUsuario.setEnableToggle(true);
        eliminarUsuario.setTitle("Eliminar usuario(s)");

        editarUsuario = new ToolbarButton("Editar");
        editarUsuario.setEnableToggle(true);
        editarUsuario.setTitle("Editar usuario");

        configuracionUsuario = new ToolbarButton("Configuracion");
        configuracionUsuario.setEnableToggle(true);
        configuracionUsuario.setTitle("Configuracion de usuarios");

        pagingToolbar = new PagingToolbar(storeUsuarios);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No hay usuarios");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoUsuario);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarUsuario);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarUsuario);
//        pagingToolbar.addSeparator();
//        pagingToolbar.addButton(configuracionUsuario);

        too_busquedaPBW = new Toolbar();

        tex_ciPBU = new TextField("CI", "ci");
        tex_nombrePBU = new TextField("Nombre", "nombre");
        tex_apellidosPBU = new TextField("Apellidos", "apellidos");
        tex_loginPBU = new TextField("Login", "login");
        too_buscarPBW = new ToolbarButton("Buscar");
        too_buscarPBW.setPressed(true);

        too_busquedaPBW.addText("CI:");
        too_busquedaPBW.addField(tex_ciPBU);
        too_busquedaPBW.addText("Nombre:");
        too_busquedaPBW.addField(tex_nombrePBU);
        too_busquedaPBW.addText("Apellidos:");
        too_busquedaPBW.addField(tex_apellidosPBU);
        too_busquedaPBW.addText("Login:");
        too_busquedaPBW.addField(tex_loginPBU);
        too_busquedaPBW.addButton(too_buscarPBW);

        gridUsuarios.setTopToolbar(too_busquedaPBW);
        gridUsuarios.setBottomToolbar(pagingToolbar);

        add(gridUsuarios);
        aniadirListenersBuscador();
        aniadirListenersBuscadoresText();
        aniadirListenersUsuario();
        gridUsuarios.setWidth(ANCHO);
        gridUsuarios.setHeight(ALTO);
    }

    private void aniadirListenersUsuario() {
        //**************************************************
        //***********ELIMINAR USUARIO
        //**************************************************

        eliminarUsuario.addListener(new ButtonListenerAdapter() {

            private boolean procederAEliminar;
            int repeat = 0;

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModelUsuario.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idusuario");
                    MessageBox.confirm("Eliminar Usuario", "Realmente desea eliminar este usuario??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Usuario.php?funcion=EliminarUsuario&idusuario=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el usuario", "cargar");
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
                                                    storeUsuarios.reload();
                                                    gridUsuarios.reconfigure(storeUsuarios, columnModelUsuario);
                                                    gridUsuarios.getView().refresh();
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
                    MessageBox.alert("No hay usuario selecionado para eliminar o selecciono mas de uno.");
                }

                eliminarUsuario.setPressed(false);
            }
        });

        //**************************************************
        //***********EDITAR USUARIO
        //**************************************************
        editarUsuario.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        //Window.alert(grid.getWidth() + "---------------" + grid.getHeight());
                        ext_elementUsuario = Ext.get("grid-lista-usuarios");
                        //ext_element.mask();
                        Record[] records = cbSelectionModelUsuario.getSelections();
                        String selecionado = "";
                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idusuario");
                            if (formulario != null) {
                                formulario.clear();
                                formulario.destroy();
                                formulario = null;
                            }
                            formulario = new EditarUsuarioForm(selecionado, Usuario.this);
                            formulario.show();
                        } else {
                            Utils.setErrorPrincipal("Seleccione solo un usuario por favor", "error");
                        }

                        editarUsuario.setPressed(false);
                    }
                });

        //**************************************************
        //***********NUEVO USUARIO
        //**************************************************
        nuevoUsuario.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (formulario != null) {
                    formulario.clear();
                    formulario.destroy();
                    formulario = null;
                }
                formulario = new EditarUsuarioForm(null, Usuario.this);
                formulario.show();
                nuevoUsuario.setPressed(false);
            }
        });


        //**************************************************
        //*********** LISTENERS DE LA TABLA
        //**************************************************
        gridUsuarios.addListener(new PanelListenerAdapter() {

            @Override
            public void onRender(Component component) {
                storeUsuarios.load(0, 100);
            }
        });
        gridUsuarios.addGridCellListener(new GridCellListenerAdapter() {

            @Override
            public void onCellClick(GridPanel grid, int rowIndex, int colIndex, EventObject e) {
                if (grid.getColumnModel().getDataIndex(colIndex).equals("indoor")) {
                    Record record = grid.getStore().getAt(rowIndex);
                    record.set("indoor", !record.getAsBoolean("indoor"));
                }

            }
        });
//        configuracionUsuario.addListener(new ButtonListenerAdapter() {
//
//            @Override
//            public void onClick(Button button, EventObject e) {
//                Record[] records = cbSelectionModelUsuario.getSelections();
//                if (records.length == 1) {
//                    selecionado = records[0].getAsString("idusuario");
//                    String enlace = "php/dao/UsuarioListar.php?function=getConfUsuario&idusuario=" + selecionado;
//                    Utils.setErrorPrincipal("Cargando ultima configuracion de usuario", "cargar");
//                    final Conector conec = new Conector(enlace, false);
//                    try {
//                        conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {
//
//                            public void onResponseReceived(Request request, Response response) {
//                                String data = response.getText();
//                                JSONValue jsonValue = JSONParser.parse(data);
//                                JSONObject jsonObject;
//                                if ((jsonObject = jsonValue.isObject()) != null) {
//                                    String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
//                                    String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
//                                    Record[] recordss = cbSelectionModelUsuario.getSelections();
//                                    String idUsuariod = recordss[0].getAsString("idusuario");
//                                    String loginUsuariod = recordss[0].getAsString("login");
//                                    String almacenesd = "No";
//                                    String descuentod = "5";
//                                    String digitosd = "2";
//                                    String registrod = "No";
//                                    String preciosd = "No";
//                                    String multivendedor = "No";
//                                    String precioSistema = "No";
//                                    String idCuenta = "";
//                                    if (errorR.equalsIgnoreCase("true")) {
//                                        Utils.setErrorPrincipal(mensajeR, "mensaje");
//                                        JSONObject perConfO;
//                                        JSONValue perConfV;
//                                        perConfV = jsonObject.get("resultado");
//                                        if ((perConfO = perConfV.isObject()) != null) {
//                                            almacenesd = Utils.getStringOfJSONObject(perConfO, "almacenes");
//                                            descuentod = Utils.getStringOfJSONObject(perConfO, "descuento");
//                                            digitosd = Utils.getStringOfJSONObject(perConfO, "digitos");
//                                            registrod = Utils.getStringOfJSONObject(perConfO, "registro");
//                                            preciosd = Utils.getStringOfJSONObject(perConfO, "precios");
//                                            multivendedor = Utils.getStringOfJSONObject(perConfO, "multivendedor");
//                                            precioSistema = Utils.getStringOfJSONObject(perConfO, "precioSistema");
//                                            idCuenta = Utils.getStringOfJSONObject(perConfO, "idcuenta");
//                                        }
//                                    } else {
//                                        //Window.alert(mensajeR);
//                                        Utils.setErrorPrincipal(mensajeR, "error");
//                                    }
////                                    Window.alert(cajad+"--"+nombreCaja);
//                                    if (formularioConf != null) {
//                                        formularioConf.clear();
//                                        formularioConf.destroy();
//                                        formularioConf = null;
//                                    }
//                                    formularioConf = new ConfUsuarioWindow(idUsuariod, loginUsuariod, almacenesd, descuentod, digitosd, registrod, preciosd, multivendedor, precioSistema, idCuenta);
//                                    formularioConf.show();
//                                }
//                            }
//
//                            public void onError(Request request, Throwable exception) {
//                                //Window.alert("Ocurrio un error al conectar con el servidor ");
//                                Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
//                            }
//                        });
//                    } catch (RequestException ex) {
//                        //Window.alert("Ocurrio un error al conectar con el servidor");
//                        Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
//                    }
//                } else {
//                    MessageBox.alert("Ud no selecciono un usuario o selecciono varios... ");
//                }
//            }
//        });

    }

    public void aniadirListenersBuscador() {

        too_buscarPBW.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                buscarSegunParametros();
            }
        });
    }

    public void aniadirListenersBuscadoresText() {
        //**************************************************
        //*********** BUSCADOR CI
        //**************************************************
        tex_apellidosPBU.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //**************************************************
        //*********** BUSCADOR NOMBRE
        //**************************************************
        tex_ciPBU.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 2");
                }
            }
        });

        //**************************************************
        //*********** BUSCADOR APELLIDOS
        //**************************************************
        tex_loginPBU.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //**************************************************
        //*********** BUSCADOR LOGIN
        //**************************************************
        tex_nombrePBU.addListener(new TextFieldListenerAdapter() {

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
        storeUsuarios.reload(new UrlParam[]{new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarci", tex_ciPBU.getText()), new UrlParam("buscarnombres", tex_nombrePBU.getText()),
                    new UrlParam("buscarapellido", tex_apellidosPBU.getText()), new UrlParam("buscarlogin", tex_loginPBU.getText())}, false);
    }
}
