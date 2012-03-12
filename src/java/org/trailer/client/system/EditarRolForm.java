/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.system;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.FileUpload;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.form.Checkbox;
//import com.google.gwt.user.client.ui.CheckBox;

import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;


import java.util.ArrayList;
import java.util.List;
import org.trailer.client.util.Utils;

/**
 *
 * @author Administrador
 */
public class EditarRolForm extends Window {

    private final int ANCHO = 980;
    private final int ALTO = 480;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private Window window;
    private Panel formPanel;
    private Button but_aceptar;
    private Button but_cancelar;
    private TextField tex_nombre;
    private ComboBox com_estado;
    private String idRol;
    private String nombre;
    private String estado;

    Object funcionesCatgorias[];
    int repeat = 0;

    public EditarRolForm(String idRol, JSONObject conec) {
        this.idRol = idRol;
        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nuevo rol";

        if (idRol != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar rol";
            String nombreA = Utils.getStringOfJSONObject(conec, "nombre");
            String estadoA = Utils.getStringOfJSONObject(conec, "estado");
            //com.google.gwt.user.client.Window.alert(nombreA+"........................."+estadoA);
            this.nombre = nombreA;
            this.estado = estadoA;
        } else {
            this.estado = "Activo";
        }


        window = new Window();
        but_aceptar = new Button(nombreBoton1);
        but_cancelar = new Button(nombreBoton2);

        window.setTitle(tituloTabla);
        window.setAutoHeight(true);
        window.setAutoWidth(true);
        window.setLayout(new FitLayout());
        window.setPaddings(WINDOW_PADDING);
        window.setButtonAlign(Position.CENTER);
        window.addButton(but_aceptar);
        window.addButton(but_cancelar);

        window.setCloseAction(Window.CLOSE);
        window.setPlain(true);

        formPanel = new Panel();
        //strips all Ext styling for the component
        formPanel.setBaseCls("x-plain");
//        formPanel.setLabelWidth(ANCHO - 800);
//        formPanel.setUrl("save-form.php");
//        formPanel.setWidth(ANCHO);
//        formPanel.setHeight(ALTO);
        formPanel.setAutoHeight(true);
        formPanel.setAutoWidth(true);

        tex_nombre = new TextField("Nombre", "nombre");
        com_estado = new ComboBox("Estado", "estado");

        String[] estados = new String[]{"Activo", "Inactivo"};
        SimpleStore estadosStore = new SimpleStore("estados", estados);
        estadosStore.load();
        com_estado.setDisplayField("estados");
        com_estado.setStore(estadosStore);

        FormPanel for_identificacion = new FormPanel();
        //for_identificacion.setLayout(new HorizontalLayout(10));

        for_identificacion.setBaseCls("x-plain");
        for_identificacion.setLabelWidth(ANCHO - 800);
        for_identificacion.setWidth(ANCHO - 30);
        for_identificacion.setHeight(ALTO - 430);
        // Label lab_nombre = new Label("Nombre:");
        //  Label lab_estado = new Label("Estado:");

        //  lab_nombre.setStyle("font:normal 11px arial,tahoma,helvetica,sans-serif");
        //  lab_estado.setStyle("font:normal 11px arial,tahoma,helvetica,sans-serif");
        //  for_identificacion.add(lab_nombre);
        for_identificacion.add(tex_nombre);
        // for_identificacion.add(lab_estado);
        initValidators();
        for_identificacion.add(com_estado);

        formPanel.add(for_identificacion);

        initcrearFielsets(conec, idRol);
        window.add(formPanel);
        window.setPosition(5, 150);
        intValues();

    }

    public Window getWindow() {
        return window;

    }

    private void initValidators() {
        com_estado.setEditable(false);
        com_estado.setAllowBlank(false);
        tex_nombre.setMaxLength(50);
        tex_nombre.setAllowBlank(false);
    }

    private void initcrearFielsets(JSONObject conec, String idrol) {



        Panel root = new Panel();
        root.setBaseCls("x-plain");
        root.setId("root");
        root.setAutoScroll(true);

        root.setWidth(ANCHO - 30);
        root.setHeight(ALTO - 50);
        FieldSet categoria = null;

        int auxFilas = 0;
        float auxCantFilasF = 0;
        int auxCantFilas = 0;
        Checkbox manager1;
        JSONArray jarray;
        JSONValue dat = conec.get("resultado");
        if ((jarray = dat.isArray()) != null) {
            JSONValue catsV;
            JSONObject catsO;
            for (int i = 0; i < jarray.size(); i++) {
                catsV = jarray.get(i);
                if ((catsO = catsV.isObject()) != null) {
                    String cadnombreV = Utils.getStringOfJSONObject(catsO, "nombre");
                    String cadidV = Utils.getStringOfJSONObject(catsO, "idcategoriafuncion");

                    categoria = new FieldSet(cadnombreV);
                    categoria.setLayout(new HorizontalLayout(5));
                    categoria.setId(cadidV);
                    JSONArray funciones;
                    cadnombreV = cadnombreV + "M";
                    JSONValue funV = catsO.get(cadnombreV);
                    if ((funciones = funV.isArray()) != null) {

                        //funcS = conec.getObjects(cadS, null);
                        ////auxCantFilas = (int) Math.floor(funcS.length/4);
                        auxCantFilasF = funciones.size();
                        auxCantFilasF = auxCantFilasF / 5;
                        auxCantFilas = redondedo(auxCantFilasF);
                        Panel pan_checks = null;
                        auxFilas = 0;
                        for (int ii = 0; ii < funciones.size(); ii++) {
                            JSONValue funVV = funciones.get(ii);
                            JSONObject funOO;
                            if ((funOO = funVV.isObject()) != null) {
                                if (auxFilas == 0) {
                                    String idpanel = "Panel" + ii;
                                    pan_checks = new Panel();
                                    pan_checks.setId(idpanel);
                                    pan_checks.setLayout(new VerticalLayout(5));
                                    pan_checks.setBaseCls("x-plain");
                                    categoria.add(pan_checks);
                                }
                                auxFilas++;
                                String nom = Utils.getStringOfJSONObject(funOO, "descripcion");
                                String idchek = Utils.getStringOfJSONObject(funOO, "idfuncion");
                                manager1 = new Checkbox(nom);
                                manager1.setStyleName("x-plain");
                                manager1.setName(nom);
                                manager1.setId("F" + idchek);
                                if (idrol != null) {
                                    String existe = Utils.getStringOfJSONObject(funOO, "existe");
                                    if (existe.equalsIgnoreCase("1")) {
                                        manager1.setChecked(true);
                                    }
                                }
                                pan_checks.add(manager1);
                                manager1 = null;
                                if (auxFilas >= auxCantFilas) {
                                    auxFilas = 0;
                                }
                            }
                        //pan_checks = null;
                        }
                    }
                }
                root.add(categoria);
                categoria = null;
            }
        } else {
            Utils.setErrorPrincipal("Los datos no son validos", "error");
        }

//        //Utils.getArrayOfJSONObject(jsonObject, "resultado");
//        for (int i = 0; i < cats.length; i++) {
//
////com.google.gwt.user.client.Window.alert(".........." + categoria.);
//        }
        formPanel.add(root);
    }

    private void intValues() {
        tex_nombre.setValue(nombre);
        com_estado.setValue(estado);

    }

    public Button getBut_aceptar() {
        return but_aceptar;
    }

    public Button getBut_cancelar() {
        return but_cancelar;
    }

    public String getIdRol() {
        return idRol;
    }

    public Panel getFormPanel() {
        return formPanel;
    }

    public TextField getTex_nombre() {
        return tex_nombre;
    }

    public ComboBox getCom_estado() {
        return com_estado;
    }

    public int redondedo(float f) {
        int aux = (int) f;
        if (aux < f) {
            aux = aux + 1;
        }
        return aux;
    }

    public Object[] getSeleccionados() {
        List<Object> funcionesSelecionadas = new ArrayList<Object>();
        Component[] todosFunciones = formPanel.findByType("checkbox");
        String a = "";
        for (int i = 0; i < todosFunciones.length; i++) {
            Checkbox ver = null;
            try {
                ver = (Checkbox) todosFunciones[i];
            } catch (Exception e) {
                ver = null;
            }

            if (ver != null) {
                if (ver.getValue() == true) {
                    funcionesSelecionadas.add(ver);
                }
            }
        }
        return funcionesSelecionadas.toArray();
    }
}