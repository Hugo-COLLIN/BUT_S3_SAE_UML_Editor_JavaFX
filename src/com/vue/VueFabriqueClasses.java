package com.vue;

import com.modele.Sujet;
import com.modele.elements.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;

import javafx.scene.layout.AnchorPane;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VueFabriqueClasses extends AnchorPane {
    private ArrayList<VueClasse> classesList;

    public void generer(Sujet sujet, List<ClasseInterface> classes) {
        classesList = new ArrayList<>();
        this.getChildren().clear();
        this.setPrefSize(1000, 1000);
        Group classeVue = new Group();
        for (ClasseInterface classe : classes) {
            if (classe == null) continue;
            FabriqueDeVue fabriqueDeVue = new FabriqueVueClasse();
            VueClasse vueElement = (VueClasse) fabriqueDeVue.creerVueElement();

            vueElement.setTitle(classe.getNom());
            vueElement.ajouterSeparateur();
            if (sujet.getClasseCourante() != null) {
                vueElement.imageAdd("AjouterAttribut");
            }

            Random random = new Random();
            ReadOnlyDoubleProperty x = this.prefWidthProperty();
            ReadOnlyDoubleProperty y = this.prefHeightProperty();
            double randX = random.nextDouble(x.doubleValue());
            double randY = random.nextDouble(y.doubleValue());
            int i = 0;
            while (i < classeVue.getChildren().size()) {
                System.out.println("huh");
                Node vueClasse = classeVue.getChildren().get(i);
                if (vueClasse instanceof VueClasse) {
                    if (randX >= vueClasse.getLayoutX() && randX <= vueClasse.getBoundsInParent().getWidth()
                    && randY >= vueClasse.getLayoutY() && randY <= vueClasse.getBoundsInParent().getHeight()) {
                        randX = random.nextDouble();
                        randY = random.nextDouble();
                        i = 0;
                    }else {
                        i++;
                    }
                }
            }
            vueElement.setLayoutX(randX);
            vueElement.setLayoutY(randY);

            for (Attribut attribut : classe.getAttributs()) {
                fabriqueDeVue = new FabriqueVueAttribut();

                VueElementClasse vueAttribut = (VueElementClasse) fabriqueDeVue.creerVueElement();

                vueAttribut.setAccessibility(attribut.getVisibilite());
                vueAttribut.setType(attribut.getType());
                vueAttribut.setName(attribut.getNom());

                vueElement.setAttribut(vueAttribut);
            }

            vueElement.ajouterSeparateur();
            if (sujet.getClasseCourante() != null) {
                vueElement.imageAdd("AjouterMethode");
            }

            for (Methode methode : classe.getMethodes()) {
                fabriqueDeVue = new FabriqueVueMethode();

                VueElementClasse vueMethode = (VueElementClasse) fabriqueDeVue.creerVueElement();

                vueMethode.setAccessibility(methode.getVisibilite());
                vueMethode.setType(methode.getType());
                vueMethode.setName(methode.getNom());
                vueMethode.setParameters(methode.getParametres());

                vueElement.setMethode(vueMethode);
            }

            for (Association association : classe.getAssociations()) {

            }

            for (Implementation implementation : classe.getImplementations()) {

            }
            classeVue.getChildren().add(vueElement);
            classesList.add(vueElement);
        }
        this.getChildren().add(classeVue);
    }

    public ArrayList<VueClasse> getClassesList() {
        return classesList;
    }

    public WritableImage exportImage()
    {
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(3, 3));
        return this.snapshot(spa, null);
    }
}
