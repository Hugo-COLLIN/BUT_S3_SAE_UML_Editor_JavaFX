package com.vue;

import com.modele.Modele;
import com.vue.Fabrique.VueFabriqueClasses;
import com.vue.Observateur.VueArborescence;
import com.vue.Observateur.VueDiagramme;
import com.vue.Observateur.VueMenu;
import javafx.scene.layout.BorderPane;

public class VueGlobal extends BorderPane {
    private VueMenu vueMenu;
    private VueDiagramme vueDiagramme;
    private Modele modele;


    public VueGlobal(Modele modele) {
        this.modele = modele;
        initBorderPane();
    }

    public void initBorderPane() {
        vueMenu = new VueMenu(modele, this);
        vueDiagramme = new VueDiagramme();
        modele.enregistrerObservateur(vueDiagramme);
        modele.enregistrerObservateur(vueMenu);
        vueMenu.actualiser(modele);
        vueDiagramme.actualiser(modele);
        VueArborescence vueSelectionRepertoire = new VueArborescence();
        modele.enregistrerObservateur(vueSelectionRepertoire);
        this.setLeft(vueSelectionRepertoire);
        this.setTop(vueMenu);
        this.setCenter(vueDiagramme);
    }

    public VueFabriqueClasses getVueFabriqueClasse()
    {
        return this.vueDiagramme.getVueFabriqueClasses();
    }
}
