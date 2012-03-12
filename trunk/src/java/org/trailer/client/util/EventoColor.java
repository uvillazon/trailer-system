///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package org.balderrama.client.util;
//
///**
// *
// * @author miguel
// */
//import com.google.gwt.http.client.Request;
//import com.google.gwt.http.client.RequestCallback;
//import com.google.gwt.http.client.RequestException;
//import com.google.gwt.http.client.Response;
//import com.google.gwt.json.client.JSONArray;
//import com.google.gwt.json.client.JSONObject;
//import com.google.gwt.json.client.JSONParser;
//import com.google.gwt.json.client.JSONValue;
//import com.google.gwt.user.client.ui.ClickListener;
//import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
//import com.gwtext.client.core.EventObject;
//import com.gwtext.client.core.Position;
//import com.gwtext.client.data.Record;
//import com.gwtext.client.data.SimpleStore;
//import com.gwtext.client.widgets.Button;
//import com.gwtext.client.widgets.Window;
//import com.gwtext.client.widgets.Panel;
//import com.gwtext.client.widgets.form.Checkbox;
//import com.gwtext.client.widgets.event.ButtonListenerAdapter;
//import com.gwtext.client.widgets.form.ComboBox;
//import com.gwtext.client.widgets.form.Field;
//import com.gwtext.client.widgets.form.FieldSet;
//import com.gwtext.client.widgets.form.FormPanel;
//import com.gwtext.client.widgets.form.Hidden;
//import com.gwtext.client.widgets.form.TextArea;
//import com.gwtext.client.widgets.form.TextField;
//import com.gwtext.client.widgets.layout.AnchorLayoutData;
//import com.gwtext.client.widgets.layout.FitLayout;
//import com.gwtext.client.widgets.layout.BorderLayout;
//import com.gwtext.client.widgets.layout.BorderLayoutData;
//import com.gwtext.client.widgets.layout.HorizontalLayout;
//import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
//import com.gwtext.client.widgets.layout.VerticalLayout;
//import com.gwtext.client.widgets.MessageBox;
//import com.gwtext.client.widgets.layout.TableLayout;
//import com.gwtext.client.widgets.layout.TableLayoutData;
//import com.gwtext.client.core.RegionPosition;
//import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
//import org.balderrama.client.marca.Marca;
//import org.balderrama.client.emergentes.FormularioSeleccionarModelo;
//import org.balderrama.client.emergentes.FormularioSeleccionarColor;
//import org.balderrama.client.emergentes.FormularioSeleccionarMaterial;
//import org.balderrama.client.util.Conector;
//import org.balderrama.client.util.Utils;
//import org.balderrama.client.beans.Modelo;
//import org.balderrama.client.beans.Color;
//import org.balderrama.client.beans.Material;
//import org.balderrama.client.configuracion.ListaColorSimple;
//
//public class EventoColor  extends TextFieldListenerAdapter {
//
//    private FormularioSeleccionarColor for_color;
//    private Color colorSeleccionado;
//    boolean respuesta = false;
//    TextField tex_color;
//
//    public EventoColor(){
//
//       // this.tex_color=tex_color;
//    }
//
//    public void onSpecialKey(Field field, EventObject e) {
//
//                if (e.getKey() == EventObject.ENTER) {
//                    // abrir la lsita de productos for_proveedor
//                    //MessageBox.alert(field.getValueAsString());
//                    String codigoProveedor = field.getValueAsString().trim();
//                    if (codigoProveedor.isEmpty() || findByCodigoProveedor(codigoProveedor)) {
//                        if (for_color == null || for_color.isHidden()) {
//                            //MessageBox.alert("evento ingreso");
//                            showListColor();
//                        } else {
//                            //MessageBox.alert("evento no ingreso");
//                            for_color.onFocus();
//
//                        }
//
//                    }
//                }
//            }
//
//     private void addListenerFormularioSeleccionarColor() {
//                for_color.getLayout().getBut_aceptar().addListener(new ButtonListenerAdapter() {
//
//                    @Override
//                    public void onClick(Button button, EventObject e) {
//                        openFormularioColor(for_color);
//                    }
//                });
//    }
//
//    private boolean findByCodigoProveedor(final String codigoBuscado) {
//                respuesta = false;
//                String enlace = "php/Colores.php?funcion=BuscarColorPorId&idcolor=" + codigoBuscado;
//                Utils.setErrorPrincipal("Cargando parametros del color", "cargar");
//                final Conector conec = new Conector(enlace, false);
//
//                try {
//                    conec.getRequestBuilder().sendRequest(null, new RequestCallback() {
//
//                        private String idcolor;
//                        private String nombre;
//                        private String descripcion;
//                        private String codigo;
//
//
//
//                        public void onResponseReceived(Request request, Response response) {
//                            String data = response.getText();
//                            JSONValue jsonValue = JSONParser.parse(data);
//                            JSONObject jsonObject;
//
//                            if ((jsonObject = jsonValue.isObject()) != null) {
//                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
//                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
//                                if (errorR.equalsIgnoreCase("true")) {
//                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
//                                    JSONValue proveedorValue = jsonObject.get("resultado");
//                                    JSONObject proveedorObject;
//
//                                    if ((proveedorObject = proveedorValue.isObject()) != null) {
//
//                                        idcolor = Utils.getStringOfJSONObject(proveedorObject, "idcolor");
//                                        nombre = Utils.getStringOfJSONObject(proveedorObject, "nombre");
//                                        descripcion = Utils.getStringOfJSONObject(proveedorObject, "descripcion");
//                                        codigo = Utils.getStringOfJSONObject(proveedorObject, "codigo");
////                                        fax = Utils.getStringOfJSONObject(proveedorObject, "fax");
////                                        paginaWeb = Utils.getStringOfJSONObject(proveedorObject, "paginaweb");
//
//                                        colorSeleccionado =new Color(idcolor,nombre,descripcion,codigo);
//
//                                        tex_color.setValue(colorSeleccionado.getIdcolor());
//                                        //tex_marca.setValue(modeloSeleccionado.getIdmarca());
//                                        //tex_producto.focus();
//                                        respuesta =true;
//                                    } else {
//                                        resetCamposColor();
//
//                                        Utils.setErrorPrincipal("No se recuperaron correctamente lo valores de modelo", "error");
//                                    }
//
//                                } else {
//                                    resetCamposColor();
//
//                                    Utils.setErrorPrincipal(mensajeR, "error");
//                                }
//
//                            }
//                        }
//
//                        public void onError(Request request, Throwable exception) {
//                            resetCamposColor();
//
//                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
//
//                        }
//                    });
//
//                } catch (RequestException ex) {
//                    ex.getMessage();
//                    resetCamposColor();
//
//                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
//                }
//
//                return respuesta;
//            }
//
//            private void showListColor() {
//                for_color = new FormularioSeleccionarColor();
//                for_color.showFormulario();
//                addListenerFormularioSeleccionarColor();
//
//            }
//public void resetCamposColor() {
//
//        tex_color.reset();
//        tex_color.focus();
//
//    }
//public void openFormularioColor(FormularioSeleccionarColor for_color) {
//
//        colorSeleccionado = for_color.getProveedorSeleccionado();
//        if (colorSeleccionado == null) {
//            MessageBox.alert("Por favor solo seleccione un color.");
//        } else {
//            for_color.closeFormulario();
//            tex_color.setValue(colorSeleccionado.getIdcolor());
//            //tex_producto.focus();
//        }
//
//    }
//}
