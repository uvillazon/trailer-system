/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.trailer.client.util;

/**
 *
 * @author miguel
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gwt.user.client.ui.Grid;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 *
 * @author example
 */
public class LayoutFormPanel {

    Panel pan_layout;
    Button but_aceptar;
    Button but_cancelar;
    FormPanel form_formualario;
    Window win_ventana;
    String id_window;
    String id_panelForm;
    private int ANCHO = 500;
    private int ALTO = 400;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    String titulo;
    /** Solo acepta cadenas : Editar,Ver, Nuevo,Eliminar,Agregar */
    String tipo;
    boolean mostrarBotones = true;

    /**
     * titulo: Tutilo a ser mostrado en la ventana
     * tipo: Solo acepta cadenas ; Editar,Ver, Nuevo
     */
    public LayoutFormPanel(String titulo, String tipo) {
        this.titulo = titulo;
        this.tipo = tipo;
        intConponent();
    }

    /**
     * ancho por defecto es 500 y alto es 400
     */
    public LayoutFormPanel(String titulo, String tipo, int ancho, int alto) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.ALTO = alto;
        this.ANCHO = ancho;
        intConponent();
    }

    public LayoutFormPanel(String titulo, String tipo, int ancho, int alto, boolean mostrarBotones) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.ALTO = alto;
        this.ANCHO = ancho;
        this.mostrarBotones = mostrarBotones;
        intConponent();

    }

    private void intConponent() {

        win_ventana = new Window();

        String nombreBoton1 = "Guardar";

        String nombreBoton2 = "Cancelar";

        if (tipo.equalsIgnoreCase("Editar")) {
            nombreBoton1 = "Actualizar";
        }
        if (tipo.equalsIgnoreCase("Ver")) {
            nombreBoton2 = "Aceptar";
        }
        if (tipo.equalsIgnoreCase("Agregar")) {
            nombreBoton1 = "Agregar";
        }
        if (tipo.equalsIgnoreCase("Eliminar")) {
            nombreBoton1 = "Eliminar";
        }


        but_aceptar = new Button(nombreBoton1);
        but_cancelar = new Button(nombreBoton2);

        win_ventana.setTitle(titulo);
        win_ventana.setWidth(ANCHO);
        //   win_ventana.setHeight(ALTO);
        win_ventana.setMinWidth(ANCHO);
        //  win_ventana.setMinHeight(ALTO);
        win_ventana.setLayout(new FitLayout());
        win_ventana.setPaddings(WINDOW_PADDING);
        win_ventana.setButtonAlign(Position.CENTER);
        addButtons();
        addListeners();

        win_ventana.setCloseAction(Window.CLOSE);
        win_ventana.setPlain(true);

        form_formualario = new FormPanel();
        //strips all Ext styling for the component
        form_formualario.setBaseCls("x-plain");
        form_formualario.setLabelWidth(ANCHO - 400);
        form_formualario.setUrl("save-form.php");
        form_formualario.setWidth(ANCHO);
        form_formualario.setHeight(ALTO);

        win_ventana.add(form_formualario);
    }

    private void addButtons() {

        if (mostrarBotones) {
            if (!tipo.equalsIgnoreCase("Ver")) {
                win_ventana.addButton(but_aceptar);
            }
            win_ventana.addButton(but_cancelar);
        }
    }

    public int getALTO() {
        return ALTO;
    }

    public int getANCHO() {
        return ANCHO;
    }

    public AnchorLayoutData getANCHO_LAYOUT_DATA() {
        return ANCHO_LAYOUT_DATA;
    }

    public int getWINDOW_PADDING() {
        return WINDOW_PADDING;
    }

    public Button getBut_aceptar() {
        return but_aceptar;
    }

    public Button getBut_cancelar() {
        return but_cancelar;
    }

    public FormPanel getForm_formualario() {
        return form_formualario;
    }

    public Panel getPan_layout() {
        return pan_layout;
    }

    public String getTitulo() {
        return titulo;
    }

    public Window getWin_ventana() {
        return win_ventana;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private void addListeners() {

        but_cancelar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                win_ventana.destroy();
                win_ventana.close();
            //win_ventana = null;

            }
        });
    }

    public void setId_panelForm(String id_panelForm) {
        this.form_formualario.setId(id_panelForm);
    }

    public void setId_window(String id_window) {
        this.win_ventana.setId(id_window);
    }

    public boolean isHidden() {
        return this.win_ventana.isHidden() || !this.win_ventana.isCreated() || !this.win_ventana.isVisible();
    }
}

