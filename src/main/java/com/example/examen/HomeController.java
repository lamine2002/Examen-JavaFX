package com.example.examen;

import com.example.examen.lib.Product;
import com.example.examen.repository.ProductRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    ProductRepository productRepository;

    @FXML
    private PieChart circleChart;

    @FXML
    private Label circleChartLabel;

    @FXML
    private StackedBarChart<?, ?> diagramChart;

    @FXML
    private Label diagramChartLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productRepository = new ProductRepository();
        ObservableList<Product> products = productRepository.getAll();
        //  charts sous forme de cercle qui représente le nombre de présence de produit par catégorie
        for (Product product : products) {
            circleChart.getData().add(new PieChart.Data(product.getCategorie(), product.getQuantite()));
        }

        //  charts sous forme de diagramme qui représente le nombre de présence de produit par catégorie
        XYChart.Series series = new XYChart.Series();
        for (Product product : products) {
            series.getData().add(new XYChart.Data(product.getCategorie(), product.getQuantite()));
        }
        diagramChart.getData().add(series);
    }
}
