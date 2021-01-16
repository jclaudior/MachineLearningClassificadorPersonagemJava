package interfacegrafica;

import opencv.ExtratorImagem;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Preditor extends JFrame {
    private  Instances instancias;
    private double camisetaLaranjaBart;
    private double calcaAzulBart;
    private double sapatoAzulBart;
    private double calcaAzulHomer;
    private double bocaMarromHomer;
    private double sapatoCinzaHomer;
    private JButton selecionar;
    private JTextField t1;
    private JLabel lblImagem;
    private JFileChooser fc;
    private JButton btnExtrairCaracteristicas;
    private JLabel lblCaracteristicaHomer;
    private JLabel lblAzulCalcaHomer;
    private JLabel lblMarromBocaHomer;
    private JLabel lblSapatoCinzaHomer;
    private JLabel lblCaracteristicaBart;
    private JLabel lblCamisaLaranjaBart;
    private JLabel lblAzulCalcaBart;
    private JLabel lblAzulSapato;
    private JButton btnClassificar;
    private JLabel lblNeive;
    private JLabel lblNeiveBart;
    private JLabel lblNeiveHomer;
    private JLabel lblNeiveBartValue;
    private JLabel lblNeiveHomerValue;

    public static void main(String[] args) {

        Preditor janela = new Preditor();

    }

    public Preditor (){

        this.setBounds(0,0,700,500);

        this.setLayout(null);
        selecionar = new JButton();
        selecionar.setBounds(5,5,120,30);
        selecionar.setText("Selecionar");
        this.add(selecionar);
        t1=new JTextField("");
        t1.setBounds(130,5, 550,30);
        t1.setVisible(true);
        this.add(t1);
        lblImagem = new JLabel("");
        lblImagem.setBorder(BorderFactory.createLineBorder(Color.black));
        lblImagem.setBounds(5, 40, 350, 400);
        this.add(lblImagem);
        selecionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc = new JFileChooser();
                int retorno = fc.showDialog(null, "Selecione a Imagem");
                if(retorno == JFileChooser.APPROVE_OPTION){
                    File arquivo = fc.getSelectedFile();
                    t1.setText(arquivo.getAbsolutePath());

                    BufferedImage imagemBmp = null;
                    try {
                        imagemBmp = ImageIO.read(arquivo);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    ImageIcon imageLabel = new ImageIcon(imagemBmp);
                    lblImagem.setIcon(new ImageIcon(imageLabel.getImage().getScaledInstance(lblImagem.getWidth(), lblImagem.getHeight(), Image.SCALE_DEFAULT)));

                }
            }

        });
        btnExtrairCaracteristicas  = new JButton();
        btnExtrairCaracteristicas.setBounds(360,40,200,30);
        btnExtrairCaracteristicas.setText("Extrair Caracteristicas");
        this.add(btnExtrairCaracteristicas);

        lblCaracteristicaHomer = new JLabel("Caracteristica Homer");
        lblCaracteristicaHomer.setBounds(360, 75, 150, 30);
        this.add(lblCaracteristicaHomer);
        lblAzulCalcaHomer = new JLabel("-");
        lblAzulCalcaHomer.setBounds(360, 110, 150, 30);
        this.add(lblAzulCalcaHomer);
        lblMarromBocaHomer = new JLabel("-");
        lblMarromBocaHomer.setBounds(360, 145, 150, 30);
        this.add(lblMarromBocaHomer);
        lblSapatoCinzaHomer = new JLabel("-");
        lblSapatoCinzaHomer.setBounds(360, 180, 150, 30);
        this.add(lblSapatoCinzaHomer);

        lblCaracteristicaBart = new JLabel("Caracteristica Bart");
        lblCaracteristicaBart.setBounds(515, 75, 150, 30);
        this.add(lblCaracteristicaBart);
        lblCamisaLaranjaBart = new JLabel("-");
        lblCamisaLaranjaBart.setBounds(515, 110, 150, 30);
        this.add(lblCamisaLaranjaBart);
        lblAzulCalcaBart = new JLabel("-");
        lblAzulCalcaBart.setBounds(515, 145, 150, 30);
        this.add(lblAzulCalcaBart);
        lblAzulSapato = new JLabel("-");
        lblAzulSapato.setBounds(515, 180, 150, 30);
        this.add(lblAzulSapato);

        btnExtrairCaracteristicas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExtratorImagem extratorImagem = new ExtratorImagem();
                float [] caracteristicas =  extratorImagem.estrairCaracteristicasImagem(t1.getText());
                lblCamisaLaranjaBart.setText(String.valueOf(caracteristicas[0]));
                camisetaLaranjaBart = caracteristicas[0];
                lblAzulCalcaBart.setText(String.valueOf(caracteristicas[1]));
                calcaAzulBart = caracteristicas[1];
                lblAzulSapato.setText(String.valueOf(caracteristicas[2]));
                sapatoAzulBart = caracteristicas[2];
                lblAzulCalcaHomer.setText(String.valueOf(caracteristicas[3]));
                calcaAzulHomer = caracteristicas[3];
                lblMarromBocaHomer.setText(String.valueOf(caracteristicas[4]));
                bocaMarromHomer = caracteristicas[4];
                lblSapatoCinzaHomer.setText(String.valueOf(caracteristicas[5]));
                sapatoCinzaHomer = caracteristicas[5];
            }

        });

        btnClassificar  = new JButton();
        btnClassificar.setBounds(565,40,100,30);
        btnClassificar.setText("Classificar");
        this.add(btnClassificar);
        btnClassificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    carregaWeka();
                    classificaNaiveBayes();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });
        lblNeive = new JLabel("Neive Bayes");
        lblNeive.setBounds(360, 215, 150, 30);
        this.add(lblNeive);
        lblNeiveBart = new JLabel("Bart:");
        lblNeiveBart.setBounds(360, 250, 50, 30);
        this.add(lblNeiveBart);
        lblNeiveHomer = new JLabel("Homer:");
        lblNeiveHomer.setBounds(360, 285, 50, 30);
        this.add(lblNeiveHomer);
        lblNeiveBartValue = new JLabel("-");
        lblNeiveBartValue.setBounds(415, 250, 50, 30);
        this.add(lblNeiveBartValue);
        lblNeiveHomerValue = new JLabel("-");
        lblNeiveHomerValue.setBounds(415, 285, 50, 30);
        this.add(lblNeiveHomerValue);



        this.setVisible(true);
    }

    public void  carregaWeka() throws Exception {
        ConverterUtils.DataSource ds = new ConverterUtils.DataSource("src\\opencv\\caracteristicas.arff");
        instancias =  ds.getDataSet();
        instancias.setClassIndex(instancias.numAttributes()-1);
        System.out.println(instancias.toString());
    }

    public void classificaNaiveBayes() throws Exception {
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(instancias);

        Instance novo = new DenseInstance(instancias.numAttributes());
        novo.setDataset(instancias);
        novo.setValue(0,camisetaLaranjaBart);
        novo.setValue(1,calcaAzulBart);
        novo.setValue(2,sapatoAzulBart);
        novo.setValue(3, bocaMarromHomer);
        novo.setValue(4,calcaAzulHomer);
        novo.setValue(5,sapatoCinzaHomer);

        double resultado [] = nb.distributionForInstance(novo);
        DecimalFormat df = new DecimalFormat("#,##0");
        String bart = df.format(resultado[0]);
        String homer = df.format(resultado[1]);
        System.out.println("Bart: " + resultado[0]);
        System.out.println("Homer: " + resultado[1]);
        System.out.println("Bart: " + bart);
        System.out.println("Homer: " + homer);
        lblNeiveBartValue.setText(bart);
        lblNeiveHomerValue.setText(homer);
    }


}
