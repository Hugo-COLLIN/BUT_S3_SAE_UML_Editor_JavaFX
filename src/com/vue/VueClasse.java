package com.vue;

import com.Main;
import com.controlleur.ControlleurAjouterClasse;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.Main.controlleurAjouterClasse;

public class VueClasse extends FlowPane implements ElementDeVue{

    protected final Label titleLabel;
    protected static VBox content;
    protected final ArrayList<VueElementClasse> attributs;
    protected final ArrayList<VueElementClasse> methodes;
    protected final List<ImageView> imageViews;

    private TextField saisiTitre = new TextField();
    private TextField saisiAccessibilite = new TextField();
    private TextField saisiType = new TextField();
    private Boolean etreMethode = false;

    public VueClasse() {
        super();
        this.setOnMousePressed(Main.controlleurGlisserDeposer);
        this.setOnMouseDragged(Main.controlleurGlisserDeposer);
        content = new VBox();
        titleLabel = new Label();
        this.attributs = new ArrayList<>();
        this.methodes = new ArrayList<>();
        this.setStyle("-fx-background-color: #ffc75a");
        int nbAttributs = attributs.size();
        int nbMethodes = methodes.size();
        etreMethode = false;
        this.setMaxSize(50 + 20 * (nbAttributs + nbMethodes), 50 + 20 * (nbAttributs + nbMethodes));
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        imageViews = new ArrayList<>();
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black;" +
                "-fx-border-radius: 5px;" +
                "-fx-background-radius: 5px;" +
                "-fx-background-color: #ffc75a;");

        this.getChildren().add(content);
    }

    public void setMinSize(double width, double height) {
        super.setMinSize(width, height);
    }

    public void setTitle(String title) {
        StackPane titlePane = new StackPane();
        this.titleLabel.setText(title);
        this.titleLabel.setStyle("-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: black;" +
                "-fx-padding: 0 0 0 0;");
        titlePane.getChildren().add(titleLabel);
        content.getChildren().add(titlePane);
    }

    public void setAttribut(VueElementClasse vueAttribut){
        this.attributs.add(vueAttribut);
        content.getChildren().add(vueAttribut);
    }

    public void setMethode(VueElementClasse vueElementClasse){
        this.methodes.add(vueElementClasse);
        content.getChildren().add(vueElementClasse);
    }

    public void setPos(double posX, double posY) {
        this.setLayoutX(posX);
        this.setLayoutY(posY);
    }

    public void ajouterSeparateur() {
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setHalignment(HPos.CENTER);
        Platform.runLater(() -> separator.setPrefWidth(this.getWidth()));
        separator.getStylesheets().add("separator.css");
        separator.setPadding(new javafx.geometry.Insets(1.5, 0, 1.5, 0));
        content.getChildren().add(separator);
    }

    public double getLargeur() {
        double[] largeur = new double[1];
        Platform.runLater(() -> largeur[0] = this.getWidth());
        return largeur[0];
    }

    public double getHauteur(){
        double[] hauteur = new double[1];
        Platform.runLater(() -> hauteur[0] = this.getHeight());
        return hauteur[0];
    }

    public void deplacer(double posX, double posY) {
        this.setLayoutX(posX);
        this.setLayoutY(posY);
    }

    public void imageAdd(String id) {
        // Creation d'un stackPane pour
        StackPane stackPaneImageView = new StackPane();
        ImageView imageView = new ImageView("add.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setId(id);
        stackPaneImageView.getChildren().add(imageView);
        stackPaneImageView.setAlignment(Pos.CENTER_RIGHT);
        imageViews.add(imageView);
        content.getChildren().add(stackPaneImageView);
    }

    public List<ImageView> getImageView() {
        return imageViews;
    }

    public void ajouterAttribut(String nom) {
        // Ajout des attributs
        HBox hbox = new HBox();
        // On clear tout les éléments de la hbox pour éviter les doublons
        hbox.getChildren().clear();
        // On crée un label pour l'accessibilité
        saisiAccessibilite = textfield("Accessibilité");
        // On crée un label pour le type
        saisiType = textfield("Type");
        // On ajoute les éléments dans la hbox
        hbox.getChildren().addAll(saisiAccessibilite, saisiType);
        // Positionnement des textfiels dans le HBox existant
        // On get le nombre d'attributs pour les ajouter en dessous
        int placement = attributs.size() + 3;
        // On teste si c'est une méthode
        if (etreMethode) {
            // Si oui on on get le nombre de méthodes et on ajoute 2 pour le séparateur et l'icon d'ajout
            placement += methodes.size() + 2;
        }
        // On ajoute à l'index specifié la hbox
        content.getChildren().add(placement, hbox);
        // On ajoute l'image d'ajout et un textfield pour le nom de l'attribut/méthode
        addNom(hbox,nom);
        // On met la hbox au centre
        hbox.setAlignment(Pos.CENTER);
    }

    public void ajouterMethode() {
        etreMethode = true;
        ajouterAttribut("Methode");
    }

    private TextField textfield(String nom) {
        // Creation d'un textfield pour l'ajouter dans la vue
        TextField textField = new TextField();
        textField.setFocusTraversable(false);

        textField.setPrefWidth(150);
        textField.setStyle("-fx-font-size: 15px;" +
                "-fx-border-radius: 5px;" +
                "-fx-background-radius: 5px;" +
                "-fx-label-padding: 0 0 10 0;" +
                "-fx-padding: 2 0 2 10;");
        textField.setPromptText(nom);
        return textField;
    }

    private ImageView image(String path) {
        // Ajout du bouton de validation avec une image personnalisée
        ImageView imageView = new ImageView(path);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setStyle("-fx-translate-x: -10");
        imageView.setId("validerTitre");
        return imageView;
    }

    public Boolean getEtreMethode() {
        if (etreMethode) {
            etreMethode = false;
            return true;
        }
        return false;
    }

    public TextField getSaisiTitre() {
        return saisiTitre;
    }

    public TextField getSaisiType() {
        return saisiType;
    }

    public TextField getSaisiAccessibilite() {
        return saisiAccessibilite;
    }

    private void addNom(HBox hBox,String nom) {
        // Ajout du titre
        hBox.setAlignment(Pos.CENTER);

        saisiTitre = textfield(nom);

        ImageView imageView = image("check.png");
        imageView.setId("check");
        imageView.setOnMouseClicked(controlleurAjouterClasse);


        // On ajoute tous les éléments aux la vue
        hBox.getChildren().addAll(saisiTitre,imageView);
    }

    public void ajouterTitre() {
        HBox box = new HBox();
        addNom(box,"Nom de la classe");
        content.getChildren().add(box);
    }

}
