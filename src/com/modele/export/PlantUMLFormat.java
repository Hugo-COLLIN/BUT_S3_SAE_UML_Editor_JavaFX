package com.modele.export;

import com.modele.Sujet;
import com.modele.composite.FileComposite;
import com.modele.elements.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PlantUMLFormat implements Format //extends Format
{
    private static PlantUMLFormat instance;

    private PlantUMLFormat()
    {

    }

    public void exporter(List<FileComposite> fichiers)
    {

    }

    public static Format getInstance() {
        if (instance == null)
            instance = new PlantUMLFormat();
        return instance;
    }

    public void exporter(Sujet modele, Stage stage)
    {
        String plantUML = genererPlantUML(modele);

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PlantUML files (*.plantuml)", "*.plantuml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            String fileName = file.getName();

            if (!fileName.toUpperCase().endsWith(".PLANTUML")) {
                file = new File(file.getAbsolutePath() + ".plantuml");
            }

            try {
                Files.writeString(Path.of(file.getAbsolutePath()), plantUML, StandardCharsets.UTF_8);
            }
            catch (IOException ex) {
                System.err.println("Chemin invalide");
            }
        }
        //--------------------------------------------------------------------------------
    }

    private String genererPlantUML(Sujet modele) {
        StringBuilder plantUMLBuilder = new StringBuilder("@startuml\n");
        List<ClasseInterface> classes = modele.getClasses();
        for (ClasseInterface classe : classes) {
            plantUMLBuilder.append("class ").append(classe.getNom()).append("{\n");
            for (Attribut attribut : classe.getAttributs()) {
                plantUMLBuilder.append("\t").append(getVisibiliteUml(attribut)).append(" ").append(attribut.getNom());
                setTypeUML(attribut, plantUMLBuilder);
            }
            for (Methode methode : classe.getMethodes()) {
                plantUMLBuilder.append("\t").append(getVisibiliteUml(methode)).append(" ").append(methode.getNom()).append("(");
                List<Attribut> parametres = methode.getParametres();
                for (Attribut parametre : parametres) {
                    plantUMLBuilder.append(parametre.getType()).append(parametre.getNom());
                    if (parametres.indexOf(parametre) != parametres.size() - 1)
                        plantUMLBuilder.append(",");
                }
                plantUMLBuilder.append(")");
                setTypeUML(methode, plantUMLBuilder);
            }
            plantUMLBuilder.append("}\n\n");


            // Le modele doit etre revu, pas le temps avant la fin du projet
            /*
            for (Association association : classe.getAssociations())
            {
                plantUMLBuilder.append("\t").append(association.getDestination().getType()).append("<--").append(association.getInitClasse()).append(" : ").append(association.getNom()).append("\n");
            }

            Heritage heritage = classe.getHeritage();
            if (heritage != null) plantUMLBuilder.append("\t").append(heritage.getDestinationClasse().getNom()).append(" <|-- ").append(heritage.getInitClasse().getNom()).append("\n\n");

            for (Implementation implementation : classe.getImplementations())
            {
                plantUMLBuilder.append("\t").append(implementation.getDestinationClasse().getNom()).append(" <|-- ").append(implementation.getInitClasse().getNom()).append("\n");
            }
            plantUMLBuilder.append("\n");
             */
        }
        plantUMLBuilder.append("@enduml");
        return plantUMLBuilder.toString();
    }

    private void setTypeUML(ElementVisibilite element, StringBuilder plantUMLBuilder) {
        if (element.getType() != null && !element.getType().isEmpty())
            plantUMLBuilder.append(" : ").append(element.getType()).append("\n");
        else plantUMLBuilder.append("\n");
    }


    public static String getVisibiliteUml(ElementVisibilite e) {
        return switch (e.getVisibilite()) {
            case "public" -> "+";
            case "private" -> "-";
            case "protected" -> "#";
            case "package private" -> "~";
            default -> "";
        };
    }
}
