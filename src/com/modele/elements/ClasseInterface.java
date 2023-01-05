package com.modele.elements;

import java.util.ArrayList;
import java.util.List;

public class ClasseInterface implements Element
{
    private String nom;
    private List<Attribut> attributs;
    private List<Methode> methodes;
    private List<Association> associations;
    private Heritage heritage;
    private List<Implementation> implementation;
    private boolean selectionne, modification, isInterface;

    public ClasseInterface(String nom) {
        this.nom = nom;
        this.attributs = new ArrayList<>();
        this.methodes = new ArrayList<>();
        this.associations = new ArrayList<>();
        this.implementation = new ArrayList<>();
        this.heritage = null;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Attribut> getAttributs() {
        return attributs;
    }

    public List<Methode> getMethodes() {
        return methodes;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public List<Implementation> getImplementations() {
        return implementation;
    }

    public Heritage getHeritage() {
        return heritage;
    }

    public void ajouterAttribut(String attribut) {
        String accessibilite = attribut.split(" ")[0];
        String type = attribut.split(" ")[1];
        String nom = attribut.split(" ")[2];
        this.attributs.add(new Attribut(nom, type, accessibilite));
    }

    public void ajouterMethode(String methode) {
        String accessibilite = methode.split(" ")[0];
        String type = methode.split(" ")[1];
        String nom = methode.split(" ")[2];
        List<Attribut> parametres = new ArrayList<>();
        String attributs = methode.split("\\(")[1].split("\\)")[0];
        this.methodes.add(new Methode(nom, type, accessibilite,parametres));
    }

    public void ajouterAssociation(Association association) {
        this.associations.add(association);
    }

    public void ajouterImplementation(String implementation) {
        //this.implementation.add(implementation);
    }

    public void ajouterHeritage(String heritage) {
        //this.heritage = heritage;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public void modifierAttribut(Attribut attribut, String visibilite, String type, String nom)
    {
        attribut.modifier(visibilite, type, nom);
    }

    public void modifierMethode(Methode methode, String visibilite, String type, String nom, List<Attribut> parametres)
    {
        methode.modifier(visibilite, type, nom, parametres);
    }

    public void modifierHeritage(Heritage heritage, ClasseInterface initClasse, ClasseInterface destinationClasse)
    {
        heritage.modifier(initClasse, destinationClasse);
    }

    public void modifierAssociation(Association association, String visibilite, String nom, ClasseInterface initClasse, ClasseInterface destinationClasse)
    {
        association.modifier(visibilite, nom, initClasse, destinationClasse);
    }
}
