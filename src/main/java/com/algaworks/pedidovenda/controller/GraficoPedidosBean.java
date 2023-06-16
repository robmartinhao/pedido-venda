package com.algaworks.pedidovenda.controller;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class GraficoPedidosBean {

    private LineChartModel model;

    public void preRender() {

        this.model = new LineChartModel();

        adicionarSerie("Todos os pedidos");
        adicionarSerie("Meus pedidos");
    }

    private void adicionarSerie(String rotulo) {
        ChartSeries series = new ChartSeries(rotulo);

        series.set("1", Math.random() * 1000);
        series.set("2", Math.random() * 1000);
        series.set("3", Math.random() * 1000);
        series.set("4", Math.random() * 1000);
        series.set("5", Math.random() * 1000);

        this.model.addSeries(series);
    }

    public LineChartModel getModel() {
        return model;
    }
}
