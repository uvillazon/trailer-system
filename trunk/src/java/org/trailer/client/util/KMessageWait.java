/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.util;

import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.WaitConfig;

/**
 *
 * @author Edwin
 */
public class KMessageWait extends MessageBox {

    private String mensajeArriba = "Guardando sus datos";
    private String mensajeProgreso = "Guardando...";
    public KMessageWait() {
    }

    public KMessageWait(String arriba, String progreso) {
        this.mensajeArriba = arriba;
        this.mensajeProgreso = progreso;
    }

    public void kshow() {
        show(new MessageBoxConfig() {

            {
                setMsg(mensajeArriba.toUpperCase());
                setProgressText(mensajeProgreso);
                setButtons(CANCEL);
                setWidth(300);
                setWait(true);
                setWaitConfig(new WaitConfig() {

                    {
                        setInterval(200);
                    }
                });
            }
        });
    }

    public void khide() {
        hide();
    }

    public void setMensajeArriba(String mensajeArriba) {
        this.mensajeArriba = mensajeArriba;
    }

    public void setMensajeProgreso(String mensajeProgreso) {
        this.mensajeProgreso = mensajeProgreso;
    }


}
