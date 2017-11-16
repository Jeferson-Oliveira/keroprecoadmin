/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.controllers;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import keroprecoadmin.AplicacaoUtil;
import keroprecoadmin.controllers.abstrato.Controller;
import keroprecoadmin.dao.ProdutoSupermercadoDAO;
import keroprecoadmin.dto.DtoProduto;
import keroprecoadmin.dto.DtoProdutoSupermercado;

/**
 * FXML Controller class
 *
 * @author Jeferson
 */
public class ListaPrecosFXMLController extends Controller implements Initializable {
   
    @FXML
    private Label lblNomeUsuario;

    @FXML
    private TableView<DtoProdutoSupermercado> tbPrecos;
    
    private ProdutoSupermercadoDAO produtoSupermercadoDAO;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.produtoSupermercadoDAO = new ProdutoSupermercadoDAO();
        this.lblNomeUsuario.setText("Olá, ".concat(AplicacaoUtil.getInstancia().getUsuarioLogado().getNome()));
        configurarTabela();
        tbPrecos.setItems(obterPrecosDaSemana());
        // TODO
    }    

    private void configurarTabela() {
        tbPrecos.setEditable(false);
                
      // Adicionando Colunas na tabela de produtos
        TableColumn<DtoProdutoSupermercado , String > colunaNome = new TableColumn("Nome");
        colunaNome.setMinWidth(100);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaNome.setCellFactory(TextFieldTableCell.forTableColumn());
     
        
        TableColumn<DtoProdutoSupermercado , String > colunaSupermercado = new TableColumn("Supermercado");
        colunaSupermercado.setMinWidth(100);
        colunaSupermercado.setCellValueFactory(new PropertyValueFactory<>("nomeSupermercado"));
        colunaSupermercado.setCellFactory(TextFieldTableCell.forTableColumn());
     
        TableColumn<DtoProdutoSupermercado , Double > colunaPreco = new TableColumn("Preço");
        colunaPreco.setMinWidth(50);
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        
        
        TableColumn<DtoProdutoSupermercado , String > colunaValidade = new TableColumn("Validade");
        colunaValidade.setMinWidth(100);
        colunaValidade.setCellValueFactory(new PropertyValueFactory<>("dataValidadeFormatada"));
        colunaValidade.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn<DtoProdutoSupermercado , String > colunaLocalizacao = new TableColumn("Localização");
        colunaLocalizacao.setMinWidth(100);
        colunaLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacaoSupermercado"));
        colunaLocalizacao.setCellFactory(TextFieldTableCell.forTableColumn());
        
        tbPrecos.getColumns().addAll(colunaNome , colunaSupermercado,colunaLocalizacao, colunaPreco, colunaValidade);
        
       

    }

    private ObservableList<DtoProdutoSupermercado> obterPrecosDaSemana() {
         ObservableList<DtoProdutoSupermercado> produtos = FXCollections.observableArrayList();
         Calendar dataDataHoje = Calendar.getInstance();
         dataDataHoje.add(Calendar.DAY_OF_YEAR, 7);
         produtos.addAll(this.produtoSupermercadoDAO.listarPrecosPorPeriodo(new Date(),dataDataHoje.getTime()));
           return produtos;    
    }
    
}
